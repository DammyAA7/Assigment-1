import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner; 

public class Main {
    public static void main(String args[]) {  
        
        Scanner obj = new Scanner(System.in);  // Create a Scanner object 
        System.out.println("\rWelcome! To start a game of patience press 'S' ");

        String response = obj.nextLine();  // Read user input
        if(response.toLowerCase().equals("s")){
            startGame();
            while (true) {
                String r1 = obj.nextLine();
                if(r1.equalsIgnoreCase("shuffle")){
                    System.out.println("New Game Started");
                    startGame();
                } else if(r1.equalsIgnoreCase("quit")){
                    System.out.println("Game terminated");
                    break;
                } else{
                    System.out.println("Invalid input");
                }
            }
        }

    }

    public static void startGame(){
        Deck deck = new Deck();
        deck.shuffleDeck();
        
        deck.distributeCards();
        Stack<Card> shuffledDeck = new Stack<>();
        shuffledDeck.addAll(0, deck.getCards());
        
        deck.showAllPiles();

        System.out.println("\nNumber of cards: " + shuffledDeck.size());

        deck.showGeneralCardPile(shuffledDeck);
    }

}
