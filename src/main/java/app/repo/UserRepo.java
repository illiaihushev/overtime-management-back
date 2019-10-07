package app.repo;

import app.model.UserDAO;
import app.repo.projections.UserIdManagerId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepo extends CrudRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);

    @Query("select u.username from UserDAO u " +
            "where u.lastUpdate > :lastRelevantTime and u.username in :usernames")
    List<String> findRelevantUsernamesByUsername(@Param("usernames") Collection<String> usernames,
                                                 @Param("lastRelevantTime") Timestamp lastRelevantTime);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "insert into users (\"email\", \"name\", \"manager_id\", \"position_id\")" +
                    "values (" +
                    ":username, " +
                    ":name, " +
                    "(select id from users mng where mng.email = :manager)," +
                    ":positionId)")
    void savePlainUser(@Param("username") String username,
                       @Param("name") String name,
                       @Param("manager") String manager,
                       @Param("positionId") int positionId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "insert into users (\"email\", \"name\", \"manager_id\", \"position_id\")" +
                    "values (" +
                    ":username, " +
                    ":name, " +
                    "null," +
                    ":positionId)")
    void savePlainUser(@Param("username") String username,
                       @Param("name") String name,
                       @Param("positionId") int positionId);

    @Query(nativeQuery = true,
            value = "select u.id, u.manager_id" +
                    " from users u" +
                    " where u.email in :usernames" +
                    " order by u.email")
    List<UserIdManagerId> findManagers(@Param("usernames") Collection<String> usernames);


    @Query("from UserDAO u" +
            " where u.username in :usernames" +
            " and u.lastUpdate > :lastRelevantTime")
    Collection<UserDAO> findRelevantByUsername(@Param("usernames") Collection<String> workers,
                                               @Param("lastRelevantTime") Timestamp lastRelevantTime);

    Collection<UserDAO> findByUsernameIn(Collection<String> workers);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "insert into projects_users (user_id, project_id)" +
                    "values (:userId, :projectId)")
    void save(@Param("userId") long userId, @Param("projectId") long projectId);
}