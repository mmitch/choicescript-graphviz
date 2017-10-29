/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class ChoiceNode extends Node
{

	public ChoiceNode(int indent)
	{
		super(indent);
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.TRIANGLE, Color.CORNSILK, "?") + formatEdges();
	}

	private String formatEdges()
	{
		return getNext().map(this::formatEdge).orElse("");
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

}
