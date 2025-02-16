//import statements
import java.util.*;
public class Main {
    public static void main(String[] args) {

        // sets up preliminary variables
        int dayCount = 1;
        int weight;
        int option1;
        int option2;
        int choice;
        int choice2;
        int total = 0;
        int pray;
        boolean win = false;

        //creates a scanner
        Scanner in = new Scanner(System.in);
        System.out.println("Please input your user name.");
        //saves the user's name
        String name = in.next();

        //background story
        System.out.println("\nLong long ago... " + name + " was a fisherman living near the Baltic Sea providing for his family.\n" +
                "One day, the kraken came out of the water and stole his wife and told him:\n \"You must catch fish with a total weight of 100 kg in 10 days to prove your worth or else I eat your wife\" ");

        //instructions for the game
        System.out.print("""
                ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                Instructions:
                One fish will bite each day, a mini-game is then played to determine if you will catch the fish or not
                During the mini-game, you can "reel in" to have a percentage chance of catching the fish (failure would mean fish escapes)
                or "reel out" which tires out the fish and increase the chances of you catching it but there is a small chance of your rod breaking and the fish escaping

                After you have caught the fish, you can:
                \t1: Eat it to increase your strength stat which means bigger chances of catching a fish
                \t2: Releasing it to increase your luck stat which increases the size of future catches
                \t3: Trade the fish for a better fishing rod

                *Remember, the bigger the fish, the harder it is to catch
                ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                """);


        //creates an object that will be the player for this game
        Character player = new Character();

        //repeats the game for 10 days
        for (int i = 0; i < 10; i++){

            System.out.println("Day " + (dayCount+i));
            System.out.println("""
                          /`-.
                        /    `-.
                      O/        `-.
                      |            `-.
                      /\\              `-.
                      \\_\\_               `-.
                    ########~~~~~~~~~~~~~~~~<*))))<~~~~~~~~~~~~~~~~~~
                    """);

            //displays the character sheet of the player
            System.out.println(player.displayStats(name) +"\n");

            //creates a fish object
            Fish bite = new Fish();

            //calculates the total weight of the fish
            weight = totalWeight(bite.getWeight(), player.getLuck());

            //sets up the percentage of the two options that the player has access to
            option1 = (30 + (player.getStrength() * 6) - weight);
            option2 = (15 - player.rodStats()*3 + weight/player.getStrength());

            //asks the user for their choice
            System.out.println("A " + weight + "kg fish just bite! What do you want to do?\n\t(1) = Reel In: There is a " + option1 +"% chance of catching the fish" + "\n\t(2) = Reel out (Increase catch chance by 5%): There is a " + option2 + "% chance of losing the fish" );
            choice = in.nextInt();

            //defensive coding to make sure the user inputs 1 or 2
            while(choice != 1 && choice != 2){
                System.out.println("Bad input, try again (1,2).");
                choice = in.nextInt();
            }

            //if they choose to catch the fish
            if (choice == 1){
                //catches the fish
                if(reel(option1)){
                    System.out.println("Hooray! You caught the fish!");
                    //adds to the total weight of fish caught
                    total = total + weight;
                    player.setGoal(total);
                    //flag becomes true to allow them to decide what to do with their fish
                    win = true;
                }
                //fails to catch the fish
                else {
                    System.out.println("Unlucky, the fish escaped :(");
                }
            }
            //if they choose to tire out the fish
            else {
                //fish escapes
                if (reel(option2)){
                    System.out.println("Unlucky, the fish escaped :(");
                }
                //fish did not escape
                else {
                    //run the mini-game again with different percentages for options
                    //catches the fish - if the mini-game returns true
                    if(miniGame(option1, option2)){
                        total = total + weight;
                        //adds to the total weight of fish caught
                        player.setGoal(total);
                        win = true;
                    }
                    //fish escapes - if the mini-game returns false
                    else{
                        System.out.println("Unlucky, the fish escaped :(");
                    }
                }
            }

            //if they caught a fish
            if (win){
                //gives the player 3 options
                System.out.println("""
                        
                        Good job on catching that fish, what do you want to do with it?
                        \t(1): Eat the fish to increase strength
                        \t(2): Release the fish to increase luck
                        \t(3): Trade the fish to upgrade your fishing rod
                        """);
                choice2 = in.nextInt();

                //defensive coding to make sure the user inputs 1, 2, 3 (if they input 3, their rod cannot already be legendary)
                while (choice2 != 1 && choice2 != 2 && (choice2 !=3 || player.getRod().equals("Legendary"))){
                    System.out.println("Bad input, either you didn't input (1,2,3) or your fishing rod is already at max level.");
                    choice2 = in.nextInt();
                }
                //upgrade the player's stats according to their choice
                player = player.upgrade(weight, choice2);
            }

            //if they did not catch a fish
            else{
                //instruction on a "redemption" mechanic
                System.out.println("\nYou did not catch a fish today, pray to the Gods and see if they can have mercy on you. (Press 8)");
                pray = in.nextInt();
                //if they pray correctly
                if (pray == 8){
                    //percentage chance that they get rewarded kg of fish equal to 10x their luck stat
                    if(player.pray()){
                        System.out.println("The Gods have answered and granted you with " + (player.getLuck() * 10) +"kg of fish.");
                        //adds to the total weight of fish caught
                        total = total + (player.getLuck() * 10);
                        player.setGoal(total);
                    }
                    //nothing happens
                    else{
                        System.out.println("Nothing seems to happen...");
                    }
                }
                //no fish if they did not pray correctly
                else{
                    System.out.println("Insolence! I guess you will be going home with nothing today then.");
                }
            }
            //reset if they caught a fish or not
            win = false;
        }

        //displays the character sheet of the player
        System.out.println(player.displayStats(name) +"\n");

        //checks to see if the user has won or not
        if(player.getGoal() >= 100){
            System.out.println("""
                                                                    ▓▓██████████▓▓████                           \s
                                                                ████░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██                         \s
                                                              ██░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒▒▒▒▒██                       \s
                                                            ██░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██                     \s
                                                          ██░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██                   \s
                                                        ██░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒▒▒▒▒▒▒██                 \s
                                  ██████████████        ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒██                 \s
                                ▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒██      ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒▒▒▒▒██                 \s
                              ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██  ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██                 \s
                            ▓▓▒▒▒▒▓▓██████▓▓▓▓▒▒▒▒██  ██▒▒░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒██                 \s
                            ██▒▒▓▓██        ██▒▒▒▒██  ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██                 \s
                            ██▒▒██        ██▓▓▒▒▒▒██  ██░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░▒▒▒▒▒▒██                 \s
                            ██▓▓▓▓        ██░░▒▒██    ██▒▒▒▒▒▒▒▒██▒▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓██    ████████     \s
                              ██        ██▓▓▓▓▒▒██    ██▒▒▒▒▒▒▒▒██▒▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓██    ██▒▒▒▒▒▒▒▒██▓▓ \s
                                        ██░░▓▓▓▓██    ██▒▒░░▒▒░░░░▒▒▒▒░░░░▒▒▒▒▒▒▒▒░░▒▒▒▒▓▓██    ██▒▒▒▒▓▓████▒▒▒▒██
                                        ████████████    ▓▓▒▒▒▒▒▒██▒▒▒▒▒▒██▒▒▒▒▒▒▒▒▒▒▓▓▓▓██    ██▒▒▒▒▓▓██    ██▓▓ \s
                                        ████▒▒▒▒▒▒▒▒██  ██▓▓▒▒▒▒▒▒██████▒▒▒▒▒▒▒▒▓▓▓▓▓▓██      ██▒▒▒▒██           \s
                                        ██▒▒▒▒▒▒▒▒▒▒▒▒██▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓██████▓▓██      ██▒▒▒▒▒▒██           \s
                                    ████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██▓▓██████▒▒▒▒▒▒▒▒▓▓▒▒▒▒░░██▓▓██    ██▒▒▒▒▓▓██           \s
                          ▓▓██      ████▒▒▒▒▒▒██▓▓▒▒▒▒▒▒▒▒██░░░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░████████▒▒▒▒▒▒██             \s
                        ██▒▒▒▒██    ████▒▒▒▒██▓▓██▓▓▒▒▓▓██▒▒▒▒▒▒▒▒▒▒▒▒██▓▓▒▒▒▒▒▒▒▒▒▒▒▒██▓▓▒▒▒▒▒▒▓▓██             \s
                      ██▒▒▒▒▒▒▒▒▓▓██▒▒▒▒▒▒▓▓██▓▓▓▓██▓▓▓▓██▒▒▒▒▒▒▒▒▒▒██▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒██▓▓▓▓▓▓██████████         \s
                    ▓▓▒▒████▓▓▒▒▒▒▒▒▒▒▒▒▒▒██  ████████▓▓██▒▒▒▒▒▒▒▒▒▒▒▒████▓▓██▓▓▒▒▒▒▒▒██▓▓▓▓██▒▒▒▒▒▒▒▒▒▒██       \s
                      ██    ██░░▓▓▓▓▓▓▓▓██            ▓▓▓▓████▒▒▒▒▒▒▒▒▒▒▓▓▓▓  ██▒▒▒▒▒▒██▓▓██▒▒▒▒▒▒▒▒▒▒▒▒▒▒██     \s
                              ██▓▓░░████      ██████████      ████▒▒▒▒▒▒██    ▓▓▒▒▒▒▒▒▒▒██▒▒▒▒▒▒░░▓▓▓▓██▓▓▒▒██▓▓ \s
                                ▓▓██        ██▒▒▒▒▒▒▒▒▒▒████████▒▒▒▒▒▒▓▓██    ██▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓██      ████     \s
                                        ████▒▒▒▒░░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓██        ██▓▓▓▓▒▒▒▒▒▒░░██                 \s
                                    ▓▓▓▓▒▒▒▒▓▓▓▓▓▓▒▒▓▓░░▓▓▓▓░░▓▓▓▓░░██            ▓▓▓▓░░▓▓▓▓▓▓                   \s
                                    ████▒▒▒▒▓▓▓▓▓▓▓▓▓▓░░▓▓▓▓░░▓▓▓▓░░▓▓            ▓▓▓▓░░▓▓▓▓▓▓                   \s
                                        ██▓▓██    ████▓▓██▓▓▓▓██████                ████████                     \s
                    
                    "Because you have caught at least 100kg worth of fish, I shall pardon your wife."
                    
                    La Fin
                    """);
        }
        else{
            System.out.println("""
                                   
                                         .-'   `'.
                                        /         \\
                                        |         ;
                                        |         |           ___.--,
                               _.._     |0) ~ (0) |    _.---'`__.-( (_.
                        __.--'`_.. '.__.\\    '--. \\_.-' ,.--'`     `""`
                       ( ,.--'`   ',__ /./;   ;, '.__.'`    __
                       _`) )  .---.__.' / |   |\\   \\__..--""  ""\"--.,_
                      `---' .'.''-._.-'`_./  /\\ '.  \\ _.-~~~````~~~-._`-.__.'
                            | |  .' _.-' |  |  \\  \\  '.               `~---`
                             \\ \\/ .'     \\  \\   '. '-._)
                              \\/ /        \\  \\    `=.__`~-.
                              / /\\         `) )    / / `"".`\\
                        , _.-'.'\\ \\        / /    ( (     / /
                         `--~`   ) )    .-'.'      '.'.  | (
                                (/`    ( (`          ) )  '-;
                                 `      '-;         (-'
                
                "You have failed your mission, you shall never see your wife again."
                
                La Fin
                """);
        }
    }



