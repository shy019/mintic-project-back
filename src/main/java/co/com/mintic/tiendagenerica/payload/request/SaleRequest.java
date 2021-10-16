package co.com.mintic.tiendagenerica.payload.request;

import java.util.List;

import co.com.mintic.tiendagenerica.models.Client;

public class SaleRequest {

	private long cedulaUsuario;

	private Client cedulaClientes;

	private double ivaVenta;

	private double totalVenta;

	private double valorVenta;
	
	private List<Object> codigoProducto;

	public List<Object> getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(List<Object> codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public long getCedulaUsuario() {
		return cedulaUsuario;
	}

	public void setCedulaUsuario(long cedulaUsuario) {
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

	public SaleRequest(long cedulaUsuario, Client cedulaClientes, double ivaVenta, double totalVenta,
			double valorVenta) {
		super();
		this.cedulaUsuario = cedulaUsuario;
		this.cedulaClientes = cedulaClientes;
		this.ivaVenta = ivaVenta;
		this.totalVenta = totalVenta;
		this.valorVenta = valorVenta;
	}

	public SaleRequest() {
		super();
	}

}