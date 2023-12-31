package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import application.dao.FelhasznaloDAO;
import application.model.Felhasznalo;

public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private FelhasznaloDAO felhasznaloDAO;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Felhasznalo user = felhasznaloDAO.getUserByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return user;
  }

}