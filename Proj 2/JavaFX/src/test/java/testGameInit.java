// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testGameInit {

    @Test
    public void handInit () {

        BlackjackGame game = new BlackjackGame();
        game.startGame();

        assertEquals (2, game.getPlayerHand().size());
        assertEquals (2, game.getBankerHand().size());
    }

    @Test
    public void deckInit () {

        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();

        String[] suitNames = {"CLUBS", "SPADES", "DIAMONDS", "HEARTS"};

        assertEquals(52, dealer.deckSize());

        for (String suit : suitNames) {
            for (int i = 1; i <= 13; i++) {
                assertEquals(i, dealer.drawOne().getValue());
            }
        }
    }

    @Test
    public void deckShuffle () {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        dealer.shuffleDeck();

        assertNotEquals(1, dealer.drawOne());
    }
}
