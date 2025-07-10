public class Card {

    private final String suit;
    private final String cardValue;
    final static String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    final static String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(String suit, String cardValue) {
        this.suit = suit;
        this.cardValue = cardValue;
    }

    public static Card generateValues() {
        int randomSuitIdx = (int) (Math.random() * suits.length);
        int randomCardValueIdx = (int) (Math.random() * cardValues.length);
        String randomSuit = suits[randomSuitIdx];
        String randomCardValue = cardValues[randomCardValueIdx];
        return new Card(randomSuit, randomCardValue);
    }

    public String getSuit() {
        return suit;
    }

    public String getCardValue() {
        return cardValue;
    }

    @Override
    public String toString() {
        return "Card: " + cardValue + " of " + suit;
    }

    public int getCardValueAsInt() {
        if (cardValue.equals("A")) {
            return 11;
        } else {
            return Integer.parseInt(cardValue);
        }
    }
}

