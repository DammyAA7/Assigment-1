import java.util.Stack;
import java.util.List;
import java.util.HashMap;

public class Moves {
    private HashMap<String, Integer> ranks; // Mapping of card ranks to numerical values
    private HashMap<String, Integer> suits; // Mapping of card suits to numerical values

    public Moves() {
        initializeRanks(); // Initialize rank mappings
        suits = new HashMap<>(); // Initialize suit mappings
        suits.put("Spades", 1);
        suits.put("Diamonds", 2);
        suits.put("Clubs", 3);
        suits.put("Hearts", 4);
    }

    /**
     * Handles moving cards between piles, the deck, or foundation piles.
     * @param instructions Array containing the source, destination, and optional number of cards to move.
     * @param piles List of piles on the board.
     * @param deck Stack representing the general deck.
     * @param foundationPiles List of foundation piles.
     * @param deckInstance The current game deck instance.
     * @return true if the move is successful, false otherwise.
     */

     public boolean moveCard(String[] instructions, List<Stack<Card>> piles, Stack<Card> deck, List<Stack<Card>> foundationPiles, Deck deckInstance) {
        String from = instructions[0];
        String to = instructions[1];
        int cardCount = 1; // Default to moving a single card

        Stack<Card> fromPile;
        Stack<Card> toPile;

        // Determine source and destination piles
        try {
            fromPile = getPile(from, piles, deck, foundationPiles);
            toPile = getPile(to, piles, deck, foundationPiles);
        } catch (IllegalArgumentException e) {
            return false; // Invalid pile identifier
        }

        // Handle move to a foundation pile
        if (to.startsWith("f")) {
            int foundationPileIndex = Integer.parseInt(to.substring(1));
            return isMoveToFoundationPileAllowed(foundationPileIndex, fromPile, toPile, cardCount);
        }

        // Handle move involving multiple cards if specified
        if (instructions.length == 3) {
            cardCount = Integer.parseInt(instructions[2]);
        }

        HashMap<String, Integer> maxMovableCards = deckInstance.getMaxMovableCards();
        String pileIdentifier = to; 
        int maxMovable = maxMovableCards.get(pileIdentifier);

        // Validate that the number of cards being moved does not exceed the maximum allowed
        if (cardCount > maxMovable) {
            return false;
        }

        // Temporary stack to hold cards while transferring from one pile to another
        Stack<Card> tempStack = new Stack<>();

        // Check if the move is allowed
        if (!isMoveAllowed(fromPile, toPile, cardCount)) {
            return false;
        } else {
            // Transfer cards from fromPile to toPile using a temporary stack
            for (int i = 0; i < cardCount; i++) {
                tempStack.push(fromPile.pop());
            }
            for (int i = 0; i < cardCount; i++) {
                toPile.push(tempStack.pop());
            }

            // Update the maximum movable cards for the destination pile
            if (toPile.isEmpty()) {
                deckInstance.updateMaxMovable(pileIdentifier, 0);
            } else {
                deckInstance.updateMaxMovable(pileIdentifier, maxMovable + 1);
            }
        }
        return true; // Move was successful
    }

    /**
     * Initializes the rank mappings for cards.
     */
    private void initializeRanks() {
        ranks = new HashMap<>();
        ranks.put("A", 1); // Ace
        for (int i = 2; i <= 10; i++) {
            ranks.put(String.valueOf(i), i); // Numbers 2-10
        }
        ranks.put("J", 11); // Jack
        ranks.put("Q", 12); // Queen
        ranks.put("K", 13); // King
    }

