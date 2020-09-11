package p4;

import java.sql.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;
    private int reiziger_id;

    OVChipkaart(int kaartNummer, Date geldigTot, int klasse, int saldo, int reizigerId){
        this.kaart_nummer = kaartNummer;
        this.geldig_tot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reizigerId;
    }

    public void setGeldigTot(Date geldig_tot) { this.geldig_tot = geldig_tot; }
    public void setKlasse(int klasse) { this.klasse = klasse; }
    public void setSaldo(int saldo) { this.saldo = saldo; }
    public void setReizigerId(int reiziger_id) { this.reiziger_id = reiziger_id; }

    public int getKaartNummer() { return kaart_nummer; }
    public Date getGeldigTot() { return geldig_tot; }
    public int getKlasse() { return klasse; }
    public int getSaldo() { return saldo; }
    public int getReizigerId() { return reiziger_id; }

    @Override
    public String toString() {
        return "OVChipkaart {" +
                "kaart nummer= " + kaart_nummer +
                ", geldig tot= " + geldig_tot +
                ", klasse= " + klasse +
                ", saldo= " + saldo +
                ", reiziger id= " + reiziger_id +
                '}';
    }
}
