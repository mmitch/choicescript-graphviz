/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dot.Color;
import dot.Shape;

public class ChoiceNode extends Node
{
	private final List<Node> children = new ArrayList<>();

	public ChoiceNode(int indent)
	{
		super(indent);
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.TRIANGLE, Color.CORNSILK, "?") + formatEdges();
	}

	@Override
	public void append(Node node)
	{
		if (isDeeper(node))
		{
			children.add(node);
		}
		else
		{
			super.append(node);
		}
	}

	private String formatEdges()
	{
		return Stream.concat(children.stream(), getNextAsStream()) //
				.map(this::formatEdge) //
				.collect(Collectors.joining());
	}

	private String formatEdge(Node selection)
	{
		Node target = selection.getNext().orElse(this.getNext().orElse(null));
		if (selection instanceof SelectionNode)
		{
			return dotEdgeTo(target, ((SelectionNode) selection).getSelection());
		}
		return dotEdgeTo(target);
	}

	private Stream<Node> getNextAsStream()
	{
		return getNext().map(Stream::of).orElse(Stream.empty());
	}
}
