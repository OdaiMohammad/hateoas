package com.mainfraim.hateoas.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * A task entity. Model's a task's attributes.
 */
@Entity
@Data
public class Task {

    /**
     * Task ID.
     */
    @Id
    @GeneratedValue
    private final Long id;

    /**
     * Task name.
     * The tasks's name is required and must at least be 5 characters long.
     */
    @NotNull
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
    @NotNull
    @NotBlank
    @FutureOrPresent
    private Date startDate;

    /**
     * Task end date.
     * A task's end date must not be null or blank, and must be a future date.
     */
    @Column(name = "end_date")
    @NotNull
    @NotBlank
    @Future
    private Date endDate;

    @Column(name = "created_date")
    @CreatedDate
    @NotNull
    private Date createdDate;
}
