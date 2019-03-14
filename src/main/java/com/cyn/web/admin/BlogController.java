package com.cyn.web.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.FailedContext;
import org.hibernate.type.SetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cyn.po.Blog;
import com.cyn.po.Tag;
import com.cyn.po.Type;
import com.cyn.po.User;
import com.cyn.service.BlogService;
import com.cyn.service.TagService;
import com.cyn.service.TypeService;
import com.cyn.vo.BlogQuery;




@Controller
@RequestMapping("/admin")
public class BlogController {
	
	private static final String INPUT = "admin/blogs-input";
	private static final String LIST = "admin/blogs";
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";
	
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, 
											BlogQuery blog, Model model) {
		
		model.addAttribute("types", typeService.listType());
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		//System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		return LIST;
	}
	
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, 
											BlogQuery blog, Model model) {
		//System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		return "admin/blogs :: blogList";
	}
	
	
	@GetMapping("/blogs/input")
	public String input(Model model) {
		setTypeAndTag(model);
		model.addAttribute("blog", new Blog());
		
		return INPUT;  
	}
	
	private void setTypeAndTag(Model model) {
		// transfer type and tag information to frontend
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
	}
	
	
	
	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		setTypeAndTag(model);
		Blog blog =  blogService.getBlog(id).get();
		blog.init();
		model.addAttribute("blog", blogService.getBlog(id).get());
		return INPUT;
	}
	
	
	
	@PostMapping("/blogs")
	public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
		System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		blog.setUser((User) session.getAttribute("user"));
		
		blog.setType(typeService.getType(blog.getType().getId()).get());
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(blog.getTagIds());
		blog.setTags(tagService.listTag(blog.getTagIds()));
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Blog blog2 = blogService.saveBlog(blog);
		if (blog2 == null) {
			redirectAttributes.addFlashAttribute("message", "Failed to add new blog!");
		} else {
			redirectAttributes.addFlashAttribute("message", "Blog added successfully!");
		}
		System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		return REDIRECT_LIST;
	}
	
	
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		String title = blogService.getBlog(id).get().getTitle();
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "Blog " + title + " deleted.");
		return REDIRECT_LIST;
	}
	
}
