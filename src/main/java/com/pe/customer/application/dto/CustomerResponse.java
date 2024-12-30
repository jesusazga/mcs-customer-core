package com.pe.customer.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerResponse {

	private Long id;
    private String name;
    private String lastName;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;
    private String zodiacSign;
}
