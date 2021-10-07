package co.com.mintic.tiendagenerica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mintic.tiendagenerica.IService.ISupplierService;
import co.com.mintic.tiendagenerica.models.Supplier;
import co.com.mintic.tiendagenerica.repository.SupplierRepository;

@Service
public class SupplierService implements ISupplierService {

	@Autowired
	SupplierRepository supplierRepository;

	@Override
	public List<Supplier> listar() {
		return supplierRepository.findAll();
	}

	@Override
	public Optional<Supplier> listarNitProveedor(Long id) {
		return supplierRepository.findByNitProveedor(id);
	}

	@Override
	public int save(Supplier p) {
		if (supplierRepository.findByNitProveedor(p.getNitProveedor()).isPresent()
				&& supplierRepository.findByNitProveedor(p.getNitProveedor()).get().getNitProveedor() > 0) {
			return 0;
		} else {
			int res = 0;
			Supplier sup = supplierRepository.save(p);
			if (!sup.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public boolean delete(int id) {
		return supplierRepository.deleteByNitProveedor(id);
	}

	@Override
	public Optional<Supplier> update(Supplier p) {
		return null;
	}

	@Override
	public Optional<Supplier> listarNombreProveedor(String nombre) {
		return supplierRepository.findByNombreProveedor(nombre);
	}

}
