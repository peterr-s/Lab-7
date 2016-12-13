package de.ws1617.pccl.parser;

import java.util.ArrayList;
import java.util.Stack;
import de.ws1617.pccl.grammar.*;

public class TopDownParser
{
	private ArrayList<Terminal> input;
	private Grammar grammar;
	private Lexicon lexicon;
	private Stack<NonTerminal> state;
	private Stack<Rule> analysis;

	/**
	 * constructor invoked by BFS_TopDown.initialize()
	 * 
	 * @param grammar - grammar to use
	 * @param lexicon - lexicon to use
	 * @param input - input string (tokenized)
	 * @param startSymbol - the start symbol (in this case also the initial state of the parser)
	 */
	public TopDownParser(Grammar grammar, Lexicon lexicon, Terminal[] input, NonTerminal startSymbol)
	{
		this.grammar = grammar;
		this.lexicon = lexicon;

		this.input = new ArrayList<>();
		for(int i = 0; i < input.length; i ++)
			this.input.add(input[i]);

		state = new Stack<>();
		state.add(startSymbol);

		analysis = new Stack<>();
	}

	/**
	 * constructor used by clone() method
	 * 
	 * @param grammar - same grammar
	 * @param lexicon - same lexicon
	 * @param input - same input sequence
	 * @param state - deep copy of states
	 */
	private TopDownParser(Grammar grammar, Lexicon lexicon, ArrayList<Terminal> input, Stack<NonTerminal> state, Stack<Rule> analysis)
	{
		this.grammar = grammar;
		this.lexicon = lexicon;
		this.input = input;
		this.state = state;
		this.analysis = analysis;
	}

	/**
	 * clones the parser object
	 * 
	 * @return a copy of the parser (same grammar/lexicon, shallow copies of input, state, analysis)
	 */
	public TopDownParser clone()
	{
		// clone nonterminal stack
		Stack<NonTerminal> stateClone = new Stack<>();
		stateClone.addAll(state); // for some reason the iterator of Stack returns elements in forward order

		// clone terminal arraylist
		ArrayList<Terminal> inputClone = new ArrayList<>();
		inputClone.addAll(input);

		// clone rule stack
		Stack<Rule> analysisClone = new Stack<>();
		analysisClone.addAll(analysis);

		return new TopDownParser(grammar, lexicon, inputClone, stateClone, analysisClone);
	}

	/**
	 * checks whether the parser has found its way to the sentence
	 * 
	 * @return true if yes, else false
	 */
	public boolean isGoal()
	{
		// goal has been found if there are no nonterminals to expand and no terminals to parse
		return input.isEmpty() && state.isEmpty();
	}

	public ArrayList<TopDownParser> successors()
	{
		// at this point I think successors is just an alias of predict...
		return predict();
	}

	/**
	 * returns all states that can be derived from the current state with a single rule application
	 * 
	 * @return an ArrayList of new TopDownParsers with all possible rules applied
	 */
	private ArrayList<TopDownParser> predict()
	{
		ArrayList<TopDownParser> derivations = new ArrayList<>();

		// if the stack is empty there's nothing to derive
		if(state.isEmpty())
			return derivations;

		NonTerminal thisSymbol = state.peek();

		// apply grammatical rules
		for(ArrayList<Symbol> grammarRule : grammar.getRuleForLHS(thisSymbol))
		{
			TopDownParser ruleClone = clone();
			if(ruleClone.apply(grammarRule)) // if rule can be applied successfully
			{
				derivations.add(ruleClone);
				derivations.addAll(ruleClone.predict());
			}
		}

		// apply lexical rules
		for(ArrayList<Terminal> lexicalRule : lexicon.getRules(thisSymbol))
		{
			TopDownParser ruleClone = clone();
			ArrayList<Symbol> cloneToCircumventCasting = new ArrayList<>(); // this is ridiculous but I guess I can't cast ArrayList<Terminal> to ArrayList<Symbol>
			cloneToCircumventCasting.addAll(lexicalRule);
			if(ruleClone.apply(cloneToCircumventCasting))
			{
				derivations.add(ruleClone);
				derivations.addAll(ruleClone.predict());
			}
		}

		return derivations;
	}

	/**
	 * applies a rule to the parser
	 * rule is always applied to first nonterminal on stack
	 * 
	 * @param grammarRule - the rule to apply
	 * @return true if application of rule is possible, else false;
	 */
	public boolean apply(ArrayList<Symbol> rule)
	{
		// remove top nonterminal
		NonTerminal thisNT = state.pop(); // capture for making rule later

		ArrayList<Symbol> clones = new ArrayList<>();
		for(Symbol item : rule)
		{
			if(item instanceof NonTerminal)
			{
				NonTerminal clone = ((NonTerminal) item).clone();
				clone.setUniqueIdentifier();
				clones.add(clone);
			}

			else if(item instanceof Terminal)
				clones.add(((Terminal) item).clone());
		}

		// add new symbols in reverse order
		for(int i = clones.size() - 1; i >= 0; i --)
		{
			Symbol s = clones.get(i);

			if(s instanceof NonTerminal) // apply grammar transformations
				state.push((NonTerminal) s); // use clone so that it has a new memory address/unique id for graph generation later
			else if(s instanceof Terminal)
			{
				// always dealing w head; if it doesn't match first element of input it isn't right
				if(input.isEmpty())
					return false;
				if(((Terminal) s).equals(input.get(0)))
					input.remove(0); // no use in making clone; if this fails the object will be discarded
				else
					return false;
			}
		}

		// add rule to log
		analysis.push(new Rule(thisNT, clones));

		// as long as there aren't too many nonterminals left it's ok
		return !isTooLong();
	}

	/**
	 * checks state length to avoid infinite recursion
	 * relies on there not being any rules with the empty string
	 * 
	 * @return true if the current hypothesis is longer than the input
	 */
	public boolean isTooLong()
	{
		return state.size() > input.size();
	}

	public String toString()
	{
		String str = "digraph {\n";

		for(Rule r : analysis)
			str += "\t" + r.toString() + "\n";

		str += "}";

		return str;
	}

	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if(o == null)
			return false;

		if(!(o instanceof TopDownParser))
			return false;

		TopDownParser t = (TopDownParser) o;
		if(!grammar.equals(t.getGrammar()))
			return false;
		if(!lexicon.equals(t.getLexicon()))
			return false;
		if(!input.equals(t.getInput()))
			return false;
		if(!state.equals(t.getState()))
			return false;
		if(!analysis.equals(t.getAnalysis()))
			return false;

		return true;
	}

	public ArrayList<Terminal> getInput()
	{
		return input;
	}

	public Grammar getGrammar()
	{
		return grammar;
	}

	public Lexicon getLexicon()
	{
		return lexicon;
	}

	public Stack<NonTerminal> getState()
	{
		return state;
	}

	public Stack<Rule> getAnalysis()
	{
		return analysis;
	}
}
