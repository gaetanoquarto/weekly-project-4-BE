package it.gaetanoquarto.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {
	
	@PostMapping("login_success")
	@ResponseBody
	public String login_success() {
		return "login effettuato con successo!";
	}
	
	@GetMapping("")
	@ResponseBody
	public String home() {
		return "sei sulla homepage.<br> scrivi /utenti, /dispositivi, /ruoli per visitare il nostro sito";
	}

}
