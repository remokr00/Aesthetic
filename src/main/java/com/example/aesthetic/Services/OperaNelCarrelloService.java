package com.example.aesthetic.Services;

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Entities.OperaNelCarrello;
import com.example.aesthetic.Entities.Utente;
import com.example.aesthetic.Repositories.OperaNelCarrelloRepository;
import com.example.aesthetic.Repositories.OperaRepository;
import com.example.aesthetic.Repositories.UtenteRepository;
import com.example.aesthetic.Support.Eccezioni.OpereInesistenteException;
import com.example.aesthetic.Support.Eccezioni.ProdottoEsauritoException;
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
public class OperaNelCarrelloService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OperaNelCarrelloRepository operaNelCarrelloRepository;

    @Autowired
    private OperaRepository operaRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteService utenteService;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<OperaNelCarrello> getCarrello(String email) throws UtenteInesistenteException {
        if(! utenteRepository.existsByMail(email))
            throw new UtenteInesistenteException();
        Utente utente = utenteService.getUtente(email);
        return  utente.getCarrello();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void rimuoviProdotto(OperaNelCarrello o) {operaNelCarrelloRepository.delete(o);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OperaNelCarrello aggiorna(OperaNelCarrello o) {
        return operaNelCarrelloRepository.save(o);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public OperaNelCarrello add(Utente utente, Opera opera, int quantita) throws UtenteInesistenteException, OpereInesistenteException, ProdottoEsauritoException {
        if(! utenteRepository.existsByCodiceFiscale(utente.getCodiceFiscale()))
            throw new UtenteInesistenteException();
        if(! operaRepository.existsByCodice(opera.getCodice()))
            throw new OpereInesistenteException();
        if(opera.getQuantita()<quantita)
            throw new ProdottoEsauritoException();
        //controllo se l'utente ha gia nell'ordine l'opera, in questo caso aggiorno la quantità
        OperaNelCarrello o;
        if(operaNelCarrelloRepository.findByUtenteAndProdottoAndOrdine(utente, opera , null)!=null){
            o = operaNelCarrelloRepository.findByUtenteAndProdottoAndOrdine(utente, opera, null);
            o.setQuantita(o.getQuantita() + quantita);
            return entityManager.merge(o);
        }else{
            o = new OperaNelCarrello(opera, quantita, utente);
            operaNelCarrelloRepository.save(o);
        }
       return o;
    }







}
