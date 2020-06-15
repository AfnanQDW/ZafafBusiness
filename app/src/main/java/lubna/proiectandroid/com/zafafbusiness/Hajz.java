package lubna.proiectandroid.com.zafafbusiness;

import java.util.Date;

public class Hajz {

    private String namevenus,price,nameuser;
    private Date date;


    public Hajz() {
    }

    public Hajz(String namevenus, String price, String nameuser, Date date) {
        this.namevenus = namevenus;
        this.price = price;
        this.nameuser = nameuser;
        this.date = date;
    }

    public String getNamevenus() {
        return namevenus;
    }

    public void setNamevenus(String namevenus) {
        this.namevenus = namevenus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
