package com.uicheon.catalogservice.service;

import com.uicheon.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
	Iterable<CatalogEntity> getAllCatalogs();

}
