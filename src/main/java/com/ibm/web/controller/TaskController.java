package com.ibm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.web.model.Site;
import com.ibm.web.repository.SiteRepository;

public class TaskController {

	@Autowired
	private static SiteRepository er;

	public static void addSite(String url) {
		Site site = new Site();
		site.setUrl(url);
		er.save(site);
		System.out.println(url); 
		System.out.println("OK"); 

	}
}

