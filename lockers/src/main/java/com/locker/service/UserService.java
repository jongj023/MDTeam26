package com.locker.service;

import com.locker.dao.UserRepository;
import com.locker.dao.UserRoleRepository;
import com.locker.model.User;
import com.locker.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by randyr on 2/19/16.
 */

@Service
@Transactional
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public boolean registerNewUser(User user) {
        if (usernameExists(user.getUsername())) {
            logger.error("Username was already found in database: " + user.getUsername());
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserRole userRole = new UserRole(user.getUsername(), "USER");
        userRoleRepository.save(userRole);
        return true;
    }

    public boolean usernameExists(String username) {
        List<User> result = userRepository.findByUsername(username);
        return !result.isEmpty();
    }
}
