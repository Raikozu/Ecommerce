package ecommerce.personalecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prodotti")
public class Prodotto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="nome", nullable = false, unique = true)
    private String nome;
    @Column(name="quantita")
    private int quantita;
    @Column(name="descrizione")
    private String descrizione;
    @Column(name="prezzoUnitario", nullable = false)
    private Double prezzoUnitario;
    @Column(name="disponibile")
    private boolean disponibile=true;
    @Version
    @Column(name="version", nullable = false)
    private Long version;
}
