// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

public class Card {

    private String suit;
    private int value;

    public Card (String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String getSuit() {
        return suit;
    }
}