package com.co.mintic.tiendagenerica.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.mintic.tiendagenerica.modelo.Persona;

@Repository
public interface IPersona extends CrudRepository<Persona,Integer> {

}
