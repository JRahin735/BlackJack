// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class testEndgame {


    @Test
    public void playerWon() {

        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList <Card> playerHand = new ArrayList<Card>();
        ArrayList <Card> bankerHand = new ArrayList<Card>();

        Card cardA = new Card("DIAMONDS", 7);
        Card cardB = new Card("DIAMONDS", 6);

        playerHand.add(cardA);
        playerHand.add(cardA);
        playerHand.add(cardB);

        bankerHand.add(cardA);
        bankerHand.add(cardB);
        bankerHand.add(cardB);

        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("player", winner);
    }

    @Test
    public void playerLost() {

        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList <Card> playerHand = new ArrayList<Card>();
        ArrayList <Card> bankerHand = new ArrayList<Card>();

        Card cardA = new Card("DIAMONDS", 7);
        Card cardB = new Card("DIAMONDS", 6);

        playerHand.add(cardA);
        playerHand.add(cardB);
        playerHand.add(cardB);

        bankerHand.add(cardA);
        bankerHand.add(cardA);
        bankerHand.add(cardB);

        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("dealer", winner);
    }

    @Test
    public void playerDraws() {

        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList <Card> playerHand = new ArrayList<Card>();
        ArrayList <Card> bankerHand = new ArrayList<Card>();

        Card cardA = new Card("DIAMONDS", 7);
        Card cardB = new Card("DIAMONDS", 6);

        playerHand.add(cardA);
        playerHand.add(cardB);
        playerHand.add(cardB);

        bankerHand.add(cardA);
        bankerHand.add(cardB);
        bankerHand.add(cardB);

        String winner = logic.whoWon(playerHand, bankerHand);
        assertEquals("push", winner);
    }

    @Test
    public void aceLogic() {

        BlackjackGameLogic logic = new BlackjackGameLogic();
        ArrayList <Card> playerHand = new ArrayList<Card>();

        Card cardA = new Card("DIAMONDS", 1);
        Card cardB = new Card("DIAMONDS", 20);
        Card cardC = new Card("DIAMONDS", 10);


        playerHand.add(cardA);
        playerHand.add(cardA);
        int total = logic.handTotal(playerHand);
        assertEquals(12, total);

        playerHand.remove(0);
        playerHand.remove(0);

        playerHand.add(cardB);
        playerHand.add(cardA);
        total = logic.handTotal(playerHand);
        assertEquals(21, total);

        playerHand.remove(0);
        playerHand.remove(0);

        playerHand.add(cardC);
        playerHand.add(cardA);
        total = logic.handTotal(playerHand);
        assertEquals(21, total);


    }
}
