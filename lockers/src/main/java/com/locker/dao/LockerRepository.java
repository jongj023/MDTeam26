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

    @Query(value = "SELECT l.* FROM locker l WHERE l.locker_tower IN (:tower) AND l.locker_floor = :floor AND l.user IS NULL " +
            "ORDER BY l.locker_tower, l.locker_floor, l.locker_number", nativeQuery = true)
    Iterable<LockerEntity> findFreeWithCriteria(@Param("tower") String tower, @Param("floor") String floor);

    @Query(value = "SELECT l.* FROM locker l WHERE l.locker_tower IN ('A', 'B', 'C') AND l.locker_floor = :floor AND l.user IS NULL " +
            "ORDER BY l.locker_tower, l.locker_floor, l.locker_number", nativeQuery = true)
    Iterable<LockerEntity> findFreeWithCriteriaAllTowers(@Param("floor") String floor);

    @Query(value = "SELECT * FROM locker l WHERE l.locker_tower=:tower AND l.locker_floor=:floor AND l.locker_number=:number", nativeQuery = true)
    Iterable<LockerEntity> checkExistingLocker(@Param("tower") String tower, @Param("floor") int floor, @Param("number") String number);

    @Query(value = "SELECT COUNT(*) FROM locker l WHERE l.date_expired < DATE(NOW());", nativeQuery = true)
    Integer getOverdueAmount();

    @Query(value = "SELECT * FROM locker l WHERE l.date_expired IS NOT NULL ORDER BY date_expired;", nativeQuery = true)
    Iterable<LockerEntity> getExpirationLockers();

    @Query(value = "SELECT * FROM locker WHERE user = :user", nativeQuery = true)
    Iterable<LockerEntity> getLockerWithUsername(@Param("user") String username);

    @Query(
            value = "SELECT l.* FROM locker l LEFT OUTER JOIN user u ON l.user = u.username WHERE " +
                    "concat_ws('|',CONCAT(CONCAT(l.locker_tower, l.locker_floor), l.locker_number), l.date_acquired, " +
                    "l.date_expired, l.user, u.firstname, u.lastname, u.email) LIKE :query"
            , nativeQuery = true
    )
    Iterable<LockerEntity> searchLockers(@Param("query") String query);

    @Query(value = "SELECT * FROM locker l WHERE l.user = :username", nativeQuery = true)
    LockerEntity findLockerByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM locker l ORDER BY l.locker_tower, l.locker_floor, l.locker_number", nativeQuery = true)
    Iterable<LockerEntity> findAllSorted();

    @Query(value = "SELECT * FROM locker l WHERE l.user IS NULL ORDER BY l.locker_tower, l.locker_floor, l.locker_number", nativeQuery = true)
    Iterable<LockerEntity> getAllFreeLockers();

    @Query(value = "SELECT * FROM locker l WHERE l.user IS NOT NULL ORDER BY l.locker_tower, l.locker_floor, l.locker_number", nativeQuery = true)
    Iterable<LockerEntity> getAllClaimedLockers();
}

