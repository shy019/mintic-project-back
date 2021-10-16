package co.com.mintic.tiendagenerica.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "detalle_ventas", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo_detalle_venta") })
public class SaleDetail {

	@Id
	@Column(name = "codigo_detalle_venta")
	private long codigoDetalleVenta;

	@NotBlank
	@Column(name = "cantidad_producto")
	private long cantidadProducto;

	@OneToOne
	@JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto")
	private Product codigoProducto;

	@OneToOne
	@JoinColumn(name = "codigo_venta", referencedColumnName = "codigo_venta")
	private Sale codigoVenta;

	@NotBlank
	@Column(name = "valor_total")
	private double valorTotal;

	@NotBlank
	@Column(name = "valor_venta")
	private double valorVenta;

	public long getCodigoDetalleVenta() {
		return codigoDetalleVenta;
	}

	public void setCodigoDetalleVenta(long codigoDetalleVenta) {
		this.codigoDetalleVenta = codigoDetalleVenta;
	}

	public long getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(long cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Product getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(Product codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public Sale getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(Sale codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public double getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public SaleDetail(@NotBlank long cantidadProducto, @NotBlank Product codigoProducto, @NotBlank Sale codigoVenta,
			@NotBlank double valorTotal, @NotBlank double valorVenta) {
		super();
		this.cantidadProducto = cantidadProducto;
		this.codigoProducto = codigoProducto;
		this.codigoVenta = codigoVenta;
		this.valorTotal = valorTotal;
		this.valorVenta = valorVenta;
	}

	public SaleDetail() {
		super();
	}

}