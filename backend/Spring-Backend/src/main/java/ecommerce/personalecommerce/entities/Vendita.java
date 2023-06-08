package ecommerce.personalecommerce.entities;



import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vendite")
public class Vendita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_acquisto")
    private Date data;
    
    @ManyToOne
    @JoinColumn(name="compratore", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente compratore;

    @ManyToOne
    @JoinColumn(name="prodotto", referencedColumnName = "nome")
    private Prodotto prodotto;

    @Column(name="quantita")
    private int quantita;

    @Column(name="prezzo_totale")
    private double prezzoTotale;
}
