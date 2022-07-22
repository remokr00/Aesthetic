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

    @ManyToOne
    @JoinColumn(name = "acquirente")
    private Utente acquirente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.MERGE)
    private List<OperaNelCarrello> carrello;

    public Ordine(List<OperaNelCarrello> opereNelCarrello, Utente acquirente) {
        this.carrello = opereNelCarrello;
        this.acquirente = acquirente;
    }

    public Ordine() {
    }
}
