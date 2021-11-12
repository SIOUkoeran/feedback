package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    List<Register> findByProjectId(Long projectId);

}
