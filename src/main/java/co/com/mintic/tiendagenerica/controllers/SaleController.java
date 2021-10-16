package co.com.mintic.tiendagenerica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.com.mintic.tiendagenerica.IService.ISaleService;
import co.com.mintic.tiendagenerica.models.Sale;
import co.com.mintic.tiendagenerica.models.SaleDetail;
import co.com.mintic.tiendagenerica.payload.request.SaleRequest;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.repository.ProductRepository;
import co.com.mintic.tiendagenerica.repository.SaleDetailRepository;
import co.com.mintic.tiendagenerica.repository.SaleRepository;
import co.com.mintic.tiendagenerica.repository.UserRepository;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;

@Controller
@RequestMapping("/mintic/tiendagenerica/api")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class SaleController {
	@Autowired
	private ISaleService service;

	@Autowired
	SaleRepository saleRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ISaleService iSaleService;

	@Autowired
	SaleDetailRepository saleDetailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/all", produces = { "application/JSON" })
	public ResponseEntity listar() {
		try {
			List<Sale> personas = saleRepository.findAll();
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/saleclient", produces = { "application/JSON" })
	public ResponseEntity listarVentasClientes() {
		try {
			List<Object[]> ventasCliente = saleRepository.findClients();
			return new ResponseEntity(ventasCliente, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/{id}", produces = { "application/JSON" })
	public ResponseEntity getSale(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(service.listarCodigoVenta(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/savesale", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity save(@RequestBody SaleRequest p) {
		try {
			Sale sale = new Sale();
			sale.setCodigoVenta(saleRepository.count() + 1);
			sale.setCedulaUsuario(userRepository.findById(p.getCedulaUsuario()).get());
			sale.setCedulaClientes(p.getCedulaClientes());
			sale.setIvaVenta(p.getIvaVenta());
			sale.setTotalVenta(p.getTotalVenta());
			sale.setValorVenta(p.getValorVenta());
			if (iSaleService.save(sale) == 1) {
				p.getCodigoProducto().stream().forEach((x) -> {
					SaleDetail saleDetail = new SaleDetail();
					String[] lista = x.toString().split(",");
					lista[0] = lista[0].substring(1, lista[0].length());
					lista[lista.length - 1] = lista[lista.length - 1].substring(0,
							lista[lista.length - 1].length() - 1);
					saleDetail.setCantidadProducto(Long.parseLong(lista[lista.length - 2].trim()));
					saleDetail.setValorTotal(Double.parseDouble(lista[lista.length - 4].trim()));
					saleDetail.setValorVenta(Double.parseDouble(lista[lista.length - 3].trim()));
					saleDetail.setCodigoVenta(sale);
					saleDetail.setCodigoDetalleVenta(saleDetailRepository.count() + 1);
					saleDetail
							.setCodigoProducto(productRepository.findByCodigoProducto(Long.parseLong(lista[0].trim())).get());
					saleDetailRepository.save(saleDetail);
				});
				return new ResponseEntity("La venta se realizó con exito", HttpStatus.OK);
			} else {
				throw new Exception("No se pudo realizar la venta");
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/editsale", method = RequestMethod.PUT, produces = {
			"application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity editar(@RequestBody Sale sale) {
		if (saleRepository.existsByCodigoVenta(sale.getCodigoVenta())) {
			// Create new sale's account
			saleRepository.setSailInfoById(sale.getCedulaUsuario().getId(),
					sale.getCedulaClientes().getCedulaClientes(), sale.getIvaVenta(), sale.getTotalVenta(),
					sale.getValorVenta(), sale.getCodigoVenta());
			return new ResponseEntity(new MessageResponse("Venta modificada con exito"), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Venta no encontrada"));
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/sale/deletesale/{id}", method = RequestMethod.DELETE, produces = { "application/JSON" })
	public ResponseEntity delete(@PathVariable("id") long id) {
		try {
			Sale sup = new Sale();
			sup = saleRepository.getById(id);
			if (sup != null) {
				saleRepository.delete(sup);
				return new ResponseEntity(new MessageResponse("Venta Eliminada con exito"), HttpStatus.OK);
			} else {
				return new ResponseEntity(new MessageResponse("Venta no encontrada"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
