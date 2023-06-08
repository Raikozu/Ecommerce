package ecommerce.personalecommerce.utilities.supports;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DataCheck {
    
    public static boolean isAllCorrect(String email, String nome, String cognome, String telefono, String password){
        if(isEmailCorrect(email) && isPasswordCorrect(password) && isNumberCorrect(telefono) && isNameCorrect(nome) && isNameCorrect(cognome)){
            return true;
        }
        return false;
    }       
    public static boolean isEmailCorrect(String email){
        String regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.matches(regex)){
            return true;
        }
        return false;
    }
    
    public static boolean isNameCorrect(String str){
        String regex="^[A-Za-z\\p{L}]+$";
        if (str.matches(regex)){
            return true;
        }
        return false;
    }

    public static boolean isPasswordCorrect(String password){
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (password.matches(regex)){
            return true;
        }
        return false;
    }
    
    public static boolean isNumberCorrect(String number){
        String regex = "^(\\+\\d{1,3})?\\s?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$";
        if (number.matches(regex)){
            return true;
        }
        return false;
    }
}
