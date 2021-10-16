package co.com.mintic.tiendagenerica.IService;


import java.util.List;
import java.util.Optional;

import co.com.mintic.tiendagenerica.models.Sale;




public interface ISaleService{
	public List<Sale> listar();

	public Optional<Sale> listarCodigoVenta(Long id);

	public int save(Sale p);

	public boolean delete(int id);

	public Optional<Sale> update(Sale p);
}
