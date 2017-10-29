/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dot.Color;
import dot.Shape;

public abstract class Node
{
	protected interface LabelProvider extends Function<Node, String>
	{

	}

	private static int idSequencer = 0;
	private final int indent;
	private final String id;
	private Set<Node> next = new HashSet<>();

	protected Node(int indent)
	{
		this.indent = indent;
		id = String.valueOf(idSequencer++);
	}

	public void append(Node node)
	{
		next.add(node);
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

	protected final String dotEdgeTo(Stream<Node> targets)
	{
		return targets //
				.map(target -> String.format(" %s -> %s;\n", id, target.id)) //
				.collect(Collectors.joining());
	}

	protected final String dotEdgeTo(Stream<Node> targets, LabelProvider labelProvider)
	{
		return targets //
				.map(target -> String.format(" %s -> %s [ label=\"%s\" ];\n", id, target.id, labelProvider.apply(target))) //
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

	protected final boolean isDeeper(Node other)
	{
		return other.indent > this.indent;
	}
}
