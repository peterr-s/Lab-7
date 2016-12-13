package de.ws1617.pccl.grammar;

import java.util.ArrayList;

/**
 * A context-free or regular grammar production rule.
 * 
 * @author bjoern
 *
 */
public class Rule {

	// the rule left hand side
	private NonTerminal lhs;
	// the rule right hand side
	private ArrayList<Symbol> rhs;

	
	/**
	 * Creates a rule with the given parameters.
	 * 
	 * @param lhs the rule left hand side.
	 * @param rhs the rule right hand side
	 */
	public Rule(NonTerminal lhs, ArrayList<Symbol> rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return "[" + lhs + " --> " + rhs + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
		result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (lhs == null) {
			if (other.lhs != null)
				return false;
		} else if (!lhs.equals(other.lhs))
			return false;
		if (rhs == null) {
			if (other.rhs != null)
				return false;
		} else if (!rhs.equals(other.rhs))
			return false;
		return true;
	}

	public NonTerminal getLhs() {
		return lhs;
	}

	public void setLhs(NonTerminal lhs) {
		this.lhs = lhs;
	}

	public ArrayList<Symbol> getRhs() {
		return rhs;
	}

	public void setRhs(ArrayList<Symbol> rhs) {
		this.rhs = rhs;
	}
}
