package com.mycompany.semestrakaa;

/**
 *
 * @author matej
 */
public class Obec {
    private final int PSC;
    private final String obec;
    private int pocetmuzu= 0;
    private int pocetzen = 0;
    private int celkovypocet = 0;

    public Obec(int psc, String obec, int pocetmuzu, int pocetzen, int celkovypocet) {
        this.PSC = psc;
        this.obec = obec;
        this.pocetmuzu = pocetmuzu;
        this.pocetzen = pocetzen;
        this.celkovypocet = celkovypocet;
    }
    
    public Obec(int psc, String obec, int pocetmuzu, int pocetzen) {
        this.PSC = psc;
        this.obec = obec;
        this.pocetmuzu = pocetmuzu;
        this.pocetzen = pocetzen;
        this.celkovypocet = pocetmuzu + pocetzen;
    }

    public int getCisloobce() {
        return PSC;
    }

    public String getObec() {
        return obec;
    }

    public int getPocetmuzu() {
        return pocetmuzu;
    }

    public int getPocetzen() {
        return pocetzen;
    }

    public int getCelkovypocet() {
        return celkovypocet;
    }

    public void setPocetmuzu(int pocetmuzu) {
        this.pocetmuzu = pocetmuzu;
        UpdateCelkovypocet();
    }
    
    public void setPocetzen(int pocetzen) {
        this.pocetzen = pocetzen;
        UpdateCelkovypocet();
    }

    private void UpdateCelkovypocet() {
        this.celkovypocet = pocetmuzu + pocetzen;
    }

    @Override
    public String toString() {
        return "Obec{" + "psc=" + PSC + ", obec=" + obec + ", pocetmuzu=" + pocetmuzu + ", pocetzen=" + pocetzen + ", celkovypocet=" + celkovypocet + '}';
    }
    
}