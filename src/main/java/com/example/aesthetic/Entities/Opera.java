package com.example.aesthetic.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;


import javax.persistence.*;
import java.util.List;


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

    @Version
    @Column(name = "versione", nullable = false)
    @JsonIgnore
    private long versione;

    @OneToMany(targetEntity = Carrello.class, mappedBy = "opera", cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    private List<Carrello> carrello;



}
