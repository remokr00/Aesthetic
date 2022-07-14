package com.example.aesthetic.Entities;

//Implementata da Irtuso Remo

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "ordine", schema = "Aesthetic")
public class Ordine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice", nullable = false)
    private int codice;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data", nullable = false)
    @CreationTimestamp
    private Date data;

    @Basic
    @Column(name = "numero_carta", nullable = false, length = 20, unique = true)
    private String carta;

    @Basic
    @Column(name = "scadenza", nullable = false, length = 5)
    private String scadenza;

    @Basic
    @Column(name = "CVV", nullable = false, length = 3, unique = true)
    private String cvv;

    //definisco le relazioni
    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente acquirente;

    @ManyToMany
    @JoinTable(name = "carrello", joinColumns = @JoinColumn(name = "codice_prodotto"), inverseJoinColumns = @JoinColumn(name = "codice_opera"))
    private List<Opera> prodotti;


}
