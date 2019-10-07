package app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "projects")
public class ProjectDAO implements ExistingAppItem<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pmo_id")
    private UserDAO pmo;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany(mappedBy = "projects")
    private List<UserDAO> users;

    public ProjectDAO(){

    }



    public ProjectDAO(String name, UserDAO pmo, Timestamp lastUpdate) {
        this.name = name;
        this.pmo = pmo;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDAO getPmo() {
        return pmo;
    }

    public void setPmo(UserDAO pmo) {
        this.pmo = pmo;
    }

    public List<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDAO> users) {
        this.users = users;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ProjectDAO)) return false;

        ProjectDAO projectDAO = (ProjectDAO) o;

        if (id != null && id.equals(projectDAO.id)) return true;

        return  name.equals(projectDAO.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getNaturalId() {
        return name;
    }
}
