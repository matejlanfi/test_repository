package com.mycompany.semestrakaa;

import java.util.Iterator;

/**
 *
 * @author matej
 */
public interface IAbstrDoubleList<T> {
    void zrus();
    boolean jePrazdny();
    
    void vlozPrvni (T data);
    void vlozPosledni(T data);
    void vlozNaslednika (T data);
    void vlozPredchudce(T data);
    
    T zpristupniAktualni();
    T zpristupniPrvni();
    T zpristupniPosledni();
    T zpristupniNaslednika();
    T zpristupniPredchudce();
    
    T odeberAktualni();
    T OdeberPrvni();
    T odeberPosledni();
    T odeberNaslednika();
    T odeberPredchudce();
    
    Iterator<T> iterator();
}