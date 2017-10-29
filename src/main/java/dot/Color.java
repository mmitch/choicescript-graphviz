/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package dot;

public enum Color
{
	NONE, CHARTREUSE, FIREBRICK, CORNSILK, POWDERBLUE, ORANGE;

	public String dotString()
	{
		return this.toString().toLowerCase();
	}
}
