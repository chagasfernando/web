package com.ibm.web.controller;

import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.web.batch.AsyncService;
import com.ibm.web.model.Site;
import com.ibm.web.model.SubSite;
import com.ibm.web.repository.SiteRepository;
import com.ibm.web.repository.SubSiteRepository;

@Controller
public class SiteController {

	@Autowired
	private SiteRepository er;

	@Autowired
	private SubSiteRepository sr;

	@Resource
	AsyncService services;

	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@RequestMapping(value = "/pesquisa", method = RequestMethod.GET)
	public String form() {
		return "add/formSite";
	}

	@RequestMapping(value = "/pesquisa", method = RequestMethod.POST)
	public String form(@Valid Site site, BindingResult result, RedirectAttributes attributes) throws Exception {
		System.out.println(site.getUrl());
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "URL the invalid!");
			return "redirect:/pesquisa";
		}
		er.save(site);
		List<String> links = LinkController.getLinks(site.getUrl());

		for (String link : links) {

			Future<String> process = services.processLinks(site, link);
						
		}
		
		attributes.addFlashAttribute("mensagem", "URL is ADD!");
		return "redirect:/pesquisa";
	}

	@RequestMapping("/consulta")
	public ModelAndView searchSite() {
		ModelAndView mv = new ModelAndView("add/searchSite");
		Iterable<Site> sites = er.findAll();
		mv.addObject("sites", sites);
		return mv;
	}

	@RequestMapping(value = "/{codigo}")
	public ModelAndView detailSite(@PathVariable("codigo") long codigo) {
		Site site = er.findByCodigo(codigo);

		Iterable<SubSite> subsites = sr.findBySite(site);

		ModelAndView mv = new ModelAndView("add/detailSite");

		mv.addObject("subsites", subsites);
		return mv;
	}

}
