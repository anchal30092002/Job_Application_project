package com.Anchal.firstjobApp.Person.DTO;

public class activatePersonOut {
    private boolean isExist;
    private boolean isActivated;//true if already activated false if we are activating is now

    public activatePersonOut(boolean isExist, boolean isAtivated) {
        this.isExist = isExist;
        this.isActivated = isAtivated;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
