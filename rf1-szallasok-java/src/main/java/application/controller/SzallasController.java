package application.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import application.dao.SzallasDAO;
import application.dao.FelhasznaloDAO;
import application.model.Szallas;
import application.model.Felhasznalo;

@Controller
public class SzallasController {
  @Autowired
  private SzallasDAO szallasDAO;
  @Autowired
  private FelhasznaloDAO felhasznaloDAO;


  @GetMapping(value = "/")
  public String listSzallas(Model model) {
    List<String> user_mails = new ArrayList<>();
    List<Szallas> szallasList = szallasDAO.listSzallasok();
    model.addAttribute("Szallasok", szallasList);

    for (Szallas szallas: szallasList) {
      Felhasznalo user = felhasznaloDAO.getUserById(szallas.getId());
      if (user != null) {
        user_mails.add(user.getEmail());
      } else {
        user_mails.add("Ismeretlen e-mail cím");
      }
    }

    model.addAttribute("user_mails", user_mails);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getName().equals("anonymousUser")) {
      model.addAttribute("current_user", new Felhasznalo());
    } else {
      Felhasznalo currentUser = felhasznaloDAO.getUserByEmail(authentication.getName());
      if (currentUser != null) {
        model.addAttribute("current_user", currentUser);
      } else {
        model.addAttribute("current_user", new Felhasznalo());
      }
    }

    return "index"; // Feltételezve, hogy van egy 'index.html' nevű nézet
  }

  @PostMapping(value = "/add")
  public String addSzallas(@RequestParam("nev") String nev, @RequestParam("cim") String cim, @RequestParam("feroHelyekSzama") int feroHelyekSzama, @RequestParam("foglalt") boolean foglalt) {
    Szallas szallas = new Szallas(nev, cim, feroHelyekSzama, foglalt);
    szallasDAO.insertSzallas(szallas);
    return "redirect:/";
  }

  @PostMapping(value = "/delete/{id}")
  public String deleteSzallas(@PathVariable("id") int id) {
    szallasDAO.deleteSzallas(id);
    return "redirect:/";
  }

  @GetMapping(value = "/edit/{id}")
  public String editSzallas(@PathVariable("id") int id, Model model) {
    Szallas szallas = szallasDAO.getSzallasById(id);
    model.addAttribute("szallas", szallas);
    return "update-szallas"; // Feltételezve, hogy van egy 'update-szallas.html' nevű nézet
  }

  @PostMapping(value = "/update/{id}")
  public String updateSzallas(@PathVariable("id") int id, @RequestParam("nev") String nev, @RequestParam("cim") String cim, @RequestParam("feroHelyekSzama") int feroHelyekSzama, @RequestParam("foglalt") boolean foglalt) {
    szallasDAO.updateSzallas(id, nev, cim, feroHelyekSzama, foglalt);
    return "redirect:/";
  }
  @PostMapping(value = "/foglal/{id}")
  public String foglalSzallas(@PathVariable("id") int id) {
    Szallas szallas = szallasDAO.getSzallasById(id);
    if (szallas != null && !szallas.isFoglalt()) {
      szallasDAO.updateSzallas(id, szallas.getNev(), szallas.getCim(), szallas.getFeroHelyekSzama(), true);
    }
    return "redirect:/";
  }
  
  @PostMapping(value = "/foglalas-torles/{id}")
  public String torolFoglalas(@PathVariable("id") int id) {
    Szallas szallas = szallasDAO.getSzallasById(id);
    if (szallas != null && szallas.isFoglalt()) {
      szallasDAO.updateSzallas(id, szallas.getNev(), szallas.getCim(), szallas.getFeroHelyekSzama(), false);
    }
    return "redirect:/";
  }
}