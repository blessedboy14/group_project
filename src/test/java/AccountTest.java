import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {
    
    @Test
    @DisplayName("range test")
    void register(){
        String num = "0";
        Account anAcc = new Account("Иван", "Петров");
        boolean expected = anAcc.register();
        Assertions.assertFalse(expected);
    }
}
