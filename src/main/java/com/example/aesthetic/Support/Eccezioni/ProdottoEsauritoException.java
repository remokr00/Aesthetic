package com.example.aesthetic.Support.Eccezioni;

public class ProdottoEsauritoException extends Exception{
    private final String msg = "Prodotto esaurito";

    public ProdottoEsauritoException(){
        super();
        System.out.println(msg);
    }
}


