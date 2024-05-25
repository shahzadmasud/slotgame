package shahzad.game.model;

public class Card {
    public final CardRank rank;
    public final CardSuit suit;

    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }
}
