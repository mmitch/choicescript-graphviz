/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dot.Color;
import dot.Shape;

public class ChoiceNode extends Node
{

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.TRIANGLE, Color.CORNSILK, "?") + formatEdges();
	}

	private String formatEdges()
	{
		return getNext() //
				.map(this::formatEdge) //
				.collect(Collectors.joining());
	}

	private String formatEdge(Node selection)
	{
		String myId = getId();
		Node target = getNextNodeFrom(selection).orElse(getNextNodeFrom(this).orElse(null));
		if (selection instanceof SelectionNode)
		{
			return dotEdgeTo(Stream.of(target), ((SelectionNode) selection).getSelection());
		}
		return dotEdgeTo(Stream.of(target));
	}

	private Optional<Node> getNextNodeFrom(Node node)
	{
		return node.getNext().findFirst();
	}

}
