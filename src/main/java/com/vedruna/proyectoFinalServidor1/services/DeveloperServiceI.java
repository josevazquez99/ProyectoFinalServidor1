package com.vedruna.proyectoFinalServidor1.services;


import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;

public interface DeveloperServiceI {

    void saveDeveloper(Developer developer);
    boolean deleteDeveloper(Integer id);
    Developer findById(int developerId);



    
} 