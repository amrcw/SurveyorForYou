package uk.co.surveyorforyou.surveyorforyou;

/**
 * Created by Ruwan on 16/02/2017.
 */

public class Properties {

    private String refNo;
    private String name;
    private String phone;
    private String postcode;
    private String dateOrdered;
    private String dueDate;

    public Properties(String refNo, String name, String phone, String postcode,String dateOrdered, String dueDate){

        this.setRefNo(refNo);
        this.setName(name);
        this.setPhone(phone);
        this.setPostcode(postcode);
        this.setDateOrdered(dateOrdered);
        this.setDueDate(dueDate);
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
