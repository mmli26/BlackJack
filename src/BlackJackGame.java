import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackGame {
    private final Deck deck;
    private final Player player;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private final Scanner scanner;
    private boolean doubleDown;

    public BlackJackGame() {
        deck = new Deck();
        deck.shuffleDeck();
        player = new Player("Player", 0, 200);
        Player dealer = new Player("Dealer", 0, 0);
        scanner = new Scanner(System.in);
    }

    public void playGame() {
        boolean playAgain = true;
        while (playAgain) {
            if (deck.isEmpty()) {
                deck.resetDeck();
            }

            System.out.println("\nWelcome to Blackjack!");
            System.out.println("Current Bank: $" + player.getBank());

            int bet = getValidBet();
            player.setBet(bet);
            player.setBank(player.getBank() - bet);

            playerHand = new ArrayList<>();
            dealerHand = new ArrayList<>();
            doubleDown = false;

            playerHand.add(deck.dealCard());
            playerHand.add(deck.dealCard());
            dealerHand.add(deck.dealCard());
            dealerHand.add(deck.dealCard());

            displayHands(false);

            if (calculateHandValue(playerHand) == 21) {
                handleBlackjack();
            } else {
                playerTurn();
                if (!doubleDown && calculateHandValue(playerHand) <= 21) {
                    dealerPlays();
                }
                determineWinner();
            }

            System.out.println("Your bank: $" + player.getBank());
            System.out.println("Net Gain/Loss: $" + (player.getBank() - 200));

            String playAgainInput = getValidMove("\nDo you want to play again? (y/n): ", "y", "n");
            playAgain = playAgainInput.equals("y");
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void playerTurn() {
        boolean firstMove = true;

        while (calculateHandValue(playerHand) < 21) {
            if (firstMove && player.getBank() >= player.getBet()) {
                String move = getValidMove("Hit, Stand, or Double Down? (h/s/d): ", "h", "s", "d");
                if (move.equals("h")) {
                    playerHand.add(deck.dealCard());
                    displayHands(false);
                } else if (move.equals("d")) {
                    doubleDown = true;
                    player.setBank(player.getBank() - player.getBet());
                    player.setBet(player.getBet() * 2);
                    playerHand.add(deck.dealCard());
                    displayHands(false);
                    break;
                } else {
                    break;
                }
            } else {
                String move = getValidMove("Hit or Stand? (h/s): ", "h", "s");
                if (move.equals("h")) {
                    playerHand.add(deck.dealCard());
                    displayHands(false);
                } else {
                    break;
                }
            }
            firstMove = false;
        }
    }

    private void handleBlackjack() {
        int dealerScore = calculateHandValue(dealerHand);

        System.out.println("\nBlackjack! You win automatically unless the dealer also has Blackjack.");
        displayHands(true);

        if (dealerScore == 21) {
            System.out.println("Dealer also has Blackjack! It's a tie.");
            player.setBank(player.getBank() + player.getBet());
        } else {
            System.out.println("Blackjack pays 2:1!");
            player.setBank(player.getBank() + (player.getBet() * 3));
        }
    }

    private void dealerPlays() {
        while (calculateHandValue(dealerHand) < 17) {
            System.out.println("Dealer hits...");
            dealerHand.add(deck.dealCard());
        }
        System.out.println("Dealer stands.");
        displayHands(true);
    }

    private void displayHands(boolean revealDealer) {
        System.out.println("\nYour hand: " + playerHand + " (Value: " + calculateHandValue(playerHand) + ")");
        if (revealDealer) {
            System.out.println("Dealer's hand: " + dealerHand + " (Value: " + calculateHandValue(dealerHand) + ")");
        } else {
            System.out.println("Dealer's hand: [Hidden, " + dealerHand.get(1) + "]");
        }
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int aces = 0;
        for (Card card : hand) {
            if (card.getCardValue().matches("J|Q|K")) {
                value += 10;
            } else if (card.getCardValue().equals("A")) {
                value += 11;
                aces++;
            } else {
                value += Integer.parseInt(card.getCardValue());
            }
        }
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }

    private void determineWinner() {
        int playerScore = calculateHandValue(playerHand);
        int dealerScore = calculateHandValue(dealerHand);

        if (playerScore > 21) {
            System.out.println("Bust! You lose.");
        } else if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("You win!");
            player.setBank(player.getBank() + (player.getBet() * 2));
        } else if (playerScore == dealerScore) {
            System.out.println("It's a tie!");
            player.setBank(player.getBank() + player.getBet());
        } else {
            System.out.println("Dealer wins!");
        }
    }

    private int getValidBet() {
        int bet;
        while (true) {
            try {
                System.out.print("Enter your bet amount: ");
                bet = Integer.parseInt(scanner.next().trim());
                if (bet > 0 && bet <= player.getBank()) {
                    return bet;
                }
                System.out.println("Invalid bet. Please enter an amount within your bank balance.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric bet.");
            }
        }
    }

    private String getValidMove(String prompt, String option1, String option2) {
        return getValidMove(prompt, option1, option2, null);
    }

    private String getValidMove(String prompt, String option1, String option2, String option3) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.next().trim().toLowerCase();
            if (input.equals(option1) || input.equals(option2) || (option3 != null && input.equals(option3))) {
                return input;
            }
            System.out.println("Invalid input. Please enter '" + option1 + "', '" + option2 + "'" + (option3 != null ? ", or '" + option3 + "'" : "") + ".");
        }
    }
}
