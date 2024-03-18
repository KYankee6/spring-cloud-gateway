package com.uicheon.catalogservice.messagequeue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uicheon.catalogservice.jpa.CatalogEntity;
import com.uicheon.catalogservice.jpa.CatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private final CatalogRepository repository;

	@KafkaListener(topics = "example-catalog-topic")
	public void updateQty(String kafkaMessage) {
		log.info("Kafka Message: ->" + kafkaMessage);
		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
			});
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		if (entity != null) {
			entity.setStock(entity.getStock() - (Integer)map.get("qty"));
			repository.save(entity);
		}
	}
}
