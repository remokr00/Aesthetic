package com.example.aesthetic.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "carrello", schema = "Aesthetic")
public class OperaNelCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice", nullable = false)
    private int codice;

    @Basic
    @Column(name = "quantita", nullable = true)
    private int quantita;

    @Basic
    @Column(name = "prezzo", nullable = true)
    private float prezzo;

    @ManyToOne
    @JoinColumn(name = "ordine_associato")
    @JsonIgnore
    @ToString.Exclude
    private Ordine ordine;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opera")
    private Opera opera;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utente")
    @JsonIgnore
    @ToString.Exclude
    private Utente utente;

    public OperaNelCarrello(Opera opera,int quantita,Utente utente){
        this.opera = opera;
        this.quantita = quantita;
        this.utente=utente;
    }



    public OperaNelCarrello() {

    }
}
