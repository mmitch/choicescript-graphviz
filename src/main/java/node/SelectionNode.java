/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class SelectionNode extends Node
{
	private final String selection;

	public SelectionNode(int indent, String selection)
	{
		super(indent);
		this.selection = selection;
	}

	@Override
	public String formatForDot()
	{
		// handled by named edges in {@link ChoiceNode}
		return "";
	}

	public String getSelection()
	{
		return selection;
	}

}
