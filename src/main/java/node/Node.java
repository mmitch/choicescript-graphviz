/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Node
{
	private static int idSequencer = 0;
	private final String id;
	private Set<Node> next = new HashSet<>();

	protected Node()
	{
		id = String.valueOf(idSequencer++);
	}

	public void append(Node node)
	{
		next.add(node);
	}

	public abstract String formatForDot();

	protected final String dotNode(String shape, String content)
	{
		return dotNode(shape, content, null);
	}

	protected final String dotNode(String shape, String content, String tooltip)
	{
		List<String> attributes = new ArrayList<>();
		attributes.add("shape=" + shape);
		attributes.add("label=\"" + content + "\"");
		if (tooltip != null)
		{
			attributes.add("tooltip=\"" + tooltip + "\"");
		}
		return String.format("%s [ %s ];\n", id, String.join(",", attributes));
	}

	protected final String dotEdgeTo(Stream<Node> targets)
	{
		return targets //
				.map(target -> String.format("%s -> %s;\n", id, target.id)) //
				.collect(Collectors.joining());
	}

	protected final String dotEdgeTo(Stream<Node> targets, String label)
	{
		return targets //
				.map(target -> String.format("%s -> %s [ label=\"%s\" ];\n", id, target.id, label)) //
				.collect(Collectors.joining());
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
