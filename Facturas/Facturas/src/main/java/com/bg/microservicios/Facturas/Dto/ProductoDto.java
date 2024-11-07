package com.bg.microservicios.Facturas.Dto;

import lombok.Data;

@Data
public class ProductoDto {
    private Integer idProducto;  
    private String producto;
    private String descripcion;
    private Double precio;
    private Integer stock;
	public Integer getIdProducto() {
		return idProducto;
	}
}
