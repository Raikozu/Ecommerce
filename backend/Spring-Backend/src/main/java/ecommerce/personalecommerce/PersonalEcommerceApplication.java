package ecommerce.personalecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.entities.ProdottoNelCarrello;
import ecommerce.personalecommerce.entities.Ruolo;
import ecommerce.personalecommerce.entities.Utente;
import ecommerce.personalecommerce.repositories.ProdottoNelCarrelloRepository;
import ecommerce.personalecommerce.repositories.ProdottoRepository;
import ecommerce.personalecommerce.repositories.UtenteRepository;
import ecommerce.personalecommerce.services.UtenteService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class PersonalEcommerceApplication {
	private final UtenteRepository utenteRepository;
	private final ProdottoRepository prodottoRepository;
	private final ProdottoNelCarrelloRepository prodottoNelCarrelloRepository;
	public static void main(String[] args) {
		SpringApplication.run(PersonalEcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UtenteService utenteService){
		return args -> {
			Utente u=new Utente(null, "Fra", "Gaglia", "3479732831", "fra@", "$2a$10$x8sJFBus3n/nEt8cmpPWYOZrbPpHw2erA2e31.tMumoyM3jqaVLEK", 100, true, Ruolo.ADMIN, null);
			utenteRepository.save(u);
			Prodotto p=new Prodotto(null,"pane",3,"bellissimo",3.50,true,null);
			prodottoRepository.save(p);
			ProdottoNelCarrello pNC=new ProdottoNelCarrello(null,u,p,2,7);
			prodottoNelCarrelloRepository.save(pNC);
		};

	}
}
