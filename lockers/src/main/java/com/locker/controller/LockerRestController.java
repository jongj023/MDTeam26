package com.locker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locker.jsonview.Views;
import com.locker.model.*;
import com.locker.service.LockerHistoryService;
import com.locker.service.LockerService;
import com.locker.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by randyr on 5/15/16.
 */

@RestController
public class LockerRestController {

    @Autowired
    private LockerService lockerService;
    @Autowired
    private LockerHistoryService history;
    @Autowired
    private TicketService ticketService;

    private final String TICKET_REQUEST_TITLE = "Locker claimed";
    private final String TICKET_REQUEST_CONTENT = " has claimed locker on"; //user, claimed locker, date.

    @RequestMapping(value = "/getlockers", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<LockerEntity>> getLockers() {
        Iterable<LockerEntity> lockers = lockerService.findAllSorted();
        return new ResponseEntity<Iterable<LockerEntity>>(lockers, HttpStatus.OK);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/addlocker")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody addLocker(@RequestBody LockerEntity locker) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidLocker(locker)) {
            Iterable<LockerEntity> existingLockers = lockerService.checkExistingLocker(locker.getLockerTower(), locker.getLockerFloor(), locker.getLockerNumber());
            if (existingLockers.iterator().hasNext()) {
                result.setCode("204");
                result.setMessage("Locker with these properties already exist.");
            } else {
                lockerService.save(locker);
                result.setCode("200");
                result.setMessage("");
            }
        } else {
            result.setCode("400");
            result.setMessage("Given locker properties are not correct.");
        }

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/editlocker")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody editLocker(@RequestBody LockerEntity locker) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidLocker(locker)) {
            Iterable<LockerEntity> existingLockers = lockerService.checkExistingLocker(locker.getLockerTower(), locker.getLockerFloor(), locker.getLockerNumber());

            if (existingLockers.iterator().hasNext()) {
                result.setCode("204");
                result.setMessage("Locker with these properties already exist.");
            } else {
                LockerEntity currentLocker = lockerService.findLockerById(locker.getLockerid()); //Get currently-edited locker
                LockerEntity oldLocker = new LockerEntity(currentLocker.getLockerFloor(), currentLocker.getLockerNumber(), currentLocker.getLockerTower());

                //Change current values.
                currentLocker.setLockerTower(locker.getLockerTower());
                currentLocker.setLockerFloor(locker.getLockerFloor());
                currentLocker.setLockerNumber(locker.getLockerNumber());

                history.logLockerEdited(currentLocker, oldLocker);
                lockerService.edit(currentLocker);

                result.setCode("200");
                result.setMessage("");
            }
        } else {
            result.setCode("400");
            result.setMessage("Given locker properties are not correct.");
        }


