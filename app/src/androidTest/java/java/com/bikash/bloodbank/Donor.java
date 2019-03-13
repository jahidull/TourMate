package java.com.bikash.bloodbank;

/**
 * Created by root on 11/6/16.
 */

public class Donor {
    String name;
    String contuctNumber;
    String city;
    String bloodGroup;
    String lat;
    String lan;
    String gender;
    String last;
    String age;
    String weight;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
    public  String getGender(){
        return  gender;
    }
    public void setGender(String gender){
        this.gender=gender;
    }

    public String getLast(){
        return  last;
    }
    public  void setLast(String last){
        this.last=last;
    }

    public String getAge()
    {
        return age;
    }
    public void setAge(String age)
    {
        this.age=age;
    }
    public String getWeight()
    {
        return weight;
    }
    public void setWeight(String weight)
    {
        this.weight=weight;
    }

    public Donor() {

    }

    public Donor(String name, String contuctNumber, String bloodGroup, String city, String lat, String lng, String gender, String last, String age, String weight) {
        this.name = name;
        this.contuctNumber = contuctNumber;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.lat = lat;
        this.lan = lng;
        this.gender=gender;
        this.last=last;
        this.age=age;
        this.weight=weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContuctNumber() {
        return contuctNumber;
    }

    public void setContuctNumber(String contuctNumber) {
        this.contuctNumber = contuctNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
