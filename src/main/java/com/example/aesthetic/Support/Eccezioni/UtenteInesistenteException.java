package com.example.aesthetic.Support.Eccezioni;

public class UtenteInesistenteException extends Exception{

    private final String msg = "Utente non esistente";

    public UtenteInesistenteException(){
        super();
        System.out.println(msg);
    }

}
