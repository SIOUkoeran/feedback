package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByProjectIdAndAppraisedUserIdAndEvalUserId(Long projectId, Long appraisedUserId, Long EvalUserId);
}
