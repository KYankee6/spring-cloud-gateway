package com.uicheon.catalogservice.jpa;

import static jakarta.persistence.GenerationType.*;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "catalog")
public class CatalogEntity implements Serializable {
	@Id @GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120, unique = true)
	private String productId;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private Integer stock;
	@Column(nullable = false)
	private Integer unitPrice;

	@Column(nullable = false, updatable = false, insertable = false)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date createdAt;


}
