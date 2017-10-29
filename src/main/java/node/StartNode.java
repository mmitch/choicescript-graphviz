/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class StartNode extends Node
{
	@Override
	public String formatForDot()
	{
		return dotNode(Shape.DOUBLECIRCLE, Color.CHARTREUSE, "START") + dotEdgeTo(getNext());
	}
}
