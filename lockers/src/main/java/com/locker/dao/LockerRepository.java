package com.locker.dao;

import com.locker.model.LockerEntity;
import com.locker.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by randyr on 3/30/16.
 * */

@Repository
public interface LockerRepository extends CrudRepository<LockerEntity, Long> {

    @Query(value = "SELECT user FROM locker l WHERE l.user IS NOT NULL;", nativeQuery = true)
    String[] getUsersWithLocker();

    @Query(value = "SELECT * FROM locker l WHERE l.user IS NULL", nativeQuery = true)
    Iterable<LockerEntity> getFreeLockers();

    @Query(value = "SELECT * FROM locker l WHERE l.locker_tower=:tower AND l.locker_floor=:floor AND l.locker_number=:number", nativeQuery = true)
    Iterable<LockerEntity> checkExistingLocker(@Param("tower") String tower, @Param("floor") int floor, @Param("number") String number);

    @Query(value = "SELECT COUNT(*) FROM locker l WHERE l.date_expired < DATE(NOW());", nativeQuery = true)
    Integer getOverdueAmount();

    @Query(value = "SELECT * FROM locker l WHERE l.date_expired IS NOT NULL ORDER BY date_expired;", nativeQuery = true)
    Iterable<LockerEntity> getExpirationLockers();
}

