package com.example.aesthetic.Repositories;

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Entities.OperaNelCarrello;
import com.example.aesthetic.Entities.Ordine;
import com.example.aesthetic.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperaNelCarrelloRepository extends JpaRepository<OperaNelCarrello, Integer> {


    OperaNelCarrello findByUtenteAndProdottoAndOrdine(Utente utente, Opera opera, Ordine ordine);

    OperaNelCarrello findByUtente(Utente utente);

}
