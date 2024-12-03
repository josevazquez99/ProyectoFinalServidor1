package com.vedruna.proyectoFinalServidor1.services;


import java.util.List;

import org.springframework.data.domain.Page;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;


public interface ProjectServiceI {

    Page<ProjectDTO> showAllProjects(int page, int size); 
    Page<ProjectDTO> showProjectsByName(String name,int page, int size);
    void saveProject(Project project);
    boolean deleteProject(Integer id);
    boolean moveProjectToTesting(Integer id);
    boolean moveProjectToProduction(Integer id);
    boolean updateProject(Integer id, Project project);
    Project findById(Integer projectId);
    List<ProjectDTO> getProjectsByTechnology(String techName);

    
} 