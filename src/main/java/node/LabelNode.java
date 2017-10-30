/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.HashMap;
import java.util.Map;

import dot.Color;
import dot.Shape;

public class LabelNode extends Node
{
	private static Map<String, LabelNode> knownLabels = new HashMap<>();

	private final String label;

	public LabelNode(String label)
	{
		super(0);
		this.label = label;
		registerLabel();
	}

	@Override
	public String formatForDot()
	{
		return dotNode(Shape.CDS, Color.ORANGE, label) + dotEdgeToNext();
	}

	public static LabelNode findByLabel(String label)
	{
		return knownLabels.get(label);
	}

	private void registerLabel()
	{
		if (knownLabels.containsKey(label))
		{
			throw new RuntimeException("duplicate label `" + label + "' defined");
		}
		knownLabels.put(label, this);
	}

}
