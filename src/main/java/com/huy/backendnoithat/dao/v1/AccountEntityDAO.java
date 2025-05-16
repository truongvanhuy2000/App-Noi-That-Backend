package com.huy.backendnoithat.dao.v1;

import com.huy.backendnoithat.entity.Account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountEntityDAO extends JpaRepository<AccountEntity, Integer>, PagingAndSortingRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByUsername(String username);
}
