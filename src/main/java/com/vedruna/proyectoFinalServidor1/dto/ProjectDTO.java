package com.vedruna.proyectoFinalServidor1.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.proyectoFinalServidor1.persistance.model.Developer;
import com.vedruna.proyectoFinalServidor1.persistance.model.Project;
import com.vedruna.proyectoFinalServidor1.persistance.model.Technology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private int id;

    private String name;

    private String description;

    private Date start_date;

    private Date end_date;

    private String repository_url;

    private String demo_url;

    private String picture;

    private Integer  state;


    private List<TechnologyDTO> technologies;

    private List<DeveloperDTO> developers;


    public ProjectDTO(Project p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.start_date = p.getStart_date();
        this.end_date = p.getEnd_date();
        this.repository_url = p.getRepository_url();
        this.demo_url = p.getDemo_url();
        this.picture = p.getPicture();
        this.state = p.getStateProject() != null ? p.getStateProject().getId() : null; 
        this.technologies=technologiesDTO(p.getTechnologies());
        this.developers=developersDTO(p.getDevelopers());

    }
    public List<TechnologyDTO> technologiesDTO(List<Technology> technologies) {
        List<TechnologyDTO> technologiesRegistered = new ArrayList<TechnologyDTO>();
        for (Technology t : technologies) {
            technologiesRegistered.add(new TechnologyDTO(t));
        }
        return technologiesRegistered;
    }
    public List<DeveloperDTO> developersDTO(List<Developer> developers) {
        List<DeveloperDTO> developersRegistered = new ArrayList<DeveloperDTO>();
        for (Developer d : developers) {
            developersRegistered.add(new DeveloperDTO(d));
        }
        return developersRegistered;
    }
    
}
