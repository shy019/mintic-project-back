package co.com.mintic.tiendagenerica.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	Optional<Sale> findByCodigoVenta(long id);

	Boolean existsByCodigoVenta(long id);

	@Transactional
	Boolean deleteByCodigoVenta(long id);

	public static final String FIND_PROJECTS = "SELECT v.cedula_clientes, c.nombre_cliente, sum(v.valor_venta) FROM grupo56_equipo7.ventas v inner join grupo56_equipo7.clientes c on c.cedula_clientes = v.cedula_clientes group by v.cedula_clientes";
	@Query(value = FIND_PROJECTS, nativeQuery = true)
	public List<Object[]> findClients();
	
	@Modifying
	@Transactional
	@Query("update Sale s set s.cedulaUsuario = ?1,s.cedulaClientes = ?2,s.ivaVenta = ?3 ,s.totalVenta = ?4, s.valorVenta = ?5 where s.codigoVenta = ?6")
	void setSailInfoById(long cedulaUsuario, long cedulaClientes, double ivaVenta, double totalVenta, double valorVenta,
			long codigoVenta);
}
