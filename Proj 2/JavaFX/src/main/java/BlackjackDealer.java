// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer {

    private ArrayList<Card> cardDeck = new ArrayList<Card>();


    // this should generate 52 cards, one for each of 13 faces and 4 suits
    public void generateDeck() {

        String[] suitNames = {"clubs", "spades", "diamonds", "hearts"};

        for (String suit : suitNames) {
            for (int i = 1; i <= 13; i++) {
                cardDeck.add(new Card(suit, i));
            }
        }
    }

    // this will return an Arraylist of two cards and leave the remainder of the deck able to be drawn later
    public ArrayList<Card> dealHand() {

        ArrayList<Card> toReturn = new ArrayList<Card>();

        toReturn.add(cardDeck.remove(0));
        toReturn.add(cardDeck.remove(0));

        return toReturn;
    }

    //  this will return the next card on top of the deck
    public Card drawOne() {

        return cardDeck.remove(0);
    }

    // this will return all 52 cards to the deck and shuffle their order
    public void shuffleDeck() {

        Collections.shuffle(cardDeck);
    }

    // this will return the number of cards left in the deck. After a call to shuffleDeck() this should be 52.
    public int deckSize() {

        return cardDeck.size();
    }
}