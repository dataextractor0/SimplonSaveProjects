package com.example.crud.controller;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.crud.entity.Foo;
import com.example.crud.service.FooService;
import com.example.crud.service.FooServiceImpl;

@Controller
@RequestMapping("/city")
public class CityController {
	
	@Inject
	FooService fooService;	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Foo> findAll() {
	    return fooService.getAll();	
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Long update(@RequestBody Foo resource) {

		fooService.saveFoo(resource);		
	    return resource.getId();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Long delete(@PathVariable("id") Long id) {

		return fooService.deleteFooById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Foo create(@RequestBody Foo resource) {

		fooService.saveFoo(resource);		
	    return resource;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Foo> read(@PathVariable("id") Long id,@CookieValue(value="name", defaultValue="notmyvalue", required=false) String name ) {
		Optional<Foo> optionalFoo = fooService.findbyId(id);
		return optionalFoo.isPresent()?ResponseEntity.ok().body(optionalFoo.get()):ResponseEntity.notFound().build();
	}
	

}
