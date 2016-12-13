package de.ws1617.pccl.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grammar
{

	// set of non-terminals in this grammar
	private HashSet<NonTerminal> nts;
	// key: NT (LHS), value: Set<List<Symbols>> (RHS)
	private HashMap<NonTerminal, HashSet<ArrayList<Symbol>>> rules;
	// key: NT (rightmost), value: map<NT (lhs), Set<List<Symbol>> (rule rhs)
	private HashMap<NonTerminal, HashMap<NonTerminal, HashSet<ArrayList<Symbol>>>> rightMostMap;

	/**
	 * Creates an empty grammar.
	 */
	public Grammar()
	{
		super();
		rules = new HashMap<>();
		nts = new HashSet<>();
		rightMostMap = new HashMap<>();
	}

	/**
	 * Adds a key as lhs in the grammar.
	 * 
	 * @param nt the key.
	 */
	public void addKey(NonTerminal nt)
	{
		if(!rules.containsKey(nt))
		{
			rules.put(nt, new HashSet<>());
		}
		nts.add(nt);
	}

	/**
	 * Adds a rule from lhs to rhs to the grammar.
	 * 
	 * @param lhs the rule left hand side.
	 * @param rhs the rule right hand side.
	 */
	public void addRule(NonTerminal lhs, ArrayList<Symbol> rhs)
	{
		addKey(lhs);
		rules.get(lhs).add(rhs);
		for(int i = 0; i < rhs.size(); i ++)
		{
			if(rhs.get(i) instanceof NonTerminal)
			{
				nts.add((NonTerminal) rhs.get(i));
			}
		}
		// add right-indexed rule
		NonTerminal rightMost = (NonTerminal) rhs.get(rhs.size() - 1);
		if(!rightMostMap.containsKey(rightMost))
		{
			rightMostMap.put(rightMost, new HashMap<>());
		}
		if(!rightMostMap.get(rightMost).containsKey(lhs))
		{
			// also initialize the underlying hashset
			rightMostMap.get(rightMost).put(lhs, new HashSet<>());
		}
		rightMostMap.get(rightMost).get(lhs).add(rhs);
	}

	/**
	 * Returns all rules where rightmost is the non-terminal at the right edge of the rule right hand side.
	 * 
	 * @param rightmost a NonTerminal.
	 * 
	 * @return a list of rules.
	 */
	public HashMap<NonTerminal, HashSet<ArrayList<Symbol>>> getRulesByRightmost(NonTerminal rightmost)
	{
		if(!rightMostMap.containsKey(rightmost))
		{
			return new HashMap<>();
		}
		// if it has the key it also has at least one value
		return rightMostMap.get(rightmost);
	}

	/**
	 * Returns all rule right hand sides where lhs is on the left of the rule.
	 * 
	 * @param lhs the rule left hand side.
	 * @return a set of rule right hand sides.
	 */
	public HashSet<ArrayList<Symbol>> getRuleForLHS(NonTerminal lhs)
	{
		if(rules.containsKey(lhs))
		{
			return rules.get(lhs);
		}
		return new HashSet<ArrayList<Symbol>>();
	}

	/**
	 * Returns a pretty formatted representation of the grammar.
	 * 
	 * @return a String representation.
	 */
	public String prettyPrint()
	{
		StringBuilder sb = new StringBuilder();
		for(NonTerminal lhs : rules.keySet())
		{
			for(ArrayList<Symbol> rhs : rules.get(lhs))
			{
				sb.append(lhs);
				sb.append(" --> ");
				for(int i = 0; i < rhs.size(); i ++)
				{
					sb.append(rhs.get(i));
					sb.append(" ");
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns the set of all non-terminals in the grammar.
	 * 
	 * @return all non-terminals used in the grammar.
	 */
	public Set<NonTerminal> getNonTerminals()
	{
		return nts;
	}

	public HashMap<NonTerminal, HashSet<ArrayList<Symbol>>> getRules()
	{
		return rules;
	}

	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(o == null)
			return false;
		if(!(o instanceof Grammar))
			return false;

		if(!rules.equals(((Grammar) o).getRules()))
			return false;

		return true;
	}
}
