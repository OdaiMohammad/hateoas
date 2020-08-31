package com.mainfraim.hateoas.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * A project entity. Models a project's attributes.
 */
@Entity
@Data
public class Project {

    /**
     * Project ID.
     */
    @Id
    @GeneratedValue
    private final Long id;

    /**
     * Project name.
     * The project's name is required and must at least be 5 characters long.
     */
    @NotNull
    @Size(min = 5)
    private String name;

    /**
     * Project description.
     */
    private String description;

    /**
     * Project tasks.
     * Each project has many tasks that are unique.
     * When a project is delete, its tasks are also deleted.
     */
    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Task> tasks;

    /**
     * Project creation date.
     */
    @Column(name = "created_date")
    @CreatedDate
    @NotNull
    private Date createdDate;
}
