package p5.Domein;


import p5.Domein.Adres;
import p5.Domein.OVChipkaart;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> OVKaarten;

    public Reiziger(int id, String voorlttrs, String tsnvgsl, String achternm, Date gbdatum) {
        this.id = id;
        this.voorletters = voorlttrs;
        this.tussenvoegsel = tsnvgsl;
        this.achternaam = achternm;
        this.geboortedatum = gbdatum;
    }
    public Reiziger(int id, String voorlttrs, String tsnvgsl, String achternm, Date gbdatum, Adres adres) {
        this(id, voorlttrs, tsnvgsl, achternm, gbdatum);
        this.adres = adres;
    }
    public Reiziger(int id, String voorlttrs, String tsnvgsl, String achternm, Date gbdatum, List<OVChipkaart> kaarten) {
        this(id, voorlttrs, tsnvgsl, achternm, gbdatum);
        this.OVKaarten = kaarten;
    }
    public Reiziger(int id, String voorlttrs, String tsnvgsl, String achternm, Date gbdatum, Adres adres, List<OVChipkaart> kaarten) {
        this(id, voorlttrs, tsnvgsl, achternm, gbdatum, adres);
        this.OVKaarten = kaarten;
    }

    public int getId() {
        return this.id;
    }
    public String getVoorletters() { return this.voorletters; }
    public String getTussenvoegsel() { return  this.tussenvoegsel; }
    public String getAchternaam() { return this.achternaam; }
    public Date getGeboortedatum() { return this.geboortedatum; }
    public Adres getAdres() {return this.adres;}
    public String getNaam() {
        return (this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam);
    }
    public List<OVChipkaart> getOVKaarten() { return OVKaarten; }

    public void addOVChipKaart(OVChipkaart kaart){
        OVKaarten.add(kaart);
    }

    public void setId(int id) { this.id = id; }
    public void setVoorletters(String voorlttrs) {this.voorletters = voorlttrs;}
    public void setTussenvoegsel(String tussenvgsl) {this.tussenvoegsel = tussenvgsl;}
    public void setAchternaam(String achternm) {this.achternaam = achternm;}
    public void setAdres(Adres adres) {this.adres = adres;}
    public void setOVChipKaarten(List<OVChipkaart> OVKaarten) { this.OVKaarten = OVKaarten;}

    public String toString() {
        if (adres == null) {
            return ("Reiziger {Naam: " + this.getNaam() +
                    " Id: " + getId() +
                    " Geboortedatum: " + this.geboortedatum + "}");
        } else {
            return ("Reiziger {Naam: " + this.getNaam() +
                    " Id: " + getId() +
                    " Geboortedatum: " + this.geboortedatum
                    + ", " + adres + "}");
        }
    }
}