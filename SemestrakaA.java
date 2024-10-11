package com.mycompany.semestrakaa;

import java.io.IOException;

/**
 *
 * @author matej
 */
public class SemestrakaA {

    public static void main(String[] args) throws IOException {
        Obyvatele obyv = new Obyvatele();
        obyv.importData("kraje.csv");
        obyv.zobrazObce(enumKraj.PRAHA);
        obyv.odeberObec(enumPozice.PRVNI, enumKraj.PRAHA);
        System.out.println("");
        obyv.zobrazObce(enumKraj.PRAHA);
        System.out.println("");
        obyv.zpristupniObec(enumPozice.PRVNI, enumKraj.PRAHA);
        for(int i = 0;i<=4;i++)
        {
            obyv.zpristupniObec(enumPozice.NASLEDNIK, enumKraj.PRAHA);
        }
        obyv.vlozObec(new Obec(12345,"abcd",12,12), enumPozice.NASLEDNIK, enumKraj.PRAHA);
        obyv.zobrazObce(enumKraj.PRAHA);
    }
}