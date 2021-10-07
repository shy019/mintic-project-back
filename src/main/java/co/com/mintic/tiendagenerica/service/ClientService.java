package co.com.mintic.tiendagenerica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mintic.tiendagenerica.IService.IClientService;
import co.com.mintic.tiendagenerica.models.Client;
import co.com.mintic.tiendagenerica.repository.ClientRepository;

@Service
public class ClientService implements IClientService {
	@Autowired
	private ClientRepository data;

	@Override
	public List<Client> listar() {
		return (List<Client>) data.findAll();
	}

	@Override
	public Optional<Client> listarClientId(long id) {
		return data.findByCedulaCliente(id);
	}

	@Override
	public Optional<Client> listarClientName(String name) {
		return data.findByNombreCliente(name);
	}

	@Override
	public int save(Client p) {
		if (data.findByCedulaCliente(p.getCedulaClientes()).isPresent()
				&& data.findByCedulaCliente(p.getCedulaClientes()).get().getCedulaClientes() > 0) {
			return 0;
		} else {
			int res = 0;
			Client per = data.save(p);
			if (!per.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public void delete(int id) {
		data.deleteByCedulaCliente(id);
	}

}
