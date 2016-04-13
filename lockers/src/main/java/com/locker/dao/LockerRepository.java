package com.locker.dao;

import com.locker.model.Locker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by randyr on 3/30/16.
 * */

@Repository
public interface LockerRepository extends CrudRepository<Locker, Long> {

}
