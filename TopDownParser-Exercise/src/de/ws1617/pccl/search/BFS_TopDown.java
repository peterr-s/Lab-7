package de.ws1617.pccl.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import de.ws1617.pccl.grammar.*;
import de.ws1617.pccl.parser.*;

public class BFS_TopDown
{

	/**
	 * 
	 * 
	 * @param input
	 * @param grammar
	 * @param lexicon
	 * @param startSymbol
	 * @return
	 */
	public static HashSet<TopDownParser> search(Terminal[] input, Grammar grammar, Lexicon lexicon, NonTerminal startSymbol)
	{
		Queue<TopDownParser> agenda = new LinkedList<>();
		// initialize with start symbol
		HashSet<TopDownParser> results = new HashSet<>();
		HashSet<TopDownParser> init = initialize(grammar, lexicon, startSymbol, input);
		for(TopDownParser initial : init)
		{
			agenda.add(initial);
			agenda.addAll(initial.successors());
		}

		while (!agenda.isEmpty())
		{
			TopDownParser top = agenda.poll();
			if(top.isGoal())
				results.add(top);
		}

		return results;
	}

	/**
	 * Expands the start symbol for initializing the top-down parser.
	 * 
	 * @param grammar
	 * @param lexicon
	 * @param startSymbol
	 * @param input
	 * @return
	 */
	private static HashSet<TopDownParser> initialize(Grammar grammar, Lexicon lexicon, NonTerminal startSymbol, Terminal[] input)
	{
		HashSet<TopDownParser> initParserSet = new HashSet<>();
		initParserSet.add(new TopDownParser(grammar, lexicon, input, startSymbol));

		return initParserSet;
	}

}
