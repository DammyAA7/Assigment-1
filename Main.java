import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {  
        Deck deck = new Deck();

        //deck.showDeck();
        deck.shuffleDeck();
        
        List<Stack<Card>> piles = deck.distributeCards();
        Stack<Card> shuffledDeck = new Stack<>();
        shuffledDeck.addAll(0, deck.getCards());
        
        deck.showAllPiles(piles);

        System.out.println("\nNumber of cards: " + shuffledDeck.size());

        deck.showGeneralCardPile(shuffledDeck);

        


    }

}
