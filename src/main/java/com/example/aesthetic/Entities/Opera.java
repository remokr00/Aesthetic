package com.example.aesthetic.Entities;


import lombok.Data;


import javax.persistence.*;


@Entity
@Data
@Table(name = "opera", schema = "Aesthetic")
public class Opera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice", nullable = false)
    private int codice;

    @Basic
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Basic
    @Column(name = "artista", nullable = false, length = 50)
    private String artista;

    @Basic
    @Column(name = "descrizione", length = 500)
    private String descrizione;

    @Basic
    @Column(name = "prezzo", nullable = false)
    private float prezzo;

    @Basic
    @Column(name = "quantita", nullable = false)
    private int quantita;

    @Basic
    @Column(name = "tipologia", length = 20)
    private String tipologia;

}
