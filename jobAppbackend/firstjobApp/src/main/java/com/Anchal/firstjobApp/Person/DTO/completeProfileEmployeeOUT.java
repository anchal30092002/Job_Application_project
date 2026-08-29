package com.Anchal.firstjobApp.Person.DTO;

public class completeProfileEmployeeOUT {
    boolean profileCmpltdNow;
    boolean isRegistrationCmpltd;
    boolean alreadyCompleted;

    public completeProfileEmployeeOUT(boolean profileCmpltd, boolean isRegistrationCmpltd, boolean alreadyCompleted) {
        this.profileCmpltdNow = profileCmpltd;
        this.isRegistrationCmpltd = isRegistrationCmpltd;
        this.alreadyCompleted = alreadyCompleted;
    }

    public completeProfileEmployeeOUT() {
    }

    public boolean isProfileCmpltdNow() {
        return profileCmpltdNow;
    }

    public void setProfileCmpltdNow(boolean profileCmpltdNow) {
        this.profileCmpltdNow = profileCmpltdNow;
    }

    public boolean isRegistrationCmpltd() {
        return isRegistrationCmpltd;
    }

    public void setRegistrationCmpltd(boolean registrationCmpltd) {
        isRegistrationCmpltd = registrationCmpltd;
    }

    public boolean isAlreadyCompleted() {
        return alreadyCompleted;
    }

    public void setAlreadyCompleted(boolean alreadyCompleted) {
        this.alreadyCompleted = alreadyCompleted;
    }
}
