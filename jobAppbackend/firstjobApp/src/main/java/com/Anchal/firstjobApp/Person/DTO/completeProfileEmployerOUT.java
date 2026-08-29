package com.Anchal.firstjobApp.Person.DTO;

public class completeProfileEmployerOUT {
    boolean profileCmpltdNow;
    boolean isRegistrationCmpltd;
    boolean alreadyCompleted;

    public completeProfileEmployerOUT(boolean profileCmpltdNow, boolean isRegistrationCmpltd, boolean alreadyCompleted) {
        this.profileCmpltdNow = profileCmpltdNow;
        this.isRegistrationCmpltd = isRegistrationCmpltd;
        this.alreadyCompleted = alreadyCompleted;
    }

    public completeProfileEmployerOUT() {
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
