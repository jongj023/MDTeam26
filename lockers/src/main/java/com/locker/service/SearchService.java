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
        String[] lockers = lockerRepository.getFreeLockers();
        for(String locker : lockers){

            String varLocker = locker;
            int index = varLocker.lastIndexOf(",");
            varLocker = varLocker.substring(0, index);
            String varTower = Character.toString(Character.toLowerCase(tower));
            String varFloor = Integer.toString(floor);


            if(floor == 0){
                char i = varLocker.charAt(2);
                varFloor = Character.toString(i);
            }

            if(tower == 'X'){
                char i = varLocker.charAt(0);
                varTower = Character.toString(i);
            }

            if(varLocker.equals(varTower + "," + varFloor)) {
                return locker.replace(",", "");
            }
        }
        return "No locker found, please try again";
    }
}
