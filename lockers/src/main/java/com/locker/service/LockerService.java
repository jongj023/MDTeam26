package com.locker.service;

import com.locker.dao.LockerRepository;
import com.locker.dao.UserRepository;
import com.locker.model.LockerEntity;
import com.locker.model.UserEntity;
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

    public Iterable<LockerEntity> findAll() {
        return lockerRepository.findAll();
    }

    public LockerEntity findLockerById(long id) {return lockerRepository.findOne(id);}

    public void setUser(Long id, String username) {
        UserEntity user;
        if (username.isEmpty()) {
            user = null;
        } else {
            user = userRepository.findByUsername(username);
        }
        LockerEntity locker = lockerRepository.findOne(id);
        locker.setUser(user);
        lockerRepository.save(locker);
    }

}
