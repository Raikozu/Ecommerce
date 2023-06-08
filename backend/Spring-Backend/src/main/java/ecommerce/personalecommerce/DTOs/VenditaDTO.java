package ecommerce.personalecommerce.DTOs;

import java.util.Date;

import ecommerce.personalecommerce.entities.Vendita;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VenditaDTO {
    
    private Date data;
    private String email;
    private String nomeProdotto;
    private int quantita;
    private double prezzo;

    public VenditaDTO(Vendita v){
        data=v.getData();
        email=v.getCompratore().getEmail();
        nomeProdotto=v.getProdotto().getNome();
        quantita=v.getQuantita();
        prezzo=v.getPrezzoTotale();
    }
}
