package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.TechnologyRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI {

    @Autowired
    TechnologyRepositoryI technologyRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    /**
     * Saves a technology with the projects associated
     * @param technology the technology to be saved
     */
    @Override
    public void saveTechnology(Technology technology) {
        // Verifica si ya existe una tecnología con el mismo ID
        if (technologyRepository.existsById(technology.getId())) {
            throw new IllegalArgumentException("El ID de la tecnología ya está en uso");
        }
        
        List<Project> managedProjects = new ArrayList<>();
        
        for (Project project : technology.getProjectsTechnologies()) {
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add, 
                () -> { 
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }
        
        // Asociar los proyectos gestionados por JPA a la tecnología
        technology.setProjectsTechnologies(managedProjects);
        
        // Guardar la tecnología con los proyectos asociados
        technologyRepository.save(technology);
    }
    

    /**
    * Deletes a technology by their ID.
    * 
    * @param id the ID of the technology to be deleted
    * @return true if the technology was successfully deleted, otherwise throws an exception
    * @throws IllegalArgumentException if no technology exists with the given ID
    */
    @Override
    public boolean deleteTechnology(Integer id) {
        Optional<Technology> technology = technologyRepository.findById(id);
    
        if (technology.isPresent()) {
            technologyRepository.deleteById(id); 
            return true;
        } else {
            throw new IllegalArgumentException("No existe ninguna tecnología con el ID: " + id);
        }
    }


    /**
     * Finds a technology by its ID.
     * 
     * @param techId the ID of the technology to be found
     * @return an Optional containing the technology if found, otherwise an empty Optional
     */
    public Technology findById(Integer techId) {
        return technologyRepository.findById(techId).orElse(null); 
    }


    /**
    * Associates a technology with a project.
    *
    * @param technologyId the ID of the technology to associate
    * @param projectId the ID of the project to associate with
    * @throws IllegalArgumentException if the technology or project with the given IDs is not found
    */
    @Override
    public void associateTechnologyWithProject(int projectId, int technologyId) {
        Technology technology = technologyRepository.findById(technologyId).orElseThrow(() -> 
            new IllegalArgumentException("Technology with ID " + technologyId + " not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> 
            new IllegalArgumentException("Project with ID " + projectId + " not found"));
        project.getTechnologies().add(technology);
        technology.getProjectsTechnologies().add(project);
        projectRepository.save(project);
        technologyRepository.save(technology);
    }


}
