
public class Card {
    private String color;
    private String shape;
    private int number;

    public Card(String color, String shape, int number){
        this.color = color;
        this.shape = shape;
        this.number = number;
    }


    public String getCard(String color, String shape, int number){
        String card = "##############";
        int cardLength = 12;
        int cardWidth = 12;
        Object[] cardDetail = new Object[3];
        cardDetail[0] = new String(color);
        cardDetail[2] = new Integer(number);
        cardDetail[1] = new String(shape);
        int cardDetailIndex = 0;
        for (int i = 0; i < cardLength; i++){
            if(i % 3 == 0){
                String line = "";
                double length = cardDetail[cardDetailIndex].toString().length();
                int before = ((cardWidth - length) % 2 == 0) ? (int)(length / 2) : (int)Math.ceil(length / 2);
                int after = 0;
                for (int j = 0; j < cardWidth; j++){
                    line 
                }

                cardDetailIndex++;

            }

        }
        card +=       "#            #";
        card +=       "#            #";
        card +=       "#            #";
        card +=       "#            #";
        card +=       "#   "+color+"      #"; 
        card +=       "#            #";
        card +=       "#            #";
        card +=       "#            #";
        card +=       "#            #";
        card +=        "##############";
        return card;

    }
}
