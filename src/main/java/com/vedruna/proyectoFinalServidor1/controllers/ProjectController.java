package com.vedruna.proyectoFinalServidor1.controllers;

import java.time.LocalDate;
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
     * @param bindingResult the binding result for validation errors.
     * @return a ResponseEntity containing a ResponseDTO with either a success
     *         message and the saved project, or an error message if validation
     *         fails or the project's start date is before today.
     */

    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
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

    // Verificar si start_date es antes de hoy
    LocalDate today = LocalDate.now();
    if (project.getStart_date().toLocalDate().isBefore(today)) {
        ResponseDTO<Object> response = new ResponseDTO<>("Validation Error", "The start date cannot be before today.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Guardar el proyecto si no hay errores
    projectService.saveProject(project);
    ResponseDTO<Object> response = new ResponseDTO<>("Project created successfully", null); // Mensaje sin detalles
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



    /**
     * Moves all projects to testing state.
     *
     * @return a ResponseEntity with a String body and a status of 200 if the
     *         operation is successful, or a status of 404 if there are no
     *         projects to move.
     */
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToTesting(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to testing successfully");
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to testing: " + e.getMessage());
        }
    }
    
    /**
     * Moves all projects to production state.
     *
     * @return a ResponseEntity with a String body and a status of 200 if the
     *         operation is successful, or a status of 404 if there are no
     *         projects to move.
     */
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            boolean result = projectService.moveProjectToProduction(id);
            if (result) {
                return ResponseEntity.ok("Projects moved to production successfully");
            } else if(projectService.findById(id)==null){ 
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No projects were moved to testing");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while moving projects to production: " + e.getMessage());
        }
    }





   
    
    /**
     * Gets all projects by a given technology.
     * 
     * @param tech The name of the technology to search for.
     * @return a ResponseEntity with a ResponseDTO containing a list of Projects if
     *         there are projects with the given technology, or a 404 if there are no
     *         projects with that technology.
     */
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        List<ProjectDTO> projects = projectService.getProjectsByTechnology(tech);
    
        if (projects.isEmpty()) {
            ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("No projects found with this technology", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Projects found successfully", projects);
        return ResponseEntity.ok(response);
    }
    
    
    

    
}
