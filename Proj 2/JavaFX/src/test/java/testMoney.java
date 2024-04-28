// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testMoney {

    @Test
    public void invalidBet() {

        BlackjackGame game = new BlackjackGame();

        game.setTotalWinnings(0);
        game.setCurrentBet(10);
        assertFalse(game.allowBet());
    }

    @Test
    public void whenWon() {

        BlackjackGame game = new BlackjackGame();
        Card cardA = new Card("DIAMONDS", 5);
        Card cardB = new Card("DIAMONDS", 6);

        game.getPlayerHand().add(cardB);
        game.getBankerHand().add(cardA);

        game.setTotalWinnings(300);
        game.setCurrentBet(10);

        double winnings = game.evaluateWinnings();
        assertEquals(20, winnings);
        assertEquals(320, game.getTotalWinnings());
    }

    @Test
    public void whenLost() {

        BlackjackGame game = new BlackjackGame();
        Card cardA = new Card("DIAMONDS", 5);
        Card cardB = new Card("DIAMONDS", 6);

        game.getPlayerHand().add(cardA);
        game.getBankerHand().add(cardB);

        game.setTotalWinnings(300);
        game.setCurrentBet(10);

        double winnings = game.evaluateWinnings();
        assertEquals(-10, winnings);
        assertEquals(290, game.getTotalWinnings());

    }

    @Test
    public void whenBlackjack() {

        BlackjackGame game = new BlackjackGame();
        Card cardA = new Card("DIAMONDS", 1);
        Card cardB = new Card("DIAMONDS", 10);

        game.getPlayerHand().add(cardA);
        game.getPlayerHand().add(cardB);
        game.getBankerHand().add(cardA);

        game.setTotalWinnings(300);
        game.setCurrentBet(10);

        double winnings = game.evaluateWinnings();
        assertEquals(25, winnings);
        assertEquals(325, game.getTotalWinnings());

    }

    @Test
    public void whenDraw() {

        BlackjackGame game = new BlackjackGame();
        Card cardA = new Card("DIAMONDS", 5);

        game.getPlayerHand().add(cardA);
        game.getBankerHand().add(cardA);

        game.setTotalWinnings(300);
        game.setCurrentBet(10);

        double winnings = game.evaluateWinnings();
        assertEquals(0, winnings);
        assertEquals(300, game.getTotalWinnings());
    }

}
