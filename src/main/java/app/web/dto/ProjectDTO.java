package app.web.dto;

import app.model.ExistingAppItem;

import java.sql.Timestamp;

public class ProjectDTO implements ExistingAppItem<String> {
    private String name;
    private String pmo;
    private Timestamp lastUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPmo() {
        return pmo;
    }

    public void setPmo(String pmo) {
        this.pmo = pmo;
    }

    @Override
    public String getNaturalId() {
        return name;
    }

    @Override
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(Timestamp timestamp) {
        this.lastUpdate = timestamp;
    }
}