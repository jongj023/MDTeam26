package com.locker.controller;

import com.locker.model.UserEntity;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by randyr on 22-4-16.
 */

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Used for getting list of users through javascript. (e.g. modal in locker.html)
     * @return list of users
     */
    @RequestMapping(value="/getusers", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<UserEntity>> getUsers() {
        Iterable<UserEntity> users = userService.findAll();
        return new ResponseEntity<Iterable<UserEntity>>(users, HttpStatus.OK);
    }
}
