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
import com.vedruna.proyectoFinalServidor1.persistance.model.State;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.StateRepositoryI;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    
    @Autowired
    ProjectRepositoryI projectRepository;

    
    @Autowired
    StateRepositoryI stateRepository;
    
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
     * Gets a project by name.
     * 
     * @param name the name of the project.
     * @return the project or a 404 if not found.
     */
    @Override
    public ProjectDTO showProjectByName(String name) {
        List<Project> projects = projectRepository.findAll();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().contains(name)) {
                project = p;
                break;
            }
        }
        if (project == null) {
            throw new IllegalArgumentException("No project found with name containing: " + name);
        }
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
            // Buscamos el estado por su ID 
            Optional<State> stateOptional = stateRepository.findById(2);
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get()); // Asignamos el estado
                projectRepository.save(project); // Guardamos el proyecto con el nuevo estado
                isUpdated = true;
            } else {
                System.out.println("State with ID 2 does not exist.");
            }
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
            Optional<State> stateOptional = stateRepository.findById(3);
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get()); // Asignamos el estado
                projectRepository.save(project); // Guardamos el proyecto con el nuevo estado
                isUpdated = true;
            } else {
                System.out.println("State with ID 3 does not exist.");
            }
        } else {
            System.out.println("Project with ID " + id + " does not exist.");
        }
        return isUpdated;
    }


    /**
    * Finds a project by its ID.
    *
    * @param projectId the ID of the project to be found
    * @return an Optional containing the found project, or an empty Optional if no project is found with the given ID
    */
    @Override
    public Project findById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null); 
    }

    @Override
    public List<ProjectDTO> getProjectsByTechnology(String techName) {
        return projectRepository.findProjectsByTechnology(techName);
    }
    
    





    


    
    
}
