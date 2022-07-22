package com.example.aesthetic.Services;

import com.example.aesthetic.Entities.Utente;
import com.example.aesthetic.Repositories.UtenteRepository;
import com.example.aesthetic.Support.Eccezioni.UtenteEsistenteException;
import com.example.aesthetic.Support.Eccezioni.UtenteInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED) //richiede una nuova transazione
    public Utente registraUtente(Utente utente) throws UtenteEsistenteException {
        if (utenteRepository.existsByMail(utente.getMail()) || utenteRepository.existsByCodiceFiscale(utente.getCodiceFiscale())){
            throw new UtenteEsistenteException();
        }
        //TO DO REGISTRAZIONE CON KEYCLOAK
        return utenteRepository.save(utente);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaUtente(Utente utente) throws UtenteInesistenteException {
        if(!utenteRepository.existsByMail(utente.getMail())){
            throw new UtenteInesistenteException();
        }
        utenteRepository.delete(utente);
    }
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Utente getUtente(String email){
        return utenteRepository.findByMailContaining(email);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Utente> ricercaAvanzata(String nome, String cognome, String codiceFiscale, String dataDiNascita, String mail){
        return utenteRepository.advancedResearch(nome, cognome, codiceFiscale, dataDiNascita, mail);
    }

}
