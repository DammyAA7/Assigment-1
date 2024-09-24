import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {  
        Deck deck = new Deck();

        //deck.showDeck();
        deck.shuffleDeck();
        
        List<List<Card>> piles = deck.distributeCards();
        List<Card> shuffledDeck = deck.getCards();
        

        for(int i = 0; i < piles.size(); i++){
            System.out.println("\rPile " + (i + 1));
            for(int j = 0; j < piles.get(i).size(); j++){
                deck.showCard((piles.get(i)).get(j));
                System.out.print(" ");
            }
        }


    }
}
