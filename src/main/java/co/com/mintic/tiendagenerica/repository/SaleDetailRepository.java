package co.com.mintic.tiendagenerica.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.SaleDetail;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
	Optional<SaleDetail> findByCodigoDetalleVenta(long id);

	Optional<SaleDetail> findByCantidadProducto(long id);

	Boolean existsByCodigoDetalleVenta(long id);

	Boolean existsByCantidadProducto(long id);

	@Transactional
	Boolean deleteByCodigoDetalleVenta(long id);

	@Modifying
	@Transactional
	@Query("update SaleDetail s set s.cantidadProducto = ?1,s.codigoProducto = ?2,s.codigoVenta = ?3 ,s.valorTotal = ?4, s.valorVenta = ?5 where s.codigoVenta = ?6")
	void setSailInfoById(long cantidadProducto, long codigoProducto, long codigoVenta, double valorTotal,
			double valorVenta, long codigoDetalleVenta);
}
