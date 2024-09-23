import java.util.List;
import java.util.Random;
import java.util.ArrayList; // import the ArrayList class


public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        String[] colors = {"Red", "Black"};
        String[] shapes = {"Spades", "Diamonds", "Clubs", "Hearts"};
        String[] numbers = {"A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}; 

        // Nested loop to generate all combinations of colors, shapes, and numbers
        for (String color : colors) {
            for (String shape : shapes) {
                for (String number : numbers) {
                    this.cards.add(new Card(color, shape, number)); // Add each card to the deck
                }
            }
        }
    }

    public void showDeck() {
        for (Card card : cards) {
            System.out.print(card.getCard());
        }
    }

    public void shuffleDeck(){
        Random rnd = new Random();
        for (int i = cards.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);

            Card card = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, card);
        }
        for (int i = cards.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);

            Card card = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, card);
        }
    }

    // Method to get all cards
    public List<Card> getCards() {
        return cards;
    }
}
