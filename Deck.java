import java.util.List;
import java.util.Random;
import java.util.ArrayList; // import the ArrayList class


public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}; 

        // Nested loop to generate all combinations of colors, shapes, and numbers
        
        for (String suit : suits) {
            String color = "";
            
            if(suit.equals("Spades") || suit.equals("Clubs")){
                color = "Black"; 
            } else if(suit.equals("Diamonds") || suit.equals("Hearts")){
                color = "Red";
            }
            for (String rank : ranks) {
                this.cards.add(new Card(color, suit, rank)); // Add each card to the deck
            }
        }
    }

    public void showDeck() {
        for (Card card : cards) {
            System.out.print(card.getCard());
        }
    }

    public void showPile(List<Card> pile) {
        for (Card card : pile) {
            System.out.print(card.getCard());
        }
    }

    public void showCard(Card card) {
        System.out.print(card.getCard());
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

    public List<List<Card>> distributeCards() {
        List<List<Card>> piles = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            piles.add(new ArrayList<>());  // Initialize each pile
        }
        int cardIndex = 0;
        for(int i = 0; i < 7; i++){
            for(int j = i; j < 7; j++){
                cardIndex = rnd.nextInt(cards.size());
                piles.get(j).add(cards.get(cardIndex));
                cards.remove(cardIndex);
            }
        }
        return piles;
    }

    // Method to get all cards
    public List<Card>getCards() {
        return cards;
    }
}
