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

import co.com.mintic.tiendagenerica.IService.IClientService;
import co.com.mintic.tiendagenerica.models.Client;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.repository.ClientRepository;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;

@Controller
@RequestMapping("/mintic/tiendagenerica/api")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class ClientController {
	@Autowired
	private IClientService service;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	IClientService iClientService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/client/all", produces = { "application/JSON" })
	public ResponseEntity listar() {
		try {
			List<Client> personas = clientRepository.findAll();
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/client/{id}", produces = { "application/JSON" })
	public ResponseEntity getClient(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(clientRepository.findByCedulaCliente(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/client/saveclient", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity save(@RequestBody Client p) {
		try {
			if (iClientService.save(p) == 0) {
				return new ResponseEntity("El cliente " + p.getNombreCliente() + " ya se encuentra registrado.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(p, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/client/editclient", method = RequestMethod.PUT, produces = {
			"application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity editar(@RequestBody Client client) {
		if (clientRepository.existsByEmailCliente(client.getEmailCliente())) {
			// Create new client's account
			clientRepository.setClientInfoById(client.getDireccionCliente(), client.getEmailCliente(),
					client.getNombreCliente(), client.getTelefonoCliente(), client.getCedulaClientes());
			return new ResponseEntity(new MessageResponse("Cliente modificado con exito"), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email no encontrado"));
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/client/deleteclient/{id}", method = RequestMethod.DELETE, produces = {
			"application/JSON" })
	public ResponseEntity delete(@PathVariable("id") long id) {
		try {
			Client sup = new Client();
			sup = clientRepository.getById(id);
			if (sup != null) {
				clientRepository.delete(sup);
				return new ResponseEntity(new MessageResponse("Cliente Eliminado con exito"), HttpStatus.OK);
			} else {
				return new ResponseEntity(new MessageResponse("Cliente no encontrado"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
