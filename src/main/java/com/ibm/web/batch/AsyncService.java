package com.ibm.web.batch;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.ibm.web.controller.LinkController;
import com.ibm.web.model.Site;
import com.ibm.web.model.SubSite;
import com.ibm.web.repository.SiteRepository;
import com.ibm.web.repository.SubSiteRepository;

@Service
public class AsyncService {

	@Autowired
	private SiteRepository er;

	@Autowired
	private SubSiteRepository sr;

	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Async
	public Future<String> processLinks(long id, String url) throws InterruptedException {
		log.info("###Start Thread id: " + Thread.currentThread().getId());

		Site base = er.findByCodigo(id);
		SubSite subsite = new SubSite();
		subsite.setSite(base);
		subsite.setUrl(url);
		sr.save(subsite);
		// log.info("------>" + url);
		List<String> sublinks = LinkController.getSubLinks(url);
		
		Site site = new Site();
		site.setRoot(base.getUrl());
		site.setUrl(url);
		er.save(site);

		for (String sublink : sublinks) {
			//log.info("------------>" + sublink);
			Future<String> process = processSubLinks(sublink, site);
		}

		log.info("###Done Thread id: " + Thread.currentThread().getId());
		String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
		return new AsyncResult<>(processInfo);
	}

	@Async
	public Future<String> processSubLinks(String url, Site base) throws InterruptedException {
		log.info("###Start Thread id: " + Thread.currentThread().getId());

		SubSite subsite = new SubSite();
		subsite.setSite(base);
		subsite.setUrl(url);
		sr.save(subsite);
		// log.info(url);

		log.info("###Done Thread id: " + Thread.currentThread().getId());
		String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
		return new AsyncResult<>(processInfo);
	}
}