        return result;
    }

    private boolean isValidLocker(LockerEntity locker) {
        boolean valid = true;

        if (locker == null) return false;
        //LockerFloor is already taken care of by @Min(0) constraint. If floor < 0, ajax request will return error with result == null.
        if (locker.getLockerTower().isEmpty() || locker.getLockerNumber().isEmpty() || Integer.parseInt(locker.getLockerNumber()) >= 100 || locker.getLockerFloor() > 5) {
            valid = false;
        }

        return valid;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/gethistory", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public AjaxResponseBody<String> getHistory(@RequestBody HistoryLimit limit) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();
        Iterable<LockerHistoryEntity> lockerHistory;

        if (limit.getLimit() >= 0) {
            lockerHistory = history.findAllLimit(limit.getLimit());
        } else {
            lockerHistory = history.findAllSorted();
        }

        //Convert the list of history to JSON format.
        ObjectMapper mapper = new ObjectMapper();
        String JsonResult = "Could not JSON-ify history data.";
        try {
            JsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lockerHistory);
            result.setCode("200");
            result.setMessage("Lockers with limit " + limit.getLimit() + " returned.");
            result.setResult(JsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setCode("204");
            result.setMessage("Failed to get locker history.");
            result.setResult(JsonResult);
        }

        return result;
    }

    @RequestMapping(value = "/getoverdueamount", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Integer> getOverdueAmount() {
        Integer amount = lockerService.getOverdueAmount();
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

    @RequestMapping(value="/getexpirationlockers", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<LockerEntity>> getExpirationLockers() {
        Iterable<LockerEntity> lockers = lockerService.getExpirationLockers();
        return new ResponseEntity<Iterable<LockerEntity>>(lockers, HttpStatus.OK);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public AjaxResponseBody<String> searchFreeLocker(@RequestBody SearchQuery query) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        if (isValidSearchQuery(query)) {
            Iterable<LockerEntity> lockers = lockerService.searchLocker(query.getSearchFloor(), query.getSearchTower());

            if (!lockers.iterator().hasNext()) {
                result.setCode("204");
                result.setMessage("No empty locker found with requested location.");
            } else {
                String json = "";
                try {
                    json = createJsonFromLockerEntity(lockers);
                    result.setCode("200");
                    result.setMessage("Successfully found lockers");
                    result.setResult(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    result.setCode("204");
                    result.setMessage("Failed to return locker results. Please try again.");
                    result.setResult(json);
                }
            }
        } else {
            result.setCode("400");
            result.setMessage("Submitted fields did not meet criteria.");
        }

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/locker/search", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public AjaxResponseBody<String> search(@RequestBody String query) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();
        System.out.println(query);
        Iterable<LockerEntity> lockers = lockerService.search(query);

        String json = "";
        try {
            json = createJsonFromLockerEntity(lockers);
            result.setCode("200");
            result.setMessage("Successfully found lockers");
            result.setResult(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setCode("204");
            result.setMessage("Failed to get locker history.");
            result.setResult(json);
        }

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/locker/setuser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public AjaxResponseBody<String> setUser(@RequestBody Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        LockerEntity locker = lockerService.findLockerById(id);
        if (locker.getUser() != null) {
            result.setCode("204");
            result.setMessage("Locker has already been claimed.");
        } else {
            if (name != null) {
                int response = lockerService.setUser(id, name);
                switch (response) {
                    case LockerService.SUCCESS:
                        result.setMessage("Changes successfully saved");
                        result.setCode("200");
                        setTicketLockerRequest(id, name);
                        break;
                    case LockerService.USER_HAS_LOCKER:
                        result.setMessage("You are already assigned to a locker");
                        result.setCode("204");
                        break;
                    case LockerService.USERNAME_NOT_FOUND:
                        result.setMessage("User was not found.");
                        result.setCode("404");
                        break;
                    case LockerService.LOCKER_ALREADY_CLAIMED:
                        result.setMessage("Locker is already claimed");
                        result.setCode("204");
                        break;
                    default:
                        result.setCode("500");
                        result.setMessage("Something went wrong, please try again.");
                        break;
                }
            } else {
                result.setCode("400");
                result.setMessage("Cannot assign locker, user not logged in.");
            }
        }

        return result;
    }

    /* id is equal to the lockerid (used for foreign key @ ticket) */
    private void setTicketLockerRequest(Long id, String username) {
        String content = username + TICKET_REQUEST_CONTENT + " " +
                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Timestamp(new java.util.Date().getTime()));
        LockerEntity locker = lockerService.findLockerById(id);
        ticketService.save(locker, TICKET_REQUEST_TITLE, content);
    }

    private boolean isValidSearchQuery(SearchQuery query) {
        boolean valid = true;

        if (query == null) return false;

        String tower = query.getSearchTower();
        String floor = query.getSearchFloor();

        if (tower == null || tower.isEmpty() || floor == null || floor.isEmpty()) {
            valid = false;
        }

        return valid;
    }

    private String createJsonFromLockerEntity(Iterable<LockerEntity> array) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String JsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(array);

        return JsonResult;
    }

}
