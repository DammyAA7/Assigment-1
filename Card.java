import java.sql.Array;
import java.util.Arrays;

public class Card {
    private String color;
    private String suit;
    private String rank; 

    public Card(String color, String suit, String rank){
        this.color = color;
        this.suit = suit;
        this.rank = rank;
    }

    
    public String[] createCard(){
        int cardLength = 12;  // Height of the card
        String[] cardDisplay = new String[12];
        for(int i = 0; i < cardLength; i++){
            if (i == 0 || i == cardLength - 1){
                cardDisplay[i] = "------------------";
            } else {
                cardDisplay[i] = "|                |";  // Empty row
            }
        }
        return cardDisplay;
    }

    public String[] getCard() {
        //String card = "";
        int cardLength = 12;  // Height of the card
        int cardWidth = 16;  // Height of the card
        Object[] cardDetail = new Object[3];
        cardDetail[0] = this.color;
        cardDetail[1] = this.suit;
        cardDetail[2] = this.rank;


        String[] card = createCard();
        int cardDetailIndex = 0;
        for (int i = 3; i < cardLength; i = i + 3) {
            if (i % 3 == 0 && cardDetailIndex < cardDetail.length) {
                String detail = cardDetail[cardDetailIndex].toString();
                int paddingFront = (int) Math.ceil((cardWidth - detail.length()) / 2); // Calculate padding on front side
                int paddingBack = (int) Math.floor((cardWidth - detail.length()) / 2); // Calculate padding on back side
                paddingFront = (detail.length() % 2 == 0) ? paddingBack : paddingBack + 1;
                String paddedDetail = String.format("|%" + paddingFront + "s%s%" + paddingBack + "s|", "", detail, "");
                card[i] = paddedDetail;
                cardDetailIndex++;
            }
        }
        return card;
    }

    public Card emptyCard(){
        return new Card("", "Empty Pile", "");
    }

    public String getSuit(){
        return suit;
    }

    public String getRank(){
        return rank;
    }

    public String getColor(){
        return color;
    }
}
