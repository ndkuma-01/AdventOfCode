package Utilities.aoc23;

import java.util.Scanner;

/**********************************************************
 Frac.java - a Java representation of a Frac

 Author: Diane Kramer
 History:
 Created:   9/25/01
 Modified: 10/16/01 - added gcd method to reduce Frac
 Modified: 02/19/06 - include licence terms in comments

 Description:  This class provides storage for internal
 representation of, and methods to manipulate Fracs.
 A Frac consists of two integers, one for numerator
 and one for denominator.  An example Frac is 3/4.
 A valid Frac must not have zero in the denominator.

 This software is licensed "as-is" under a non-exclusive,
 worldwide, royalty-free right to reproduce the software,
 prepare derivative works of the software and distribute
 the software or any derivative works created.  The user
 bears the risk of using it.  No express warranties,
 guarantees or conditions are implied.
 ***********************************************************/

public class Frac {
    // member variables
    private Long numerator, denominator;  // stores the Frac data

    /**********************************************************
     Method:         Default Constructor
     Purpose:        Create a new Frac object and initialize it
     with "invalid" data
     Parameters:     None
     Preconditions:  None
     Postconditions: a new Frac object is created with numerator
     and denominator set to 0
     ***********************************************************/
    public Frac() {
        numerator = denominator = 0L;
    }

    /********************************************/
    /* public accessor methods for private data */
    /********************************************/

    /**********************************************************
     Method:         getNumerator
     Purpose:        access data stored in numerator member variable
     Parameters:     None
     Preconditions:  None
     Postconditions: None
     Returns:        integer data stored in numerator member variable
     ***********************************************************/
    public long getNumerator() {
        return numerator;
    }

    /**********************************************************
     Method:         setNumerator
     Purpose:        provide data to store in numerator member variable
     Parameters:     num: an integer value
     Preconditions:  None
     Postconditions: the value of num will be stored in numerator
     member variable
     ***********************************************************/
    public void setNumerator(long num) {
        numerator = num;
    }

    /**********************************************************
     Method:         getDenominator
     Purpose:        access data stored in denominator member variable
     Parameters:     None
     Preconditions:  None
     Postconditions: None
     Returns:        integer data stored in denominator member variable
     ***********************************************************/
    public long getDenominator() {
        return denominator;
    }

    /**********************************************************
     Method:         setDenominator
     Purpose:        provide data to store in denominator member variable
     Parameters:     den: an integer value
     Preconditions:  None
     Postconditions: the value of den will be stored in denominator
     member variable
     ***********************************************************/
    public void setDenominator(long den) {
        denominator = den;
    }

    /****************************************************/
    /* public action methods for manipulating Fracs */
    /****************************************************/

    /**********************************************************
     Method:         add
     Purpose:        Add two Fracs, a and b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     b, the Frac to add to "this"
     Preconditions:  Both Fracs a and b must contain valid values
     Postconditions: None
     Returns:        A Frac representing the sum of two
     Fracs a & b
     ***********************************************************/
    public Frac add(Frac b) {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator");
        // find lowest common denominator
        long common = lcd(denominator, b.denominator);
        // convert Fracs to lcd
        Frac commonA = new Frac();
        Frac commonB = new Frac();
        commonA = convert(common);
        commonB = b.convert(common);
        // create new Frac to return as sum
        Frac sum = new Frac();
        // calculate sum
        sum.numerator = commonA.numerator + commonB.numerator;
        sum.denominator = common;
        // reduce the resulting Frac
        sum = sum.reduce();
        return sum;
    }

    /**********************************************************
     Method:         subtract
     Purpose:        Subtract Frac b from a, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     b, the Frac to subtract from "this"
     Preconditions:  Both Fracs a and b must contain valid values
     Postconditions: None
     Returns:        A Frac representing the differenct of the
     two Fracs a & b
     ***********************************************************/
    public Frac subtract(Frac b) {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator");
        // find lowest common denominator
        long common = lcd(denominator, b.denominator);
        // convert Fracs to lcd
        Frac commonA = new Frac();
        Frac commonB = new Frac();
        commonA = convert(common);
        commonB = b.convert(common);
        // create new Frac to return as difference
        Frac diff = new Frac();
        // calculate difference
        diff.numerator = commonA.numerator - commonB.numerator;
        diff.denominator = common;
        // reduce the resulting Frac
        diff = diff.reduce();
        return diff;
    }

    /**********************************************************
     Method:         multiply
     Purpose:        Multiply Fracs a and b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     The Frac b to multiply "this" by
     Preconditions:  Both Fracs a and b must contain valid values
     Postconditions: None
     Returns:        A Frac representing the product of the
     two Fracs a & b
     ***********************************************************/
    public Frac multiply(Frac b) {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator");
        // create new Frac to return as product
        Frac product = new Frac();
        // calculate product
        product.numerator = numerator * b.numerator;
        product.denominator = denominator * b.denominator;
        // reduce the resulting Frac
        product = product.reduce();
        return product;
    }

