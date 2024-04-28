// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import java.util.ArrayList;

public class BlackjackGameLogic {


    // given two hands this should return either player or dealer or push depending on who wins.
    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand) {

        int playerVal = handTotal(playerHand1);
        int dealerVal = handTotal(dealerHand);

        if (playerVal > dealerVal) {
            return "player";
        }
        else if (playerVal < dealerVal) {
            return "dealer";
        }
        else {
            return "push";
        }
    }

    // this should return the total value of all cards in the hand.
    public int handTotal(ArrayList<Card> hand) {

        int handVal = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getValue() != 1 || card.getValue() > 10) {
                handVal += card.getValue();
            } else if (card.getValue() > 10) {
                handVal += 10;
            } else {
                numAces++;
            }
        }

        // aces' value is 1 or 11 based on what is most beneficial
        if (numAces > 0) {

            for (int i = 0; i < numAces; i++) {
                if ((handVal+11) <= 21) {
                    handVal += 11;
                }
                else {
                    handVal++;
                }
            }
        }

        return handVal;
    }

    // this method should return true if the dealer should draw another card, i.e. if the value is 16 or less
    public boolean evaluateBankerDraw(ArrayList<Card> hand) {

        int handVal = handTotal(hand);

        if (handVal < 16) {
            return true;
        }
        else {
            return false;
        }
    }

}