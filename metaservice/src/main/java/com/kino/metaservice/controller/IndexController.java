package com.kino.metaservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController extends AbstractController {

	@GetMapping("/index")
	public ModelAndView myindex(){
		return new ModelAndView("index");
	}

}
