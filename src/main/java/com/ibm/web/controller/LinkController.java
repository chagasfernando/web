package com.ibm.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkController {

	public static Set<String> uniqueURL = new HashSet<String>();
	public static Set<String> uniqueSUBURL = new HashSet<String>();
	public static String my_site;

	public static List<String> getLinks(String url) {

		final ArrayList<String> result = new ArrayList<String>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(10 * 1000).get();
		} catch (IOException ex) {

		}

		Elements links = doc.select("a");

		for (Element link : links) {
			my_site = link.attr("abs:href");

			if (my_site != null && !my_site.isEmpty()) {
				boolean add = uniqueURL.add(link.attr("abs:href"));
				if (add && link.attr("abs:href").contains(my_site)) {
					result.add(link.attr("abs:href"));
				}
			}
		}

		return result;
	}

	public static List<String> getSubLinks(String url) {
		//System.out.println("-------->" + url);
		final ArrayList<String> result = new ArrayList<String>();		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a");
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = uniqueSUBURL.add(this_url);				
				if (this_url != null && !this_url.isEmpty()) {
					result.add(this_url);
					//System.out.println("---------------->" + this_url);
				}				
				if (add && this_url.contains(my_site)) {
					//System.out.println("------------------------>" + this_url);
					//result.add(this_url);
					getSubLinks(this_url);
				}
			});

		} catch (IOException ex) {

		}
		return result;

	}
}
