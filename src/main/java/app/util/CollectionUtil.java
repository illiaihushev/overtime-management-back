package app.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {

    @SafeVarargs
    public static <T> List<T> concatToList(Collection<T> ... collections) {
        List<T> list = new ArrayList<>();

        for (Collection<T> collection : collections) {
            list.addAll(collection);
        }

        return list;
    }


}
