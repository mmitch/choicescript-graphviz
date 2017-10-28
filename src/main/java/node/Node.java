/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Node
{
	private static int idSequencer = 0;
	private final int id;
	private List<Node> next = new ArrayList<>();

	protected Node()
	{
		id = idSequencer++;
	}

	public void append(Node node)
	{
		next.add(node);
	}

	// FIXME: no separation of NodeString and EdgeString needed! dot files can
	// be mixed
	public String getNodeString()
	{
		List<String> attributes = new ArrayList<>();
		attributes.add("shape=" + getNodeShape());
		attributes.add("label=\"" + getNodeContent() + "\"");
		String toolTip = getNodeToolTip();
		if (toolTip != null)
		{
			attributes.add("tooltip=\"" + toolTip + "\"");
		}
		return String.format("%d [ %s ];\n", id, String.join(",", attributes));
	}

	public String getEdgeString()
	{
		return next.stream() //
				.map(child -> String.format("%d -> %d;\n", id, child.id)) //
				.collect(Collectors.joining());
	}

	protected abstract String getNodeContent();

	protected abstract String getNodeShape();

	protected String getNodeToolTip()
	{
		// empty by default
		return null;
	}

	protected final String getId()
	{
		return String.valueOf(id);
	}

	protected final Stream<Node> getNext()
	{
		return next.stream();
	}
}
