public class Main {
    public static void main(String args[]) {
        Card card = new Card("black", "clubs", "2");

        //card.getCard("Red", "Spades", 2);
        

        Deck deck = new Deck();
        deck.showDeck();  // Display the entire deck

    }
}
