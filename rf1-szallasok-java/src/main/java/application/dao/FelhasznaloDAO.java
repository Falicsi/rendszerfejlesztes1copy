package application.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import application.model.Felhasznalo;

@Repository
public class FelhasznaloDAO extends JdbcDaoSupport {

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  public void insertUser(Felhasznalo user) {
    String sql = "INSERT INTO Felhasznalok(Email, Szerepkor, Jelszo) VALUES (?, ?, ?)";
    getJdbcTemplate().update(sql, user.getEmail(), user.getRole(), passwordEncoder.encode(user.getPassword()));
  }

  public Felhasznalo getUserById(int id) {
    String sql = "SELECT * FROM Felhasznalok WHERE ID=?";
    return getUser(sql, id);
  }

  public Felhasznalo getUserByEmail(String email) {
    String sql = "SELECT * FROM Felhasznalok WHERE Email=?";
    return getUser(sql, email);
  }

  private Felhasznalo getUser(String sql, Object... params) {
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, params);

    if (!rows.isEmpty()) {
      Map<String, Object> row = rows.get(0);
      Felhasznalo user = new Felhasznalo();
      user.setId((Integer) row.get("ID"));
      user.setEmail((String) row.get("Email"));
      user.setPassword((String) row.get("Jelszo"));
      user.setRole((String) row.get("Szerepkor"));
      return user;
    }
    return null;
  }

  public boolean validateUser(String email, String password) {
    String sql = "SELECT Jelszo FROM Felhasznalok WHERE Email = ?";
    assert getJdbcTemplate() != null;
    String storedPassword = getJdbcTemplate().queryForObject(sql, String.class, email);
    return passwordEncoder.matches(password, storedPassword);
  }

}