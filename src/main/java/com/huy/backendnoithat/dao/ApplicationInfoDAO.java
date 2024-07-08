package com.huy.backendnoithat.dao;

import com.huy.backendnoithat.entity.ApplicationInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationInfoDAO extends CrudRepository<ApplicationInfoEntity, Integer> {
}
