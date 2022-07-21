package com.example.aesthetic.Support.Eccezioni;

public class ArtistaEsistenteException extends Exception{

    private final String msg = "Artista già esistente";

    public ArtistaEsistenteException(){
        super();
        System.out.println(msg);
    }
}
