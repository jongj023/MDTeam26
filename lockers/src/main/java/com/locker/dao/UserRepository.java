package com.locker.dao;

import com.locker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by randyr on 2/20/16.
 */

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByUsername(String username);
}
