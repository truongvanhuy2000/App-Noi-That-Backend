package com.huy.backendnoithat.manager;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.model.FileSearchRequest;
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
public class SavedFileEntityManager {
    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public PagedList<SavedFileEntity> search(
        int page, int size,
        FileSearchRequest fileSearchRequest
    ) {
        var criteriaBuilder = criteriaBuilderFactory.create(entityManager, SavedFileEntity.class, "sfe")
            .leftJoinFetch("sfe.accountEntity", "acc");

        if (fileSearchRequest.getUserId() != null) {
            criteriaBuilder.where("acc.id").eq(fileSearchRequest.getUserId());
        }

        if (fileSearchRequest.getFileName() != null) {
            String fileName = fileSearchRequest.getFileName();
            criteriaBuilder.where("sfe.fileName").like().value(SqlUtils.createLikeExpression(fileName)).noEscape();
        }

        if (fileSearchRequest.getFileType() != null) {
            criteriaBuilder.where("sfe.fileType").eq(fileSearchRequest.getFileType());
        }

        if (fileSearchRequest.getUploadStatus() != null) {
            criteriaBuilder.where("sfe.uploadStatus").eq(fileSearchRequest.getUploadStatus());
        }

        int offset = page * size;
        return criteriaBuilder.page(offset, size).orderByAsc("id").getResultList();
    }
}
