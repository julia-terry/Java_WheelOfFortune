package WheelOfFortune;

import java.util.ArrayList;

/**
 * Class that defines a player for a game with monetary winnings and 
 * a limited number of choices
 */
public class Player {
    // amount of money won
    private int winnings = 0; 
    // amount of money won
    private String name; 
    // number of times they've tried to solve puzzle
    private int numGuesses = 0;
    private ArrayList <String> prizes = new ArrayList<>();

    /**
     * Constructor
     * @param name String that is the player's name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return int amount of money won so far 
     */
    public int getWinnings() {
        return winnings;
    }

    /**
     * Adds amount to the player's winnings
     * @param amount int money to add
     */
    public void incrementScore(int amount) {
        if (amount < 0) return;
        this.winnings += amount;
    }

    /**
     * Getter 
     * @return String player's name 
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return int the number of guesses used up 
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    //Add 1 to the number of guesses used up
    public void incrementNumGuesses() {
        this.numGuesses++;
    }
    
    /**
     * Resets for a new game (only number of guesses)
     * This does not reset the winnings.
     */
    public void reset() {
        this.numGuesses = 0;
    }
    
    //Takes away player's winnings and prizes
    public void bankrupt() {
        this.winnings = 0;
        this.prizes.clear();
    }
    
    /**
     * Setter 
     * @param s String of the prize to add 
     */
    public void setPrize(String s){
        this.prizes.add(s);
    }
    /**
     * Getter 
     * @return String of player's entire list of prizes
     */
    public String getPrizes(){
        prizes.forEach(System.out::println);
        // got the code from https://www.mkyong.com/java8/java-8-foreach-examples/
        return "";
    }
}
