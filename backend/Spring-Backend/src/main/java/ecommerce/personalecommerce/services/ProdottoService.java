package ecommerce.personalecommerce.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ecommerce.personalecommerce.entities.Prodotto;
import ecommerce.personalecommerce.repositories.ProdottoRepository;
import ecommerce.personalecommerce.utilities.exceptions.NegativeNumberException;
import ecommerce.personalecommerce.utilities.exceptions.ProductAlreadyExistsException;
import ecommerce.personalecommerce.utilities.exceptions.ProductDoesntExistException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdottoService {
    private final ProdottoRepository prodottoRepository;

    public Prodotto addProdotto(Prodotto p) throws RuntimeException{
        Prodotto tmp=prodottoRepository.findByNome(p.getNome());
        if (tmp!=null && tmp.isDisponibile()==true) throw new ProductAlreadyExistsException();
        if (tmp!=null && tmp.isDisponibile()==false) tmp.setDisponibile(true);
        prodottoRepository.save(p);
        return p;
    }

    public Prodotto addQuantity(String name, int quantita) throws RuntimeException{
        if (quantita<0) throw new NegativeNumberException();
        Prodotto tmp=prodottoRepository.findByNome(name);
        if(tmp==null) throw new ProductAlreadyExistsException();
        tmp.setQuantita(tmp.getQuantita()+quantita);
        prodottoRepository.save(tmp);
        return tmp;
    }

    public Prodotto removeQuantita(String name, int quantita) throws RuntimeException{
        Prodotto tmp=prodottoRepository.findByNome(name);
        if(tmp==null) throw new ProductAlreadyExistsException();
        int totale=tmp.getQuantita()-quantita;
        if (totale<0 || quantita<0) throw new NegativeNumberException();
        tmp.setQuantita(tmp.getQuantita()-quantita);
        prodottoRepository.save(tmp);
        return tmp;
    }

    public Prodotto modifyDescrizione(String name, String descrizione) throws RuntimeException{
        Prodotto tmp=prodottoRepository.findByNome(name);
        if (tmp==null)throw new ProductDoesntExistException();
        tmp.setDescrizione(descrizione);
        prodottoRepository.save(tmp);
        return tmp;
    }
    public Prodotto deleteProdotto(String name) throws RuntimeException{
        Prodotto tmp=prodottoRepository.findByNome(name);
        if(tmp==null || tmp.isDisponibile()==false) throw new ProductDoesntExistException();
        tmp.setDisponibile(false);
        prodottoRepository.save(tmp);
        return tmp;
    }
    public Prodotto getProdotto(String name) throws RuntimeException{
        Prodotto tmp=prodottoRepository.findByNome(name);
        if (tmp==null) throw new ProductDoesntExistException();
        return tmp;
    }
    public List<Prodotto> getAllProdotti(){
        return prodottoRepository.findAll();
    }
    public Page<Prodotto> findByDescrizione(String descrizione, int nPage, int nDimension){
        PageRequest pageable=PageRequest.of(nPage, nDimension);
        return prodottoRepository.findByDescrizione(descrizione, pageable);
    }
}
