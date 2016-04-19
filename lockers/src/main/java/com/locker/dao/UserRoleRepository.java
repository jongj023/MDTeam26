package com.locker.dao;

import com.locker.model.UserRolesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by randyr on 2/20/16.
 */

@Repository
public interface UserRoleRepository extends CrudRepository<UserRolesEntity, Long> {

}
