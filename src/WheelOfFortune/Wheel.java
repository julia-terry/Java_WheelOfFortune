package WheelOfFortune;

import java.util.ArrayList;
import java.util.Random;

//This class defines a Wheel for the Wheel of Fortune game
public class Wheel {
    // enumerated type, wheel wedges can be any of these
    public enum WedgeType {MONEY, BANKRUPT, LOSE_TURN, PRIZE}
    // the type of the current sping
    private WedgeType spinType;
    // if a money wedge, the amount
    private int spinDollarAmount;
    // if a prize wedge, the prize list and final prize
    private ArrayList <String> prizes = new ArrayList<>();
    private String finalPrize;
    // list of wedges
    private ArrayList<Wedge> wedges = new ArrayList<Wedge>();
    

    /**
     * Constructor
     * Creates the wheel
     */
    public Wheel() {
        // put a bankrupt wedge on the wheel
        wedges.add(new Wedge(WedgeType.BANKRUPT));
        
        // put a lose-turn wedge on the wheel
        wedges.add(new Wedge(WedgeType.LOSE_TURN));
        
        // put 20 money wedges on the wheel
        for (int i = 1; i < 20; i++) {
            wedges.add(new Wedge(WedgeType.MONEY));
        }
        
        // put a prize wedge on the wheel
        wedges.add(new Wedge(WedgeType.PRIZE));

    }
    
    /**
     * Spins the wheel
     * @return WedgeType 
     */
    public WedgeType spin() {
        int index = (int)(Math.random()*wedges.size());
        spinType = wedges.get(index).getType();
        spinDollarAmount = wedges.get(index).getAmount();
        return spinType;
    }
    
    /**
     * Getter
     * For when the spin lands on a money wedge
     * @return int the amount of money on the wedge
     */
    public int getAmount() {
        return spinDollarAmount;
    }
    
    /**
     * Getter
     * For when the spin lands on a prize wedge
     * @return string the prize they won/could win
     */
    public String getPrize() {
        // initializes the prizes ArrayList
        prizes.add("a new car");
        prizes.add("a vacation to the Bahamas");
        prizes.add("a boat");
        prizes.add("a drone");
        prizes.add("a cruise to Alaska");
        
        // selects a random prize
        Random generator = new Random();
        int randomInt = generator.nextInt(5);
        finalPrize = prizes.get(randomInt);
        
        // returns the prize
        return finalPrize;
    }
    
}
