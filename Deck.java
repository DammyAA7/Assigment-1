import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList; // import the ArrayList class
import java.util.HashMap;

public class Deck {
    private Stack<Card> cards;
    private List<Stack<Card>> piles = new ArrayList<>();
    private List<Stack<Card>> foundationPiles = new ArrayList<>();
    private HashMap<String, Integer> ranks;

    public Deck() {
        this.cards = new Stack<>();
        initializeRanks();
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
         

        // Nested loop to generate all combinations of colors, shapes, and numbers
        
        for (String suit : suits) {
            String color = "";
            
            if(suit.equals("Spades") || suit.equals("Clubs")){
                color = "Black"; 
            } else if(suit.equals("Diamonds") || suit.equals("Hearts")){
                color = "Red";
            }
            for (String rank : ranks.keySet()) {
                this.cards.add(new Card(color, suit, rank)); // Add each card to the deck
            }
        }
    }

    public void initializeRanks() {
        ranks = new HashMap<>();
        ranks.put("A", 1); // Ace
        for (int i = 2; i <= 10; i++) {
            ranks.put(String.valueOf(i), i); // 2-10
        }
        ranks.put("J", 11); // Jack
        ranks.put("Q", 12); // Queen
        ranks.put("K", 13); // King
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

    public List<Stack<Card>> getAllPiles(){
        return piles;
    }

    public Stack<Card> getGenCards(){
        return cards;
    }

    public void showFoundationPiles() {
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        int cardLength = 12; // Height of the card display
        Card card = new Card("", "Empty", "");
        
        // Header to label the foundation piles
        System.out.println("Foundation Piles:");
        for (int i = 0; i < suits.length; i++) {
            System.out.printf(" %s (%s cards)", suits[i], foundationPiles.get(i).size());
            if (i != suits.length - 1) {
                System.out.print("      "); // Add spacing between the labels
            }
        }
        System.out.print("\n");
    
        // Display the top card of each foundation pile, or indicate that the pile is empty
        for (int i = 0; i < cardLength; i++) {
            for (int j = 0; j < foundationPiles.size(); j++) {
                Stack<Card> foundationPile = foundationPiles.get(j);
                
                if (!foundationPile.isEmpty()) {
                    // Print the card's display (line by line)
                    System.out.print(foundationPile.peek().getCard()[i] + "     ");
                } else {
                    // Show empty pile when there are no cards in the foundation
                    System.out.print(card.getCard()[i] + "     ");
                }
            }
            System.out.print("\n");
        }
    }
    

    public void showAllPiles(){
        Card card = new Card("", "Empty", "");
        int cardLength = 12;
        for(int i = 0; i < cardLength; i++){
            if(i == 0){
                for(int j = 0; j < (piles.size() == 0 ? 1 : piles.size()); j++){
                    if(j != 0)
                        System.out.print("             ");
                    System.out.printf("     Pile %s (%s)", j + 1, piles.get(j).size());   
                }
                System.out.print("\n");
            }
            
            for(int j = 0; j < (piles.size() == 0 ? 1 : piles.size()); j++){
                if(j != 0)
                    System.out.print("     ");  
                if (!piles.get(j).isEmpty()) { // Only call peek() if the pile is not empty
                    System.out.print(piles.get(j).peek().getCard()[i] + "     ");
                } else {
                    System.out.print(card.getCard()[i] + "     ");
                }
            }
            System.out.print("\n");
        }
        

    }

    public void showCard(Card card) {
        //System.out.print(card.getCard());
        for (int i = 0; i < card.getCard().length; i++) {
            System.out.println(card.getCard()[i]);
       }  
    }




    public void showGeneralCardPile(Stack<Card> shuffledDeck) {
        showCard(shuffledDeck.peek());
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

    public void distributeCards() {
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            if(i < 4)
                foundationPiles.add(new Stack<>());
            piles.add(new Stack<>());  // Initialize each pile
        }
        int cardIndex = 0;
        for(int i = 0; i < 7; i++){ //O^2 - change to hashmap 
            for(int j = i; j < 7; j++){
                cardIndex = rnd.nextInt(cards.size());
                piles.get(j).push(cards.get(cardIndex));
                cards.remove(cardIndex);
            }
        }
    }

    // Method to get all cards
    public Stack<Card>getCards() {
        return cards;
    }
}
