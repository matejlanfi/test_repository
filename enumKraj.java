package com.mycompany.semestrakaa;

public enum enumKraj {
    PRAHA(1,"Hlavní město praha"),
    JIHOCESKY(2,"Jihočeský"),
    JIHOMORAVSKY(3,"Jihomoravský"),
    KARLOVARSKY(4,"Karlovarský"),
    VYSOCINA(5,"Kraj Vysočina"),
    KRALOVEHRADECKY(6,"KraloveHradecký"),
    LIBERECKY(7,"Liberecký"),
    MORAVSKOSLEZKY(8,"MoravskoSlezký"),
    OLOMOUCKY(9,"Olomoucký"),
    PARDUBICKY(10,"Pardubický"),
    PLZENSKY(11,"Plzenský"),
    STREDOCESKY(12,"Středočeský"),
    USTECKY(13,"Ustecký"),
    ZLINSKY(14,"Zlinský");
    
    final private int id;
    final private String nazev;
    
    enumKraj(int id, String nazev){
        this.id = id;
        this.nazev = nazev;
    }

    public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }
}
