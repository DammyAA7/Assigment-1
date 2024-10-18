import java.util.Stack;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class Main {
    private static int score = 0; // Variable to track the game score

    public static void main(String[] args) {
        Moves moves = new Moves(); // Initialize Moves class for game moves
        Deck deck = new Deck(); // Create a new deck
        deck.shuffleDeck(); // Shuffle the deck before starting the game
        Queue<Card> tempGenCards = new LinkedList<>(); // Temporary queue for general cards

        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input
        System.out.println("\rWelcome! To start a game of patience, press 'S' ");

        String response = scanner.nextLine(); // Read user input for game start
        if (response.equalsIgnoreCase("s")) {
            startGame(deck); // Start the game if user presses 'S'

            // Main game loop
            while (true) {
                String userInput = scanner.nextLine(); // Read user input during the game
                System.out.println("======================================================================");
                
                if (userInput.equalsIgnoreCase("shuffle")) {
                    // Start a new game by shuffling a new deck
                    System.out.println("New Game Started");
                    deck = new Deck();
                    deck.shuffleDeck();
                    score = 0; // Reset score for new game
                    startGame(deck); // Initialize new game setup
                } else if (userInput.equalsIgnoreCase("quit")) {
                    // Exit the game
                    System.out.println("Game terminated");
                    break;
                } else if (userInput.toLowerCase().contains("move")) {
                    // Handle card move command
                    String[] instructions = decipherMove(userInput.replace("move ", ""));
                    boolean success = moves.moveCard(instructions, deck.getAllPiles(), deck.getGenCards(), deck.getFoundationPiles(), deck);
                    if (success) {
                        updateScore(instructions, deck); // Update score if move is successful
                        continueGame(deck, tempGenCards); // Continue the game after a valid move

                        // Check for game over after each valid move
                        if (isGameOver(deck)) {
                            System.out.println("Game Over! No more moves left.");
                            break; // Exit the loop if the game is over
                            } 
                        }
                    else {
                        System.out.println("Invalid move. Please try again."); // Error message for invalid move
                    }
                } else if (userInput.equalsIgnoreCase("d")) {
                    // Draw a card from the general pile
                    deck.popGenCards(tempGenCards);
                    continueGame(deck, tempGenCards);
                } else {
                    System.out.println("Invalid input. Please enter a valid command."); // Error message for invalid command
                }
            }
        }
        scanner.close();
    }

    // Splits the move command into an array of strings
    public static String[] decipherMove(String move) {
        return move.split("-");
    }

    // Initializes the game by distributing cards and displaying the initial setup
    public static void startGame(Deck deck) {
        deck.distributeCards(); // Distribute cards to piles and foundation piles
        Stack<Card> shuffledDeck = new Stack<>();
        shuffledDeck.addAll(0, deck.getCards()); // Copy shuffled deck

        deck.showAllPiles(); // Display the piles

        System.out.println("\nNumber of cards: " + shuffledDeck.size()); // Show total number of cards

        deck.showGeneralCardPile(shuffledDeck); // Show the top card of the general card pile
        deck.showFoundationPiles(); // Show the foundation piles
        deck.showMaxMovableCards(); // Display max movable cards information

        System.out.println("Current Score: " + score); // Show initial score
    }

    // Updates the score based on the type of move performed
    public static void updateScore(String[] instructions, Deck deck) {
        String from = instructions[0];
        String to = instructions[1];

        if (from.equalsIgnoreCase("g") && to.startsWith("f")) {
            // 10 points for moving from the general pile to a foundation pile
            score += 10;
        } else if (from.startsWith("p") && to.startsWith("f")) {
            // 20 points for moving from a lane to a foundation pile
            score += 20;
        } else if (from.startsWith("p") && to.startsWith("p")) {
            // 5 points for moving between lanes
            score += 5;
        }
    }

    public static boolean isGameOver(Deck deck) {
        // Check if the general cards are empty
        if (!deck.getGenCards().isEmpty()) {
            return false; // The game is not over if there are cards in the general pile
        }
    
        // Check if all lane piles are empty
        // for (LanePile lanePile : deck.getAllPiles()) {
        //     if (!lanePile.isEmpty()) {
        //         return false; // The game is not over if there's at least one card in the lane piles
        //     }
        // }
    
        // If all checks are passed, return true, meaning the game is over
        return true;
    }
    

    // Continues the game by displaying the current state and updating the general pile if necessary
    public static void continueGame(Deck deck, Queue<Card> tempGenCards) {
        deck.showAllPiles(); // Display the piles

        System.out.println("\nNumber of cards: " + (deck.getGenCards().size() + tempGenCards.size())); // Show total number of cards

        // Refill the general pile from temporary queue if the general pile is empty
        if (deck.getGenCards().isEmpty()) {
            deck.setGenCards(tempGenCards);
            tempGenCards.clear();
        }

        deck.showGeneralCardPile(deck.getGenCards()); // Show the top card of the general pile
        deck.showFoundationPiles(); // Show the foundation piles
        deck.showMaxMovableCards(); // Display max movable cards information

        System.out.println("Current Score: " + score); // Show the current score
    }
}
