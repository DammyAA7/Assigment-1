public class Card {
    private String color;
    private String shape;
    private int number;

    public Card(String color, String shape, int number){
        this.color = color;
        this.shape = shape;
        this.number = number;
    }

    public String getCard(){
        String card = "##############";
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
