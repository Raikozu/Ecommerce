package ecommerce.personalecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.entities.ProdottoNelCarrello;
import ecommerce.personalecommerce.entities.Utente;
import java.util.List;


public interface ProdottoNelCarrelloRepository extends JpaRepository<ProdottoNelCarrello, Integer>{
    
    ProdottoNelCarrello findByCompratoreAndProdotto(Utente u, Prodotto p);
    List<ProdottoNelCarrello> findByCompratore(Utente compratore);
    @Query("SELECT DISTINCT pnc.compratore FROM ProdottoNelCarrello pnc")
    List<Utente> findDistinctCompratoriInCarrello();
}
