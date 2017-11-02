/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dot.Color;
import dot.Shape;

public abstract class Node
{
	private static int idSequencer = 0;
	private final int indent;
	private final String id;
	private Node next;

	protected Node(int indent)
	{
		this.indent = indent;
		id = String.valueOf(idSequencer++);
	}

	public void append(Node node)
	{
		// FIXME: assertions for "endless loop (self)" and "duplicate call"
		next = node;
	}

	public final String getId()
	{
		return String.valueOf(id);
	}

	public abstract String formatForDot();

	protected final String dotNode(Shape shape, Color color, String content)
	{
		return dotNode(shape, color, content, null);
	}

	protected final String dotNode(Shape shape, Color color, String content, String tooltip)
	{
		List<String> attributes = new ArrayList<>();
		attributes.add("shape=" + shape.dotString());
		attributes.add("fillcolor=" + color.dotString());
		attributes.add("style=filled");
		attributes.add("label=\"" + content + "\"");
		if (tooltip != null)
		{
			attributes.add("tooltip=\"" + tooltip + "\"");
		}
		return String.format(" %s [ %s ];\n", id, String.join(",", attributes));
	}

	protected final String dotEdgeToNext()
	{
		return getNext().map(next -> dotEdgeTo(next)).orElse("");
	}

	protected final String dotEdgeTo(Node target)
	{
		return String.format(" %s -> %s;\n", id, target.id);
	}

	protected final String dotEdgeTo(Node target, String label)
	{
		return String.format(" %s -> %s [ label=\"%s\" ];\n", id, target.id, label);
	}

	protected final Optional<Node> getNext()
	{
		return Optional.ofNullable(next);
	}

	protected final boolean isDeeper(Node other)
	{
		return other.indent > this.indent;
	}

	protected void appendIfDangling(Node start, Node newNode)
	{
		Node node = start;
		while (true)
		{
			if (node == newNode)
			{
				// if we come across the node to be appended, there is nothing
				// to do
				return;
			}

			if (node instanceof GotoNode)
			{
				// a *goto jumps to a fixed location, so no further appending is
				// necessary
				return;
			}

			Node next = node.getNext().orElse(null);

			if (next == null)
			{
				// that's our dangling end!
				node.append(newNode);
				return;
			}

			node = next;
		}
	}
}
