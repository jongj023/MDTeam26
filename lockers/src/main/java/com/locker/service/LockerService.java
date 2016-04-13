package com.locker.service;

import com.locker.dao.LockerRepository;
import com.locker.dao.UserRepository;
import com.locker.model.Locker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by randyr on 3/30/16.
 */

@Service
@Transactional
public class LockerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LockerRepository lockerRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

//    public boolean registerNewUser(User user) {
//        if (usernameExists(user.getUsername())) {
//            logger.error("Username was already found in database: " + user.getUsername());
//            return false;
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setEnabled(1);
//        userRepository.save(user);
//        UserRole userRole = new UserRole(user.getUsername(), "USER");
//        userRoleRepository.save(userRole);
//        return true;
//    }

    public Iterable<Locker> findAll() {
        return lockerRepository.findAll();
    }
}
