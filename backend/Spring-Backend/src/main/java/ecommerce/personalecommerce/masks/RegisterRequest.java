package ecommerce.personalecommerce.masks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
}
