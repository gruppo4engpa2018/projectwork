package it.eng.unipa.projectwork.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.eng.unipa.projectwork.dao.DAO;
import it.eng.unipa.projectwork.model.Supplier;
import it.eng.unipa.projectwork.model.User;

@Stateless
public class UserServiceImpl implements UserService{
	
	@EJB
	DAO dao;

	@Override
	public List<User> allUsers() {
		return dao.find(User.class);
	}

	@Override
	public User getUser(String username) {
		return dao.load(User.class,username);
	}
	
	@Override
	public Supplier getSupplierFromEmail(String mittente) {
		Map<String,Object> map = new HashMap<>();
		map.put("email", mittente);
		List<Supplier> l = dao.find(Supplier.class,"select s from Supplier s where s.email = :email",map );
		return l.isEmpty()?null:l.get(0);
	}

}
