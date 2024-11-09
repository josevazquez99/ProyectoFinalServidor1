package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.exceptions.ProjectNotFoundException;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    
    @Autowired
    ProjectRepositoryI projectRepository;
    
    
    @Override
    public List<ProjectDTO> showAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> projectsDTO = new ArrayList<>();
    
        for (Project p : projects) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(p.getId());
            projectDTO.setName(p.getName());
            projectDTO.setDescription(p.getDescription());
            projectDTO.setStart_date(p.getStart_date());
            projectDTO.setEnd_date(p.getEnd_date());
            projectDTO.setRepository_url(p.getRepository_url());
            projectDTO.setDemo_url(p.getDemo_url());
            projectDTO.setPicture(p.getPicture());
            projectsDTO.add(projectDTO);
        }
    
        return projectsDTO;
    }

    @Override
    public ProjectDTO showProjectByName(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(() -> new ProjectNotFoundException("Proyecto no encontrado con el nombre: " + name));
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
            throw new ProjectNotFoundException("No existe ningún proyecto con el ID: " + id);
        }
    }
    
    
    
    


    
    
}
