/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class SelectionNode extends Node
{
	private final String selection;

	public SelectionNode(String selection)
	{
		this.selection = selection;
	}

	@Override
	public String getEdgeString()
	{
		// handled by named edges in {@link ChoiceNode}
		return "";
	}

	@Override
	public String getNodeString()
	{
		// handled by named edges in {@link ChoiceNode}
		return "";
	}

	@Override
	protected String getNodeShape()
	{
		// handled by named edges in {@link ChoiceNode}
		return "";
	}

	@Override
	protected String getNodeContent()
	{
		return selection;
	}
}
