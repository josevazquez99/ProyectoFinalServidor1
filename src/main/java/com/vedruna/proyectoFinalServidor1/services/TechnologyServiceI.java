package com.vedruna.proyectoFinalServidor1.services;


import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;

public interface TechnologyServiceI {

    void saveTechnology(Technology technology);
    boolean deleteTechnology(Integer id);



    
} 