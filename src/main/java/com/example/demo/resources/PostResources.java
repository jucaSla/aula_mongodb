package com.example.demo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Post;
import com.example.demo.resources.util.URL;
import com.example.demo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResources {

	@Autowired
	PostService service;
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<Post> FindById(@PathVariable String id){
		Post obj = service.findById(id);
	
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value ="/titleSearch")
	public ResponseEntity<List<Post>> FindByTitle(@RequestParam(value="text", defaultValue = "") String text){
		text = URL.decoderParam(text);
		List<Post> list = service.findByTitle(text);
	
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value ="/fullSearch")
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value="text", defaultValue = "") String text,
			@RequestParam(value="minDate", defaultValue = "") String minDate, 
			@RequestParam(value="maxDate", defaultValue = "") String maxDate){
		text = URL.decoderParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> list = service.fullSearch(text, min, max);
	
		return ResponseEntity.ok().body(list);
	}
}
