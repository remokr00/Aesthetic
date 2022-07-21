package com.example.aesthetic.Support.Eccezioni;

public class OpereInesistenteException extends Exception{

    private final String msg = "Opera non esistente";

    public OpereInesistenteException(){
        super();
        System.out.println(msg);
    }
}
