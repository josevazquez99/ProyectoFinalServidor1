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


    @Override
    public Technology findById(int techId) {
        return technologyRepository.findById(techId).orElse(null);
    }
}
