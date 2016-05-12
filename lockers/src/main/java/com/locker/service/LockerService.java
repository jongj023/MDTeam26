package com.locker.service;

import com.locker.dao.LockerRepository;
import com.locker.dao.UserRepository;
import com.locker.model.LockerEntity;
import com.locker.model.LockerHistoryEntity;
import com.locker.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    @Autowired
    private LockerHistoryService lockerHistoryService;

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
            if (user == null) return;
        }

        if (user != null) {
            String[] users = lockerRepository.getUsersWithLocker();
            for (String userCheck : users) {
                if (user.equals(userCheck)) {
                    return; //TODO Error handling (user is already assigned to a locker).
                }
            }
        }
        LockerEntity locker = lockerRepository.findOne(id);
        locker.setUser(user);
        locker.setTimestamp(new Timestamp(new java.util.Date().getTime()));

        if (user == null) {
            lockerHistoryService.logRemoved(locker);
            if (locker.getDate() != null) {
                lockerHistoryService.logExpirationCleared(locker);
            }
            locker.setDate(null);
        } else {
            lockerHistoryService.logAssigned(locker);
        }

        lockerRepository.save(locker);
    }

    public String[] getUsersWithLocker() {
        return lockerRepository.getUsersWithLocker();
    }

    public void setExpirationDate(String dateText, Long id) {
        LockerEntity locker = lockerRepository.findOne(id);
        if (locker.getUser() == null) return;
        if (dateText.isEmpty()) {
            locker.setDate(null);
            lockerHistoryService.logExpirationCleared(locker);
        } else {
            java.sql.Date oldDate = locker.getDate();
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date date;
            try {
                date = df.parse(dateText);
                locker.setDate(new java.sql.Date(date.getTime()));
            } catch (ParseException p) {
                logger.error(p.toString());
                return;
            }
            //Logging changes
            if (oldDate != null) {
                lockerHistoryService.logExpirationEdited(locker, oldDate);
            } else {
                lockerHistoryService.logExpiration(locker);
            }

        }

        lockerRepository.save(locker);
    }

    public void editLocker(Long id, String lockerTower, int lockerFloor, int lockerNumber) {
        LockerEntity locker = lockerRepository.findOne(id);
        if (lockerNumber < 0 || lockerNumber > 100) {return;}
        LockerEntity oldLocker = locker; //logging purposes.
        locker.setLockerTower(lockerTower);
        locker.setLockerNumber(lockerNumber + "");
        locker.setLockerFloor(lockerFloor);
        lockerHistoryService.logLockerEdited(locker, oldLocker);
        lockerRepository.save(locker);
    }
}
