package com.vedruna.proyectoFinalServidor1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/projects")
    public List<ProjectDTO> getAllProjects() {
        return projectService.showAllProjects();
    }

    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> showProjectByName(@PathVariable String name) {
        ProjectDTO project = projectService.showProjectByName(name);
        ResponseDTO<ProjectDTO> response = new ResponseDTO<>("Project found successfully", project);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, los obtenemos y los agregamos a la respuesta
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
        projectService.saveProject(project);
        ResponseDTO<Object> response = new ResponseDTO<>("Project successfully saved", project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            throw new IllegalArgumentException("There isn't any project with the ID:" + id);
        }
        ResponseDTO<String> response = new ResponseDTO<>("Project deleted successfully", "Project with ID " + id + " deleted.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

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
