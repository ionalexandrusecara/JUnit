package uk.ac.standrews.cs.cs2001.w03.common;

/**
 * Created by Alex Jr on 9/27/2016.
 */

//Exception
public class InsufficientFundsException extends Exception {
    public String getMessage(){
        return "Insufficient customer funds!";
    }
}
