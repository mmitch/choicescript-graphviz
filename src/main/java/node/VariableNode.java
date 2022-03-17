/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class VariableNode extends Node
{
	public enum Type
	{
		SET, RANDOM, CREATE, TEMP
	};

	private final String variable;
	private final Type type;

	public VariableNode(int indent, Type type, String variable)
	{
		super(indent);
		this.type = type;
		this.variable = variable;
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.HEXAGON, Color.POWDERBLUE, getNodeContent()) + dotEdgeToNext();
	}

	private String getNodeContent()
	{
		return String.format("%s %s", type, variable);
	}

}
