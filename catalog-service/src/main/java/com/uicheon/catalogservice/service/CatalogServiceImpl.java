package com.uicheon.catalogservice.service;

import org.springframework.stereotype.Service;

import com.uicheon.catalogservice.jpa.CatalogEntity;
import com.uicheon.catalogservice.jpa.CatalogRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
	private final CatalogRepository catalogRepository;

	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}
}
