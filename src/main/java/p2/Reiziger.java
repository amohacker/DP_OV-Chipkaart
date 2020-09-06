package p2;


import java.sql.Date;

public class Reiziger {
    int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private final Date geboortedatum;

    public Reiziger(int id, String voorlttrs, String tsnvgsl, String achternm, Date gbdatum) {
        this.id = id;
        this.voorletters = voorlttrs;
        this.tussenvoegsel = tsnvgsl;
        this.achternaam = achternm;
        this.geboortedatum = gbdatum;
    }

    public int getId() {
        return this.id;
    }
    public String getVoorletters() { return this.voorletters; }
    public String getTussenvoegsel() { return  this.tussenvoegsel; }
    public String getAchternaam() { return this.achternaam; }
    public Date getGeboortedatum() { return this.geboortedatum; }

    public String getNaam() {
        return (this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam);
    }

    public void setId(int id) { this.id = id; }
    public void setVoorletters(String voorlttrs) {this.voorletters = voorlttrs;}
    public void setTussenvoegsel(String tussenvgsl) {this.tussenvoegsel = tussenvgsl;}
    public void setAchternaam(String achternm) {this.achternaam = achternm;}

    public String toString() {
        return ("Naam: " + this.getNaam() +
                "\nId: " + getId() +
                "\nGeboortedatum: " + this.geboortedatum
                );
    }
}