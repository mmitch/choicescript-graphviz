import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import node.Node;
import node.SelectionNode;
import node.StartNode;
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

		showWarnings(nodes);
	}

	private static void showWarnings(List<Node> nodes)
	{
		// TODO: REFACTOR: it is a bit stupid to first convert to dot and then
		// parse that output to get the followup nodes…

		Map<String, Node> unusedIds = new HashMap<>();
		nodes.stream() //
				.filter(Main::nodeReceivesConnections) //
				.forEach(node -> unusedIds.put(node.getId(), node));

		Pattern nextNodePattern = Pattern.compile("^\\s*\\d+\\s*->\\s*(\\d+)");

		nodes.stream() //
				.map(Node::formatForDot) //
				.flatMap(dotLines -> Stream.of(dotLines.split("\n"))) //
				.flatMap(dotLine -> {
					Matcher nextNode = nextNodePattern.matcher(dotLine);
					if (nextNode.find())
					{
						return Stream.of(nextNode.group(1));
					}
					return Stream.empty();
				}) //
				.forEach(unusedIds::remove);

		unusedIds.values().forEach(node -> System.err.printf("WARNING: unconnected node %s: [%s]\n%s\n", node.getId(), node.getClass().getSimpleName(), node.formatForDot()));
	}

	private static boolean nodeReceivesConnections(Node node)
	{
		if (node instanceof SelectionNode)
		{
			return false;
		}
		if (node instanceof StartNode)
		{
			return false;
		}
		return true;
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
