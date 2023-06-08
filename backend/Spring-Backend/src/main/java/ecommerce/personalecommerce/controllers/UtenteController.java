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

import ecommerce.personalecommerce.masks.AuthenticationRequest;
import ecommerce.personalecommerce.masks.RegisterRequest;
import ecommerce.personalecommerce.services.JwtService;
import ecommerce.personalecommerce.services.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/utente")
@RequiredArgsConstructor
public class UtenteController {
    
    private final UtenteService utenteService;
    private final JwtService jwtService;

    @PostMapping("/registrati")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        try {
            return new ResponseEntity<>(utenteService.register(request), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accedi")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request){
        try {
            return new ResponseEntity<>(utenteService.authenticate(request), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/modifica")
    public ResponseEntity<Object> modify(HttpServletRequest request, @RequestParam(name = "nome", required = false) String nome, @RequestParam(name= "cognome", required = false) String cognome, @RequestParam(name="password" , required = false) String password, @RequestParam(name="telefono" , required = false) String telefono){
        try {
            String email= jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.modify(email, nome, cognome, password, telefono), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping("/aggiungiSaldo")
    public ResponseEntity<Object> addSaldo(HttpServletRequest request, @RequestParam("saldo") double saldo){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.addSaldo(email, saldo), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/saldo")
    public ResponseEntity<Object> getSaldo(HttpServletRequest request){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.getSaldo(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUtente(@PathVariable String email){
        try {
            return new ResponseEntity<>(utenteService.getUtente(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/listaUtenti")
    public ResponseEntity<Object> getAllUtente(){
        try {
            return new ResponseEntity<>(utenteService.getAllUtente(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    //ATTENZIONE, operazione atomica e si perdono i dati di Vendita
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/elimina")
    public ResponseEntity<Object> deleteUtente(@RequestParam("email") String email){
        try {
            return new ResponseEntity<>(utenteService.deleteUtente(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/aggiungi")
    public ResponseEntity<Object> addProdottoNelCarrello(HttpServletRequest request, @RequestParam("nome") String nome, @RequestParam("quantita") int quantita){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.addProdottoNelCarrello(email, nome, quantita), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/rimuoviQuantita")
    public ResponseEntity<Object> removeQuantitaFromProdottoNelCarrello(HttpServletRequest request, @RequestParam("nome") String nome, @RequestParam("quantita") int quantita){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.removeQuantitaFromProdottoNelCarrelloDTO(email, nome, quantita), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/rimuoviProdotto")
    public ResponseEntity<Object> removeQuantitaFromProdottoNelCarrello(HttpServletRequest request, @RequestParam("nome") String nome){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.removeProdottoNelCarrello(email, nome), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/compraSingoloProdotto")
    public ResponseEntity<Object> buyProdottoNelCarrello(HttpServletRequest request, @RequestParam("nome") String nome){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.buyProdottoNelCarrello(email, nome), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping("/compraTutto")
    public ResponseEntity<Object> buyCarrello(HttpServletRequest request){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.buyCarrello(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/carrello")
    public ResponseEntity<Object> removeQuantitaFromProdottoNelCarrello(HttpServletRequest request){
        try {
            String email=jwtService.tokenGetEmail(request);
            return new ResponseEntity<>(utenteService.getCompratoreCarrello(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/carrelli")
    public ResponseEntity<Object> getAllProdottoNelCarrello(){
        try {
            return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
}
