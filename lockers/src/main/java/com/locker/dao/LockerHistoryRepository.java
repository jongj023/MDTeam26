package com.locker.dao;

import com.locker.model.LockerHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by randyr on 5/10/16.
 */
@Repository
public interface LockerHistoryRepository extends CrudRepository<LockerHistoryEntity, Long> {

    @Query(value = "SELECT * FROM lockerhistory WHERE locker=:id ORDER BY date_updated DESC LIMIT :limit", nativeQuery = true)
    Iterable<LockerHistoryEntity> findAllWithIdLimit(@Param("id") Long id, @Param("limit") int limit);

    @Query(value = "SELECT * FROM lockerhistory ORDER BY date_updated DESC LIMIT :limit", nativeQuery = true)
    Iterable<LockerHistoryEntity> findAllLimit(@Param("limit") int limit);

    @Query(value = "SELECT * FROM lockerhistory ORDER BY date_updated DESC", nativeQuery = true)
    Iterable<LockerHistoryEntity> findAllSorted();
}
