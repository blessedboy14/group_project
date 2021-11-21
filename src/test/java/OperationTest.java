import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    @DisplayName("Этот тест проверяет функцию возвращения баланса")
    void showBalance() {
        String pin = "qwerty12345";
        String cardNumber = "31597";
        Operation operate = new Operation(cardNumber, pin);
        int actual = operate.showBalance(cardNumber);
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Этот тест проверяет функцию возвращения баланса")
    void deposit() {
        String pin = "Qdasd125fdaasd123eda%^32";
        String cardNumber = "44356";
        String memo = "hello";
        int amount = 10;
        Operation operate = new Operation(cardNumber, pin);
        Assertions.assertFalse(operate.deposit(amount, cardNumber, memo));
    }

    @Test
    @DisplayName("Этот тест проверяет функцию возвращения баланса")
    void sendToOther() {
        String pin = "123qwerty123";
        String cardNumber = "18386";
        String memo = "hello";
        int amount = 10;
        String cardNumber2 = "18386";
        Operation operate = new Operation(cardNumber, pin);
        Assertions.assertFalse(operate.sendToOther(amount,cardNumber2, cardNumber, memo));
    }

    @Test
    @DisplayName("Этот тест проверяет функцию возвращения баланса")
    void refill() {
        String pin = "123451";
        String cardNumber = "36897";
        String memo = "hello";
        int amount = 10;
        Operation operate = new Operation(cardNumber, pin);
        Assertions.assertFalse(operate.refill(amount, cardNumber, memo));
    }

    @Test
    @DisplayName("Этот тест проверяет функцию возвращения баланса")
    void showTransHistory() {
        String pin = "sadaf";
        String cardNumber = "98346";
        Operation operate = new Operation(cardNumber, pin);
        Assertions.assertFalse(operate.showTransHistory(cardNumber));
    }
}
