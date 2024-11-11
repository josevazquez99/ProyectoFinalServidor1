package com.vedruna.proyectoFinalServidor1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.repository.DeveloperRepositoryI;
import com.vedruna.proyectoFinalServidor1.persistance.repository.ProjectRepositoryI;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI {


    @Autowired
    DeveloperRepositoryI developerRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    @Override
    public void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
    }

    @Override
    public boolean deleteDeveloper(Integer id) {
        Optional<Developer> developer = developerRepository.findById(id);
    
        if (developer.isPresent()) {
            developerRepository.deleteById(id); 
            return true;
        } else {
            throw new IllegalArgumentException("No existe ningún developer con el ID: " + id);
        }
    }
    
}
