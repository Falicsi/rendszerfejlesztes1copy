package application.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import application.model.Szallas;

@Repository
public class SzallasDAO extends JdbcDaoSupport {
  @Autowired
  DataSource dataSource;

  @Autowired
  FelhasznaloDAO userDAO;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  public void insertSzallas(Szallas szallas) {
    String sql = "INSERT into szallasok(nev, cim, ferohelyekszama, foglalt) VALUES (?,?,?,?)";
    assert getJdbcTemplate() != null;
    getJdbcTemplate().update(sql, szallas.getNev(), szallas.getCim(), szallas.getFeroHelyekSzama(), szallas.isFoglalt());
  }

  public List<Szallas> listSzallasok() {
    String sql = "SELECT * FROM szallasok";
    assert getJdbcTemplate() != null;
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

    List<Szallas> result = new ArrayList<>();
    for (Map<String, Object> row : rows) {
      if (row == null) {
        continue; // Ha a sor null, akkor ugorjunk a következőre
      }

      Integer id = row.get("id") != null ? (Integer) row.get("id") : null;
      if (id == null) {
        continue; // Ha az ID null, ugorjunk a következőre
      }

      Szallas szallas = new Szallas();
      szallas.setId(id);
      szallas.setNev(row.get("nev") != null ? (String) row.get("nev") : "");
      szallas.setCim(row.get("cim") != null ? (String) row.get("cim") : "");
      szallas.setFeroHelyekSzama(row.get("feroHelyekSzama") != null ? (Integer) row.get("feroHelyekSzama") : 0);
      szallas.setFoglalt(row.get("foglalt") != null ? (Boolean) row.get("foglalt") : false);

      result.add(szallas);
    }

    return result;
  }


  public Szallas getSzallasById(int id) {
    String sql = "SELECT * FROM Szallasok WHERE id = " + id;
    assert getJdbcTemplate() != null;
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

    if (!rows.isEmpty()) {
      Map<String, Object> row = rows.get(0);
      Szallas szallas = new Szallas();
      //szallas.setId((Integer) row.get("id"));
      szallas.setNev((String) row.get("nev"));
      szallas.setCim((String) row.get("cim"));
      szallas.setFeroHelyekSzama((Integer) row.get("feroHelyekSzama"));
      szallas.setFoglalt((Boolean) row.get("foglalt"));
      return szallas;
    }

    return null;
  }

  public void deleteSzallas(int id) {
    String sql = "DELETE FROM Szallasok WHERE id = " + id;
    assert getJdbcTemplate() != null;
    getJdbcTemplate().update(sql);
  }

  public void updateSzallas(int id, String nev, String cim, int ferohelyekszama, boolean foglalt) {
    String sql = "UPDATE Szallasok SET nev=?, cim=?, ferohelyekszama=?, foglalt=? WHERE id=?";
    getJdbcTemplate().update(
            sql,
            nev,
            cim,
            ferohelyekszama,
            foglalt,
            id
    );
  }
}