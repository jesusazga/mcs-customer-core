package com.pe.customer.util;

public class ConstantSwagger {

	private ConstantSwagger() {}
	// Ejemplo para errores de validación
    public static final String VALIDATION_ERROR_400_BAD_REQ = """
        {
            "lastName": "El apellido no puede ser nulo",
            "name": "El nombre no puede ser nulo",
            "birthDate": "La fecha de nacimiento debe estar en el formato 'yyyy-MM-dd', por ejemplo: 1992-12-31",
            "age": "La edad no puede ser nula"
        }
        """;
    
    public static final String VALIDATION_ERROR_422_METRICS = "No se encontraron clientes para calcular métricas.";
    public static final String VALIDATION_ERROR_422_DUPLICATE_NAME = "Ya existe un cliente con el mismo nombre.";
}
