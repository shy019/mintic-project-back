package co.com.mintic.tiendagenerica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.com.mintic.tiendagenerica.IService.IUserService;
import co.com.mintic.tiendagenerica.models.User;
import co.com.mintic.tiendagenerica.payload.response.MessageResponse;
import co.com.mintic.tiendagenerica.repository.RoleRepository;
import co.com.mintic.tiendagenerica.repository.UserRepository;
import co.com.mintic.tiendagenerica.security.services.RefreshTokenService;

@Controller
@RequestMapping("/mintic/tiendagenerica/api")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class UserController {
	@Autowired
	private IUserService service;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/user/all", produces = { "application/JSON" })
	public ResponseEntity listar() {
		try {
			List<User> personas = service.listar();
			personas.forEach(System.out::println);
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/user/{id}", produces = { "application/JSON" })
	public ResponseEntity getUser(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity(service.listarPersonaId(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/user/saveuser", method = RequestMethod.POST, consumes = {
			"application/JSON" }, produces = { "application/JSON" })
	public ResponseEntity save(@RequestBody User p) {
		try {
			if (service.save(p) == 0) {
				return new ResponseEntity("El usuario " + p.getName() + " ya se encuentra registrado.",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/user/edituser", method = RequestMethod.PUT, produces = {
			"application/JSON" }, consumes = { "application/JSON" })
	public ResponseEntity editar(@RequestBody User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			// Create new user's account
			userRepository.setUserInfoById(user.getName(), encoder.encode(user.getPassword()), user.getEmail());
			return new ResponseEntity(new MessageResponse("Usuario modificado con exito"), HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email no encontrado"));
		}
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/user/deleteuser/{id}", method = RequestMethod.DELETE, produces = { "application/JSON" })
	public ResponseEntity delete(@PathVariable("id") long id) {
		try {
			if (userRepository.findById(id).isPresent()) {
				refreshTokenService.deleteByUserId(id);
				userRepository.deleteByCedula(id);
				return new ResponseEntity(new MessageResponse("Usuario Eliminado con exito"), HttpStatus.OK);
			} else {
				return new ResponseEntity(new MessageResponse("Usuario no encontrado"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
