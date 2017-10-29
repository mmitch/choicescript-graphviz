/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import dot.Color;
import dot.Shape;

public class TextNode extends Node
{
	private final StringBuffer text = new StringBuffer();

	public void appendText(String line)
	{
		text.append(line);
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.BOX, Color.NONE, "T[" + text.length() + "]", getNodeToolTip()) + dotEdgeTo(getNext());
	}

	private String getNodeToolTip()
	{
		int end = text.length();
		if (end > 100)
		{
			end = 100;
		}
		return text.substring(0, end).replace("\"", "\\\"");
	}
}
