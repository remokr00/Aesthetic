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

    //troviamo gli ordini effettuati in data x
    List<Ordine> findByData(Date data);

    //troviamo gli acquisti fatti dall'utente x in data y
    @Query("SELECT o "+
            "FROM Ordine o "+
            "WHERE o.data > :inizio AND o.data < :fine AND o.acquirente = :utente")
    List<Ordine> findByBuyerInPeriod(Date inizio, Date fine, Utente acquirente);

    //troviamo gli ordini di una particolare opera (supponendo esistano solo pezzi unici)
    Ordine findByOpera(Opera opera);

    //troviamo gli ordini con un determinato codice
    Ordine findByCodice(Integer codice);

    //verifico se l'ordine che ha il seguente codice esiste
    boolean existsByCodice(int codice);

    //elimino gli ordini associati al codice
    void deleteByCodice(int codice);

    //elimino gli ordini associati a un utente
    @Query("SELECT o "+
            "FROM Ordine o "+
            "WHERE o.acquirente.codiceFiscale = :cf ")
    void deleteByCFAcquirente(String cf);

}
