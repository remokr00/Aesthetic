package com.example.aesthetic.Entities;

//Implementata da Irtuso Remo

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "utente", schema = "Aesthetic")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente", nullable = false)
    private int idUtente;

    @Basic
    @Column(name = "codice_fiscale", nullable = false, length = 15)
    private String codiceFiscale;

    @Basic
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Basic
    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Basic
    @Column(name = "data_di_nascita", nullable = false, length = 10)
    private String dataDiNascita;

    @Basic
    @Column(name = "indirizzo", length = 50)
    private String indirizzo;

    @Basic
    @Column(name = "e_mail", length = 50)
    private String mail;


    @OneToMany(mappedBy = "acquirente", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<OperaNelCarrello> carrello;


}