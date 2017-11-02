convert ChoiceScript files to GraphViz trees
============================================

[![Build Status](https://travis-ci.org/mmitch/choicescript-graphviz.svg?branch=master)](https://travis-ci.org/mmitch/choicescript-graphviz)

examples
--------

1. [example ChoiceScript file](example/example.txt)
2. [converted to dot](example/example.dot)
3. [converted to SVG](example/example.dot.svg)

project status
--------------

- spontaneous idea
- had some fun coding
- works so far

Not all ChoiceScript parameters are implemented yet.  I don't know if
they ever will be.

Note that text nodes are shortened to *T[character_count]*.  The first
part of the text will be displayed as a tooltip.

todos
-----

- implement missing commands
  - *comment
  - *else
  - *elsif
  - *gosub
  - *goto_scene
  - *hide_reuse
  - *input_text
  - *return
  - *temp
- *if between *choice and #selection
- show *disable_reuse on #selection graph edge
- show *selectable_if on #selection graph edge

resources
---------

- [ChoiceScript introduction](https://www.choiceofgames.com/make-your-own-games/choicescript-intro/)
- [ChoiceScript sources](https://github.com/dfabulich/choicescript)
