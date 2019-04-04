package com.ibm.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.ibm.web.model.Site;
import com.ibm.web.model.SubSite;

public interface SubSiteRepository extends CrudRepository<SubSite, String>{
	Iterable<SubSite> findBySite(Site site);
}

