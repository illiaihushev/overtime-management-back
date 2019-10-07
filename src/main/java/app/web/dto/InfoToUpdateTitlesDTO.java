package app.web.dto;

import java.util.ArrayList;
import java.util.Collection;

public class InfoToUpdateTitlesDTO {
    private Collection<String> employees;
    private Collection<String> directManagers;
    private Collection<String> lineManagers;
    private Collection<String> PMOs;
    private Collection<String> projects;
    private String initiator;

    public InfoToUpdateTitlesDTO() {

    }

    public InfoToUpdateTitlesDTO(Collection<String> employees, Collection<String> directManagers, Collection<String> lineManagers, Collection<String> PMOs, Collection<String> projects) {
        this.employees = employees;
        this.directManagers = directManagers;
        this.lineManagers = lineManagers;
        this.PMOs = PMOs;
        this.projects = projects;
    }

    public Collection<String> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<String> employees) {
        this.employees = employees;
    }

    public Collection<String> getDirectManagers() {
        return directManagers;
    }

    public void setDirectManagers(Collection<String> directManagers) {
        this.directManagers = directManagers;
    }

    public Collection<String> getLineManagers() {
        return lineManagers;
    }

    public void setLineManagers(Collection<String> lineManagers) {
        this.lineManagers = lineManagers;
    }

    public Collection<String> getPMOs() {
        return PMOs;
    }

    public void setPMOs(Collection<String> PMOs) {
        this.PMOs = PMOs;
    }

    public Collection<String> getProjects() {
        return projects;
    }

    public void setProjects(Collection<String> projects) {
        this.projects = projects;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

}

