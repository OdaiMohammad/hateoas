/*
 * MIT License
 *
 * Copyright (c) 2020 Odai Mohammad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.cloud_native.hateoas.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project {

    /**
     * Project ID.
     */
    @Id
    @GeneratedValue
    private Long id;

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
    @JoinColumn(name = "project_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Task> tasks;

    /**
     * Project creation date.
     */
    @Column(name = "created_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
