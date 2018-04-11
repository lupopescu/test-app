package guru.springframework.controller;

import lombok.extern.slf4j.Slf4j;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Liuda on 4/10/18.
 */

@Slf4j
@Controller
public class RecipeController {
	private final RecipeService recipeService;

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

		return "recipe/recipeform";
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedComand = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + savedComand.getId() + "/show";
	}

	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		System.out.println("Deleting id: " + id);
		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}

}
