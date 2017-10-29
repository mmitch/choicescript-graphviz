/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Shape;

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
		return dotNode(Shape.DIAMOND, condition) + dotEdgeTo(getNext());
	}

}
