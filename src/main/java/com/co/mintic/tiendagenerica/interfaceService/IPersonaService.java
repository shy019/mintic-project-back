package com.co.mintic.tiendagenerica.interfaceService;


import java.util.List;
import java.util.Optional;

import com.co.mintic.tiendagenerica.modelo.Persona;



public interface IPersonaService{
	public List<Persona> listar();

	public Optional<Persona> listarPersonaId(int id);

	public int save(Persona p);

	public void delete(int id);

	public Optional<Persona> update(Persona p);
}
