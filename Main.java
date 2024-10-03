import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {  
        Deck deck = new Deck();

        //deck.showDeck();
        deck.shuffleDeck();
        
        List<List<Card>> piles = deck.distributeCards();
        List<Card> shuffledDeck = deck.getCards();
        
        showAllPiles(piles, deck);
        showAllPiles(piles, deck);

        


    }

    public static void showAllPiles(List<List<Card>> piles, Deck deck){
        for(int i = 0; i < piles.size(); i++){
            System.out.println("\rPile " + (i + 1));
            System.out.println("\runshown cards - " + (piles.get(i).size() - 1));
            int last_card = piles.get(i).size() - 1;
            deck.showCard((piles.get(i)).get(last_card));
        }
    }
}
