/*
 * Copyright 2017 (C)  Christian Garbs <mitch@cgarbs.de>
 * Licensed under GNU GPL 3 (or later)
 */
package parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import node.ChoiceNode;
import node.EndNode;
import node.GotoNode;
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
	private EndNode end;
	private Node current;
	private int lastIndent = 0;
	private String indenter = "";

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
		if (line.matches("^\\s*$"))
		{
			return;
		}

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
			current = stack.pop();
			lastIndent--;
		}

		line = line.trim();

		if (isCommand(line))
		{
			parseCommand(indent, removeFirst(line));
		}
		else if (isChoice(line))
		{
			parseChoice(indent, removeFirst(line));
		}
		else
		{
			parseText(indent, line);
		}

	}

	private void parseCommand(int indent, String line)
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
				current = appendNodeToCurrent(new ChoiceNode(indent));
				break;

			case "if":
				current = appendNodeToCurrent(new IfNode(indent, params));
				break;

			case "create":
				current = appendNodeToCurrent(new VariableNode(indent, Type.CREATE, params));
				break;

			case "set":
				current = appendNodeToCurrent(new VariableNode(indent, Type.SET, params));
				break;

			case "rand":
				current = appendNodeToCurrent(new VariableNode(indent, Type.RANDOM, params));
				break;

			case "goto_scene": // TODO: add multifile handling
			case "finish":
				current = appendNodeToCurrent(new GotoNode(indent, end));
				break;

			case "goto":
				current = appendNodeToCurrent(new GotoNode(indent, params));
				break;

			case "label":
				current = appendNodeToCurrent(new LabelNode(params));
				break;

			case "selectable_if": // TODO: better handling needed!
				split = params.split("#", 2);
				parseChoice(indent, split[1]);
				break;

			case "disable_reuse": // TODO: better handling needed!
				split = params.split("#", 2);
				parseChoice(indent, split[1]);
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

	private void parseChoice(int indent, String line)
	{
		current = stack.peek();
		current = appendNodeToCurrent(new SelectionNode(indent, line));
	}

	private void parseText(int indent, String line)
	{
		if (!(current instanceof TextNode))
		{
			current = appendNodeToCurrent(new TextNode(indent));
		}

		((TextNode) current).appendText(line);
	}

	private String removeFirst(String line)
	{
		int toremove = 1;
		if (firstCharIs(line, ' ')) {
			toremove = indenter.length();
		}
		return line.substring(toremove);
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
		// Initially determine the indenter type
		String temp = "";
		if (indenter == "") {
			if (firstCharIs(line, '\t') || firstCharIs(line, ' ')) {
				if (firstCharIs(line, '\t')) {
					indenter = "\t";
				} else if (firstCharIs(line, ' ')) {
					// gather spaces
					for(int i = 0; i<line.length(); i++) {
						if (line.charAt(i) == ' ') {
							temp += ' ';
						} else {
							break;
						}
					}
					indenter = temp;
				}
			}
		}
		// See if line is indented
		if (indenter == "\t") {
			return firstCharIs(line, '\t');
		} else {
			return firstCharIs(line, ' ');
		}
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
