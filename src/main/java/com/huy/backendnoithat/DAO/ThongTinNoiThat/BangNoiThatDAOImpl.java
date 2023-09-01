package com.huy.backendnoithat.DAO.ThongTinNoiThat;

import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BangNoiThatDAOImpl implements BangNoiThatDAO {
    private EntityManager entityManager;
    @Autowired
    public BangNoiThatDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PhongCachNoiThatEntity> fetchAll() {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery("from PhongCachNoiThatEntity pc ORDER BY pc.id", PhongCachNoiThatEntity.class);
        EntityGraph<PhongCachNoiThatEntity> graph = entityManager.createEntityGraph(PhongCachNoiThatEntity.class);
        Subgraph<NoiThatEntity> node1 = graph.addSubgraph("noiThatEntity");
        Subgraph<HangMucEntity> node2 = node1.addSubgraph("hangMucEntity");
        node2.addSubgraph("vatLieuEntity").addAttributeNodes("thongSoEntity");

        query.setHint("javax.persistence.fetchgraph", graph);
        return query.getResultList();
     }
}
