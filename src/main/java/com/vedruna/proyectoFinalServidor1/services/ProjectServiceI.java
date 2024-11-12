package com.vedruna.proyectoFinalServidor1.services;


import org.springframework.data.domain.Page;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectServiceI {

    Page<ProjectDTO> showAllProjects(int page, int size); 
    ProjectDTO showProjectByName(String name);
    void saveProject(Project project);
    boolean deleteProject(Integer id);
    boolean updateProject(Integer id, Project project);


    
} 