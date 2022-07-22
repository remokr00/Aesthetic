package com.example.aesthetic.Services;

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Entities.OperaNelCarrello;
import com.example.aesthetic.Entities.Ordine;
import com.example.aesthetic.Entities.Utente;
import com.example.aesthetic.Repositories.OperaRepository;
import com.example.aesthetic.Repositories.OrdineRepository;
import com.example.aesthetic.Repositories.UtenteRepository;
import com.example.aesthetic.Support.Eccezioni.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private OperaRepository operaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Ordine creaOrdine(String email) throws OrdineEsistenteException, OpereInesistenteException, UtenteInesistenteException, CarrelloVuotoException, ProdottoEsauritoException {
       Utente utente = utenteRepository.findByMailContaining(email);
       List<OperaNelCarrello> carrello = utente.getCarrello();
       if(carrello==null || carrello.size()==0) throw new CarrelloVuotoException();
       Ordine ordine = ordineRepository.save(new Ordine(carrello, utente));
       for(OperaNelCarrello o: carrello){
           o.setOrdine(ordine);
           entityManager.merge(ordine);
           Opera opera = o.getOpera();
           entityManager.lock(opera, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
           int quant = opera.getQuantita() - o.getQuantita();
           if(quant<0){
               throw new ProdottoEsauritoException();
           }
           opera.setQuantita(quant);
           entityManager.merge(opera);
       }
       return ordine;
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> getAcquistoByUtente(String email) throws UtenteInesistenteException {
        if ( !utenteRepository.existsByMail(email) ) {
            throw new UtenteInesistenteException();
        }
        return ordineRepository.findByAcquirente(utenteRepository.findByMailContaining(email));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaOrdine(Integer codice) throws OrdineInesistenteException {
        if(!ordineRepository.existsByCodice(codice)){
            throw new OrdineInesistenteException();
        }
        ordineRepository.deleteByCodice(codice);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Ordine aggiorna(Ordine ordine) throws OrdineInesistenteException {
        if(!ordineRepository.existsByCodice(ordine.getCodice())){
            throw new OrdineInesistenteException();
        }
        return ordineRepository.save(ordine);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> ricercaPerData(Date data){
        return ordineRepository.findByData(data);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Ordine> ricercaPerPeriodoDiAcquisto(Date inizio, Date fine, Utente utente){
        return ordineRepository.findByBuyerInPeriod(inizio, fine, utente);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> findAll(){return ordineRepository.findAll();}

}
