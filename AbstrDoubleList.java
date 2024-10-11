package com.mycompany.semestrakaa;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author matej
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T>{
    
    private Item<T> prvni;
    private Item<T> posledni;
    private Item<T> aktualni;
    private int pocet;
    
    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocet = 0;
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    @Override
    public void vlozPrvni(T data) {
        Item<T> polozka = new Item<>(data);
        if (prvni == null)//prvni do prazdneho
        {
            //polozka.nextItem=polozka;
            polozka.setNextItem(polozka);
            polozka.setPreviosItem(polozka);
            prvni = polozka;
            posledni = polozka;
        }
        else //prvni do zaplneneho
        {
            polozka.setPreviosItem(prvni.getPreviosItem());
            polozka.setNextItem(prvni);
            prvni.setPreviosItem(polozka);
            prvni = polozka;
        }
        aktualni = prvni;
        pocet++;
    }

    @Override
    public void vlozPosledni(T data) {
        Item<T> polozka = new Item<>(data);
        if(posledni == null) //posledni do prazdneho
        {
            polozka.setNextItem(polozka);
            polozka.setPreviosItem(polozka);
            posledni = polozka;
            prvni = polozka;
        }
        else //posledni do zaplneneho
        {
            polozka.setNextItem(posledni.getNextItem());
            polozka.setPreviosItem(posledni);
            posledni.setNextItem(polozka);
            posledni = polozka;
        }
        aktualni = posledni;
        pocet++;
    }

    @Override
    public void vlozNaslednika(T data) {
        if(pocet == 0){vlozPrvni(data);}
        else{
            if(aktualni == posledni)
            {
                Item<T> polozka = new Item<>(data);
                polozka.setNextItem(prvni);
                polozka.setPreviosItem(aktualni);
                aktualni.setNextItem(polozka);
                prvni.setPreviosItem(polozka);
                posledni = polozka;
                aktualni = polozka;
            }
            else
            {
                Item<T> polozka = new Item<>(data);
                polozka.setNextItem(aktualni.getNextItem());
                polozka.setPreviosItem(aktualni);
                aktualni.getNextItem().setPreviosItem(polozka);
                aktualni.setNextItem(polozka);
            }
        pocet++;
        }
    }

    @Override
    public void vlozPredchudce(T data) {
        if(pocet == 0){vlozPrvni(data);}
        else{
        if(aktualni == prvni)
            {
                Item<T> polozka = new Item<>(data);
                polozka.setNextItem(aktualni);
                polozka.setPreviosItem(posledni);
                posledni.setNextItem(polozka);
                aktualni.setPreviosItem(polozka);
                prvni = polozka;
                aktualni = polozka;
            }
            else
            {
                Item<T> polozka = new Item<>(data);
                polozka.setNextItem(aktualni.getNextItem());
                polozka.setPreviosItem(aktualni);
                aktualni.getNextItem().setPreviosItem(polozka);
                aktualni.setNextItem(polozka);
            }
        pocet++;
        }
    }

    @Override
    public T zpristupniAktualni() {
        if(jePrazdny()){
            throw new NoSuchElementException();
        }else{
            return aktualni.obj;
        }
    }

    @Override
    public T zpristupniPrvni() {//nastaví na první a vrací data
        if(jePrazdny())
        {
            throw new NoSuchElementException();
        }
        else{
            aktualni = prvni;
            return aktualni.obj;
        }
    }

    @Override
    public T zpristupniPosledni() {
        if(jePrazdny()){
            throw new NoSuchElementException();   
        }else{
            aktualni = posledni;
            return aktualni.obj;
        }
    }

    @Override
    public T zpristupniNaslednika() {
        if(jePrazdny()){
            throw new NoSuchElementException();
        }else{
        aktualni = aktualni.getNextItem();
        return aktualni.obj;
        }
    }

    @Override
    public T zpristupniPredchudce() {
        if(jePrazdny())
        {
            throw new NoSuchElementException();
        }else{
        aktualni = aktualni.getPreviosItem();
        return aktualni.obj;
        }
    }

    @Override
    public T odeberAktualni() {// Po odebrání aktualního se objek aktualní nastaví na prvního
        if(jePrazdny()){
            throw new RuntimeException("Seznam je prazdný");
        }
        else
        {
            aktualni.getNextItem().setPreviosItem(aktualni.getPreviosItem());
            aktualni.getPreviosItem().setNextItem(aktualni.getNextItem());
            if(aktualni==prvni){prvni = aktualni.getNextItem();aktualni = prvni;}
            else if(aktualni == posledni){posledni = aktualni.getPreviosItem();aktualni = prvni;}
            else{aktualni = prvni;}
            pocet--;
            return aktualni.obj;
        }
    }

    @Override
    public T OdeberPrvni() {
        if(jePrazdny()){
            throw new RuntimeException("Seznam je prazdný");
        }
        else
        {
            prvni.getNextItem().setPreviosItem(prvni.getPreviosItem());
            prvni.getPreviosItem().setNextItem(prvni.getNextItem());
            prvni = prvni.getNextItem();
            pocet--;
            aktualni = prvni;
            return aktualni.obj;
        }
    }

    @Override
    public T odeberPosledni() {
        if(jePrazdny()){
            throw new RuntimeException("Seznam je prazdný");
        }
        else
        {
            posledni.getNextItem().setPreviosItem(posledni.getPreviosItem());
            posledni.getPreviosItem().setNextItem(posledni.getNextItem());
            posledni = posledni.getPreviosItem();
            pocet--;
            aktualni = posledni;
            return aktualni.obj;
        }
    }

    @Override
    public T odeberNaslednika() {
        if(jePrazdny()){
            throw new RuntimeException("Seznam je prazdný");
        }
        else
        {
            aktualni = aktualni.getNextItem();
            aktualni.getNextItem().setPreviosItem(aktualni.getPreviosItem());
            aktualni.getPreviosItem().setNextItem(aktualni.getNextItem());
            if(aktualni==prvni){prvni = aktualni.getNextItem();aktualni = prvni;}
            else if(aktualni == posledni){posledni = aktualni.getPreviosItem();aktualni = posledni;}
            else{aktualni = aktualni.getNextItem();}
            pocet--;
            return aktualni.obj;
        }
    }

    @Override
    public T odeberPredchudce() {
        if(jePrazdny()){
            throw new RuntimeException("Seznam je prazdný");
        }
        else
        {
            aktualni.getPreviosItem();
            aktualni.getNextItem().setPreviosItem(aktualni.getPreviosItem());
            aktualni.getPreviosItem().setNextItem(aktualni.getNextItem());
            if(aktualni==prvni){prvni = aktualni.getNextItem();aktualni = prvni;}
            else if(aktualni == posledni){posledni = aktualni.getPreviosItem();aktualni = posledni;}
            else{aktualni = aktualni.getNextItem();}
            pocet--;
            return aktualni.obj;
        }
    }

    @Override
    public Iterator<T> iterator() { //při posledním metoda hasNext()vratí false
        return new Iterator<T>() {
            private Item<T> current = prvni;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.obj;
                current = current.getNextItem();
                if(current == prvni){
                    current = null;
                }
                return data;
            }
        };
    }
    
//    public int getPosition() {
//        int pos = 1;
//        Item wanted = aktualni;
//        this.zpristupniPrvni();
//        while(aktualni != wanted)
//        {
//            this.zpristupniNaslednika();
//            pos++;
//        }
//        return pos;
//    }
//    
//    public AbstrDoubleList GetList(){
//        return (AbstrDoubleList) aktualni.obj;
//    }
    
    public void vypis(){
        System.out.println(aktualni.obj.toString());
    }
    
    private static class Item<T> {
        final private T obj;
        private Item nextItem = null;
        private Item previosItem = null;

        public Item(T obj) {
            this.obj = obj;
        }

        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPreviosItem() {
            return previosItem;
        }

        public void setNextItem(Item<T> nextItem) {
            this.nextItem = nextItem;
        }

        public void setPreviosItem(Item<T> previosItem) {
            this.previosItem = previosItem;
        }
    }
}