package co.com.mintic.tiendagenerica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mintic.tiendagenerica.IService.IUserService;
import co.com.mintic.tiendagenerica.interfaces.IPersona;
import co.com.mintic.tiendagenerica.models.User;

@Service
public class UserService implements IUserService {
	@Autowired
	private IPersona data;

	@Override
	public List<User> listar() {
		return (List<User>) data.findAll();
	}

	@Override
	public Optional<User> listarPersonaId(Long id) {
		return data.findPersonaByCedula(id);
	}

	@Override
	public Optional<User> listarPersonaName(String name) {
		return data.findPersonaByName(name);
	}

	@Override
	public int save(User p) {
		if (data.findPersonaByCedula(p.getId()).get().getId() > 0) {
			return 0;
		} else {
			int res = 0;
			User per = data.save(p);
			if (!per.equals(null)) {
				res = 1;
			}
			return res;
		}
	}

	@Override
	public Optional<User> update(User persona) {
		Optional<User> p = data.findPersonaByCedula(persona.getId());
		Optional<User> nuevaPersona = p.map(per -> {
			per.setEmail(persona.getEmail());
			per.setName(persona.getName());
			per.setPassword(persona.getPassword());
			per.setUser(persona.getUser());
			return per;
		});
		data.save(nuevaPersona.get());
		return nuevaPersona;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
