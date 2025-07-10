import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        initializeDeck();
    }

    public void initializeDeck() {
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String value : cardValues) {
                deck.add(new Card(suit, value));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card dealCard() {
        if (isEmpty()) {
            return null;
        }
        return deck.remove(deck.size() - 1);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public void resetDeck() {
        deck.clear();
        initializeDeck();
        shuffleDeck();
    }
}
