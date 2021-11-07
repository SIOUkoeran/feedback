package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {

}
