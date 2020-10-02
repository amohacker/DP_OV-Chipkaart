package p5.Domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;
    private int reiziger_id;
    private List<Product> producten;

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, int saldo, int reizigerId){
        this.kaart_nummer = kaartNummer;
        this.geldig_tot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reizigerId;
        producten = new ArrayList<>();
    }

    public void setGeldigTot(Date geldig_tot) { this.geldig_tot = geldig_tot; }
    public void setKlasse(int klasse) { this.klasse = klasse; }
    public void setSaldo(int saldo) { this.saldo = saldo; }
    public void setReizigerId(int reiziger_id) { this.reiziger_id = reiziger_id; }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        this.producten.add(product);
        product.addovChipkaart(this);
    }

    public void removeProduct(Product product) {
        producten.remove(product);
    }

    public int getKaartNummer() { return kaart_nummer; }
    public Date getGeldigTot() { return geldig_tot; }
    public int getKlasse() { return klasse; }
    public int getSaldo() { return saldo; }
    public int getReizigerId() { return reiziger_id; }

    public List<Product> getProducten() {
        return producten;
    }

    @Override
    public String toString() {
        return "OVChipkaart {" +
                "kaart nummer= " + kaart_nummer +
                ", geldig tot= " + geldig_tot +
                ", klasse= " + klasse +
                ", saldo= " + saldo +
                ", reiziger id= " + reiziger_id +
                ", producten= " + getProducten() +
                '}';
    }
}
