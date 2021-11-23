import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class BankAccountTest {

    public static Scanner sc = new Scanner(System.in);

    @Test
    void mainMenu() {

        String cardNumber = "21312";
        String pin  = "213123";
        Operation operate = new Operation(cardNumber, pin);
        boolean expected = BankAccount.mainMenu(sc, operate, cardNumber);
        Assertions.assertFalse(expected);
    }

    @Test
    void mainMenuA() {

        String cardNumber = "21312";
        String pin  = "213123";
        Operation operate = new Operation(cardNumber, pin);
        boolean expected = BankAccount.mainMenuA(sc);
        Assertions.assertFalse(expected);
    }

    @Test
    void regOrLog() {
        String cardNumber = "21312";
        String pin  = "213123";
        Operation operate = new Operation(cardNumber, pin);
        boolean expected = BankAccount.regOrLog(sc);
        Assertions.assertFalse(expected);
    }
}
