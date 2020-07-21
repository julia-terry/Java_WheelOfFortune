package WheelOfFortune;

//Class that holds letters for a wheel of fortune game
public class Letter {
    // the letter
    private char letter; 
    // whether or not it has been guessed
    private boolean guessed = false; 
    // whether it is a capital letter
    private boolean capitalized = false; 
    // whether it is a space
    private boolean space = false; 
    

    /**
     * Constructor - builds the Letter object
     * If letter is special character, we mark it as guessed
     * so that it shows.
     * @param char letter 
     */
    public Letter(char letter) {
        this.letter = letter;
        // find out if this is a space
        if (letter == ' ') {
            space = true;
            guessed = true;
        } else if (!(Character.isAlphabetic(letter))) {
            // this makes punctuation show, and not part of puzzle
            guessed = true; 
        } else {
            // check if capitalized
            if (Character.isUpperCase(letter)) {
                capitalized = true;
                letter = Character.toLowerCase(letter);
            }
        }
    }
    
    /**
     * Getter
     * @return char letter
     */
    public char getLetter() {
        return letter;
    }
    
    /**
     * Getter
     * @return boolean capitalized
     */
    public boolean isCap() {
        return capitalized;
    }
    
    /**
     * Getter
     * @return boolean space 
     */
    public boolean isSpace() {
        return space;
    }
    
    /**
     * Setter - sets guessed to true
     */
    public void setFound() {
        guessed = true;
    }
    
    /**
     * Getter
     * @return boolean guessed 
     */
    public boolean isFound() {
        return guessed;
    }
    
}
