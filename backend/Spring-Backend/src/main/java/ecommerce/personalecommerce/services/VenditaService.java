package ecommerce.personalecommerce.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import ecommerce.personalecommerce.DTOs.VenditaDTO;
import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.entities.Utente;
import ecommerce.personalecommerce.entities.Vendita;
import ecommerce.personalecommerce.repositories.ProdottoRepository;
import ecommerce.personalecommerce.repositories.UtenteRepository;
import ecommerce.personalecommerce.repositories.VenditaRepository;
import ecommerce.personalecommerce.utilities.exceptions.ProductDoesntExistException;
import ecommerce.personalecommerce.utilities.exceptions.UserDoesntExistException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenditaService {
    
    private final VenditaRepository venditaRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;

    public List<VenditaDTO> getAll(){
        List<Vendita> listaTmp= venditaRepository.findAll();
        List<VenditaDTO> lista= new ArrayList<>();
        for(Vendita v: listaTmp){
            VenditaDTO vDTO=new VenditaDTO(v);
            lista.add(vDTO);
        }
        return lista;
    }

    public List<VenditaDTO> getCompratore(String email) throws RuntimeException{
        Utente u=utenteRepository.findByEmail(email);
        if (u==null) throw new UserDoesntExistException();
        List<Vendita> listaTmp= venditaRepository.findByCompratore(u);
        List<VenditaDTO> lista= new ArrayList<>();
        for(Vendita v: listaTmp){
            VenditaDTO vDTO=new VenditaDTO(v);
            lista.add(vDTO);
        }
        return lista;
    }

    public List<VenditaDTO> getProdotto(String name) throws RuntimeException{
        Prodotto p=prodottoRepository.findByNome(name);
        if (p==null) throw new ProductDoesntExistException();
        List<Vendita> listaTmp= venditaRepository.findByProdotto(p);
        List<VenditaDTO> lista= new ArrayList<>();
        for(Vendita v: listaTmp){
            VenditaDTO vDTO=new VenditaDTO(v);
            lista.add(vDTO);
        }
        return lista;
    }
}
