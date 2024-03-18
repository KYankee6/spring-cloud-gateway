package com.uicheon.catalogservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uicheon.catalogservice.jpa.CatalogEntity;
import com.uicheon.catalogservice.service.CatalogService;
import com.uicheon.catalogservice.vo.ResponseCatalog;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
	private final Environment env;
	private final CatalogService catalogService;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
	}
	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getUsers() {
		Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

		List<ResponseCatalog> result = new ArrayList<>();
		catalogList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
