package com.co.mintic.tiendagenerica.service;



import java.util.List;
import java.util.Optional;

import com.co.mintic.tiendagenerica.interfaceService.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.mintic.tiendagenerica.interfaces.IPersona;
import com.co.mintic.tiendagenerica.modelo.Persona;



@Service
public class PersonaService implements IPersonaService {
    @Autowired
  	private IPersona data; 
	@Override
	public List<Persona> listar() {
		return (List<Persona>)data.findAll();
	}

	@Override
	public Optional<Persona> listarPersonaId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Persona p) {
		if(!data.findById(p.getId()).isEmpty()){
			return 0;
		}else {
			int res = 0;
			Persona per = data.save(p);
			if (!per.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public Optional<Persona> update(Persona persona){
		Optional<Persona> p = data.findById(persona.getId());
		Optional<Persona> nuevaPersona = p.map(per -> {
				per.setEmail(persona.getEmail());
				per.setName(persona.getName());
				per.setPassword(persona.getPassword());
				per.setUser(persona.getUser());
				per.setPhone(persona.getPhone());
				return per;
		});
		data.save(nuevaPersona.get());
		return nuevaPersona;
	}

	@Override
	public void delete(int id) {	
		data.deleteById(id);
	}
	
	
	
	


}
