/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class IfNode extends Node
{
	private final String condition;
	private Node conditionalPath;

	public IfNode(int indent, String condition)
	{
		super(indent);
		this.condition = condition;
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.DIAMOND, Color.CORNSILK, condition) + dotEdgeTo(getNext(), this::getEdgeLabel);
	}

	private String getEdgeLabel(Node node)
	{
		if (isDeeper(node))
		{
			return "Y";
		}
		return "N";
	}

	@Override
	public void append(Node node)
	{
		if (isDeeper(node))
		{
			conditionalPath = node;
		}
		super.append(node);
	}
}
