package com.seoul.feedback.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Register> registerList;


    @Builder
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //== 비즈니스 로직==//

}
