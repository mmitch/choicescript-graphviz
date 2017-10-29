/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class GotoNode extends Node
{
	public GotoNode(int indent, LabelNode labelNode)
	{
		super(indent);
		super.append(labelNode);
	}

	@Override
	public String formatForDot()
	{
		// TODO: use node/shape, only a link to the label -> skip in source
		// rendering, not so easy...
		return dotNode(Shape.POINT, Color.NONE, "") + dotEdgeTo(getNext());
	}

	@Override
	public void append(Node node)
	{
		// as *goto jumps to a specific label and continues there, no additional
		// nodes can be appended
	}

}
