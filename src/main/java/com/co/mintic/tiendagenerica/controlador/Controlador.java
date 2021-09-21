package com.co.mintic.tiendagenerica.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.co.mintic.tiendagenerica.interfaceService.IPersonaService;
import com.co.mintic.tiendagenerica.modelo.Persona;

@Controller
@RequestMapping("/mintic/tiendagenerica")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class Controlador {
	@Autowired
	private IPersonaService service;

	@RequestMapping(value = "users", produces = {"application/JSON"})
	public ResponseEntity listar() {
		try {
			List<Persona> personas = service.listar();
			personas.forEach(System.out::println);
			return new ResponseEntity(personas, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user/{id}", produces = {"application/JSON"})
	public ResponseEntity getUser(@PathVariable("id") int id){
		try {
			return new ResponseEntity(service.listarPersonaId(id), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/saveuser", method = RequestMethod.POST, consumes = {"application/JSON"},produces = {"application/JSON"})
	public ResponseEntity save(@RequestBody Persona p) {
		try {
			if(service.save(p) == 0){
				return new ResponseEntity("El usuario "+p.getName()+ " ya se encuentra registrado.",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/edituser", method = RequestMethod.PUT, produces = {"application/JSON"}, consumes = {"application/JSON"})
    public ResponseEntity editar(@RequestBody Persona p) {
		try {
			return new ResponseEntity(service.update(p), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.DELETE, produces = {"application/JSON"})
	public ResponseEntity delete(@PathVariable("id") int id) {
		try {
			service.delete(id);
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}


