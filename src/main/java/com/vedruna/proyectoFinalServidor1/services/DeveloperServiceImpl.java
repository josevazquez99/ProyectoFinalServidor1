package com.vedruna.proyectoFinalServidor1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.DeveloperRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI {


    @Autowired
    DeveloperRepositoryI developerRepository;

    @Autowired
    ProjectRepositoryI projectRepository;
    
    /**
     * Saves a developer with the projects associated
     * @param developer the developer to be saved
     */
    @Override
    public void saveDeveloper(Developer developer) {
    List<Project> managedProjects = new ArrayList<>();
    
    for (Project project : developer.getProjectsDevelopers()) {
        projectRepository.findById(project.getId()).ifPresentOrElse(
            managedProjects::add, 
            () -> { 
                throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
            }
        );
    }
    
    // Associate the projects managed by JPA to the developer
    developer.setProjectsDevelopers(managedProjects);
    
    // Save the developer with the associated projects
    developerRepository.save(developer);
}


    /**
    * Deletes a developer by their ID.
    * 
    * @param id the ID of the developer to be deleted
    * @return true if the developer was successfully deleted, otherwise throws an exception
    * @throws IllegalArgumentException if no developer exists with the given ID
    */
    @Override
    public boolean deleteDeveloper(Integer id) {
        Optional<Developer> developer = developerRepository.findById(id);
    
        if (developer.isPresent()) {
            developerRepository.deleteById(id); 
            return true;
        } else {
            throw new IllegalArgumentException("No existe ningún developer con el ID: " + id);
        }
    }
    
}
