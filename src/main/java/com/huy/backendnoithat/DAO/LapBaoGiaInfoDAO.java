package com.huy.backendnoithat.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface LapBaoGiaInfoDAO extends JpaRepository<LapBaoGiaInfoDAO, Integer> {
}
