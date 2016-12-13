package de.ws1617.pccl.grammar;

/**
 * A terminal symbol in a formal grammar.
 * 
 * @author bjoern
 *
 */
public class Terminal extends Symbol {

	/**
	 * Creates a terminal based on the given string value.
	 * 
	 * @param value
	 */
	public Terminal(String value) {
		super(value);
	}

	/**
	 * Returns a deep copy of the given Terminal.
	 * 
	 */
	public Terminal clone(){
		return new Terminal(String.valueOf(getValue()));
	}
	
	/**
	 * Splits a String into a list of Terminals according to a split pattern.
	 * 
	 * @param input a String.
	 * @param pattern a split patterns.
	 * @return an array of terminals.
	 */
	public static Terminal[] splitString(String input, String pattern){
		String[] splt = input.split(pattern);
		
		Terminal[] rval = new Terminal[splt.length];
		
		for (int i = 0; i < splt.length; i++) {
			rval[i] = new Terminal(splt[i]);
		}
		return rval;
	}
	
}
