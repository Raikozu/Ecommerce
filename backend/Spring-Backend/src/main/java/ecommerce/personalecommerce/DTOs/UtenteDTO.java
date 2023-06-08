package ecommerce.personalecommerce.DTOs;

import ecommerce.personalecommerce.entities.Utente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UtenteDTO {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private double saldo;

    public UtenteDTO(Utente u){
        nome=u.getNome();
        cognome=u.getCognome();
        email=u.getEmail();
        password=u.getPassword();
        telefono=u.getTelefono();
        saldo=u.getSaldo();
    }
}
