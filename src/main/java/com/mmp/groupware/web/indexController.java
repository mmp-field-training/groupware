package com.mmp.groupware.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

	@GetMapping("/")
	public String index() {
		return "redirect:staff/login";
	}
	
}
