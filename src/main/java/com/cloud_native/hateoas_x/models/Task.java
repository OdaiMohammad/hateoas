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

package com.cloud_native.hateoas_x.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * A task entity. Model's a task's attributes.
 */
@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task {

    /**
     * Task ID.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Task name.
     * The tasks's name is required and must at least be 5 characters long.
     */
    @NonNull
    @Size(min = 5)
    private String name;

    /**
     * Task description.
     */
    private String description;

    /**
     * Task start date.
     * A task's start date must not be null or blank, and must be a future or present date.
     */
    @Column(name = "start_date")
    @FutureOrPresent
    private Date startDate;

    /**
     * Task end date.
     * A task's end date must not be null or blank, and must be a future date.
     */
    @Column(name = "end_date")
    @Future
    private Date endDate;

    /**
     * Project tasks.
     * Each project has many tasks that are unique.
     * When a project is delete, its tasks are also deleted.
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @NonNull
    private Project project;

    /**
     * Task creation date.
     */
    @Column(name = "created_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
