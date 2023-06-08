package ecommerce.personalecommerce.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.personalecommerce.entities.Prodotto;
import java.util.List;



public interface ProdottoRepository extends JpaRepository<Prodotto, Integer>{
    
    Prodotto findByNome(String nome);
    List<Prodotto> findByDisponibile(boolean disponibile);
    Prodotto findByNomeAndDisponibile(String nome, boolean disponibile);
    Page<Prodotto> findByDescrizione(String descrizione, PageRequest pageable);
}
