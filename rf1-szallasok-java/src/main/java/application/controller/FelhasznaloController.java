package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import application.dao.FelhasznaloDAO;
import application.model.Felhasznalo;

@Controller
public class FelhasznaloController {

  @Autowired
  private FelhasznaloDAO felhasznaloDAO;

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping(value = "/registeruser")
  public String registerUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("role") String role) {
    Felhasznalo user = new Felhasznalo(email, password, role);
    if (felhasznaloDAO.getUserByEmail(email)  != null) {
      return "redirect:/login";
    }
    else {felhasznaloDAO.insertUser(user);}
    return "redirect:/";
  }

  @PostMapping(value = "/login")
  public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
    if (felhasznaloDAO.validateUser(email, password)) {
      return "redirect:/register";
    } else {
      return "redirect:/login";
    }
  }

}