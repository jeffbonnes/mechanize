/**
 * Copyright (C) 2012-2014 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.gistlabs.mechanize.document.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.gistlabs.mechanize.MechanizeTestCase;
import com.gistlabs.mechanize.Resource;
import com.gistlabs.mechanize.util.apache.ContentType;

/**
 * @author Martin Kersten<Martin.Kersten.mk@gmail.com>
 */
public class HtmlElementsTest extends MechanizeTestCase {

	protected String contentType() {
		return ContentType.TEXT_HTML.getMimeType();
	}

	
	@Test
	public void testFindingALink() {
		addPageRequest("http://test.com", 
				newHtml("Test Page", "<a href=\"http://test.com/myPage.html\">myPage</a>"));
		
		Resource page = agent().get("http://test.com");
		HtmlElement htmlElement = ((HtmlDocument)page).htmlElements().find("*[href$='myPage.html']");
		assertNotNull(htmlElement);
		assertEquals("http://test.com/myPage.html", htmlElement.getAttribute("href"));
	}

	@Test
	public void testFindingTwoLinks() {
		addPageRequest("http://test.com", 
				newHtml("Test Page", "<a href=\"link1\">link1</a><a href=\"link2\">link2</a>"));
		
		Resource page = agent().get("http://test.com");
		List<HtmlElement> elements = ((HtmlDocument)page).htmlElements().findAll("a[href*='link']");//byHRef(regEx("link[0-9]")));
		assertNotNull(elements);
		assertEquals(2, elements.size());
		assertEquals("link1", elements.get(0).getAttribute("href"));
		assertEquals("link2", elements.get(1).getAttribute("href"));
	}
}