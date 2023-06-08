package ecommerce.personalecommerce.repositories;



import ecommerce.personalecommerce.entities.Utente;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UtenteRepository extends JpaRepository<Utente, Integer>{
    
    Utente findByEmail(String email);
}
