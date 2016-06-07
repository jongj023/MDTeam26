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

    public static final int USERNAME_NOT_FOUND = 404;
    public static final int USER_HAS_LOCKER = 500;
    public static final int SUCCESS = 200;

    public Iterable<LockerEntity> findAll() {
        return lockerRepository.findAll();
    }

    public LockerEntity findLockerById(long id) {return lockerRepository.findOne(id);}

    public int setUser(Long id, String username) {
        UserEntity user;
        if (username.isEmpty()) {
            user = null;
        } else {
            user = userRepository.findByUsername(username);
            if (user == null) return USERNAME_NOT_FOUND;
        }

        if (user != null) {
            Iterable<LockerEntity> users = lockerRepository.getLockerWithUsername(username);
            if (users.iterator().hasNext()) return USER_HAS_LOCKER;
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

        return SUCCESS;
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

    public void setComment(String comment, Long id){
        LockerEntity locker = lockerRepository.findOne(id);
            locker.setComment(comment);
    }

    public Iterable<LockerEntity> checkExistingLocker(String tower, int floor, String number) {
        return lockerRepository.checkExistingLocker(tower, floor, number);
    }

    public void save(LockerEntity locker) {
        lockerRepository.save(locker);
        lockerHistoryService.logLockerAdded(lockerRepository.checkExistingLocker(locker.getLockerTower(),
                locker.getLockerFloor(), locker.getLockerNumber()).iterator().next());
    }

    public void edit(LockerEntity locker) {
        lockerRepository.save(locker);
    }

    public Integer getOverdueAmount() {return lockerRepository.getOverdueAmount();}

    public Iterable<LockerEntity> getExpirationLockers() {return lockerRepository.getExpirationLockers();}

    public Iterable<LockerEntity> search(String query) {
        return lockerRepository.searchLockers("%" + query + "%");
    }
}
