package co.com.mintic.tiendagenerica.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ventas", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo_venta") })
public class Sale {

	@Id
	@Column(name = "codigo_venta")
	private long codigoVenta;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User cedulaUsuario;

	@OneToOne
	@JoinColumn(name = "cedula_clientes", referencedColumnName = "cedula_clientes")
	private Client cedulaClientes;

	@NotBlank
	@Column(name = "ivaventa")
	private double ivaVenta;

	@NotBlank
	@Column(name = "total_venta")
	private double totalVenta;

	@NotBlank
	@Column(name = "valor_venta")
	private double valorVenta;

	public long getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(long codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public User getCedulaUsuario() {
		return cedulaUsuario;
	}

	public void setCedulaUsuario(User cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}

	public Client getCedulaClientes() {
		return cedulaClientes;
	}

	public void setCedulaClientes(Client cedulaClientes) {
		this.cedulaClientes = cedulaClientes;
	}

	public double getIvaVenta() {
		return ivaVenta;
	}

	public void setIvaVenta(double ivaVenta) {
		this.ivaVenta = ivaVenta;
	}

	public double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public double getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public Sale(@NotBlank User cedulaUsuario, @NotBlank Client cedulaClientes,
			@NotBlank double ivaVenta, @NotBlank double totalVenta, @NotBlank double valorVenta) {
		super();
		this.cedulaUsuario = cedulaUsuario;
		this.cedulaClientes = cedulaClientes;
		this.ivaVenta = ivaVenta;
		this.totalVenta = totalVenta;
		this.valorVenta = valorVenta;
	}

	public Sale() {
		super();
	}

}