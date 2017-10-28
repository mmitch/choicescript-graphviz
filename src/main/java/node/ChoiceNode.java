/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.Optional;
import java.util.stream.Collectors;

public class ChoiceNode extends Node
{

	@Override
	public String getEdgeString()
	{
		return getNext() //
				.map(this::formatEdge) //
				.collect(Collectors.joining());
	}

	@Override
	protected String getNodeContent()
	{
		return "?";
	}

	@Override
	protected String getNodeShape()
	{
		return "triangle";
	}

	private String formatEdge(Node selection)
	{
		String myId = getId();
		String targetId = getNextNodeFrom(selection).orElse(getNextNodeFrom(this).orElse("SOMETHING BROKE - NO TARGET"));
		return String.format("%s -> %s [ label=\"%s\" ];\n", myId, targetId, selection.getNodeContent());
	}

	private Optional<String> getNextNodeFrom(Node node)
	{
		return node.getNext().findFirst().map(Node::getId);
	}

}
