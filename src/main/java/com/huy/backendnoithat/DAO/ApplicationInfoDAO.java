package com.huy.backendnoithat.DAO;

import com.huy.backendnoithat.Entity.ApplicationInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationInfoDAO extends CrudRepository<ApplicationInfoEntity, Integer> {
}
