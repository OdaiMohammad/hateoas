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
import com.cloud_native.hateoas_x.repositories.ProjectRepository;
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
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void whenFindAllThenReturnProjects() {
        Project project1 = new Project();
        project1.setName("Test project 1");
        project1.setDescription("A project to test the projects' repository");

        Project project2 = new Project();
        project2.setName("Test project 2");
        project2.setDescription("A project to test the projects' repository");

        projectRepository.saveAll(Arrays.asList(project1, project2));

        List<Project> found = projectRepository.findAll();

        assertThat(found).isNotEmpty();
        assertThat(found).contains(project1, project2);
    }

    @Test
    public void whenFindByIdThenReturnProject() {
        Date dateBefore = new Date();
        Project project = new Project();
        project.setName("Test project");
        project.setDescription("A project to test the projects' repository");
        projectRepository.save(project);
        Date dateAfter = new Date();

        Optional<Project> found = projectRepository.findById(project.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getId()).isEqualTo(project.getId());
        assertThat(found.get().getName()).isEqualTo(project.getName());
        assertThat(found.get().getDescription()).isEqualTo(project.getDescription());
        assertThat(found.get().getCreatedDate()).isNotNull().isBetween(dateBefore, dateAfter);
    }
}
