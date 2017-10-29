/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class EndNode extends Node
{

	@Override
	public String formatForDot()
	{
		return dotNode("doublecircle", "END");
	}

}
