package co.com.mintic.tiendagenerica.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.User;


@Repository
public interface IPersona extends JpaRepository<User,Integer> {

	Optional<User> findPersonaByName(String name);
	
	Optional<User> findPersonaByCedula(Long name);
}
