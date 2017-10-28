/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class VariableNode extends Node
{
	public enum Type
	{
		SET, RANDOM, CREATE
	};

	private final String variable;
	private final Type type;

	public VariableNode(Type type, String variable)
	{
		this.type = type;
		this.variable = variable;
	}

	@Override
	protected String getNodeContent()
	{
		return String.format("%s %s", type, variable);
	}

	@Override
	protected String getNodeShape()
	{
		return "hexagon";
	}

}