package co.com.mintic.tiendagenerica.IService;


import java.util.List;
import java.util.Optional;

import co.com.mintic.tiendagenerica.models.SaleDetail;




public interface ISaleDetailService{
	public List<SaleDetail> listar();

	public Optional<SaleDetail> listarCodigoDetalleVenta(Long id);

	public int save(SaleDetail p);

	public boolean delete(int id);

	public Optional<SaleDetail> update(SaleDetail p);
}
