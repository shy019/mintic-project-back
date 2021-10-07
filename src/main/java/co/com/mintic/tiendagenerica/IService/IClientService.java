package co.com.mintic.tiendagenerica.IService;


import java.util.List;
import java.util.Optional;

import co.com.mintic.tiendagenerica.models.Client;




public interface IClientService{
	public List<Client> listar();

	public Optional<Client> listarClientId(long id);

	public int save(Client p);

	public void delete(int id);

	Optional<Client> listarClientName(String id);
}
