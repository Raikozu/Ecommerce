package ecommerce.personalecommerce.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GenerationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="prodotti_nel_carrello")
public class ProdottoNelCarrello {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="compratore", referencedColumnName = "email")
    private Utente compratore;

    @ManyToOne
    @JoinColumn(name="prodotto", referencedColumnName = "nome")
    private Prodotto prodotto;

    @Column(name="quantita")
    private int quantita;

    @Column(name="prezzo")
    private double prezzo;
}
