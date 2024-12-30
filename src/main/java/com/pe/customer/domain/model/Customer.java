package com.pe.customer.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede quedar en blanco")
	@Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
	private String name;
	
	@NotNull(message = "El apellido no puede ser nulo")
	@NotBlank(message = "El apellido no puede quedar en blanco")
	@Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
	private String lastName;
	
	@NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad no puede ser negativa")
	private Integer age;
	
	@NotNull(message = "La fecha de nacimiento no puede ser nula")
	@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "La fecha de nacimiento debe estar en el formato 'yyyy-MM-dd', por ejemplo: 1992-12-31")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String birthDate;
}
