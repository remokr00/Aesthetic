package com.example.aesthetic.Repositories;

import com.example.aesthetic.Entities.OperaNelCarrello;
import com.example.aesthetic.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrelloRepository extends JpaRepository<OperaNelCarrello, Integer> {


    //Carrello findByUtenteAndProdottoAndAcquisto(Utente utente, Opera opera, Ordine ordine);

    OperaNelCarrello findByUtenteA(Utente utente);

}
