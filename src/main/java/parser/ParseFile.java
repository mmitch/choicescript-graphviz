/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import node.ChoiceNode;
import node.EndNode;
import node.IfNode;
import node.LabelNode;
import node.Node;
import node.SelectionNode;
import node.StartNode;
import node.TextNode;
import node.VariableNode;
import node.VariableNode.Type;

public class ParseFile
{
	private final List<Node> nodes = new ArrayList<>();
	private final Stack<Node> stack = new Stack<>();
	private final Map<String, Node> labels = new HashMap<>();
	private final Set<Node> extraAppend = new HashSet<>();
	private Node end;
	private Node current;
	private int lastIndent = 0;

	public ParseFile(String filename) throws IOException
	{
		createStartNode();
		createEndNode();
		parseFile(new File(filename));
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

	private void createStartNode()
	{
		current = registerNewNode(new StartNode());
	}

	private void createEndNode()
	{
		end = registerNewNode(new EndNode());
	}

	private <T extends Node> T appendNodeToCurrent(T newNode)
	{
		if (current != null)
		{
			current.append(newNode);
		}
		extraAppend.forEach(extra -> extra.append(newNode));
		extraAppend.clear();
		return registerNewNode(newNode);
	}

	private <T extends Node> T registerNewNode(T newNode)
	{
		nodes.add(newNode);
		return newNode;
	}

	private void parseFile(File file) throws IOException
	{
		Files.lines(file.toPath()).forEach(this::parseLine);
	}

	private void parseLine(String line)
	{
		int indent = 0;
		while (isIndented(line))
		{
			indent++;
			line = removeFirst(line);
		}

		while (indent > lastIndent)
		{
			stack.push(current);
			lastIndent++;
		}

		while (indent < lastIndent)
		{
			// TODO: this needs to be refactored
			connectDanglingIfPath();

			current = stack.pop();
			lastIndent--;
		}

		line = line.trim();

		if (isCommand(line))
		{
			parseCommand(removeFirst(line));
		}
		else if (isChoice(line))
		{
			parseChoice(removeFirst(line));
		}
		else
		{
			parseText(line);
		}

	}

	private void connectDanglingIfPath()
	{
		if (stack.peek() instanceof IfNode)
		{
			extraAppend.add(current);
		}
	}

	private void parseCommand(String line)
	{
		String[] split = line.split("\\s+", 2);
		String command = split[0];
		String params = "";
		if (split.length == 2)
		{
			params = split[1];
		}

		switch (command)
		{
			case "choice":
			case "fake_choice":
				current = appendNodeToCurrent(new ChoiceNode());
				break;

			case "if":
				current = appendNodeToCurrent(new IfNode(params));
				break;

			case "set":
				current = appendNodeToCurrent(new VariableNode(Type.SET, params));
				break;

			case "rand":
				current = appendNodeToCurrent(new VariableNode(Type.RANDOM, params));
				break;

			case "goto_scene": // TODO: add multifile handling
			case "finish":
				current.append(end);

			case "goto":
				current.append(createOrFindLabel(params));
				break;

			case "label":
				current = createOrFindLabel(params);
				break;

			case "selectable_if": // TODO: better handling needed!
				split = params.split("#", 2);
				parseChoice(split[1]);
				break;

			case "disable_reuse": // TODO: better handling needed!
				split = params.split("#", 2);
				parseChoice(split[1]);
				break;

			case "advertisement":
			case "image": // TODO: or use node?
			case "page_break": // TODO: or use multiple text nodes?
				// just skip it
				break;

			default:
				throw new RuntimeException("unknown command `" + command + "' in line:\n" + line);
		}

		// TODO Auto-generated method stub

	}

	private Node createOrFindLabel(String label)
	{
		Node labelNode = labels.get(label);
		if (labelNode == null)
		{
			labelNode = new LabelNode(label);
			registerNewNode(labelNode);
			labels.put(label, labelNode);
		}
		return labelNode;
	}

	private void parseChoice(String line)
	{
		current = stack.peek();
		current = appendNodeToCurrent(new SelectionNode(line));
	}

	private void parseText(String line)
	{
		if (!(current instanceof TextNode))
		{
			if (line.matches("^\\s*$"))
			{
				return;
			}
			current = appendNodeToCurrent(new TextNode());
		}

		((TextNode) current).appendText(line);
	}

	private String removeFirst(String line)
	{
		return line.substring(1);
	}

	private boolean isChoice(String line)
	{
		return firstCharIs(line, '#');
	}

	private boolean isCommand(String line)
	{
		return firstCharIs(line, '*');
	}

	private boolean isIndented(String line)
	{
		return firstCharIs(line, '\t');
	}

	private boolean firstCharIs(String line, char c)
	{
		if (line.isEmpty())
		{
			return false;
		}
		return line.charAt(0) == c;
	}

}
