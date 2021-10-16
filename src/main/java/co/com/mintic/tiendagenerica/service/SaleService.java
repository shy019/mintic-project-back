package co.com.mintic.tiendagenerica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mintic.tiendagenerica.IService.ISaleService;
import co.com.mintic.tiendagenerica.models.Sale;
import co.com.mintic.tiendagenerica.repository.SaleRepository;

@Service
public class SaleService implements ISaleService {

	@Autowired
	SaleRepository saleRepository;

	@Override
	public List<Sale> listar() {
		return saleRepository.findAll();
	}

	@Override
	public Optional<Sale> listarCodigoVenta(Long id) {
		return saleRepository.findByCodigoVenta(id);
	}

	@Override
	public int save(Sale p) {
		if (saleRepository.findByCodigoVenta(p.getCodigoVenta()).isPresent()
				&& saleRepository.findByCodigoVenta(p.getCodigoVenta()).get().getCodigoVenta() > 0) {
			return 0;
		} else {
			int res = 0;
			Sale sup = saleRepository.save(p);
			if (!sup.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public boolean delete(int id) {
		return saleRepository.deleteByCodigoVenta(id);
	}

	@Override
	public Optional<Sale> update(Sale p) {
		return null;
	}

}
