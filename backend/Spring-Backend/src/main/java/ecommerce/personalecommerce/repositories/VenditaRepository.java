package ecommerce.personalecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.entities.Utente;
import ecommerce.personalecommerce.entities.Vendita;

public interface VenditaRepository extends JpaRepository<Vendita, Integer>{
    
    List<Vendita> findByCompratore(Utente u);

    List<Vendita> findByProdotto(Prodotto p);
}
