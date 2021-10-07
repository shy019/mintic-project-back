package co.com.mintic.tiendagenerica.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedores", uniqueConstraints = { @UniqueConstraint(columnNames = "nitproveedor") })
public class Supplier {
	@Id
	@Column(name = "nitproveedor")
	private long nitProveedor;

	@NotBlank
	@Size(max = 60)
	@Column(name = "ciudad_proveedor")
	private String ciudadProveedor;

	@NotBlank
	@Size(max = 60)
	@Column(name = "direccion_proveedor")
	private String direccionProveedor;

	@NotBlank
	@Size(max = 30)
	@Column(name = "nombre_proveedor")
	private String nombreProveedor;

	@NotBlank
	@Size(max = 120)
	@Column(name = "telefono_proveedor")
	private String telefonoProveedor;

	public Long getNitProveedor() {
		return nitProveedor;
	}

	public void setNitProveedor(Long nitProveedor) {
		this.nitProveedor = nitProveedor;
	}

	public String getCiudadProveedor() {
		return ciudadProveedor;
	}

	public void setCiudadProveedor(String ciudadProveedor) {
		this.ciudadProveedor = ciudadProveedor;
	}

	public String getDireccionProveedor() {
		return direccionProveedor;
	}

	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(String telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public Supplier(Long nitProveedor, @NotBlank @Size(max = 150) String ciudadProveedor,
			@NotBlank @Size(max = 50) String direccionProveedor, @NotBlank @Size(max = 20) String nombreProveedor,
			@NotBlank @Size(max = 120) String telefonoProveedor) {
		super();
		this.nitProveedor = nitProveedor;
		this.ciudadProveedor = ciudadProveedor;
		this.direccionProveedor = direccionProveedor;
		this.nombreProveedor = nombreProveedor;
		this.telefonoProveedor = telefonoProveedor;
	}

	public Supplier() {
		super();
	}

}
