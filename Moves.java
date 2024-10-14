import java.util.Stack;
import java.util.List;
import java.util.HashMap;

public class Moves {
    private HashMap<String, Integer> ranks;
    private HashMap<String, Integer> suits;


    public Moves(){
        initializeRanks();
        suits = new HashMap<>();
        suits.put("Spades", 1);
        suits.put("Diamonds", 2);
        suits.put("Clubs", 3);
        suits.put("Hearts", 4);
    }

    public boolean moveCard(String[] instructions, List<Stack<Card>> piles, Stack<Card> deck, List<Stack<Card>> foundationPiles, Deck d){
        
        String from = instructions[0];
        String to = instructions[1];
        int cardCount = 1;

        Stack<Card> fromPile;
        Stack<Card> toPile;
        
        try{
            fromPile = getPile(from, piles, deck, foundationPiles);
            toPile = getPile(to, piles, deck, foundationPiles);
        } catch (IllegalArgumentException e){
            return false;
        }

        if(to.startsWith("f")){
            System.out.println("true");
            int foundationPileIndex = Integer.parseInt(to.substring(1));
            return isMoveToFoundationPileAllowed(foundationPileIndex, fromPile, toPile, cardCount);
        }

        
        if (instructions.length == 3) {
            cardCount = Integer.parseInt(instructions[2]); // Number of cards to move
        } else {
            return false;
        }        
        
        
        Stack<Card> tempStack = new Stack<>();



        if(!isMoveAllowed(fromPile, toPile, cardCount)){
            return false;
        } else{
            for(int i = 0; i < cardCount; i++){
                tempStack.push(fromPile.pop());
            }
            for(int i = 0; i < cardCount; i++){
                toPile.push(tempStack.pop());
            }
            // Increase max movable cards for the fromPile by the number of cards moved
            String pileIdentifier = to; // Assume the from pile identifier is in the correct format
            System.out.println(pileIdentifier);
            //d.increaseMaxMovableCards(pileIdentifier, cardCount); // Increase by the number of cards moved 
        }
        return true; // Move successful

    } 

    private void initializeRanks() {
        ranks = new HashMap<>();
        ranks.put("A", 1); // Ace
        for (int i = 2; i <= 10; i++) {
            ranks.put(String.valueOf(i), i); // 2-10
        }
        ranks.put("J", 11); // Jack
        ranks.put("Q", 12); // Queen
        ranks.put("K", 13); // King
    }

    private boolean isMoveToFoundationPileAllowed(int foundationPileIndex, Stack<Card> fromPile, Stack<Card> toPile, int cardCount){
        
        if (fromPile.isEmpty()){
            return false;
        }

        int fromCardSuit = suits.get(fromPile.peek().getSuit());
        int fromCardRank = ranks.get(fromPile.peek().getRank());
        
        if(fromCardSuit != foundationPileIndex){
            return false;
        }

        if(fromCardRank != 1 && toPile.isEmpty()){
            return false;
        }

        if(!toPile.isEmpty()){
            int toCardRank = ranks.get(toPile.peek().getRank());
            System.out.println("rank" + toCardRank);
            if((fromCardRank - toCardRank) != 1){
                return false;
            }
        }
        

        toPile.push(fromPile.pop());
        return true;
    }

    private boolean isMoveAllowed(Stack<Card> fromPile, Stack<Card> toPile, int cardCount){
        Card card1;
        Card card2;
        int pileSize = fromPile.size();
        
        //Validate move by checking if the from Pile has enough cards
        if (pileSize < cardCount) {
            return false;
        }

        // Get the card from fromPile that will be moved and compare it to the top of toPile
        Card fromCard = fromPile.get(pileSize - cardCount);
        if(toPile.isEmpty()){
            return ranks.get(fromCard.getRank()) == 13;
        }

        Card toCard = toPile.peek();

        if(fromCard.getColor().equals(toCard.getColor()) || 
        ranks.get(fromCard.getRank()) - ranks.get(toCard.getRank()) != -1){
            return false;
        }

        for(int i = 0; i < cardCount - 1; i++){

            card1 = fromPile.get(pileSize - 1 - i);
            card2 = fromPile.get(pileSize - 2 - i);


            if(card1.getColor().equals(card2.getColor())  || ranks.get(card1.getRank()) - ranks.get(card2.getRank()) != -1){
                return false;
            } 
        }
        return true;    
    }


    private Stack<Card> getPile(String identifyPile, List<Stack<Card>> piles, Stack<Card> deck, List<Stack<Card>> foundationPiles){
        if(identifyPile.equals("g")){
            return deck;
        }
        try{
            if (identifyPile.startsWith("f")) {
                int foundationPileIndex = Integer.parseInt(identifyPile.substring(1)) - 1;
                
                if (foundationPileIndex >= 0 && foundationPileIndex < foundationPiles.size()) {
                    return foundationPiles.get(foundationPileIndex); // Get the correct pile
                } else {
                    throw new IllegalArgumentException("Error: Foundation Pile " + (foundationPileIndex + 1) + " is out of range.");
                }
            }
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Error: Invalid pile identifier. Expected format: p[number].");
        }
        try{
            if (identifyPile.startsWith("p")) {
                int pileIndex = Integer.parseInt(identifyPile.substring(1)) - 1;
                
                if (pileIndex >= 0 && pileIndex < piles.size()) {
                    return piles.get(pileIndex); // Get the correct pile
                } else {
                    throw new IllegalArgumentException("Error: Pile " + (pileIndex + 1) + " is out of range.");
                }
            }
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Error: Invalid pile identifier. Expected format: p[number].");
        }
        
        throw new IllegalArgumentException("Error: Unrecognized pile identifier. Use 'g' for deck, 'f[number] for foundation piles' or 'p[number]' for piles.");
    }

}