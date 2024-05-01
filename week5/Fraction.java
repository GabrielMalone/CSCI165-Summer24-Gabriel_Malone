/*
 * File:		Fraction.java
 * Author:		Ken Whitener
 * Modified By: Gabriel Malone
 * Date:		April 30, 2024
 * 
 */

import java.util.ArrayList;

public class Fraction {

	private int n;	// the numerator of the fraction
	private int d;	// demoninator of the fraction

	/**
	 * Fraction constructor that takes 2 arguments
	 * 
	 * @param n	the numerator of the fraction
	 * @param d	the denominator of the fraction
	 */
	public Fraction(int n, int d) {
		setNumerator(n);
		// denominator should not be set to 0. If it is, set it to 1
		if (setDenominator(d)) this.d = d;
		else this.d = 1;
	}

	public Fraction(int n) {
		setNumerator(n);
		this.d = 1;
	}
	
	/**
	 * Sets the numerator of the fraction
	 * @param n
	 */
	public void setNumerator(int n) {
		this.n = n;
	}

	/**
	 * 
	 * @return the numerator of the fraction
	 */
	public int getNumerator() {
		// return numerator
		// if denom negative, nom negative
		if (this.d < 0) this.n *= -1;
		// otherwise positive
		return this.n;
	}

	/**
	 * Method to set the denominator in a fraction
	 * @param d
	 * @return
	 */
	public boolean setDenominator(int d){
		// denominator cannot be zero
		if (d == 0) {
			this.d = 1; 
			return false;
		}
		else this.d = d;
			return true;
	}

	/**
	 * Method to return the integer value of a denominator in a fraction
	 * @return
	 */
	public int getDenominator() {
		// return denominator of the fraction
		// always positive
		return Math.abs(this.d);
	}

	/**
	 * Method to add a fraction to this fraction
	 * @return	nothing
	 */
	public void add(Fraction f) {
		addSubtractInitialize(f);
		// summed numerator
		this.n += f.n;
		this.reduce();
	}

	/**
	 * Method to subtract a fraction from this fraction
	 * @param f
	 */
	public void subtract(Fraction f) {
		addSubtractInitialize(f);
		
		// subtracted numerators
		this.n -= f.n;
		this.reduce();

	}

	/**
	 *  Method to multple two fractions together
	 * @param f
	 */
	public void multiply(Fraction f) {
		// new numerator
		this.n *= f.n;
		// new denominator
		this.d *= f.d;
		this.reduce();
	}

	/**
	 * Method to divide two fractions
	 * @param f
	 */
	public void divide(Fraction f) {
		// new numerator
		this.n *= f.d;
		// new denominator
		this.d *= f.n;
		this.reduce();
	}
	
	/**
	 * Method to reduce a fraction to its lowest commond terms
	 */
	public void reduce(){
        if ( this.n != 1  && this.n != 0 ) {
           
            // if numerator is greater than the denominator
            // and the numerator is equally divisibile by denominator, divide
            // set denominator to 1
            // e.g. 12/3 -> 4/1
		
            if (this.n > this.d && this.n % this.d == 0) {
                this.n /= this.d;
                this.d = 1;
            }
            
			//if numerator and denominator are the same
            
			else if (n == d) {
                this.n = 1;
                this.d = 1;

            }
            
			// if the denominator is equally divisible by the numerator 
            // e.g. 3/12 -> 1/4
           
			else if (this.d % this.n == 0){
                
                int ogN = this.n;
                this.n /= this.n;
                this.d /= ogN;
            }
            
            // if the denominator is not equally divisible by the numerator
            // see if the numerator and denominator have a common divisor
            // e.g. 9/12 -> 3/4 
            // if not 10/13 -- > 10 / 13 , stays the same
			// call recursively until reduced as much as possible

            else {
				ArrayList<Integer> numerators = new ArrayList<Integer>();
				recursiveReduction(null);
				// run until the last two items in the list are the same
				int index = 0;
				while (true){
					recursiveReduction(null);
					numerators.add(this.n);
					if (numerators.size() > 1 && (numerators.get(index) == numerators.get(index - 1))) break;
					index += 1;
				}
			}
		} 
	}

	/**
	 * Method to divide numerator by denominator
	 * @return the value of the fraction as a double
	 */
	public double toDecimal() {
		double fraction = (double)this.n / this.d;
		return fraction;
	}

	/**
	 * Method to return the string representation of a fraction
	 * @return the value of the fraction as a string "numerator/denominator"
	 */
	public String toString() {
		String fraction = "";
		this.reduce();
		// get unsigned values 
		int absN = Math.abs(this.n);
		int absD = Math.abs(this.d);
		// if negative
		if (this.n < 0|| this.d < 0) fraction = "-" + absN + "/" + absD;
		// if positive
		else fraction = this.n + "/" + this.d;
		return fraction;
	}

	/**
	 * Determines if this fraction is equal to another fraction
	 * @param f	the fraction to compare to
	 * @return	true if the fractions are equal, false otherwise
	 */
	public boolean equals(Fraction f) {
		// convert each to decimals and compare decimal values
		double f1 = this.toDecimal();
		double f2 = f.toDecimal();
		if (f1 == f2) return true;
		return false;	
	}

	/**
	 * Compares this fraction to another fraction to determine lexical ordering
	 * @param f	the fraction to compare to
	 * @return	-1 if this fraction is less than f, 0 if they are equal, 1 if this fraction is greater than f
	 */
	public int compareTo(Fraction f) {
		
		if (this.toDecimal() > f.toDecimal()) return  1;
		if (this.toDecimal() < f.toDecimal()) return -1;
		return 0;
	}

	private int gcd(int a, int b) {
		// if same , gcd same
		if (a == b) return a;
		// otherwise
		int gcd = (a * b);
		return gcd;	
	}

	private void addSubtractInitialize(Fraction f){
		// get gcd 
		int gcd = gcd(this.d, f.d);
		// create numerator values
		int n1 = (gcd / this.d) * this.n;
		int n2 = (gcd / f.d) * f.n;
		// set new numerators
		this.n = n1;
		f.n    = n2;
		// set new denominators
		this.d = gcd;
		f.d    = gcd;
	}

	private void recursiveReduction(Fraction f){
		// this function will be called until fully reduced
		// for a fracking like 9/12
		// find numbers that evenly go into numerator
		// start at 2 since 1 will always return 
		// stop at the value of the numerator itself
		int smallestDivisor = 1;
		ArrayList<Integer> divisors = new ArrayList<Integer>();
		
		// find numbers that evenly go into numerator
		for (int divisor = 2 ; divisor < Math.abs(this.n); divisor ++){
			// append to list
			if (this.n % divisor == 0) divisors.add(divisor);
		}
		// find numbers that evenly go into denominator
		for (int divisor = 2 ; divisor < Math.abs(this.d); divisor ++){
			// append to list
			if (this.d % divisor == 0) divisors.add(divisor);
		}
		// sort list 
		java.util.Collections.sort(divisors);
		// find the smallest duplicate from above ^ if any pairs might be in the list
		if (divisors.size() > 1) {
			for (int index = 0 ; index < divisors.size() - 1 ; index ++) {
				if (divisors.get(index) == divisors.get(index + 1)) {
					smallestDivisor = divisors.get(index);
					break;
				}
			}
			// if pair found:
			this.n /= smallestDivisor;
			this.d /= smallestDivisor;
		}
	}
}
