package app.model;

import java.sql.Timestamp;

public interface ExistingAppItem<N> {
    N getNaturalId();

    Timestamp getLastUpdate();

    void setLastUpdate(Timestamp timestamp);
}
