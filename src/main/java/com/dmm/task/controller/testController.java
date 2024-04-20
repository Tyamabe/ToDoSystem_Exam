package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

	@RequestMapping("/testcreate")
	public String index() {
		return "create";
	}

	@RequestMapping("/testedit")
	public String test() {
		return "edit";
	}

	@RequestMapping("/testmain")
	public String main() {
		return "main";
	}

	@RequestMapping("/testlogin")
	public String login() {
		return "login";
	}

}