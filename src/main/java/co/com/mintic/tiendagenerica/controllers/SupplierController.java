package co.com.mintic.tiendagenerica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import co.com.mintic.tiendagenerica.IService.ISupplierService;
import co.com.mintic.tiendagenerica.models.Product;
import co.com.mintic.tiendagenerica.models.Supplier;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.repository.ProductRepository;
import co.com.mintic.tiendagenerica.repository.SupplierRepository;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;

@Controller
@RequestMapping("/mintic/tiendagenerica/api")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class SupplierController {
	@Autowired
	private ISupplierService service;

	@Autowired
	SupplierRepository supplierRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ISupplierService iSupplierService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/all", produces = { "application/JSON" })
	public ResponseEntity listar() {
		try {
			List<Supplier> personas = supplierRepository.findAll();
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/{id}", produces = { "application/JSON" })
	public ResponseEntity getSupplier(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(service.listarNitProveedor(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/savesupplier", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity save(@RequestBody Supplier p) {
		try {
			if (iSupplierService.save(p) == 0) {
				return new ResponseEntity("El proveedor " + p.getNombreProveedor() + " ya se encuentra registrado.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(p, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/saveproducts", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity saveProducts(@RequestBody List<Product> p) {
		try {
			List<Product> productos = new ArrayList<>();
			p.forEach((producto) -> {
				productRepository.save(producto);
				productos.add(producto);
			});
			return new ResponseEntity(productos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/editsupplier", method = RequestMethod.PUT, produces = {
			"application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity editar(@RequestBody Supplier supplier) {
		if (supplierRepository.existsByNitProveedor(supplier.getNitProveedor())) {
			// Create new supplier's account
			supplierRepository.setSupplierInfo(supplier.getCiudadProveedor(), supplier.getDireccionProveedor(),
					supplier.getNombreProveedor(), supplier.getTelefonoProveedor(), supplier.getNitProveedor());
			return new ResponseEntity(new MessageResponse("Proveedor modificado con exito"), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email no encontrado"));
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/supplier/deletesupplier/{id}", method = RequestMethod.DELETE, produces = {
			"application/JSON" })
	public ResponseEntity delete(@PathVariable("id") long id) {
		try {
			Supplier sup = new Supplier();
			sup = supplierRepository.getById(id);
			if (sup != null) {
				supplierRepository.delete(sup);
				return new ResponseEntity(new MessageResponse("Proveedor Eliminado con exito"), HttpStatus.OK);
			} else {
				return new ResponseEntity(new MessageResponse("Proveedor no encontrado"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
