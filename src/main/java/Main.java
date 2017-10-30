import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import node.Node;
import parser.ParseFile;

/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Deque<String> arguments = new LinkedList<>(Arrays.asList(args));

		String inputFilename = arguments.pollFirst();
		if (inputFilename == null)
		{
			throw new RuntimeException("\n\nusage:\n  Main <input.txt> [ <output1.dot> [ ... ] ]\n");
		}

		List<Node> nodes = new ParseFile(inputFilename).getNodes();

		writeDotFile(nodes, System.out);

		arguments.forEach(outputFilename -> writeDotFile(nodes, outputFilename));

	}

	private static void writeDotFile(List<Node> nodes, String filename)
	{
		try
		{
			writeDotFile(nodes, new PrintStream(filename));
		} catch (FileNotFoundException e)
		{
			// stupid CheckedExceptions vs. Java 8 Functional stuff
			throw new RuntimeException(e);
		}
		System.err.println("also wrote to " + filename);
	}

	private static void writeDotFile(List<Node> nodes, PrintStream ps)
	{
		ps.println("digraph {");

		nodes.stream() //
				.map(Node::formatForDot) //
				.forEach(ps::print);

		ps.println("}");
	}
}
