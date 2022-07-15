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

    @ManyToOne
    @JoinColumn(name = "acquirente")
    private Utente acquirente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.MERGE)
    private List<Carrello> carrello;

}
