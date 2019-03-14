package com.cyn.web;


import com.cyn.po.Type;
import com.cyn.service.BlogService;
import com.cyn.service.TypeService;
import com.cyn.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeDisplayController {

  @Autowired
  private TypeService typeService;

  @Autowired
  private BlogService blogService;

  @GetMapping("types/{id}")
  public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                      @PathVariable Long id, Model model) {
    List<Type> types = typeService.listTypeTop(100);
    System.out.println("HAHAHAH");
    if (id == -1) {
      id = types.get(0).getId();
    }
    BlogQuery blogQuery = new BlogQuery();
    blogQuery.setTypeId(id);
    model.addAttribute("types", types);
    System.out.println("HAHAHAH");
    model.addAttribute("pages", blogService);
    model.addAttribute("activeId", id);
    System.out.println("HAHAHAH");
    return "types";
  }
}
