/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.function.Supplier;

import dot.Color;
import dot.Shape;

public class GotoNode extends Node
{
	private final Supplier<LabelNode> delayedLabelNodeLookup;

	public GotoNode(int indent, String targetLabel)
	{
		super(indent);
		delayedLabelNodeLookup = () -> LabelNode.findByLabel(targetLabel);
	}

	public GotoNode(int indent, LabelNode targetNode)
	{
		super(indent);
		delayedLabelNodeLookup = () -> targetNode;
	}

	@Override
	public String formatForDot()
	{
		if (!getNext().isPresent())
		{
			super.append(delayedLabelNodeLookup.get());
		}

		// TODO: use node/shape, only a link to the label -> skip in source
		// rendering, not so easy...
		return dotNode(Shape.POINT, Color.NONE, "") + dotEdgeToNext();
	}

	@Override
	public void append(Node node)
	{
		// as *goto jumps to a specific label and continues there, no additional
		// nodes can be appended
	}

}
