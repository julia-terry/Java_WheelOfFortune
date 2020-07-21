package WheelOfFortune;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * WofFortuneGame class
 * Contains all logistics to run the game
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;

    private Wheel wheel;
    private Player player1;
    private String phrase = "Once upon a time";
 //   private Letter[] letter_array = new Letter[16];
    private ArrayList <Letter> phraseList = new ArrayList<>(phrase.length());
    private ArrayList <String> randomPhrases = new ArrayList<>(10);
    private ArrayList <Player> allPlayers = new ArrayList<>();
    private int numPlayers;
    private String playerName;
    private Player currentPlayer;
    private char won = 'n';

    /**
     * Constructor
     * @param wheel Wheel 
     * @throws InterruptedException 
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        //get the wheel
        this.wheel = wheel;
        
        //initialization of the game
        setUpGame();

    }
    
    /**
     * Plays the game
     * @throws InterruptedException 
     */
    public void playGame() throws InterruptedException {
        // while the puzzle isn't solved, keep going
        while (!puzzleSolved){
            
            // go through all of the players once so that they each have a turn
            for(int j=0; j<numPlayers; j++){    
                // if the player won the round before, they get another turn
                if(won=='y'){
                    j=j-1;
                }

                // resets the count so that it goes through the players again
                if(j==numPlayers){
                    j=0;
                }    
                
                // gets the next player and lets them play
                currentPlayer = allPlayers.get(j);
                playTurn(currentPlayer);    
                
            }
        }
    }
    
    //Sets up all necessary information to run the game
    private void setUpGame() {
        // create a single player 
        player1 = new Player("Player1");
        char userInput;
        Scanner myScanner = new Scanner(System.in);
        
        // sets up the random list of phrases
        fillPhraseList();
        
        // gets the amount of people playing from the user
        System.out.println("How many people are playing the game?");
        try{
            numPlayers = myScanner.nextInt();
        }catch(InputMismatchException ime){
            System.out.println("You did not enter an integer.");
        }
        
        // allows the players to enter their names and adds to ArrayList allPlayers
        for(int i=0; i<numPlayers; i++){
            System.out.println("Please enter the player's name:");
            if(i==0){
                myScanner.nextLine(); //makes it not skip player index 0's input
            }
            try{
                playerName = myScanner.nextLine();
            }catch (Exception e){
                System.out.println("You did not enter a name.");
            }
            allPlayers.add(new Player(playerName));
        }
      
        // print out the rules
        System.out.println("RULES!");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println("the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has three guesses, once you have used up your three guesses, ");
        System.out.println("you can still guess letters, but no longer solve the puzzle.");
        System.out.println();
        
        // allows the user to enter their own phrase if they would like
        System.out.println("Would you like to enter your own phrase? (Y/N)");
        userInput = myScanner.next().charAt(0);
        
        
        // user will enter their own phrase
        if (userInput == 'Y' || userInput == 'y'){
            System.out.println("Please enter what phrase you want to use:");
            myScanner.nextLine(); // additional nextLine() to make it not jump straight to the game
            try{
                phrase = myScanner.nextLine();
            }catch (Exception e){
                System.out.println("You did not enter a phrase.");
            }
        }
        
        // a phrase will randomly be selected from generated list
        if (userInput == 'N' || userInput == 'n'){
            Random generator = new Random();
            int randomInt = generator.nextInt(10);
            phrase = randomPhrases.get(randomInt);
            //System.out.println("The random phrase is: " + phrase);
        }
        
        // for each character in the phrase, create a letter and add to letters array
        for (int i = 0; i < phrase.length(); i++) {
            phraseList.add(i, new Letter(phrase.charAt(i)));
        }
        // setup done
    }
    
    /**
     * One player's turn in the game
     * Spin wheel, pick a letter, choose to solve puzzle if letter found
     * @param player
     * @throws InterruptedException 
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println(player.getName() + ", you have $" + player.getWinnings());
        System.out.println("Spin the wheel! <press enter>");
        try{
            sc.nextLine();
        }catch (Exception e){
            System.out.println("You did not press enter.");
        }
        System.out.println("<SPINNING>");
        try{
            Thread.sleep(200);
        }catch(InterruptedException ex){
            System.out.println("You encountered an InterruptedException."); 
        }
        Wheel.WedgeType type = wheel.spin();
        System.out.print("The wheel landed on: ");
        switch (type) {
            case MONEY:
                money = wheel.getAmount();
                System.out.println("$" + money);
                break;
                
            case LOSE_TURN:
                System.out.println("LOSE A TURN");
                System.out.println("So sorry, you lose a turn.");
                // signal for the player to not have another turn
                won = 'n';
                return; // doesn't get to guess letter
                
            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                // signal for the player to not have another turn
                won = 'n';
                return; // doesn't get to guess letter
            
            case PRIZE:
                System.out.println("PRIZE! You have a chance to win " + wheel.getPrize()+"!");
                player.setPrize(wheel.getPrize());
                break;
                
            default:
                
        }
        System.out.println("");
        System.out.println("Here is the puzzle:");
        showPuzzle();
        System.out.println();
        System.out.println(player.getName() + ", please guess a letter.");
        //String guess = sc.next();
        
        //try/catch block not needed due to if statments below
        char letter = sc.next().charAt(0);
        if (!Character.isAlphabetic(letter)) {
            System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
        } else {
            // search for letter to see if it is in
            int numFound = 0;
            for (Letter l : phraseList) {
                if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter))) {
                    l.setFound();
                    numFound += 1;
                }
            }
            if (numFound == 0) {
                System.out.println("Sorry, but there are no " + letter + "'s.");
                // signal for the player to not have another turn
                won = 'n';
            } else {
                if (numFound == 1) {
                    System.out.println("Congrats! There is 1 letter " + letter + ":");
                    // signal for the player to have another turn
                    won = 'y';
                } else {
                    System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                    // signal for the player to have another turn
                    won = 'y';
                }
                System.out.println();
                showPuzzle();
                System.out.println();
                player.incrementScore(numFound*money);
                System.out.println("You earned $" + (numFound*money) + ", and you now have: $" + player.getWinnings());


                System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                letter = sc.next().charAt(0);
                System.out.println();
                if ((letter == 'Y') || (letter == 'y')) {
                    solvePuzzleAttempt(player);
                }
            }
            
            
        }
        
    }
    
    /**
     * Logic for when user tries to solve the puzzle
     * @param player 
     */
    private void solvePuzzleAttempt(Player player) {
        int pastWinnings = 0;
        String winnerName = "";
        
        if (player.getNumGuesses() >= 3) {
            System.out.println("Sorry, but you have used up all your guesses.");
            return;
        }
        
        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        String guess = sc.next();
        if (guess.compareToIgnoreCase(phrase) == 0) {
            System.out.println("Congratulations! You guessed it!");
            puzzleSolved = true;
            // Round is over. Write message with final stats
            for(int j=0; j<numPlayers; j++){
                //gets all the players' final winnings
                currentPlayer = allPlayers.get(j);
                System.out.print(currentPlayer.getName()+ " earned $" + currentPlayer.getWinnings() + " and ");
                // prints out prize information
                if(currentPlayer.getPrizes().length()>1){
                    System.out.println(currentPlayer.getWinnings());
                }
                else{
                    System.out.println("no prizes");
                }
                // keeps track of the winner and their winnings
                if(currentPlayer.getWinnings()> pastWinnings){
                    pastWinnings = currentPlayer.getWinnings();
                    winnerName = currentPlayer.getName();
                }
            }
            // prints out the winner
            System.out.println("Congratulations " + winnerName + " for winning!");
        } else {
            System.out.println("Sorry, but that is not correct.");
        }
    }
    
    /**
     * Display the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : phraseList) {
            if (l.isSpace()) {
                System.out.print("   ");
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");
                } else {
                    System.out.print(" _ ");
                }
            }
        }
        System.out.println();
        
    }
    
    ///For a new game reset player's number of guesses to 0
    public void reset() {
        player1.reset();
    }
  
    //Filling up a new ArrayList with phrases that the player will use if they don't want the default
    public void fillPhraseList(){
        
        randomPhrases.add("as happy as a clam");
        randomPhrases.add("carpe diem");
        randomPhrases.add("down the rabbit hole");
        randomPhrases.add("fight fire with fire");
        randomPhrases.add("go by the book");
        randomPhrases.add("head over heels");
        randomPhrases.add("if the shoe fits");
        randomPhrases.add("joined at the hip");
        randomPhrases.add("keep the ball rolling");
        randomPhrases.add("la-la land");
        
    }
            
}
