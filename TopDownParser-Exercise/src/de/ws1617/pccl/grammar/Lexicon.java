package de.ws1617.pccl.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A lexicon for a formal grammar.
 * 
 * @author bjoern
 *
 */
public class Lexicon
{

	// key: pre-terminal, value: terminal
	private HashMap<NonTerminal, HashSet<ArrayList<Terminal>>> lexMap;
	// key: terminal list, value: set of non-terminals (pos tags)
	private HashMap<ArrayList<Terminal>, HashSet<NonTerminal>> posMap;

	/**
	 * Creates an empty lexicon.
	 */
	public Lexicon()
	{
		lexMap = new HashMap<>();
		posMap = new HashMap<>();
	}

	/**
	 * Returns all part of speech tags for a given terminal expression.
	 * 
	 * @param terminal a Terminal symbol.
	 * @return a set of pos tags.
	 */
	public HashSet<NonTerminal> getPosTags(Terminal terminal)
	{
		ArrayList<Terminal> list = new ArrayList<>(1);
		list.add(terminal);
		return getPosTags(list);
	}

	/**
	 * Returns all part of speech tags for a given terminal expression.
	 * 
	 * @param terminal a Terminal symbol.
	 * @return a set of pos tags.
	 */
	public HashSet<NonTerminal> getPosTags(ArrayList<Terminal> terminal)
	{
		if(!posMap.containsKey(terminal))
		{
			return new HashSet<>();
		}
		return posMap.get(terminal);
	}

	/**
	 * Add a key to the lexicon.
	 * 
	 * @param nt a pre-terminal.
	 */
	public void addKey(NonTerminal nt)
	{
		if(!lexMap.containsKey(nt))
		{
			lexMap.put(nt, new HashSet<>());
		}

	}

	/**
	 * Adds a rule to the lexicon.
	 * 
	 * @param nt the left hand side.
	 * @param rhs the right hand side.
	 */
	public void addRule(NonTerminal nt, ArrayList<Terminal> rhs)
	{
		addKey(nt);
		lexMap.get(nt).add(rhs);
		if(!posMap.containsKey(rhs))
		{
			posMap.put(rhs, new HashSet<>());
		}
		if(!posMap.get(rhs).contains(nt))
		{
			posMap.get(rhs).add(nt);
		}
	}

	/**
	 * Returns all rule right hand sides with the given left hand side.
	 * 
	 * @param lhs the rule left hand side.
	 * @return the rule right hand sides.
	 */
	public HashSet<ArrayList<Terminal>> getRules(NonTerminal lhs)
	{
		if(lexMap.containsKey(lhs))
		{
			return lexMap.get(lhs);
		}
		return new HashSet<>();
	}

	/**
	 * Returns a set of all non-terminals in the grammar.
	 * 
	 * @return a set of all non-terminals in the grammar.
	 */
	public Set<NonTerminal> getNonTerminals()
	{
		return lexMap.keySet();
	}

	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(o == null)
			return false;
		if(!(o instanceof Lexicon))
			return false;

		Lexicon l = (Lexicon) o;
		if(!lexMap.equals(l.getLexMap()))
			return false;
		if(!posMap.equals(l.getPOSMap()))
			return false;

		return true;
	}

	public HashMap<NonTerminal, HashSet<ArrayList<Terminal>>> getLexMap()
	{
		return lexMap;
	}

	public HashMap<ArrayList<Terminal>, HashSet<NonTerminal>> getPOSMap()
	{
		return posMap;
	}
}
