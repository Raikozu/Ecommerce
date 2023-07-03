package ecommerce.personalecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.services.ProdottoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prodotto")
public class ProdottoController {
    private final ProdottoService prodottoService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/aggiungiProdotto")
    public ResponseEntity<Object> addProdotto(@RequestBody Prodotto p){
        try {
            return new ResponseEntity<>(prodottoService.addProdotto(p), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/aggiungiQuantita")
    public ResponseEntity<Object> addQuantita(@RequestParam("nome")String name, @RequestParam("quantita") int quantita){
        try {
            return new ResponseEntity<>(prodottoService.addQuantity(name, quantita), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/togliQuantita")
    public ResponseEntity<Object> removeQuantita(@RequestParam("nome")String name, @RequestParam("quantita") int quantita){
        try {
            return new ResponseEntity<>(prodottoService.removeQuantita(name, quantita), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/modificaDescrizione")
    public ResponseEntity<Object> modifyDescrizione(@RequestParam("nome") String name, @RequestParam("descrizione") String descrizione){
        try {
            return new ResponseEntity<>(prodottoService.modifyDescrizione(name, descrizione), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/rimuovi")
    public ResponseEntity<Object> deleteProdotto(@RequestParam("nome") String name){
        try {
            return new ResponseEntity<>(prodottoService.deleteProdotto(name), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{nome}")
    public ResponseEntity<Object> getProdotto(@PathVariable("nome") String name){
        try {
            return new ResponseEntity<>(prodottoService.getProdotto(name), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/listaProdotti")
    public ResponseEntity<Object> getAllProdotto(){
        return new ResponseEntity<>(prodottoService.getAllProdotti(), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/ricerca")
    public ResponseEntity<Object> findByDescrizione(@RequestParam("descrizione") String descrizione, @RequestParam("pagina") int pagina, @RequestParam("dimensione") int dimensione){
        return new ResponseEntity<>(prodottoService.findByDescrizione(descrizione, pagina, dimensione), HttpStatus.OK);
    }
}
