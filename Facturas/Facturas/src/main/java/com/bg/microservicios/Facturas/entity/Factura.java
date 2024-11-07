package com.bg.microservicios.Facturas.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Factura {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "cliente_id", nullable = false)
    private Integer clienteId;
	
    private String nit;
    private String nombreCliente;
    private String numeroFactura;
    private Double total;
    private LocalDate fecha;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "producto_factura", 
        joinColumns = @JoinColumn(name = "factura_id"), 
        inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	} 
    
	public void calcularTotal() {
	    if (productos != null) { 
	        this.total = productos.stream()
	                              .mapToDouble(Producto::getPrecio) 
	                              .sum();
	    } else {
	        this.total = 0.0; 
	    }
	}
	public List<Producto> getProductos() {
		return productos;
	}
}