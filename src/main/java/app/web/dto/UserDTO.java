package app.web.dto;

import app.model.ExistingAppItem;

import java.sql.Timestamp;
import java.util.Collection;

public class UserDTO implements ExistingAppItem<String> {
    private String username;
    private String name;
    private String manager;
    private Collection<String> projects;
    private Collection<VacationDTO> vacations;
    private byte position;
    private Timestamp lastUpdate;


    @Override
    public String getNaturalId() {
        return username;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public Collection<String> getProjects() {
        return projects;
    }

    public void setProjects(Collection<String> projects) {
        this.projects = projects;
    }

    public Collection<VacationDTO> getVacations() {
        return vacations;
    }

    public void setVacations(Collection<VacationDTO> vacations) {
        this.vacations = vacations;
    }
}