package co.com.mintic.tiendagenerica.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	Optional<Supplier> findByNombreProveedor(String nombreProveedor);
	
	Optional<Supplier> findByNitProveedor(long nitProveedor);

	Boolean existsByNitProveedor(long nitProveedor);

	Boolean existsByNombreProveedor(String nombreProveedor);

	@Transactional
	Boolean deleteByNitProveedor(long cedula);
	
	@Modifying
	@Transactional
	@Query("update Supplier s set s.ciudadProveedor = ?1, s.direccionProveedor = ?2, s.nombreProveedor = ?3, s.telefonoProveedor = ?4 where s.nitProveedor = ?5")
	void setSupplierInfo(String ciudad, String direccion, String nombre, String telefono, long id);
}
