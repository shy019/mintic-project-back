package co.com.mintic.tiendagenerica.IService;


import java.util.List;
import java.util.Optional;

import co.com.mintic.tiendagenerica.models.User;




public interface IUserService{
	public List<User> listar();

	public Optional<User> listarPersonaId(Long id);

	public int save(User p);

	public void delete(int id);

	public Optional<User> update(User p);

	Optional<User> listarPersonaName(String id);
}
