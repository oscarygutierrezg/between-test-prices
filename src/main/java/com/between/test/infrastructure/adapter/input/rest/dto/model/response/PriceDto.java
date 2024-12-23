package com.between.test.infrastructure.adapter.input.rest.dto.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PriceDto {

	private long priceList;
	private long productId;
	private long brandId;
	private long priority;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String curr;
	private double price;
}
