package com.mycompany.semestrakaa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author matej
 */
public class Obyvatele {
    AbstrDoubleList<Obec>[] lists = new AbstrDoubleList[14];
    
    public int importData(String soubor){
        int pocet = 0;
        
        for (int i = 0; i <= 13; i++) {
            lists[i] = new AbstrDoubleList<>();

            try{
                BufferedReader br = new BufferedReader(new FileReader(soubor));
                String radek = br.readLine();
                while (radek != null)
                {
                    String[] hodnoty = radek.split(";");

                    if(i+1 == toInt(hodnoty[0]))
                    {
                        if(lists[i].jePrazdny())
                        {
                            lists[i].vlozPrvni(new Obec(toInt(hodnoty[2]),hodnoty[3],toInt(hodnoty[4]),toInt(hodnoty[5]),toInt(hodnoty[6])));
                        }
                        else
                        {
                            lists[i].vlozNaslednika(new Obec(toInt(hodnoty[2]),hodnoty[3],toInt(hodnoty[4]),toInt(hodnoty[5]),toInt(hodnoty[6])));
                        }
                        pocet++;
                    }
                    radek = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException ex)
            {
                System.err.println("File not found");
            } catch (IOException ex)
            {
                System.err.println("Chyba čtení");
            }
        }
        
        return pocet;
    }
    
    public void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj){
        switch (pozice){
            case PRVNI:
                lists[kraj.getId()-1].vlozPrvni(obec);
                break;
                
            case POSLEDNI:
                lists[kraj.getId()-1].vlozPosledni(obec);
                break;
                
            case PREDCHUDCE:
                lists[kraj.getId()-1].vlozPredchudce(obec);
                break;
                
            case NASLEDNIK:
                lists[kraj.getId()-1].vlozNaslednika(obec);
                break;
        }
    }
    
    public Obec zpristupniObec(enumPozice pozice, enumKraj kraj){
        AbstrDoubleList<Obec> list = lists[kraj.getId()-1];
        switch (pozice){
            case PRVNI:
                return list.zpristupniPrvni();
                
            case POSLEDNI:
                return list.zpristupniPosledni();
                
            case PREDCHUDCE:
                return list.zpristupniPredchudce();
                
            case NASLEDNIK:
                return list.zpristupniNaslednika();
                
            case AKTUALNI:
                return list.zpristupniAktualni();
                
            default:
                return null;
        }
    }
    
    public Obec odeberObec(enumPozice pozice,enumKraj kraj){
        AbstrDoubleList<Obec> list = lists[kraj.getId()-1];
        switch (pozice){
            case PRVNI:
                return list.OdeberPrvni();
                
            case POSLEDNI:
                return list.odeberPosledni();
                
            case PREDCHUDCE:
                return list.odeberPredchudce();
                
            case NASLEDNIK:
                return list.odeberNaslednika();
                
            case AKTUALNI:
                return list.odeberAktualni();
                
            default:
                return null;
        }  
    }
    
    public float zjistiPrumer(enumKraj kraj){
        float soucet = 0, pocet = 0;
        
        Iterator<Obec> iterator = lists[kraj.getId()-1].iterator();
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            soucet += obec.getCelkovypocet();
            pocet++;
        }
        return soucet / pocet;
    }
    
    public void zobrazObce(enumKraj kraj){ //ne void, vracet neco (pole nebo abstrlist)
        Iterator<Obec> iterator = lists[kraj.getId()-1].iterator();
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            System.out.println(obec.toString());
        }
    }
    
    public void zobrazObceNadPrumer(enumKraj kraj){
        float pocet = zjistiPrumer(kraj);
        Iterator<Obec> iterator = lists[kraj.getId()-1].iterator();
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            if(pocet < obec.getCelkovypocet())
            {
                System.out.println(obec.toString());
            }
        }
    
    }
    
    public void zrus(enumKraj kraj){
        lists[kraj.getId()-1].zrus();
    }
    
    private int toInt(String num)
    {
        return Integer.parseInt(num);
    }
}
