
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
        String card = "";
        int cardLength = 12;
        int cardWidth = 12;
        Object[] cardDetail = new Object[3];
        cardDetail[0] = new String(color);
        cardDetail[2] = new Integer(number);
        cardDetail[1] = new String(shape);
        int cardDetailIndex = 0;
        for (int i = 0; i < cardLength; i++){
            if(i == 0 || (i % 11) == 0){
                card +=        "##############\n";
            }
            else if(i % 3 == 0){
                StringBuilder spaces = new StringBuilder();
                double length  = 10 - cardDetail[cardDetailIndex].toString().length();
                length = length % 2 != 0 ? Math.ceil(length / 2) : length / 2;
                System.out.println(length);
                for (int j = 0; i < (int) length; j++) {
                    spaces.append(' ');
                }
                card +="#"+spaces.toString()+cardDetail[cardDetailIndex]+spaces.toString()+"#\n";
                cardDetailIndex++;
            } else{
                card +="#            #\n";
            }

        }
        return card;

    }
}
