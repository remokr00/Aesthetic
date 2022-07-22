package com.example.aesthetic.Controllers;

import com.example.aesthetic.Entities.Opera;
import com.example.aesthetic.Services.OperaService;
import com.example.aesthetic.Support.Eccezioni.ArtistaInesistenteException;
import com.example.aesthetic.Support.Eccezioni.OperaEsistenteExcepiton;
import com.example.aesthetic.Support.Eccezioni.OpereInesistenteException;
import com.example.aesthetic.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/opere")
public class OperaController {
    @Autowired
    private OperaService operaService;


    @GetMapping("/tutte_le_opere")
    public ResponseEntity<List<Opera>> mostraTutte(){
        return new ResponseEntity<>(operaService.mostraTutteLeOpere(), HttpStatus.OK);
    }

    //metodo per creare le liste di opera però su diverse pagine
    @GetMapping("/paginate")
    public ResponseEntity<List<Opera>> paginazione(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @RequestParam(value = "sortBy", defaultValue = "codice",required = false) String sortBy) {
        List<Opera> result = operaService.paginazione(pageNumber, pageSize, sortBy);
        if ( result.size() <= 0 ) {
            return new ResponseEntity(new Messaggio("Nessun risultato!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/create_Da")
    public ResponseEntity<List<Opera>> createDa(@RequestParam(value = "artista") String artista){
        try{
            List<Opera> ris = operaService.createDa(artista);
            return new ResponseEntity<>(ris, HttpStatus.OK);
        }catch (ArtistaInesistenteException e){
            return new ResponseEntity(new Messaggio("Artista inesistente"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/aggiungi_opera")
    // @PreAuthorize("hasAuthority('amministratore_progetto')")
    public ResponseEntity<Opera> creaOpera(@RequestBody @Valid Opera opera) {
        try {
            Opera nuovo = operaService.aggiungiOpera(opera);
            return new ResponseEntity<>(nuovo, HttpStatus.OK);
        } catch (OperaEsistenteExcepiton e) {
            return new ResponseEntity(new Messaggio("Opera già registrata!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifica_opera")
    // @PreAuthorize("hasAuthority('amministratore_progetto')")
    public ResponseEntity<Opera> modificaOpera(@RequestBody @Valid Opera opera){
        try{
            Opera modificata = operaService.aggiornaOpera(opera);
            return new ResponseEntity<>(modificata, HttpStatus.OK);
        }catch (OpereInesistenteException e){
            return new ResponseEntity(new Messaggio("Opera inesistente!"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/elimina_opera")
    // @PreAuthorize("hasAuthority('amministratore_progetto')")
    public ResponseEntity<Messaggio> eliminaOpera(@RequestParam(value = "codice") Integer codice) {
        try{
            operaService.rimuoviOpera(codice);
            return new ResponseEntity<>(new Messaggio("Opera eliminata con successo"), HttpStatus.OK);
        }catch (OpereInesistenteException e){
            return new ResponseEntity<>(new Messaggio("Opera inesistente"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ricerca_avanzata")
    public ResponseEntity<List<Opera>> ricercaAvanzata(@RequestParam(value = "codice", required = false) Integer codice, @RequestParam(value = "nome",required = false) String nome, @RequestParam(value = "tipologia", required = false) String tipologia, @RequestParam(value = "prezzo1", required = false) Float prezzo1, @RequestParam(value = "prezzo2", required = false) Float prezzo2){
        List<Opera> risultato = operaService.ricercaAvanzata(codice, nome, tipologia, prezzo1, prezzo2);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }

    @GetMapping("/ricerca_per_nome")
    public ResponseEntity<List<Opera>> getByName(@RequestParam(value = "nome")String nome){
        List<Opera> risultato = operaService.ricercaPerNome(nome);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }


}