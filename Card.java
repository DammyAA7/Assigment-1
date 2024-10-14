public class Card {
    private String color; // The color of the card (e.g., red or black)
    private String suit;  // The suit of the card (e.g., Hearts, Diamonds)
    private String rank;  // The rank of the card (e.g., A, 2, 3, ..., K)

    // Constructor to initialize the Card object
    public Card(String color, String suit, String rank) {
        this.color = color;
        this.suit = suit;
        this.rank = rank;
    }


    
    // Creates a blank card representation with borders
    public String[] createCard() {
        int cardHeight = 12;  // Height of the card
        String[] cardDisplay = new String[cardHeight];

        // Populate the card display with borders and empty rows
        for (int i = 0; i < cardHeight; i++) {
            if (i == 0 || i == cardHeight - 1) {
                cardDisplay[i] = "------------------"; // Top and bottom border
            } else {
                cardDisplay[i] = "|                |";  // Empty row
            }
        }
        return cardDisplay;
    }

    // Returns a string representation of the card including color, suit, and rank
    public String[] getCard() {
        int cardHeight = 12;  // Height of the card
        int cardWidth = 16;   // Width of the card
        String[] cardDetails = new String[]{this.color, this.suit, this.rank}; // Card details

        // Create a blank card display
        String[] card = createCard();
        int detailIndex = 0;

        // Place card details in the appropriate rows of the card display
        for (int i = 3; i < cardHeight; i += 3) {
            if (detailIndex < cardDetails.length) {
                String detail = cardDetails[detailIndex];
                int paddingFront = (int) Math.ceil((cardWidth - detail.length()) / 2.0); // Calculate front padding
                int paddingBack = (int) Math.floor((cardWidth - detail.length()) / 2.0); // Calculate back padding

                // Adjust padding based on the length of the detail
                paddingFront = (detail.length() % 2 == 0) ? paddingBack : paddingBack + 1;

                // Format the padded detail into the card display
                String paddedDetail = String.format("|%" + paddingFront + "s%s%" + paddingBack + "s|", "", detail, "");
                card[i] = paddedDetail; // Insert the detail into the card display
                detailIndex++; // Move to the next detail
            }
        }
        return card; // Return the completed card display
    }

    // Getters for the card attributes
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }
}
