import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner; 
import java.util.Queue;

public class Main {
    public static void main(String args[]) {
        Moves moves = new Moves();  
        Deck deck = new Deck();
        deck.shuffleDeck();
        Queue<Card> tempGenCards = new LinkedList<>();
        
        
        Scanner obj = new Scanner(System.in);  // Create a Scanner object 
        System.out.println("\rWelcome! To start a game of patience press 'S' ");

        String response = obj.nextLine();  // Read user input
        if(response.toLowerCase().equals("s")){
            startGame(deck);
            
            while (true) {
                String r1 = obj.nextLine();
                //System.out.println("======================================================================");
                if(r1.equalsIgnoreCase("shuffle")){
                    System.out.println("New Game Started");
                    deck = new Deck();
                    deck.shuffleDeck();
                    startGame(deck);
                } else if(r1.equalsIgnoreCase("quit")){
                    System.out.println("Game terminated");
                    break;
                } else if(r1.toLowerCase().contains("move")){
                    String[] instructions = decipherMove(r1.replace("move ", ""));
                    boolean success = moves.moveCard(instructions, deck.getAllPiles(), deck.getGenCards(), deck.getFoundationPiles(), deck);
                    if (success){
                        deck.increaseMaxMovableCards(instructions[1], Integer.parseInt(instructions[2]));
                        continueGame(deck, tempGenCards);
                    } else { 
                        System.out.println("Invalid move");
                    }
                    
                } else if(r1.equalsIgnoreCase("d")){
                    deck.popGenCards(tempGenCards);
                    continueGame(deck, tempGenCards);
                    
                }else{
                    System.out.println("Invalid input");
                }
            }
        }

    }

    public static String[] decipherMove(String move){
        return move.split("-");
    }

    public static void startGame(Deck deck){
        
        deck.distributeCards();
        Stack<Card> shuffledDeck = new Stack<>();
        shuffledDeck.addAll(0, deck.getCards());
        
        deck.showAllPiles();

        System.out.println("\nNumber of cards: " + shuffledDeck.size());

        deck.showGeneralCardPile(shuffledDeck);
        deck.showFoundationPiles();

        deck.showMaxMovableCards();
    }

    public static void continueGame(Deck deck, Queue<Card> tempGenCards){ 
        deck.showAllPiles(); 
        System.out.println("\nNumber of cards: " + (deck.getGenCards().size() + tempGenCards.size()));
        if(deck.getGenCards().isEmpty()){
            deck.setGenCards(tempGenCards);
            tempGenCards.clear();
        }
        deck.showGeneralCardPile(deck.getGenCards());
        deck.showFoundationPiles();

        deck.showMaxMovableCards();
    }


}
