package app.repo;

import app.model.ProjectDAO;
import app.model.UserDAO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepo extends CrudRepository<ProjectDAO, Long> {

    @Query("select p.name from ProjectDAO p " +
            "where p.lastUpdate > :lastRelevantTime and p.name in :projectNames")
    List<String> findRelevantByName(@Param("projectNames") Collection<String> projectNames,
                                    @Param("lastRelevantTime") Timestamp lastRelevantTime);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "insert into projects (\"name\", \"pmo_id\")" +
                    "values (" +
                    ":name, " +
                    "(select id from users u where u.email = :pmo)")
    void savePlainUser(@Param("name") String name,
                       @Param("pmo") String pmo);

    Collection<ProjectDAO> findByNameIn(Collection<String> projectsNames);
}
