package myproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Example.class);
	
	private List<Project> projects = new ArrayList<>();

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
		if (projects.isEmpty()) {
			Project project1 = new Project(0L, "AngularJS", "HTML enhanced for web apps!", "http://angularjs.org");
			projects.add(project1);
			project1 = new Project(2L, "Backbone", "Models for your apps.", "http://documentcloud.github.com/backbone/");		
			projects.add(project1);
			project1 = new Project(10L, "Batman", "Quick and beautiful.", "http://batmanjs.org/");		
			projects.add(project1);
			project1 = new Project(6L, "Capuccino", "Objective-J.", "http://cappuccino.org/");		
			projects.add(project1);
			project1 = new Project(9L, "Ember", "Ambitious web apps.", "http://emberjs.com/");
			projects.add(project1);
			project1 = new Project(8L, "GWT", "JS in Java.", "https://developers.google.com/web-toolkit/");
			projects.add(project1);
			project1 = new Project(1L, "jQuery", "Write less, do more.", "http://jquery.com/");		
			projects.add(project1);
			project1 = new Project(7L, "Knockout", "MVVM pattern.", "http://knockoutjs.com/");		
			projects.add(project1);
			project1 = new Project(4L, "Sammy", "Small with class.", "http://sammyjs.org/");		
			projects.add(project1);
			project1 = new Project(5L, "Spine", "Awesome MVC Apps.", "http://spinejs.com/");		
			projects.add(project1);
			project1 = new Project(3L, "SproutCore", "Innovative web-apps.", "http://sproutcore.com/");		
			projects.add(project1);
		}
		
		return projects;
	}
	
	@RequestMapping(value = "/project/{id}", method=RequestMethod.GET)
	public Project get(@PathVariable Long id) {
		LOGGER.debug("Getting the project with id '{}'", id);
		Optional<Project> projectOp = projects.stream().filter(p -> p.getId().equals(id)).findFirst();
		if (projectOp.isPresent()) {
			return projectOp.get();
		}
		return null;
	}
	
	@RequestMapping(value = "/project/{id}", method=RequestMethod.PUT)
	public ModelMap edit(@PathVariable Long id, @RequestBody Project project) {
		LOGGER.debug("Updating project: '{}' with id: '{}'", project, id);
		ModelMap result = new ModelMap();
		Optional<Project> projectOp = projects.stream().filter(x -> x.getId().equals(project.getId())).findFirst();
		if (projectOp.isPresent()) {
			BeanUtils.copyProperties(project, projectOp.get());
			result.put("result", true);
		} else {
			result.put("result", false);			
		}
		return result;
	}
	
	@RequestMapping(value = "/project", method=RequestMethod.POST)
	public Project save(@RequestBody Project project) {
		LOGGER.debug("Saving project" + project);
//		projects.stream().max((p1, p2) -> Long.compare(p1.getId(), p2.getId()));
		Optional<Project> max = projects.stream().max(Comparator.comparing(Project::getId));
		if (max.isPresent()) {
			project.setId(max.get().getId() + 1);
			projects.add(project);
		}
		return project;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Example.class, args);
	}
}
