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

    @Query(value = "SELECT * FROM lockerhistory WHERE locker=:id ORDER BY date_updated DESC LIMIT 0, 100", nativeQuery = true)
    Iterable<LockerHistoryEntity> findAllLimit100(@Param("id") Long id);
}
