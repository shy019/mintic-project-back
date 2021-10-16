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

import co.com.mintic.tiendagenerica.IService.ISaleDetailService;
import co.com.mintic.tiendagenerica.models.SaleDetail;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.repository.ProductRepository;
import co.com.mintic.tiendagenerica.repository.SaleDetailRepository;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;

@Controller
@RequestMapping("/mintic/tiendagenerica/api")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class SaleDetailController {
	@Autowired
	private ISaleDetailService service;

	@Autowired
	SaleDetailRepository saleDetailRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/saledetail/all", produces = { "application/JSON" })
	public ResponseEntity listar() {
		try {
			List<SaleDetail> personas = saleDetailRepository.findAll();
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/saledetail/{id}", produces = { "application/JSON" })
	public ResponseEntity getSaleDetail(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(service.listarCodigoDetalleVenta(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/saledetail/savesaledetail", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity save(@RequestBody SaleDetail p) {
		try {
			if (service.save(p) == 0) {
				return new ResponseEntity(
						"El detalle venta " + p.getCodigoDetalleVenta() + " ya se encuentra registrado.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(p, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/saledetail/editsaledetail", method = RequestMethod.PUT, produces = {
			"application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity editar(@RequestBody SaleDetail saleDetail) {
		if (saleDetailRepository.existsByCodigoDetalleVenta(saleDetail.getCodigoDetalleVenta())) {
			// Create new saleDetail's account
			saleDetailRepository.setSailInfoById(saleDetail.getCantidadProducto(),
					saleDetail.getCodigoProducto().getCodigoProducto(), saleDetail.getCodigoVenta().getCodigoVenta(),
					saleDetail.getValorTotal(), saleDetail.getValorVenta(), saleDetail.getCodigoDetalleVenta());
			return new ResponseEntity(new MessageResponse("Detalle venta modificado con exito"), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Detalle venta no encontrado"));
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/saledetail/deletesaledetail/{id}", method = RequestMethod.DELETE, produces = {
			"application/JSON" })
	public ResponseEntity delete(@PathVariable("id") long id) {
		try {
			SaleDetail sup = new SaleDetail();
			sup = saleDetailRepository.getById(id);
			if (sup != null) {
				saleDetailRepository.delete(sup);
				return new ResponseEntity(new MessageResponse("Detalle venta Eliminado con exito"), HttpStatus.OK);
			} else {
				return new ResponseEntity(new MessageResponse("Detalle venta no encontrado"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
