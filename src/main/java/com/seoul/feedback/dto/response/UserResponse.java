package com.seoul.feedback.dto.response;

import com.seoul.feedback.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class UserResponse {
    private Long userId;
    private String login;

    @Builder
    public UserResponse(User user){
        this.userId = user.getId();
        this.login = user.getLogin();
    }
    public UserResponse(String login, Long id){
        this.login = login;
        this.userId = id;
    }

    @Getter
    @Setter
    public static class Project{
        private Long appraisedUserId;
        private String login;
        private boolean feedback;

        public Project(Long userId, String login, boolean feedback) {
            this.appraisedUserId = userId;
            this.login = login;
            this.feedback = feedback;
        }
    }
    @Getter
    public static class ProjectWithUserId{
        private Long userId;
        public List<Project> appraisedUserFeedbackList;

        public ProjectWithUserId(Long userId,List<Project> appraisedUserFeedbackList){
            this.userId = userId;
            this.appraisedUserFeedbackList = appraisedUserFeedbackList;
        }
    }
}
