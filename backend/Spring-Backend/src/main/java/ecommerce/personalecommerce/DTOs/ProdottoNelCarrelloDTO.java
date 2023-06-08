package ecommerce.personalecommerce.DTOs;

import ecommerce.personalecommerce.entities.ProdottoNelCarrello;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdottoNelCarrelloDTO {
    
    private String nome;
    private String descrizione;
    private int quantita;
    private double prezzo;

    public ProdottoNelCarrelloDTO (ProdottoNelCarrello p){
        nome=p.getProdotto().getNome();
        descrizione=p.getProdotto().getDescrizione();
        quantita=p.getQuantita();
        prezzo=p.getPrezzo();
    }
}
