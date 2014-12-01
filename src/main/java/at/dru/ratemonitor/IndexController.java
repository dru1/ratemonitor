package at.dru.ratemonitor;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@Autowired
	private IRateProvider updateModel;
	
	@Value("${application.appName}")
	private String appName;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		model.put("appName", appName);
		model.put("time", new Date());
		model.put("updates", updateModel.getUpdateCounter());
		model.put("rates", updateModel.getRates());
		return "index";
	}

}
