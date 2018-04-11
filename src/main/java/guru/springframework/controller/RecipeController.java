package guru.springframework.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Liuda on 4/10/18.
 */

@Slf4j
@Controller
public class RecipeController {
	private final RecipeService recipeService;
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		System.out
				.println("/recipe/show/{id} recipeService.findById(new Long(id))");
		model.addAttribute("recipe", recipeService.findById(new Long(id)));

		return "recipe/show";
	}

	@GetMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";

	}

	@GetMapping("recipe/{id}/update")
	public String UpdateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe",
				recipeService.findCommandById(Long.valueOf(id)));

		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute RecipeCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.toString());
            });

            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		System.out.println("Deleting id: " + id);
		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		System.out.println("Handling NotFoundException");
		System.out.println(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception",exception);
		return modelAndView;
	}
	
}
