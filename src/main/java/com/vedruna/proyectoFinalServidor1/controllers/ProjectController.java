package com.vedruna.proyectoFinalServidor1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceI projectService;

    /**
     * Gets all projects.
     *
     * @param page the page number.
     * @param size the page size.
     * @return the list of projects.
     */
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        return projectService.showAllProjects(page, size);
    }
    

    /**
     * Gets a project by name.
     *
     * @param name the name of the project.
     * @return the project or a 404 if not found.
     */
    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> showProjectByName(@PathVariable String name) {
        ProjectDTO project = projectService.showProjectByName(name);
        ResponseDTO<ProjectDTO> response = new ResponseDTO<>("Project found successfully", project);
        return ResponseEntity.ok(response);
    }


    /**
     * Saves a project.
     *
     * @param project the project to be saved.
     * @param bindingResult the binding result.
     * @return the response with the saved project or a 400 if there are validation errors.
     */
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        // Verificar si el campo 'name' está vacío o es nulo
        if (project.getName() == null || project.getName().isEmpty()) {
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "Name cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                             .append(": ")
                             .append(error.getDefaultMessage())
                             .append("\n")
            );
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
        // Si no hay errores, guardar el proyecto
        projectService.saveProject(project);
        ResponseDTO<Object> response = new ResponseDTO<>("Project successfully saved", project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Deletes a project.
     *
     * @param id the ID of the project to be deleted.
     * @return the response with the deleted project or a 404 if there isn't any project with the given ID.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            throw new IllegalArgumentException("There isn't any project with the ID:" + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Project deleted successfully", "Project with ID " + id + " deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
    * Updates an existing project.
    *
    * @param id the ID of the project to be updated.
    * @param project the updated project data.
    * @param bindingResult the binding result for validation errors.
    * @return a ResponseEntity containing a ResponseDTO with either a success 
    *         message and the updated project, or an error message if validation 
    *         fails or the project is not found.
    */
    @PutMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                             .append(": ")
                             .append(error.getDefaultMessage())
                             .append("\n")
            );
            
            ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        boolean projectUpdated = projectService.updateProject(id, project);
        if (!projectUpdated) {
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "There isn't any project with the ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<Object> response = new ResponseDTO<>("Project updated successfully", project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
