// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testHit {

    @Test
    public void singleHit() {

        BlackjackGame game = new BlackjackGame();
        game.startGame();

        assertEquals(2, game.getPlayerHand().size());
        assertEquals(2, game.getBankerHand().size());

        game.playerHit();
        assertEquals(3, game.getPlayerHand().size());
        assertEquals(2, game.getBankerHand().size());
    }

    @Test
    public void multiHits() {

        BlackjackGame game = new BlackjackGame();
        game.startGame();

        assertEquals(2, game.getPlayerHand().size());
        assertEquals(2, game.getBankerHand().size());

        game.playerHit();
        assertEquals(3, game.getPlayerHand().size());
        assertEquals(2, game.getBankerHand().size());
        game.playerHit();
        assertEquals(4, game.getPlayerHand().size());
        assertEquals(2, game.getBankerHand().size());
    }

    @Test
    public void detectBust() {

        BlackjackGame game = new BlackjackGame();
        game.startGame();

        Card cardA = new Card("DIAMONDS", 21);

        game.getPlayerHand().add(cardA);
        int isBust = game.playerHit();
        assertEquals(0, isBust);
    }


}
