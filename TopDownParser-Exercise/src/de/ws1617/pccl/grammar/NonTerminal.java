package de.ws1617.pccl.grammar;

/**
 * A Non-Terminal in a formal grammar.
 * 
 * @author bjoern
 *
 */
public class NonTerminal extends Symbol {

	/**
	 * Creates a NonTerminal based on the given String value.
	 * 
	 * @param value the String value.
	 */
	public NonTerminal(String value) {

		// don't alter input argument
		String toSet = String.valueOf(value);
		if (toSet.startsWith("[") && toSet.endsWith("]")) {
			toSet = toSet.substring(1, toSet.length() - 1);
		}
		setValue(toSet);
	}

	/**
	 * Checks whether the given word is a non-terminal (enclosed in square brackets).
	 * 
	 * @param symb the String representation to check.
	 * @return
	 */
	public static boolean isNonTerminal(String symb) {
		return symb.startsWith("[") && symb.endsWith("]");
	}

	/**
	 * Returns a deep copy of the given {@link NonTerminal}.
	 * 
	 */
	public NonTerminal clone() {
		return new NonTerminal(String.valueOf(getValue()));
	}

}
