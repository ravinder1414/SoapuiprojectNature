package com.nature.quickstep.tools.curator;

import gherkin.formatter.model.Comment;
import gherkin.formatter.model.Tag;

import java.util.List;


public class Feature extends gherkin.formatter.model.Feature {
	
	private static final long serialVersionUID = -8910198671320000837L;

	public Element[] elements;

	public Feature(List<Comment> comments, List<Tag> tags, String keyword,
			String name, String description, Integer line, String id) {
		super(comments, tags, keyword, name, description, line, id);				
	}
}
