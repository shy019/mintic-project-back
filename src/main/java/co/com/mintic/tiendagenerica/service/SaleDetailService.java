package co.com.mintic.tiendagenerica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mintic.tiendagenerica.IService.ISaleDetailService;
import co.com.mintic.tiendagenerica.models.SaleDetail;
import co.com.mintic.tiendagenerica.repository.SaleDetailRepository;

@Service
public class SaleDetailService implements ISaleDetailService {

	@Autowired
	SaleDetailRepository saleDetailRepository;

	@Override
	public List<SaleDetail> listar() {
		return saleDetailRepository.findAll();
	}

	@Override
	public Optional<SaleDetail> listarCodigoDetalleVenta(Long id) {
		return saleDetailRepository.findByCodigoDetalleVenta(id);
	}

	@Override
	public int save(SaleDetail p) {
		if (saleDetailRepository.findByCodigoDetalleVenta(p.getCodigoDetalleVenta()).isPresent()
				&& saleDetailRepository.findByCodigoDetalleVenta(p.getCodigoDetalleVenta()).get().getCodigoDetalleVenta() > 0) {
			return 0;
		} else {
			int res = 0;
			SaleDetail sup = saleDetailRepository.save(p);
			if (!sup.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public boolean delete(int id) {
		return saleDetailRepository.deleteByCodigoDetalleVenta(id);
	}

	@Override
	public Optional<SaleDetail> update(SaleDetail p) {
		return null;
	}

}
