package com.cyn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;
import com.cyn.service.BlogService;
import com.cyn.service.TagService;
import com.cyn.service.TypeService;



@Controller
public class IndexController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	
	@GetMapping("/")
	public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
											Model model) {
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(6));
		model.addAttribute("recommendedBlogs", blogService.listRecommendedBlogTop(6));
		System.out.println("-----index------");
		return "index";
	}
	
	@PostMapping("/search")
	public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
											 @RequestParam String query, Model model) {
		model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
		model.addAttribute("query", query);
		//System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		return "search";
	}

	
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id, Model model) {
		model.addAttribute("blog", blogService.getBlog(id).get());
		return "blog";
	}
}
