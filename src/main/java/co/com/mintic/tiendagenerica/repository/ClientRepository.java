package co.com.mintic.tiendagenerica.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByNombreCliente(String clientname);
	
	Optional<Client> findByEmailCliente(String email);
	
	Optional<Client> findByCedulaCliente(long id);

	Boolean existsByNombreCliente(String clientname);

	Boolean existsByEmailCliente(String email);

	@Transactional
	Boolean deleteByCedulaCliente(long cedula);
	
	@Modifying
	@Transactional
	@Query("update Client c set c.direccionCliente = ?1,c.emailCliente = ?2,c.nombreCliente = ?3 ,c.telefonoCliente = ?4 where c.cedulaCliente = ?5")
	void setClientInfoById(String direccionCliente, String emailCliente, String nombreCliente, String telefonoCliente, long cedulaCliente);
}
