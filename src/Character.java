public class Character {

    //attributes
    private int luck;
    private int strength;
    private String rod;
    private int goal;

    //default constructor
    public Character () {
        this.luck = 1;
        this.strength = 1;
        this.rod = "Common";
        this.goal = 0;
    }

    //specific constructor
    public Character(int luck, int strength, String rod, int goal){
        this.luck = luck;
        this.strength = strength;
        this.rod = rod;
        this.goal = goal;
    }

    //getter methods
    public int getLuck() {
        return luck;
    }
    public int getStrength() {
        return strength;
    }
    public String getRod() {
        return rod;
    }
    public int getGoal() {
        return goal;
    }

    //setter methods
    public void setGoal(int goal) {
        this.goal = goal;
    }

    //displays the stat of the player
    public String displayStats(String name){
        return("Character: " + name + "\nLuck: " + getLuck() + "\nStrength: " + getStrength() + "\nFishing Rod: " + getRod() + "\nNet Weight: " + getGoal() + "kg");
    }

    //converts the rarity of the fishing rod to a corresponding integer
    public int rodStats(){
        if (this.rod.equals("Common")){
            return 1;
        }
        else if (this.rod.equals("Uncommon")){
            return 2;
        }
        else if (this.rod.equals("Rare")){
            return 3;
        }
        else if (this.rod.equals("Epic")){
            return 4;
        }
        else{
            return 5;
        }

    }

    //redemption method when no fish is caught
    public boolean pray(){
        //random number from 1-50
        int random =(int)(Math.random()*50)+1;

        //returns true if random is less than the luck stat of the player
        return random <= this.luck;

    }

    //upgrades and changes the stats of the player
    public Character upgrade(int fish, int choice){

        //increase strength
        if (choice == 1){
            int strength = this.strength + 1 + fish/3;
            //updates the character
            return new Character (this.luck, strength, this.rod, this.goal);
        }
        //increase luck
        else if (choice == 2){
            int luck = this.luck + 1 + fish/3;
            //updates the character
            return new Character (luck, this.strength, this.rod, this.goal);
        }
        //upgrade fishing rod
        else{
            String rod = null;
            if(this.rodStats() == 1){
                rod = "Uncommon";
            }
            else if(this.rodStats() == 2){
                rod = "Rare";
            }
            else if(this.rodStats() == 3){
                rod = "Epic";
            }
            else if (this.rodStats() == 4){
                rod = "Legendary";
            }
            //updates the character
            return new Character (this.luck, this.strength, rod, this.goal);
        }
    }
}
