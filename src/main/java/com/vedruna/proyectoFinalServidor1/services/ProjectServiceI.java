package com.vedruna.proyectoFinalServidor1.services;

import java.util.List;
import java.util.Optional;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectServiceI {

    List<ProjectDTO> showAllProjects();
    ProjectDTO showProjectByName(String name);
    void saveProject(Project project);
    boolean deleteProject(Integer id);
    boolean updateProject(Integer id, Project project);


    
} 