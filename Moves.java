import java.util.Stack;
import java.util.List;
import java.util.HashMap;

public class Moves {
    private HashMap<String, Integer> ranks;

    public Moves(){
        initializeRanks();
    }

    public boolean moveCard(String[] instructions, List<Stack<Card>> piles, Stack<Card> deck){
        
        String from = instructions[0];
        String to;
        int cardCount = 1;
        if (instructions.length == 2) {
            to = instructions[1];
        } else if (instructions.length == 3) {
            to = instructions[1];
            cardCount = Integer.parseInt(instructions[2]); // Number of cards to move
        } else {
            return false;
        Stack<Card> fromPile;
        Stack<Card> toPile;
        
        try{
            fromPile = getPile(from, piles, deck);
            toPile = getPile(to, piles, deck);
        } catch (IllegalArgumentException e){
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

    private boolean isMoveAllowed(Stack<Card> fromPile, Stack<Card> toPile, int cardCount){
        Card card1;
        Card card2;
        int pileSize = fromPile.size();
        
        // Validate move by checking if the fromPile has enough cards
        if (pileSize < cardCount) {
            return false;
        }

        // Get the card from fromPile that will be moved and compare it to the top of toPile
        Card fromCard = fromPile.get(pileSize - cardCount);
        if(toPile.isEmpty()){
            return ranks.get(fromCard.getRank()) == 13;
        }

        Card toCard = toPile.peek();

        System.out.println("From card rank: " + fromCard.getRank());
        System.out.println("To card rank: " + toCard.getRank());

        System.out.println("Rank value of fromCard: " + ranks.get(fromCard.getRank()));
        System.out.println("Rank value of toCard: " + ranks.get(toCard.getRank()));

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
    private Stack<Card> getPile(String identifyPile, List<Stack<Card>> piles, Stack<Card> deck){
        if(identifyPile.equals("g")){
            return deck;
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
        
        throw new IllegalArgumentException("Error: Unrecognized pile identifier. Use 'g' for deck or 'p[number]' for piles.");
    }



    



}