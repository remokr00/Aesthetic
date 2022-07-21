package com.example.aesthetic.Repositories;

import com.example.aesthetic.Entities.Carrello;
import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Entities.Ordine;
import com.example.aesthetic.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {


    //Carrello findByUtenteAndProdottoAndAcquisto(Utente utente, Opera opera, Ordine ordine);

    Carrello findByUtenteA(Utente utente);

}
