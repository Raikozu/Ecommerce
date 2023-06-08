package ecommerce.personalecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.personalecommerce.services.VenditaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vendita")
public class VenditaController {
    
    private final VenditaService venditaService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllVendita(){
        try {
            return new ResponseEntity<>(venditaService.getAll(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getCompratore")
    public ResponseEntity<Object> getCompratore(@RequestParam("email") String email){
        try {
            return new ResponseEntity<>(venditaService.getCompratore(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getProdotto")
    public ResponseEntity<Object> getProdotto(@RequestParam("nome") String nome){
        try {
            return new ResponseEntity<>(venditaService.getProdotto(nome), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
        }
    }

}
