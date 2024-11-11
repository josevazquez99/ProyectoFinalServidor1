package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    
    @Autowired
    ProjectRepositoryI projectRepository;
    
    
    @Override
    public List<ProjectDTO> showAllProjects() {
        return projectRepository.findAll().stream()
                        .map(ProjectDTO::new)
                        .collect(Collectors.toList());

    }

    @Override
    public ProjectDTO showProjectByName(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con el nombre: " + name));
        return new ProjectDTO(project);
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
    
        if (project.isPresent()) {
            projectRepository.deleteById(id); 
            return true;
        } else {
            // Lanzamos la excepción si no se encuentra el proyecto
            throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + id);
        }
    }
    
    public boolean updateProject(Integer id, Project project) {
        Optional<Project> projectToUpdate = projectRepository.findById(id);
    
        if (projectToUpdate.isPresent()) {
            projectToUpdate.get().setName(project.getName());
            projectToUpdate.get().setDescription(project.getDescription());
            projectToUpdate.get().setStart_date(project.getStart_date());
            projectToUpdate.get().setEnd_date(project.getEnd_date());
            projectToUpdate.get().setRepository_url(project.getRepository_url());
            projectToUpdate.get().setDemo_url(project.getDemo_url());
            projectToUpdate.get().setPicture(project.getPicture());
            projectToUpdate.get().setTechnologies(project.getTechnologies());
            projectToUpdate.get().setDevelopers(project.getDevelopers());
            projectRepository.save(projectToUpdate.get());
            return true;
        } else {
            return false;
        }
    }



    
    
    


    
    
}
