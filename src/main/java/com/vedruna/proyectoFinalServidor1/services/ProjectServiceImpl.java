package com.vedruna.proyectoFinalServidor1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    
    @Autowired
    ProjectRepositoryI projectRepository;
    

    
    
    /**
     * Gets all projects.
     *
     * @param page the page number.
     * @param size the page size.
     * @return the list of projects.
     */
    @Override
    public Page<ProjectDTO> showAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); 
        Page<Project> projectPage = projectRepository.findAll(pageable); 
        return projectPage.map(ProjectDTO::new); 
    }

    /**
    * Retrieves a project by its name.
    *
    * @param name The name of the project to retrieve.
    * @return A ProjectDTO representing the project.
    * @throws IllegalArgumentException if no project is found with the provided name.
    */
    @Override
    public ProjectDTO showProjectByName(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con el nombre: " + name));
        return new ProjectDTO(project);
    }

    /**
     * Saves a project.
     *
     * @param project the project to be saved.
     */
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }
    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to be deleted
     * @return true if the project was successfully deleted, otherwise throws an exception
     * @throws IllegalArgumentException if no project exists with the given ID
     */
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
    
        if (project.isPresent()) {
            projectRepository.deleteById(id); 
            return true;
        } else {
            // We throw the exception if the project is not found
            throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + id);
        }
    }
    
    /**
     * Updates an existing project.
     *
     * @param id the ID of the project to be updated.
     * @param project the updated project data.
     * @return true if the project was successfully updated, otherwise returns false if no project is found with the given ID
     */
    
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

    /**
     * Moves all projects from development to testing state.
     *
     * @return true if there are any projects to move, otherwise returns false
     */
    @Override
    public boolean moveProjectToTesting(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        boolean isUpdated = false;
    
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.getStateProject().setName("Testing");
            projectRepository.save(project);
            isUpdated = true;
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
    
        return isUpdated;
    }
    
    

    
    

    /**
     * Moves all projects to production state.
     *
     * @return true if there are any projects to move, otherwise returns false
     */
    @Override
    public boolean moveProjectToProduction(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        boolean isUpdated = false;
    
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.getStateProject().setName("Production");
            projectRepository.save(project);
            isUpdated = true;
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
    
        return isUpdated;
    }
    
    





    


    
    
}
