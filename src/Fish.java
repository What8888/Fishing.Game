public class Fish {
    //attributes
    private int weight;

    //constructors
    public Fish () {
        this.weight = (int)(Math.random()*10)+1;
    }

    //getter method for the weight of the fish
    public int getWeight() {
        return weight;
    }
}
