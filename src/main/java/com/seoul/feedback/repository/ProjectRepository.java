package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    //List<Project> findAllByLogin(String login);
}
