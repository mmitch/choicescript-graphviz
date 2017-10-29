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
		StringBuilder sb = new StringBuilder();
		sb.append(dotNode(Shape.DIAMOND, Color.CORNSILK, condition));
		if (conditionalPath != null)
		{
			sb.append(dotEdgeTo(conditionalPath, "Y"));
		}
		getNext().map(next -> dotEdgeTo(next, "N")).ifPresent(sb::append);

		return sb.toString();
	}

	@Override
	public void append(Node node)
	{
		if (isDeeper(node))
		{
			conditionalPath = node;
		}
		else
		{
			super.append(node);
		}
	}
}
