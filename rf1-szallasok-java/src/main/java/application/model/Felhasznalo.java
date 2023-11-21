package application.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Felhasznalo implements UserDetails {

  private static final long serialVersionUID = 1L;

  int id;
  String email;
  String password;
  String role;

  public Felhasznalo() {}

  public Felhasznalo(int id, String email, String password, String role) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public Felhasznalo(String email, String password, String role) {
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + "]";
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection < ? extends GrantedAuthority > getAuthorities() {
    Set < GrantedAuthority > authorities = new HashSet < GrantedAuthority > ();
    authorities.add(new SimpleGrantedAuthority(this.role));
    return authorities;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}