    //calculates the total weight when factoring the luck stat into the equation
    public static int totalWeight(int weight, int luck){
        return (weight * luck);
    }

    //runs the mini-game
    public static boolean miniGame(int option1, int option2){

        //for each time the mini-game is run, the chance to catch the fish increases by 3%
        option1 = option1 + 5;
        System.out.println("The fish gets a little tired...\nWhat do you want to do?\n\t(1) = Reel In: There is a " + option1 +"% chance of catching the fish" +
                "\n\t(2) = Reel Out (Increase catch chance by 5%): There is a " + option2 + "% chance of losing the fish");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        //defensive coding to make sure the user inputs 1 or 2
        while(choice != 1 && choice != 2){
            System.out.println("Bad input, try again (1,2).");
            choice = in.nextInt();
        }

        //if they choose to catch the fish
        if (choice == 1){
            //catches the fish
            if(reel(option1)){
                System.out.println("Hooray! You caught the fish!");
                return true;
            }
            //fails to catch the fish
            else {
                return false;
            }
        }
        //if they choose to tire out the fish
        else {
            //fish escapes
            if (reel(option2)){
                return false;
            }
            //fish did not escape
            else {
                //run mini-game again
                return miniGame(option1, option2);
            }
        }
    }

    //calculates the chance of success for each of the options
    public static boolean reel(int succeed){
        //random number from 1-100
        int chance =(int)(Math.random()*100+1);
        return chance <= succeed;
    }
}