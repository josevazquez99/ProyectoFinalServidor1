package com.vedruna.proyectoFinalServidor1.services;

import java.util.List;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectServiceI {

    List<ProjectDTO> showAllProjects();
    ProjectDTO showProjectByName(String name);
    void saveProject(Project project);
    boolean deleteProject(Integer id);


    
} 