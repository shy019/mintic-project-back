package co.com.mintic.tiendagenerica.IService;


import java.util.List;
import java.util.Optional;

import co.com.mintic.tiendagenerica.models.Supplier;




public interface ISupplierService{
	public List<Supplier> listar();

	public Optional<Supplier> listarNitProveedor(Long id);

	public int save(Supplier p);

	public boolean delete(int id);

	public Optional<Supplier> update(Supplier p);

	Optional<Supplier> listarNombreProveedor(String id);
}
