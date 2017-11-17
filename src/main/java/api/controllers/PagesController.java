package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import api.services.LeagueService;

@Controller
public class PagesController {

  @Autowired
  private LeagueService myService;
	
  @RequestMapping("data/league")
  public String redirToList(){
      return "data/league";
  }
  
}