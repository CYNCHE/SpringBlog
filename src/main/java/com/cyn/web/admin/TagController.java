package com.cyn.web.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cyn.po.Tag;
import com.cyn.service.TagService;




@Controller
@RequestMapping("/admin")
public class TagController {
	
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/tags")
	public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
											Pageable pageable, Model model) {
		
		model.addAttribute("page", tagService.listTag(pageable));
		return "admin/tags";
	}
	
	@GetMapping("/tags/input")
	public String input(Model model) {
		model.addAttribute("tag", new Tag());
		return "admin/tag-input";
	}
	
	
	@GetMapping("tags/{id}/input")
	public String edit(Model model, @PathVariable Long id) {
		Tag tag = tagService.getTagById(id).get();
		model.addAttribute("tag", tag);
		return "admin/tag-input";
	}
	
	
	@PostMapping("/tags")
	public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
		Tag tag2 = tagService.getTagByName(tag.getName());
		if (tag2 != null) {
			result.rejectValue("name", "nameError", "Type " + tag.getName() + " already exists!");
		}
		if (result.hasErrors()) return "admin/tag-input";
		
		Tag tag3 = tagService.saveTag(tag);
		if (tag3 == null) {
			redirectAttributes.addFlashAttribute("message", "Failed to add Tag " + tag.getName());
		}
		else {
			redirectAttributes.addFlashAttribute("message", "Tag " + tag.getName() + " added successfully!");
		}
		return "redirect:/admin/tags";
	}
	
	
	@PostMapping("/tags/{id}")
	public String edit(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes) {
		Tag tag2 = tagService.getTagByName(tag.getName());
		if(tag2 != null) {
			result.rejectValue("message", "nameError", "Tag " + tag2.getName() + " already exists!");
		}
		if (result.hasErrors()) {
			return "admin/tag-input";
		}
		Tag tag3 = tagService.updateTag(id, tag);
		if (tag3 == null) {
			redirectAttributes.addFlashAttribute("message", "Edit failed!");
		}
		else {
			redirectAttributes.addFlashAttribute("message", "Edit successful!");
		}
		return "redirect:/admin/tags";
	}
	
	@GetMapping("/tags/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		tagService.deleteTag(id);
		attributes.addFlashAttribute("message", "Delete successfully!");
		return "redirect:/admin/tags";
	}
	
	

}
