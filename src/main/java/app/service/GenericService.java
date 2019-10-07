package app.service;

import app.model.ExistingAppItem;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface GenericService<N, T extends ExistingAppItem<N>, D extends ExistingAppItem<N>> {
    T makeRelevant(D dto, Timestamp timestamp);

    T createNew(D dto, Timestamp updateTime);

    boolean isIrrelevant(N naturalId);

    List<T> saveAll(Collection<D> users, Timestamp updateTime);

    CrudRepository<T, ?> getRepo();

    void initCache();

    void initWithMyself();
}
