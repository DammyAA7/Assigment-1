
public class Card {
    private String color;
    private String suit;
    private String rank;

    public Card(String color, String suit, String rank){
        this.color = color;
        this.suit = suit;
        this.rank = rank;
    }


    public String getCard() {
        String card = "";
        int cardLength = 12;  // Height of the card
        int cardWidth = 16;  // Height of the card
        Object[] cardDetail = new Object[3];
        cardDetail[0] = this.color;
        cardDetail[1] = this.suit;
        cardDetail[2] = this.rank;

        int cardDetailIndex = 0;
        for (int i = 0; i < cardLength; i++) {
            if (i == 0 || i == cardLength - 1) {
                card += "------------------\n";  // Top and bottom border
            } else if (i % 3 == 0 && cardDetailIndex < cardDetail.length) {
                String detail = cardDetail[cardDetailIndex].toString();
                int paddingFront = (int) Math.ceil((cardWidth - detail.length()) / 2); // Calculate padding on front side
                int paddingBack = (int) Math.floor((cardWidth - detail.length()) / 2); // Calculate padding on back side
                paddingFront = (detail.length() % 2 == 0) ? paddingBack : paddingBack + 1;
                String paddedDetail = String.format("|%" + paddingFront + "s%s%" + paddingBack + "s|\n", "", detail, "");
                card += paddedDetail;
                cardDetailIndex++;
            } else {
                card += "|                |\n";  // Empty row
            }
        }
        return card;
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
