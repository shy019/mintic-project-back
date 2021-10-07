package co.com.mintic.tiendagenerica.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "productos", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo_producto") })
public class Product {
	@Id
	@Column(name = "codigo_producto")
	private long codigoProducto;

	@NotBlank
	@Column(name = "ivacompra")
	private Double ivaCompra;

	@NotBlank
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nitproveedor")
	private Supplier nitProveedor;

	@NotBlank
	@Size(max = 40)
	@Column(name = "nombre_producto")
	private String nombreProducto;

	@NotBlank
	@Column(name = "precio_compra")
	private Double precioCompra;

	@NotBlank
	@Column(name = "precio_venta")
	private Double precioVenta;

	public long getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(long codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public Double getIvaCompra() {
		return ivaCompra;
	}

	public void setIvaCompra(Double ivaCompra) {
		this.ivaCompra = ivaCompra;
	}

	public Supplier getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Supplier nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Product(long codigoProducto, @NotBlank Double ivaCompra, @NotBlank Supplier nitProveedor,
			@NotBlank @Size(max = 40) String nombreProducto, @NotBlank Double precioCompra,
			@NotBlank Double precioVenta) {
		super();
		this.codigoProducto = codigoProducto;
		this.ivaCompra = ivaCompra;
		this.nitProveedor = nitProveedor;
		this.nombreProducto = nombreProducto;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
	}

	public Product() {
		super();
	}

}
