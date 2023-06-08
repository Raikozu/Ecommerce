package ecommerce.personalecommerce.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecommerce.personalecommerce.DTOs.ProdottoNelCarrelloDTO;
import ecommerce.personalecommerce.DTOs.UtenteDTO;
import ecommerce.personalecommerce.DTOs.VenditaDTO;
import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.entities.ProdottoNelCarrello;
import ecommerce.personalecommerce.entities.Ruolo;
import ecommerce.personalecommerce.entities.Utente;
import ecommerce.personalecommerce.entities.Vendita;
import ecommerce.personalecommerce.masks.AuthenticationRequest;
import ecommerce.personalecommerce.masks.AuthenticationResponse;
import ecommerce.personalecommerce.masks.RegisterRequest;
import ecommerce.personalecommerce.repositories.ProdottoNelCarrelloRepository;
import ecommerce.personalecommerce.repositories.ProdottoRepository;
import ecommerce.personalecommerce.repositories.UtenteRepository;
import ecommerce.personalecommerce.repositories.VenditaRepository;
import ecommerce.personalecommerce.utilities.exceptions.DataMismatchException;
import ecommerce.personalecommerce.utilities.exceptions.MissingUserDataException;
import ecommerce.personalecommerce.utilities.exceptions.NegativeBalanceException;
import ecommerce.personalecommerce.utilities.exceptions.NegativeNumberException;
import ecommerce.personalecommerce.utilities.exceptions.ProductDoesntExistException;
import ecommerce.personalecommerce.utilities.exceptions.ProductInPurchaseDoesntExist;
import ecommerce.personalecommerce.utilities.exceptions.UserAlreadyExistsException;
import ecommerce.personalecommerce.utilities.exceptions.UserDoesntExistException;
import ecommerce.personalecommerce.utilities.supports.DataCheck;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteService {
    
    private final UtenteRepository utenteRepository;
    private final ProdottoNelCarrelloRepository prodottoNelCarrelloRepository;
    private final ProdottoRepository prodottoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VenditaRepository venditaRepository;

    public AuthenticationResponse register(RegisterRequest request) throws RuntimeException{
        if (utenteRepository.findByEmail(request.getEmail())!=null) throw new UserAlreadyExistsException();
        String email=request.getEmail();
        String password=request.getPassword();
        if (email==null || password==null) throw new MissingUserDataException();
        String nome=request.getNome();
        String cognome=request.getCognome();
        String telefono=request.getTelefono();
        if(!DataCheck.isAllCorrect(email, nome, cognome, telefono, password)) throw new DataMismatchException();
        Utente u= new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setTelefono(telefono);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.setRuolo(Ruolo.USER);
        utenteRepository.save(u);
        String jwtToken = jwtService.generateToken(u);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws RuntimeException {  
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Utente u= utenteRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(u);
        return new AuthenticationResponse(jwtToken);
    }

    public Utente modify(String email, String nome, String cognome, String password, String telefono) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if(nome!=null && DataCheck.isNameCorrect(nome)) u.setNome(nome);
        if(cognome!=null && DataCheck.isNameCorrect(cognome)) u.setCognome(cognome);
        if(password!=null && DataCheck.isPasswordCorrect(password)) u.setPassword(passwordEncoder.encode(password));
        if(telefono!=null && DataCheck.isNumberCorrect(telefono)) u.setTelefono(telefono);
        utenteRepository.save(u);
        return u;
    }

    public Utente addSaldo(String email, double saldo) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        if(saldo<=0) throw new NegativeNumberException();
        u.setSaldo(u.getSaldo()+saldo);
        utenteRepository.save(u);
        return u;
    }

    public double getSaldo(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        return u.getSaldo();
    }

    public UtenteDTO getUtente(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        UtenteDTO utenteDTO=new UtenteDTO(u);
        return utenteDTO;
    }

    public List<UtenteDTO> getAllUtente() {
        List<Utente> listaUtenti=utenteRepository.findAll();
        List<UtenteDTO> listaUtentiDTO= new ArrayList<>();
        for (Utente u:listaUtenti){
            UtenteDTO tmp=new UtenteDTO(u);
            listaUtentiDTO.add(tmp);
        }
        return listaUtentiDTO;
    }

    @Transactional
    public Utente deleteUtente(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        utenteRepository.delete(u);
        return u;
    }

    public ProdottoNelCarrelloDTO addProdottoNelCarrello(String email, String name, int quantity) throws RuntimeException{
        Prodotto p=prodottoRepository.findByNome(name);
        if (p==null ||p.isDisponibile()==false) throw new ProductDoesntExistException();
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        if (quantity<0 || quantity>p.getQuantita()) throw new NegativeNumberException();
        ProdottoNelCarrello prodottoNelCarrello=prodottoNelCarrelloRepository.findByCompratoreAndProdotto(u, p);
        double totPrezzo;
        if (prodottoNelCarrello==null){
            totPrezzo=p.getPrezzoUnitario()*quantity;
            prodottoNelCarrello= new ProdottoNelCarrello(null, u, p, quantity, totPrezzo);
        }
        else {
            int totQuantity=prodottoNelCarrello.getQuantita() + quantity;
            totPrezzo=totQuantity*p.getPrezzoUnitario();
            if (totQuantity>p.getQuantita()) throw new NegativeNumberException();
            prodottoNelCarrello.setQuantita(totQuantity);
            prodottoNelCarrello.setPrezzo(totPrezzo);
        }
        prodottoNelCarrelloRepository.save(prodottoNelCarrello);
        ProdottoNelCarrelloDTO prodotto= new ProdottoNelCarrelloDTO(prodottoNelCarrello);
        return prodotto;
    }

    @Transactional
    public ProdottoNelCarrelloDTO removeQuantitaFromProdottoNelCarrelloDTO(String email, String name, int quantity) throws RuntimeException{
        Prodotto p=prodottoRepository.findByNome(name);
        if (p==null) throw new ProductDoesntExistException();
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        if (quantity<0) throw new NegativeNumberException();
        ProdottoNelCarrello prodottoNelCarrello=prodottoNelCarrelloRepository.findByCompratoreAndProdotto(u, p);
        if (prodottoNelCarrello==null) throw new ProductInPurchaseDoesntExist();
        prodottoNelCarrello.setQuantita(prodottoNelCarrello.getQuantita()-quantity);
        prodottoNelCarrello.setPrezzo(prodottoNelCarrello.getQuantita()*p.getPrezzoUnitario());
        ProdottoNelCarrelloDTO prodotto=new ProdottoNelCarrelloDTO(prodottoNelCarrello);
        if (prodottoNelCarrello.getQuantita()<=0){

            prodottoNelCarrelloRepository.delete(prodottoNelCarrello);
            prodotto.setQuantita(0);
        }
        return prodotto;
    }

    @Transactional
    public List<ProdottoNelCarrelloDTO> removeProdottoNelCarrello(String email, String name) throws RuntimeException{
        Prodotto p=prodottoRepository.findByNome(name);
        if (p==null) throw new ProductDoesntExistException();
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        ProdottoNelCarrello prodottoNelCarrello=prodottoNelCarrelloRepository.findByCompratoreAndProdotto(u, p);
        if (prodottoNelCarrello==null) throw new ProductInPurchaseDoesntExist();
        prodottoNelCarrelloRepository.delete(prodottoNelCarrello);
        return getCompratoreCarrello(email);
    }

    public List<ProdottoNelCarrelloDTO> getCompratoreCarrello(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        List<ProdottoNelCarrello> listaTmp=prodottoNelCarrelloRepository.findByCompratore(u);
        List<ProdottoNelCarrelloDTO> lista=new ArrayList<>();
        for (ProdottoNelCarrello pTmp: listaTmp){
            ProdottoNelCarrelloDTO p=new ProdottoNelCarrelloDTO(pTmp);
            lista.add(p);
        }
        return lista;
    }

    public Map<String, List<ProdottoNelCarrelloDTO>> getAll(){
        List<Utente> utenti=prodottoNelCarrelloRepository.findDistinctCompratoriInCarrello();
        Map<String, List<ProdottoNelCarrelloDTO>> listaCarrelli=new HashMap<>();
        for(Utente u:utenti){
            listaCarrelli.put(u.getEmail(),getCompratoreCarrello(u.getEmail()));
        }
        return listaCarrelli;
    }

    @Transactional
    public VenditaDTO buyProdottoNelCarrello(String email, String name){
        Prodotto p=prodottoRepository.findByNome(name);
        if (p==null) throw new ProductDoesntExistException();
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        ProdottoNelCarrello prodottoNelCarrello=prodottoNelCarrelloRepository.findByCompratoreAndProdotto(u, p);
        if (prodottoNelCarrello==null) throw new ProductInPurchaseDoesntExist();
        if (u.getSaldo()< prodottoNelCarrello.getPrezzo()) throw new NegativeBalanceException();
        Vendita v=new Vendita(null, null, u, p, prodottoNelCarrello.getQuantita(), prodottoNelCarrello.getPrezzo());
        venditaRepository.save(v);
        removeProdottoNelCarrello(email, name);
        u.setSaldo(u.getSaldo()-prodottoNelCarrello.getPrezzo());
        utenteRepository.save(u);
        VenditaDTO vDTO= new VenditaDTO(v);
        return vDTO;
    }

    @Transactional
    public List<VenditaDTO> buyCarrello(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        List<ProdottoNelCarrello> lista=prodottoNelCarrelloRepository.findByCompratore(u);
        if(lista==null) throw new ProductInPurchaseDoesntExist();
        List<VenditaDTO> vendite=new ArrayList<>();
        for(ProdottoNelCarrello p:lista){
            vendite.add(buyProdottoNelCarrello(email, p.getProdotto().getNome()));
        }
        return vendite;
    }

}
