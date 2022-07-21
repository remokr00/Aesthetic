package com.example.aesthetic.Services;

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Repositories.OperaRepository;
import com.example.aesthetic.Support.Eccezioni.ArtistaInesistenteException;
import com.example.aesthetic.Support.Eccezioni.OperaEsistenteExcepiton;
import com.example.aesthetic.Support.Eccezioni.OpereInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

//Implementato da Gallo Ilaria

@Service
public class OperaService {

    @Autowired
    private OperaRepository operaRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Opera> mostraTutteLeOpere() { return operaRepository.findAll(); }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Opera aggiungiOpera(Opera opera) throws OperaEsistenteExcepiton, ArtistaInesistenteException {
        if( opera.getCodice() != null || operaRepository.existsByCodice(opera.getCodice() )){
            throw new OperaEsistenteExcepiton();
       }
        return operaRepository.save(opera);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Opera aggiornaOpera(Opera opera) throws OpereInesistenteException {
        if(!operaRepository.existsByCodice(opera.getCodice())){
            operaRepository.save(opera);
        }
        return operaRepository.save(opera);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rimuoviOpera(Integer codice) throws OpereInesistenteException {
        if(!operaRepository.existsByCodice(codice)){
            throw new OpereInesistenteException();
        }
        operaRepository.deleteByCodice(codice);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Opera> paginazione(int numeroPagine, int grandezzaPagina, String ordinaPer){
        Pageable pageable = PageRequest.of(numeroPagine, grandezzaPagina, Sort.by(ordinaPer));
        Page<Opera> risultato = operaRepository.findAll(pageable);
        if(risultato.hasContent()){
            return risultato.getContent();
        }
        else{
            return new LinkedList<>();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Opera> ricercaAvanzata(Integer codice, String nome, String tipologia, Float prezzo1, Float prezzo2){
        List<Opera> risultato = operaRepository.advancedResearch(codice, nome, tipologia, prezzo1, prezzo2);
        return risultato;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Opera> ricercaPerNome(String nome){
        List<Opera> risultato = operaRepository.findByNomeContaining(nome);
        return risultato;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Opera> createDa(String artista) throws ArtistaInesistenteException{
        List<Opera> opere = operaRepository.findByArtista(artista);
        if(opere==null){
            throw new ArtistaInesistenteException();
        }
        return opere;
    }


}
