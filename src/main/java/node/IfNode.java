/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class IfNode extends Node
{
	private final String condition;

	public IfNode(String condition)
	{
		this.condition = condition;
	}

	@Override
	public String formatForDot()
	{
		return dotNode("diamond", condition) + dotEdgeTo(getNext());
	}

}
