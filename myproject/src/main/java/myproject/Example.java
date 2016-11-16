package myproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import myproject.domain.Project;

/**
 * 
 * @author dr.ricalde
 *
 */
@RestController
@EnableAutoConfiguration
public class Example {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping("/angular")
	ModelAndView angular() {
		return new ModelAndView("index.html");
	}
	
	@RequestMapping("/angular/projects")
	List<Project> getProjects() {
		List<Project> result = new ArrayList<>();
		Project project1 = new Project(0L, "AngularJS", "HTML enhanced for web apps!", "http://angularjs.org");
		result.add(project1);
		project1 = new Project(2L, "Backbone", "Models for your apps.", "http://documentcloud.github.com/backbone/");		
		result.add(project1);
		project1 = new Project(10L, "Batman", "Quick and beautiful.", "http://batmanjs.org/");		
		result.add(project1);
		project1 = new Project(6L, "Capuccino", "Objective-J.", "http://cappuccino.org/");		
		result.add(project1);
		project1 = new Project(9L, "Ember", "Ambitious web apps.", "http://emberjs.com/");
		result.add(project1);
		project1 = new Project(8L, "GWT", "JS in Java.", "https://developers.google.com/web-toolkit/");
		result.add(project1);
		project1 = new Project(1L, "jQuery", "Write less, do more.", "http://jquery.com/");		
		result.add(project1);
		project1 = new Project(7L, "Knockout", "MVVM pattern.", "http://knockoutjs.com/");		
		result.add(project1);
		project1 = new Project(4L, "Sammy", "Small with class.", "http://sammyjs.org/");		
		result.add(project1);
		project1 = new Project(5L, "Spine", "Awesome MVC Apps.", "http://spinejs.com/");		
		result.add(project1);
		project1 = new Project(3L, "SproutCore", "Innovative web-apps.", "http://sproutcore.com/");		
		result.add(project1);
		return result;
	}
	
	@RequestMapping(value = "/edit/{id}", method=RequestMethod.PUT)
	public void edit(@PathVariable Long id, @RequestBody Project project) {
		System.out.println("id: " + id + " and project: " + project);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Example.class, args);
	}
}
