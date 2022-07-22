package com.example.aesthetic.Controllers;


import com.example.demo.entities.Acquisto;
import com.example.demo.services.AcquistoService;
import com.example.demo.support.Exception.CarrelloVuotoException;
import com.example.demo.support.Exception.QuantitàProdottoNonDisponibileException;
import com.example.demo.support.Exception.UtenteNonTrovatoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrdineController {

    @Autowired
    AcquistoService acquistoService;

   @PutMapping("/addAcquisto")
    public Acquisto addAcquisto(@RequestParam(value = "email")String email) {
        try {
            return acquistoService.addAcquisto(email);
        } catch (QuantitàProdottoNonDisponibileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product quantity unavailable!", e);
        } catch (CarrelloVuotoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Carrello vuoto",e);
        }
   }

    @GetMapping("/getAcquisti")
    public List<Acquisto> getAcquisti(@RequestParam(value = "email") String email) {
        try {
            return acquistoService.getAcquistoByUtente(email);
        } catch (UtenteNonTrovatoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!", e);
        }
    }


}
