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

import com.cyn.po.Type;
import com.cyn.service.TypeService;




@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	
	@GetMapping("/types")
	public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
											Pageable pageable, Model model) {
		
		model.addAttribute("page", typeService.listType(pageable));
		return "admin/types";
	}
	
	
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/type-input";
	}
	
	
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id, Model model, Object Type) {
		model.addAttribute("type", typeService.getType(id).get());
		return "admin/type-input";
	}
	
	
	
	@PostMapping("types")
	public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if (type1 != null) {
			result.rejectValue("name", "nameError", "Type " + type1.getName() + " already exists!");
		}
		if (result.hasErrors()) {
			return "admin/type-input";
		}
		
		Type t = typeService.saveType(type);
		if (t == null) {
			attributes.addFlashAttribute("message", "Unable to add new type!");
		}
		else {
			attributes.addFlashAttribute("message", "New type " + t.getName() + " added successfully!");
		}
		return "redirect:/admin/types";
	}
	
	
	
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
		Type type1 = typeService.getTypeByName(type.getName());
		if (type1 != null) {
			result.rejectValue("name", "nameError", "Type " + type1.getName() + " already exists!");
		}
		if (result.hasErrors()) {
			return "admin/type-input";
		}
		
		Type t = typeService.updateType(id, type);
		
		if (t != null) {
			attributes.addFlashAttribute("message", "Update successfully!");
		}
		else {
			attributes.addFlashAttribute("message", "Update failed!");
		}
		
		return "redirect:/admin/types";
	}
	
	
	
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "Delete successfully!");
		return "redirect:/admin/types";
	}
	
	
	
	
	
	
	
	
	
	
}
