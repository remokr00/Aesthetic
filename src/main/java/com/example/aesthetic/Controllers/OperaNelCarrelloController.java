package com.example.aesthetic.Controllers;
import com.example.demo.entities.Prodotto;
import com.example.demo.entities.ProdottoNelCarrello;
import com.example.demo.entities.Utente;
import com.example.demo.services.ProdottoNelCarrelloService;
import com.example.demo.services.ProdottoService;
import com.example.demo.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class OperaNelCarrelloController {

    @Autowired
    private ProdottoNelCarrelloService prodottoNelCarrelloService;

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ProdottoService prodottoService;


    @GetMapping("/carrello/getcarrello")
    public List<ProdottoNelCarrello> getCarrello(@RequestParam(value = "email",required = true) String email){
        return   prodottoNelCarrelloService.getCarrello(email);
    }

    @DeleteMapping("/carrello/elimina")
    public boolean eliminaDaCarrello(@RequestParam(value = "email",required = true) String email,@RequestParam(value = "id",required = true) String id){
        System.out.println("elimina richiamato");
        Long id1= Long.valueOf(id);
        System.out.println("Id prodotto: " +id1);
        List<ProdottoNelCarrello> l =  prodottoNelCarrelloService.getCarrello(email).stream().filter(pnc->{return pnc.getAcquisto()==null;}).toList();
        for(ProdottoNelCarrello p :l) {
            if(p.getId()==id1) {
                prodottoNelCarrelloService.rimuoviProdotto(p);
                return true;
            }
        }
        return false;
    }

    @PutMapping("/carrello/addcarello")
    public ProdottoNelCarrello addCarrello( @RequestParam(value = "email")String email,@RequestParam(value = "nomeProdotto") String  nomeProdotto, @RequestParam(value = "quantità")String quantità){
        int quantita= Integer.valueOf(quantità);
        Utente u = utenteService.getUtente(email);
        Prodotto p = prodottoService.showProductsByName(nomeProdotto);
        ProdottoNelCarrello prodottoNelCarrello = new ProdottoNelCarrello(p,quantita,u);
        return prodottoNelCarrelloService.add(u,p,prodottoNelCarrello,quantita);

    }
}
