public class Player {
    private String name;
    private int bet;
    private int bank;
    private String move;

    public Player(String name, int bet, int bank) {
        this.name = name;
        this.bet = bet;
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
