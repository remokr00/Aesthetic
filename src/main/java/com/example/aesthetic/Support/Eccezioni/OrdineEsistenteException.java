package com.example.aesthetic.Support.Eccezioni;

public class OrdineEsistenteException extends Exception{

    private final String msg = "Utente già esistente";

    public OrdineEsistenteException(){
        super();
        System.out.println(msg);
    }
}
