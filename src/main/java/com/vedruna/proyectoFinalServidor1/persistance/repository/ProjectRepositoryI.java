package com.vedruna.proyectoFinalServidor1.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.proyectoFinalServidor1.persistance.model.Project;

public interface ProjectRepositoryI extends JpaRepository<Project, Integer> {
    public Optional<Project> findByName(String name);
    
} 
