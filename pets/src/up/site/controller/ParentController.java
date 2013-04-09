package up.site.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import up.site.util.ModelHelper;

@Controller
@RequestMapping("/web/parent")
public class ParentController {

	@RequestMapping(method = RequestMethod.GET)
    public String redirectHome(HttpServletRequest request,Model model) throws Exception {
		
		ModelHelper.initWeb(model, request);
		
    	return "parent";
    }
}
