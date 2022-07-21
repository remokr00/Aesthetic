package com.example.aesthetic.Support.Eccezioni;

public class UtenteInesistenteExcepiton extends Exception{

    private final String msg = "Utente non esistente";

    public UtenteInesistenteExcepiton(){
        super();
        System.out.println(msg);
    }

}
