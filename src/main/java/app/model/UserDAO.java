package app.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserDAO implements ExistingAppItem<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email")
    private String username;

    @NotNull
    @Column(name = "position_id")
    private Byte positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private UserDAO manager;

    @NotNull
    private String name;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "projects_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            foreignKey = @ForeignKey(name = "projects_users_users_fk"),
            inverseJoinColumns = {@JoinColumn(name = "project_id")},
            inverseForeignKey = @ForeignKey(name = "projects_users_projects_fk")
    )
    private Set<ProjectDAO> projects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<VacationDAO> vacations;

    public UserDAO() {

    }

    public UserDAO(String username, String name, UserDAO manager, Set<ProjectDAO> projects, List<VacationDAO> vacations, byte positionId, Timestamp date) {
        this.username = username;
        this.name = name;
        this.manager = manager;
        this.projects = projects;
        this.vacations = vacations;
        this.positionId = positionId;
        this.lastUpdate = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getPositionId() {
        return positionId;
    }

    public void setPositionId(Byte positionId) {
        this.positionId = positionId;
    }

    public Set<ProjectDAO> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDAO> projects) {
        this.projects = projects;
    }

    public List<VacationDAO> getVacations() {
        return vacations;
    }

    public void setVacations(List<VacationDAO> vacations) {
        this.vacations = vacations;
    }

    public UserDAO getManager() {
        return manager;
    }

    public void setManager(UserDAO manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof UserDAO)) return false;

        UserDAO userDAO = (UserDAO) o;

        if (id != null && id.equals(userDAO.id)) return true;

        return username.equals(userDAO.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String getNaturalId() {
        return username;
    }
}