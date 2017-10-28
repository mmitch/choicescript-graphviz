/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

public class TextNode extends Node
{
	private final StringBuffer text = new StringBuffer();

	public void appendText(String line)
	{
		text.append(line);
	}

	@Override
	protected String getNodeToolTip()
	{
		int end = text.length();
		if (end > 100)
		{
			end = 100;
		}
		return text.substring(0, end).replace("\"", "\\\"");
	}

	@Override
	protected String getNodeContent()
	{
		return String.valueOf(text.length());
	}

	@Override
	protected String getNodeShape()
	{
		return "box";
	}

}
