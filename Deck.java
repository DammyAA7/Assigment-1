import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList; // import the ArrayList class
import java.util.HashMap;

public class Deck {
    private Stack<Card> cards; // Stack to hold the deck of cards
    private List<Stack<Card>> piles = new ArrayList<>(); // List of card piles on the table
    private List<Stack<Card>> foundationPiles = new ArrayList<>(); // List of foundation piles (for each suit)
    private HashMap<String, Integer> ranks; // Map to store card ranks
    private HashMap<String, Integer> maxMovableCards; // Map to store max movable cards for each pile

    public Deck() {
        this.cards = new Stack<>();
        initializeRanks();
        initializeMaxMovableCards();
        createDeck();
    }

    // Creates a standard deck of 52 cards
    private void createDeck() {
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        for (String suit : suits) {
            String color = (suit.equals("Spades") || suit.equals("Clubs")) ? "Black" : "Red";
            for (String rank : ranks.keySet()) {
                cards.add(new Card(color, suit, rank)); // Add each card to the deck
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

    // New method to initialize max movable cards for each pile
    private void initializeMaxMovableCards() {
        maxMovableCards = new HashMap<>();
        for (int i = 0; i < 7; i++) { // Assuming 7 piles
            maxMovableCards.put("p" + (i + 1), 1); // Initial value can be set to 1 or another logic as needed
        }
    }

    // Shuffles the deck of cards using the Fisher-Yates algorithm
    public void shuffleDeck(){
        Random rnd = new Random();
        for (int i = cards.size() - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            Card card = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, card);
        }
        for (int i = cards.size() - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            Card card = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, card);
        }
    }

    // Distributes cards to the piles and initializes the foundation piles
    public void distributeCards() {
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            if(i < 4)
                foundationPiles.add(new Stack<>());// Create foundation piles for the first 4 suits
            piles.add(new Stack<>());  // Initialize each pile
        }
        // Create foundation piles for the first 4 suits
        int cardIndex = 0;
        for(int i = 0; i < 7; i++){ 
            for(int j = i; j < 7; j++){
                cardIndex = rnd.nextInt(cards.size());
                piles.get(j).push(cards.get(cardIndex));
                cards.remove(cardIndex);
            }
        }
    }

    // Increases the number of max movable cards for a given pile
    public void increaseMaxMovableCards(String pileIdentifier, int numberOfCards){
        int currentMax = maxMovableCards.get(pileIdentifier);
        maxMovableCards.put(pileIdentifier, currentMax + numberOfCards);
    }

    // Shows the maximum number of movable cards for each pile
    public void showMaxMovableCards() {
        System.out.println("Max Movable Cards for Each Pile:");
        for (int i = 0; i < piles.size(); i++) {
            String pileIdentifier = "p" + (i + 1); // Pile identifier
            int maxMovable = maxMovableCards.get(pileIdentifier); // Get max movable cards for the pile
            System.out.printf(" %s: %d\n", pileIdentifier, maxMovable); // Display pile identifier and max movable count
        }
    }

     // Getter for maxMovableCards
     public HashMap<String, Integer> getMaxMovableCards() {
        return maxMovableCards;
    }

    // Method to update max movable cards for a specific pile
    public void updateMaxMovable(String pileIdentifier, int newMaxMovable) {
        maxMovableCards.put(pileIdentifier, newMaxMovable);
    }
    

    // Shows all cards in the deck
    public void showDeck() {
        for (Card card : cards) {
            System.out.print(card.getCard());
        }
    }

    // Shows the top card of each pile
    public void showPile(List<Card> pile) {
        for (Card card : pile) {
            System.out.print(card.getCard());
        }
    }

    // Gets all card piles
    public List<Stack<Card>> getAllPiles() {
        return piles;
    }

    // Gets the general card stack (deck)
    public Stack<Card> getGenCards() {
        return cards;
    }

    // Sets the general card stack from a temporary queue
    public void setGenCards(Queue<Card> tempGenCards) {
        cards.clear();
        cards.addAll(tempGenCards);
    }

    // Pops a card from the general card stack
    public void popGenCards(Queue<Card> newGen) {
        if (!cards.isEmpty()) {
            newGen.add(cards.pop());
        }
    }

    // Gets the foundation piles
    public List<Stack<Card>> getFoundationPiles() {
        return foundationPiles;
    } 

    // Displays the foundation piles, including the top card of each pile
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
    
    // Displays all the piles on the table
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

    // Method to get all cards
    public Stack<Card>getCards() {
        return cards;
    }
}
