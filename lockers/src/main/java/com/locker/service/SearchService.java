package com.locker.service;

import com.locker.dao.LockerRepository;
import com.locker.model.LockerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by User on 6-5-2016.
 */
@Service
@Transactional
public class SearchService {

    @Autowired
    private LockerRepository lockerRepository;

    public Iterable<LockerEntity> findAll() {
        return lockerRepository.findAll();
    }

    public LockerEntity findLockerById(long id) {return lockerRepository.findOne(id);}

    public String searchLocker(String floor, String tower){
        Iterable<LockerEntity> lockers = lockerRepository.getFreeLockers();
        for(LockerEntity locker : lockers){

            if(floor.equals("0")){
                floor = locker.getLockerFloor() + "";
            }

            if(tower.equals("X")){
                tower = locker.getLockerTower();
            }

            if(locker.getLockerTower().equals(tower) && Integer.toString(locker.getLockerFloor()).equals(floor)){
                return tower + floor + locker.getLockerNumber();
            }

        }
        return null;

    }
}
