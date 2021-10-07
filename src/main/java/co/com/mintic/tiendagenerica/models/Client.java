package co.com.mintic.tiendagenerica.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clientes", uniqueConstraints = { @UniqueConstraint(columnNames = "cedula_clientes"),
		@UniqueConstraint(columnNames = "email_cliente") })
public class Client {
	@Id
	@Column(name = "cedula_clientes")
	private long cedulaCliente;

	@NotBlank
	@Size(max = 80)
	@Column(name = "direccion_cliente")
	private String direccionCliente;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email_cliente")
	private String emailCliente;

	@NotBlank
	@Size(max = 60)
	@Column(name = "nombre_cliente")
	private String nombreCliente;

	@NotBlank
	@Size(max = 30)
	@Column(name = "telefono_cliente")
	private String telefonoCliente;

	public long getCedulaClientes() {
		return cedulaCliente;
	}

	public void setCedulaClientes(long cedulaClientes) {
		this.cedulaCliente = cedulaClientes;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public Client(long cedulaClientes, @NotBlank @Size(max = 80) String direccionCliente,
			@NotBlank @Size(max = 50) String emailCliente, @NotBlank @Size(max = 60) String nombreCliente,
			@NotBlank @Size(max = 30) String telefonoCliente) {
		super();
		this.cedulaCliente = cedulaClientes;
		this.direccionCliente = direccionCliente;
		this.emailCliente = emailCliente;
		this.nombreCliente = nombreCliente;
		this.telefonoCliente = telefonoCliente;
	}

	public Client() {
		super();
	}

}
