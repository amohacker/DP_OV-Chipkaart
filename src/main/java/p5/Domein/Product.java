package p5.Domein;

import p5.Domein.OVChipkaart;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;
    private List<Integer> ovChipkaarten;

    public Product (int product_nummer, String naam, String beschrijving, int prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaarten = new ArrayList<>();
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public void setOvChipkaarten(List<Integer> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void addovChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart.getKaartNummer());
    }

    public void addovChipkaart(int ovChipkaart) {
        this.ovChipkaarten.add(ovChipkaart);
    }

    public void removeovChipkaart(OVChipkaart ovChipkaart) {
    this.removeovChipkaart(ovChipkaart.getKaartNummer());
    }

    public void removeovChipkaart(int ovChipkaart) {
        int i = 0;
        for (int o: getOvChipkaarten()) {
            if (o==ovChipkaart) {
                this.ovChipkaarten.remove(i);
            }
            i++;
        }
    }

    public int getId() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public int getPrijs() {
        return prijs;
    }

    public List<Integer> getOvChipkaarten() {
        return this.ovChipkaarten;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                "}";
    }
}
