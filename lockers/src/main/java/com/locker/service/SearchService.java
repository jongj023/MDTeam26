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

    public String searchLocker(int floor, char tower){
        Iterable<LockerEntity> lockers = lockerRepository.getFreeLockers();
        for(LockerEntity locker : lockers){
            String varTower = Character.toString(tower);
            String varFloor = Integer.toString(floor);

            if(floor == 0){
                varFloor = locker.getLockerFloor() + "";
            }

            if(tower == 'X'){
                varTower = locker.getLockerTower();
            }

            if(locker.getLockerTower().equals(varTower) && Integer.toString(locker.getLockerFloor()).equals(varFloor)){
                return varTower + varFloor + locker.getLockerNumber();
            }

        }
        return "No locker found, please try again";

    }
}
