package com.example.aesthetic.Controllers;
import com.example.aesthetic.Entities.Utente;
import com.example.aesthetic.Services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/aggiungi")
    public Utente aggiungiUtente(@RequestBody @Valid Utente utente){
        return utenteService.registraUtente(utente);
    }

    @GetMapping("/get")
    public ResponseEntity<Utente> getUtente( @RequestParam(value = "email")String email){
        if(utenteService.getUtente(email)!=null)
            return  ResponseEntity.ok().body(utenteService.getUtente(email));
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getall")
    List<Utente> getAll(){
        return utenteService.getAllUtenti();
    }





}
