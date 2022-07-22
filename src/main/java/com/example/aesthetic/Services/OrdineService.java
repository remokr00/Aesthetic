package com.example.aesthetic.Services;

import com.example.aesthetic.Entities.Opera;
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
    public Ordine creaOrdine(Ordine ordine) throws OrdineEsistenteException, OpereInesistenteException, UtenteInesistenteExcepiton {
        if(!utenteRepository.existsByCodiceFiscale(ordine.getAcquirente().getCodiceFiscale())){
            throw new UtenteInesistenteExcepiton();
        }
        List<Opera> carrello = ordine.getCarrello();
        return ordineRepository.save(ordine);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> getOrdine(Utente utente) throws UtenteInesistenteExcepiton {
        if(!utenteRepository.existsByMail(utente.getMail())){
            throw new UtenteInesistenteExcepiton();
        }
        return ordineRepository.findByAcquirente(utente);
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
    public Ordine ricercaPerOpera(Opera opera){
        return ordineRepository.findByOpera(opera);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Ordine ricercaPerCodice(Integer codice){
        return ordineRepository.findByCodice(codice);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> findAll(){return ordineRepository.findAll();}

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaOrdineDi(String codFiscale) throws UtenteInesistenteExcepiton {
        if(!utenteRepository.existsByCodiceFiscale(codFiscale)){
            throw new UtenteInesistenteExcepiton();
        }
        ordineRepository.deleteByCFAcquirente(codFiscale);
    }
}
