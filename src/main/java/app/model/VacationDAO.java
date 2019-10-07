package app.model;

import app.web.dto.VacationDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "vacations")
public class VacationDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserDAO user;

    public VacationDAO() {

    }

    public VacationDAO(UserDAO vacationist, VacationDTO vacationDTO) {
        this.startDate = vacationDTO.getStartDate();
        this.endDate = vacationDTO.getEndDate();
        this.user = vacationist;
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ProjectDAO)) return false;

        VacationDAO vacationDAO = (VacationDAO) o;

        if (id != null && id.equals(vacationDAO.id)) return true;

        return user.equals(vacationDAO.user) && startDate.equals(vacationDAO.startDate);
    }


}
