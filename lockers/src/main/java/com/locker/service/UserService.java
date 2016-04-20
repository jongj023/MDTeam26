package com.locker.service;

import com.locker.dao.UserRepository;
import com.locker.dao.UserRoleRepository;
import com.locker.model.UserEntity;
import com.locker.model.UserRolesEntity;
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

    public boolean registerNewUser(UserEntity user) {
        if (usernameExists(user.getUsername())) {
            logger.error("Username was already found in database: " + user.getUsername());
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        userRepository.save(user);
        UserRolesEntity userRole = new UserRolesEntity(user, "USER");
        userRoleRepository.save(userRole);
        return true;
    }

    public boolean usernameExists(String username) {
        List<UserEntity> result = userRepository.findByUsername(username);
        return !result.isEmpty();
    }
}
