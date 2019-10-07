package app.web.dto;

import java.util.Collection;
import java.util.List;

public class RelatedInfoDTO {
    private List<UserDTO> employees;
    private List<UserDTO> directs;
    private Collection<UserDTO> lines;
    private Collection<UserDTO> pmos;

    private Collection<ProjectDTO> projects;

    public List<UserDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserDTO> employees) {
        this.employees = employees;
    }

    public List<UserDTO> getDirects() {
        return directs;
    }

    public void setDirects(List<UserDTO> directs) {
        this.directs = directs;
    }

    public Collection<UserDTO> getLines() {
        return lines;
    }

    public void setLines(Collection<UserDTO> lines) {
        this.lines = lines;
    }

    public Collection<UserDTO> getPmos() {
        return pmos;
    }

    public void setPmos(Collection<UserDTO> pmos) {
        this.pmos = pmos;
    }

    public Collection<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(Collection<ProjectDTO> projects) {
        this.projects = projects;
    }
}
