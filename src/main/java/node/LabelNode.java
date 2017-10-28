/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class LabelNode extends Node
{
	private final String label;

	public LabelNode(String label)
	{
		this.label = label;
	}

	@Override
	protected String getNodeContent()
	{
		return label;
	}

	@Override
	protected String getNodeShape()
	{
		return "cds";
	}

}
