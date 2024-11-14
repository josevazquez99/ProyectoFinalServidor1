package com.vedruna.proyectoFinalServidor1.persistance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectRepositoryI extends JpaRepository<Project, Integer> {
    public Optional<Project> findByName(String name);
    Page<Project> findAll(Pageable pageable);
    List<Project> getProjectsByTechnology(String tech); 

    
} 
