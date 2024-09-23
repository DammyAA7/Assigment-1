import java.util.Random;

public class Main {
    public static void main(String args[]) {
        int[] solutionArray = { 1, 2, 3, 4, 5, 6, 16, 15, 14, 13, 12, 11 };

        //card.getCard("Red", "Spades", 2);
        

        Deck deck = new Deck();
        deck.showDeck();  // Display the entire deck

        deck.shuffleDeck();

        deck.showDeck();


    }
}
