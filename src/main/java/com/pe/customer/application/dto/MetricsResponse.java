package com.pe.customer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricsResponse {

	private double averageAge;
    private double standardDeviation;
}
