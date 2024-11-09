package com.vedruna.proyectoFinalServidor1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.proyectoFinalServidor1.dto.ProjectDTO;
import com.vedruna.proyectoFinalServidor1.exceptions.ProjectNotFoundException;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.services.ProjectServiceI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    public ResponseEntity<ProjectDTO> showProjectByName(@PathVariable String name) {
        ProjectDTO project = projectService.showProjectByName(name);
        return ResponseEntity.ok(project);
    }
    
    @PostMapping("/projects")
    public ResponseEntity<String> postProject(@RequestBody Project project) {
        projectService.saveProject(project);
        return ResponseEntity
                .status(HttpStatus.CREATED) 
                .body("Proyecto guardado con éxito");
        }
        @DeleteMapping("/projects/{id}")
        public ResponseEntity<String> deleteProject(@PathVariable Integer id) {
            boolean projectDeleted = projectService.deleteProject(id);
            
            if (!projectDeleted) {
                // Lanzamos la excepción si no se encuentra el proyecto
                throw new ProjectNotFoundException("No existe ningún proyecto con el ID: " + id);
            }
        
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        }
        
    
    
    


















}
