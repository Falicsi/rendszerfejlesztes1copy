package application.model;

import java.io.Serializable;

public class Szallas implements Serializable {

  private static final long serialVersionUID = 1L;

  public int id;
  public String cim;
  public String nev;
  public int ferohelyekszama;
  public boolean foglalt;

  public Szallas() {}

  public Szallas(String cim, String nev, int feroHelyekSzama, boolean foglalt) {
    this.cim = cim;
    this.nev = nev;
    this.ferohelyekszama = feroHelyekSzama;
    this.foglalt = foglalt;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getCim() {
    return cim;
  }

  public void setCim(String cim) {
    this.cim = cim;
  }

  public String getNev() {
    return nev;
  }

  public void setNev(String nev) {
    this.nev = nev;
  }

  public int getFeroHelyekSzama() {
    return ferohelyekszama;
  }

  public void setFeroHelyekSzama(int feroHelyekSzama) {
    this.ferohelyekszama = feroHelyekSzama;
  }

  public boolean isFoglalt() {
    return foglalt;
  }

  public void setFoglalt(boolean foglalt) {
    this.foglalt = foglalt;
  }

}