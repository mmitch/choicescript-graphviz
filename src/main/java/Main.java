import java.io.IOException;
import java.io.PrintStream;
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
		List<Node> nodes = new ParseFile("/home/mitch/Dropbox/TheGreatTournament/mygame/scenes/war.txt").getNodes();

		writeDotFile(nodes, System.out);
		writeDotFile(nodes, new PrintStream("foo.dot"));
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
