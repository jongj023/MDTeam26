package com.locker.service;

import com.locker.dao.LockerRepository;
import com.locker.model.LockerEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by User on 6-5-2016.
 */
public class SearchService {


    @Autowired
    private LockerRepository lockerRepository;

    public Iterable<LockerEntity> findAll() {
        return lockerRepository.findAll();
    }

    public LockerEntity findLockerById(long id) {return lockerRepository.findOne(id);}

    public void searchLocker(int floor, char tower){
        LockerEntity locker;

        String[] lockers = lockerRepository.getFreeLockers();
    }
}
