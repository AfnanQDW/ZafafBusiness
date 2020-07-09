package lubna.proiectandroid.com.zafafbusiness.Model;

import java.util.ArrayList;

public class BusinessUser {

    String uid;
    String email;

    ArrayList<String> ownedVenues;

    public BusinessUser() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getOwnedVenues() {
        return ownedVenues;
    }

    public void setOwnedVenues(ArrayList<String> ownedVenues) {
        this.ownedVenues = ownedVenues;
    }
}



