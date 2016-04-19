package com.locker.dao;

import com.locker.model.LockerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by randyr on 3/30/16.
 * */

@Repository
public interface LockerRepository extends CrudRepository<LockerEntity, Long> {
    public static final String GET_LOCKERS = "SELECT l.lockerid, l.locker_floor, l.locker_number, l.locker_tower, u.username FROM locker l JOIN user u ON l.user = u.username";

    @Query(value = GET_LOCKERS, nativeQuery = true)
    public Iterable<LockerEntity> getAllLockers();
}
