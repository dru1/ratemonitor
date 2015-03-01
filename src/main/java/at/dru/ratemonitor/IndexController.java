package at.dru.ratemonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class IndexController {
	
	@Autowired
	private IRateProvider updateModel;

	@Autowired
	private Application application;
	
	@Value("${application.appName}")
	private String appName;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		model.put("startup", application.getStartup());
		model.put("appName", appName);
		model.put("time", new Date());
		model.put("updates", updateModel.getUpdateCounter());
		model.put("rates", updateModel.getRates());
		return "index";
	}

}
