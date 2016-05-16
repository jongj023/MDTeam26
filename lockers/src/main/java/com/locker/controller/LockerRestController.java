package com.locker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.locker.jsonview.Views;
import com.locker.model.AjaxResponseBody;
import com.locker.model.LockerEntity;
import com.locker.service.LockerHistoryService;
import com.locker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by randyr on 5/15/16.
 */

@RestController
public class LockerRestController {

    @Autowired
    private LockerService lockerService;
    @Autowired
    private LockerHistoryService history;

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/addlocker")
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
    public AjaxResponseBody editLocker(@RequestBody LockerEntity locker) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidLocker(locker)) {
            Iterable<LockerEntity> existingLockers = lockerService.checkExistingLocker(locker.getLockerTower(), locker.getLockerFloor(), locker.getLockerNumber());

            if (existingLockers.iterator().hasNext()) {
                result.setCode("204");
                result.setMessage("Locker with these properties already exist.");
            } else {
                LockerEntity currentLocker = lockerService.findLockerById(locker.getLockerid()); //Get currently-edited locker
                LockerEntity oldLocker = currentLocker;

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

    /*public void editLocker(Long id, String lockerTower, int lockerFloor, int lockerNumber) {
        LockerEntity locker = lockerRepository.findOne(id);
        if (lockerNumber < 0 || lockerNumber > 100) {return;}
        LockerEntity oldLocker = locker; //logging purposes.
        locker.setLockerTower(lockerTower);
        locker.setLockerNumber(lockerNumber + "");
        locker.setLockerFloor(lockerFloor);
        lockerHistoryService.logLockerEdited(locker, oldLocker);
        lockerRepository.save(locker);
    }*/

    private boolean isValidLocker(LockerEntity locker) {
        boolean valid = true;

        if (locker == null) return false;
        //LockerFloor is already taken care of by @Min(0) constraint. If floor < 0, ajax request will return error with result == null.
        if (locker.getLockerTower().isEmpty() || locker.getLockerNumber().isEmpty() || Integer.parseInt(locker.getLockerNumber()) >= 100 || locker.getLockerFloor() > 5) {
            valid = false;
        }

        return valid;
    }

}