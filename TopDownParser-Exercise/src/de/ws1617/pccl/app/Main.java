package de.ws1617.pccl.app;

import java.io.IOException;
import java.util.HashSet;

import de.ws1617.pccl.grammar.*;
import de.ws1617.pccl.parser.TopDownParser;
import de.ws1617.pccl.search.BFS_TopDown;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			Grammar grammar = GrammarUtils.readGrammar(args[0]);
			Lexicon lexicon = GrammarUtils.readLexicon(args[1]);
			NonTerminal startSymbol = new NonTerminal(args[2]);

			Terminal[] input = new Terminal[args.length - 3];
			for(int i = 0; i < input.length; i ++)
				input[i] = new Terminal(args[i + 3].toLowerCase());

			HashSet<TopDownParser> parses = BFS_TopDown.search(input, grammar, lexicon, startSymbol);
			for(TopDownParser parse : parses)
				System.out.println(parse.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println("not enough arguments: grammar_path lexicon_path start_symbol input");
		}
	}
}
