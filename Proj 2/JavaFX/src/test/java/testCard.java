// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testCard {

    @Test
    public void singleCard() {

        Card cardA = new Card("DIAMONDS", 3);
        assertEquals(3, cardA.getValue());
    }

    @Test
    public void multipleCards() {

        ArrayList<Card> setOfCards = new ArrayList<Card>();

        for (int i = 0; i < 10; i++) {
            Card card = new Card("DIAMONDS", i);
            setOfCards.add(card);
        }

        for (int j = 0; j < 10; j++) {
            assertEquals(j, setOfCards.get(j).getValue());
        }
    }

}
