// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testShowCards {

    @Test
    public void showLessThan3() {

        BlackjackGame game = new BlackjackGame();
        game.startGame();

        ArrayList<Card> shownCards = game.showPlayerCards();

        for (int i = 0; i < game.getPlayerHand().size(); i++) {
            if (i == 2) {
                assertEquals(null, shownCards.get(i));
                continue;
            }
            assertNotEquals(null, shownCards.get(i));
        }
    }

    @Test
    public void showMoreThan3() {

        BlackjackGame game = new BlackjackGame();
        game.startGame();
        game.playerHit();
        game.playerHit();

        ArrayList<Card> shownCards = game.showPlayerCards();

        for (int i = 0; i < game.getPlayerHand().size(); i++) {
            if (i == 2) {
                assertEquals(null, shownCards.get(i));
                continue;
            }
            assertNotEquals(null, shownCards.get(i));
        }
    }
}
