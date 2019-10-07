package app.service;

import app.model.ExistingAppItem;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public abstract class GenericServiceAbstr<
            N,
            T extends ExistingAppItem<N>,
            D extends ExistingAppItem<N>
        >
        implements GenericService<N, T, D> {
    public static <T extends ExistingAppItem> Map<Boolean, List<T>> partitionByRelevantness(Collection<T> existingAppItems, Timestamp lastRelevantTime) {
        return existingAppItems.stream()
                .collect(
                        partitioningBy(
                                user -> user.getLastUpdate() != null
                                        && user.getLastUpdate().compareTo(lastRelevantTime) > 0));
    }

    public static <N> List<N> extractNaturalId(Collection<? extends ExistingAppItem<N>> users) {
        return users.stream().map(ExistingAppItem::getNaturalId).collect(toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<T> saveAll(Collection<D> items, Timestamp updateTime) {
        List<T> res = new ArrayList<>();

        items.forEach(item -> {
            T toSave;
            if (isIrrelevant(item.getNaturalId())) {
                toSave = makeRelevant(item, updateTime);
            } else {
                toSave = createNew(item, updateTime);
            }
            res.add(getRepo().save(toSave));
        });

        return res;
    }

    public static <N, T extends ExistingAppItem<N>> Map<N, T> toMapByNaturalId(Collection<T> daos) {
        return daos.stream()
                .collect(
                        toMap(ExistingAppItem::getNaturalId, Function.identity()));
    }


}