    /**********************************************************
     Method:         divide
     Purpose:        Divide Frac a by b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     The Frac b to divide "this" by
     Preconditions:  Both Fracs a and b must contain valid values
     Postconditions: None
     Returns:        A Frac representing the result of dividing
     Frac a by b
     ***********************************************************/
    public Frac divide(Frac b) {
        // check preconditions
        if ((denominator == 0) || (b.numerator == 0))
            throw new IllegalArgumentException("invalid denominator");
        // create new Frac to return as result
        Frac result = new Frac();
        // calculate result
        result.numerator = numerator * b.denominator;
        result.denominator = denominator * b.numerator;
        // reduce the resulting Frac
        result = result.reduce();
        return result;
    }

    /**********************************************************
     Method:         input
     Purpose:        Retrieve values from the user via keyboard input
     for numerator and denominator of the "this" object.
     A valid integer value must be entered for the
     numerator, and a non-zero integer value must be
     entered for denominator.
     Parameters:     None
     Preconditions:  SavitchIn class must be available to read keyboard
     input.  User needs to see command line window to be
     prompted for input.
     Postconditions: The "this" object will contain the valid data entered
     by the user.
     ***********************************************************/
    public void input() {
        Scanner stdin = new Scanner(System.in);
        // prompt user to enter numerator
        System.out.print("Please enter an integer for numerator: ");
        // get user input
        numerator = Long.parseLong(stdin.nextLine().trim());
        // prompt user to enter denominator in a loop to prevent
        // an invalid (zero) value for denominator
        do {
            System.out.print("Please enter a non-zero integer for denominator: ");
            denominator = Long.parseLong(stdin.nextLine().trim());
            // make sure it is non-zero
            if (denominator == 0)
                System.out.println("Invalid value.  Please try again.");
        } while (denominator == 0);
    }

    /**********************************************************
     Method:         output
     Purpose:        Print the value of the "this" object to the screen.
     Makes use of the toString() method.
     Uses System.out.print, rather than println for flexibility
     Parameters:     None
     Preconditions:  User needs access to command line window to see output
     Postconditions: The value of the "this" object will be printed to
     the screen
     ***********************************************************/
    public void output() {
        System.out.print(this);
    }

    /**********************************************************
     Method:         toString
     Purpose:        Convert the internal representation of a Frac,
     which is stored in two integers, into a String
     (which could then be printed to the screen)
     Parameters:     None
     Preconditions:  None
     Postconditions: The value of the "this" object will be converted
     to a String
     Returns:        A String representation of the "this" Frac
     ***********************************************************/
    public String toString() {
        String buffer = numerator + "/" + denominator;
        return buffer;
    }

    /*****************************************************/
    /* private methods used internally by Frac class */
    /*****************************************************/

    /**********************************************************
     Method:         lcd
     Purpose:        find lowest common denominator, used to add and
     subtract Fracs
     Parameters:     two integers, denom1 and denom2
     Preconditions:  denom1 and denom2 must be non-zero integer values
     Postconditions: None
     Returns:        the lowest common denominator between denom1 and
     denom2
     ***********************************************************/
    private long lcd(long denom1, long denom2) {
        long factor = denom1;
        while ((denom1 % denom2) != 0)
            denom1 += factor;
        return denom1;
    }

    /**********************************************************
     Method:         gcd
     Purpose:        find greatest common denominator, used to reduce
     Fracs
     Parameters:     two integers, denom1 and denom2
     Preconditions:  denom1 and denom2 must be positive integer values
     denom1 is assumed to be greater than denom2
     (denom1 > denom2 > 0)
     Postconditions: None
     Returns:        the greatest common denominator between denom1 and
     denom2
     Credits:        Thanks to Euclid for inventing the gcd algorithm,
     and to Prof. Joyce for explaining it to me.
     ***********************************************************/
    private long gcd(long denom1, long denom2) {
        long factor = denom2;
        while (denom2 != 0) {
            factor = denom2;
            denom2 = denom1 % denom2;
            denom1 = factor;
        }
        return denom1;
    }

    /**********************************************************
     Method:         convert
     Purpose:        convert a Frac to an equivalent one based on
     a lowest common denominator
     Parameters:     an integer common, the new denominator
     Preconditions:  the "this" Frac must contain valid data for
     numerator and denominator
     the integer value common is assumed to be greater
     than the "this" Frac's denominator
     Postconditions: None
     Returns:        A new Frac which is equivalent to the "this"
     Frac, but has been converted to the new
     denominator called common
     ***********************************************************/
    private Frac convert(long common) {
        Frac result = new Frac();
        long factor = common / denominator;
        result.numerator = numerator * factor;
        result.denominator = common;
        return result;
    }

    /**********************************************************
     Method:         reduce
     Purpose:        convert the "this" Frac to an equivalent one
     based on a greatest common denominator
     Parameters:     None
     Preconditions:  The "this" Frac must contain valid data for
     numerator and denominator
     Postconditions: None
     Returns:        A new Frac which is equivalent to a, but has
     been reduced to its lowest numerical form
     ***********************************************************/
    private Frac reduce() {
        Frac result = new Frac();
        long common = 0;
        // get absolute values for numerator and denominator
        long num = Math.abs(numerator);
        long den = Math.abs(denominator);
        // figure out which is less, numerator or denominator
        if (num > den)
            common = gcd(num, den);
        else if (num < den)
            common = gcd(den, num);
        else  // if both are the same, don't need to call gcd
            common = num;

        // set result based on common factor derived from gcd
        result.numerator = numerator / common;
        result.denominator = denominator / common;
        return result;
    }
}

