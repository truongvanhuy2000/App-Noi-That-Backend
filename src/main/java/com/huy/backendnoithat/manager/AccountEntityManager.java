package com.huy.backendnoithat.manager;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.model.UserSearchRequest;
import com.huy.backendnoithat.utils.SqlUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountEntityManager {
    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public PagedList<AccountEntity> search(
        int page, int size,
        UserSearchRequest userSearchRequest
    ) {
        var criteriaBuilder = criteriaBuilderFactory.create(entityManager, AccountEntity.class, "acc")
            .leftJoinFetch("acc.accountInformationEntity", "accInfo");
        if (userSearchRequest.getUsername() != null) {
            String username = userSearchRequest.getUsername();
            criteriaBuilder.where("acc.username").like().value(SqlUtils.createLikeExpression(username)).noEscape();
        }
        if (userSearchRequest.getEmail() != null) {
            String email = userSearchRequest.getEmail();
            criteriaBuilder.where("accInfo.email").like().value(SqlUtils.createLikeExpression(email)).noEscape();
        }
        if (userSearchRequest.getEnableStatus() != null) {
            boolean enableStatus = userSearchRequest.getEnableStatus();
            criteriaBuilder.where("acc.enabled").eq(enableStatus);
        }
        if (userSearchRequest.getActivationStatus() != null) {
            boolean activationStatus = userSearchRequest.getActivationStatus();
            criteriaBuilder.where("acc.active").eq(activationStatus);
        }
        int offset = page * size;
        return criteriaBuilder.page(offset, size).orderByAsc("acc.id").getResultList();
    }
}
