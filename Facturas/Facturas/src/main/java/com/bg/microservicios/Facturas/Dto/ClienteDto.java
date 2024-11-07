package com.bg.microservicios.Facturas.Dto;

import lombok.Data;

@Data
public class ClienteDto {
	
	private Integer id;           
    private String nombre;         
    private String email;          
    private String telefono;       
    private String direccion;      
    private String nit;   
    public Integer getId() {
		return id;
	}
}