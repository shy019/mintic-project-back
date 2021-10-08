package co.com.mintic.tiendagenerica.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByNombreProducto(String nombreProducto);
	
	Optional<Product> findByCodigoProducto(long nitProducto);

	Boolean existsByCodigoProducto(long nitProducto);

	Boolean existsByNombreProducto(String nombreProducto);

	@Transactional
	Boolean deleteByCodigoProducto(long cedula);
	
	//@Modifying
	//@Transactional
	//@Query("update Product s set s.ciudadProducto = ?1, s.direccionProducto = ?2, s.nombreProducto = ?3, s.telefonoProducto = ?4 where s.nitProducto = ?5")
	//void setProductInfo(String ciudad, String direccion, String nombre, String telefono, long id);
}
