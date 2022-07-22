package com.example.aesthetic.Repositories;
//Implementata da Gallo Ilaria

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Entities.Ordine;
import com.example.aesthetic.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {

    //troviamo gli acquisti dell'utente x
    List<Ordine> findByAcquirente(Utente acquirente);

    boolean existsByAcquirente(Utente acquirente);

    //troviamo gli ordini effettuati in data x
    List<Ordine> findByData(Date data);

    //troviamo gli acquisti fatti dall'utente x in data y
    @Query("SELECT o "+
            "FROM Ordine o "+
            "WHERE o.data > :inizio AND o.data < :fine AND o.acquirente = :utente")
    List<Ordine> findByBuyerInPeriod(Date inizio, Date fine, Utente acquirente);


    boolean existsByCodice(Integer codice);

    void deleteByCodice(Integer codice);
}