    /**
     * Checks if moving to a foundation pile is allowed.
     * @param foundationPileIndex The index of the foundation pile.
     * @param fromPile The pile from which cards are being moved.
     * @param toPile The foundation pile to which cards are being moved.
     * @param cardCount The number of cards to move.
     * @return true if the move is allowed, false otherwise.
     */
    private boolean isMoveToFoundationPileAllowed(int foundationPileIndex, Stack<Card> fromPile, Stack<Card> toPile, int cardCount) {
        if (fromPile.isEmpty()) {
            return false; // Cannot move from an empty pile
        }

        int fromCardSuit = suits.get(fromPile.peek().getSuit());
        int fromCardRank = ranks.get(fromPile.peek().getRank());

        // Check if the card's suit matches the foundation pile index
        if (fromCardSuit != foundationPileIndex) {
            return false;
        }

        // If the foundation pile is empty, only an Ace (rank 1) can be placed
        if (fromCardRank != 1 && toPile.isEmpty()) {
            return false;
        }

        // If the foundation pile is not empty, the card being moved must follow the top card by rank
        if (!toPile.isEmpty()) {
            int toCardRank = ranks.get(toPile.peek().getRank());
            if ((fromCardRank - toCardRank) != 1) {
                return false;
            }
        }

        // Perform the move to the foundation pile
        toPile.push(fromPile.pop());
        return true;
    }

    /**
     * Checks if moving a certain number of cards between two piles is allowed.
     * @param fromPile The pile from which cards are being moved.
     * @param toPile The pile to which cards are being moved.
     * @param cardCount The number of cards to move.
     * @return true if the move is allowed, false otherwise.
     */
    private boolean isMoveAllowed(Stack<Card> fromPile, Stack<Card> toPile, int cardCount) {
        int pileSize = fromPile.size();

        // Validate that the source pile has enough cards
        if (pileSize < cardCount) {
            return false;
        }

        // Get the card to be moved and compare it with the top card of the destination pile
        Card fromCard = fromPile.get(pileSize - cardCount);
        if (toPile.isEmpty()) {
            return ranks.get(fromCard.getRank()) == 13; // Only Kings can be moved to an empty pile
        }

        Card toCard = toPile.peek();

        // Validate move by checking color and rank
        if (fromCard.getColor().equals(toCard.getColor()) || ranks.get(fromCard.getRank()) - ranks.get(toCard.getRank()) != -1) {
            return false;
        }

        // Validate the sequence of cards being moved
        for (int i = 0; i < cardCount - 1; i++) {
            Card card1 = fromPile.get(pileSize - 1 - i);
            Card card2 = fromPile.get(pileSize - 2 - i);

            if (card1.getColor().equals(card2.getColor()) || ranks.get(card1.getRank()) - ranks.get(card2.getRank()) != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the appropriate pile based on its identifier.
     * @param identifyPile The identifier of the pile (e.g., "g", "f1", "p2").
     * @param piles The list of piles on the board.
     * @param deck The general deck.
     * @param foundationPiles The list of foundation piles.
     * @return The corresponding Stack<Card> object.
     */
    private Stack<Card> getPile(String identifyPile, List<Stack<Card>> piles, Stack<Card> deck, List<Stack<Card>> foundationPiles) {
        if (identifyPile.equals("g")) {
            return deck; // Return the deck if identifier is "g"
        }
        try {
            // Handle foundation pile identifiers (e.g., "f1")
            if (identifyPile.startsWith("f")) {
                int foundationPileIndex = Integer.parseInt(identifyPile.substring(1)) - 1;
                if (foundationPileIndex >= 0 && foundationPileIndex < foundationPiles.size()) {
                    return foundationPiles.get(foundationPileIndex);
                } else {
                    throw new IllegalArgumentException("Error: Foundation Pile " + (foundationPileIndex + 1) + " is out of range.");
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid pile identifier. Expected format: p[number].");
        }
        try {
            // Handle regular pile identifiers (e.g., "p1")
            if (identifyPile.startsWith("p")) {
                int pileIndex = Integer.parseInt(identifyPile.substring(1)) - 1;
                if (pileIndex >= 0 && pileIndex < piles.size()) {
                    return piles.get(pileIndex);
                } else {
                    throw new IllegalArgumentException("Error: Pile " + (pileIndex + 1) + " is out of range.");
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid pile identifier. Expected format: p[number].");
        }

        // If the identifier does not match any known format, throw an exception
        throw new IllegalArgumentException("Error: Unrecognized pile identifier. Use 'g' for deck, 'f[number] for foundation piles' or 'p[number]' for piles.");
    }
}