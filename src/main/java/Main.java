import java.io.IOException;
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

		System.out.println("digraph {");

		nodes.stream() //
				.map(Node::getNodeString) //
				.forEach(System.out::print);

		nodes.stream() //
				.map(Node::getEdgeString) //
				.forEach(System.out::print);

		System.out.println("}");
	}
}
