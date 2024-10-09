import java.util.Stack;
import java.util.List;
import java.util.HashMap;

public class Moves extends Deck {
    private HashMap<String, Integer> ranks;

    public Moves(){
        initializeRanks();
    }

    public boolean moveCard(String[] instructions, List<Stack<Card>> piles, Stack<Card> deck){
        String from = instructions[0];
        String to = instructions[1];
        int cardCount = 1;

        if (instructions.length == 3) {
            cardCount = Integer.parseInt(instructions[2]); // Number of cards to move
        }

        Stack<Card> fromPile = getPile(from, piles, deck);
        Stack<Card> toPile = getPile(to, piles, deck);
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
        Card toCard = toPile.peek();

        if(toPile.isEmpty() && ranks.get(fromCard.getRank()) != 13){
            return false;
        }
        
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
        if (identifyPile.startsWith("p")) {
            int pileIndex = Integer.parseInt(identifyPile.substring(1)) - 1;
            if (pileIndex >= 0 && pileIndex < piles.size()) {
                return piles.get(pileIndex); // Get the correct pile
            }
        }
        return null;
    }



    



}