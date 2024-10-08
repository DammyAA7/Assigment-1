import java.util.Stack;
import java.util.List;

public class Moves {

    private boolean isMoveAllowed(Stack<Card> pile1, Stack<Card> pile2, int noOfCards){
        return !pile1.peek().getColor().equals(pile2.peek().getColor());
    }

    public boolean moveCard(String[] instructions, List<Stack<Card>> piles, Stack<Card> deck){
        String from = instructions[0];
        String to = instructions[1];
        int cardCount = 1;

        Stack<Card> fromPile = getPile(from, piles, deck);
        Stack<Card> toPile = getPile(to, piles, deck);
        Stack<Card> tempStack = new Stack<>();

        if(fromPile.peek().getColor().equals(toPile.peek().getClass())){
            return false;
        } else{
            for(int i = 0; i <= cardCount; i++){
                tempStack.push(fromPile.pop());
            }
            
        }

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