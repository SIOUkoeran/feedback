package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
