package com.ibm.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.ibm.web.model.Site;

public interface SiteRepository extends CrudRepository<Site, String>{
	Site findByCodigo(long codigo);
}
