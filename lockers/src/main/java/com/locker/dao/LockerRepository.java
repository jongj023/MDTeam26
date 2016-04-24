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

}
