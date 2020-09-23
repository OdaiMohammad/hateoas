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

package com.cloud_native.hateoas_x.repository_test;

import com.cloud_native.hateoas_x.models.Project;
import com.cloud_native.hateoas_x.models.Task;
import com.cloud_native.hateoas_x.repositories.ProjectRepository;
import com.cloud_native.hateoas_x.repositories.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTests {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void whenFindByIdThenReturnTask() {
        Project project = new Project();
        project.setName("Test project");
        project.setDescription("A project to test the tasks' repository");

        projectRepository.save(project);

        Date dateBefore = new Date();

        Task task = new Task();
        task.setName("Test task");
        task.setDescription("A task to test the task' repository");
        task.setProject(project);

        taskRepository.save(task);

        Date dateAfter = new Date();

        Optional<Task> found = taskRepository.findById(task.getId());

        assertThat(found.isPresent()).isTrue();

        Task foundTask = found.get();

        assertThat(foundTask.getId()).isEqualTo(task.getId());
        assertThat(foundTask.getName()).isEqualTo(task.getName());
        assertThat(foundTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(foundTask.getCreatedDate()).isNotNull().isBetween(dateBefore, dateAfter);
    }

    @Test
    public void whenFindAllByProjectIdThenReturnTasks() {
        Project project = new Project();
        project.setName("Test project");
        project.setDescription("A project to test the tasks' repository");

        projectRepository.save(project);

        Task task1 = new Task();
        task1.setName("Test task 1");
        task1.setDescription("A task to test the tasks' repository");
        task1.setProject(project);

        Task task2 = new Task();
        task2.setName("Test task 2");
        task2.setDescription("A task to test the tasks' repository");
        task2.setProject(project);

        taskRepository.saveAll(Arrays.asList(task1, task2));

        List<Task> found = taskRepository.findAllByProject_Id(project.getId());

        assertThat(found).isNotEmpty();
        assertThat(found).contains(task1, task2);
        assertThat(found.get(0).getProject().getId()).isEqualTo(project.getId());
    }
}
