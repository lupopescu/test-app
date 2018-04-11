package guru.springframework.controller;

import java.util.Optional;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {
	private final IngredientService ingredientService;
	private final RecipeService recipeService;
	private final UnitOfMeasureService unitOfMeasureService;

	

	public IngredientController(IngredientService ingredientService,
			RecipeService recipeService,
			UnitOfMeasureService unitOfMeasureService) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
		this.unitOfMeasureService = unitOfMeasureService;
		
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		System.out
				.println("Getting ingredient list for recipe id: " + recipeId);
		model.addAttribute("recipe",
				recipeService.findCommandById(Long.valueOf(recipeId)));

		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model) {
		System.out.println("recipe/{recipeId}/ingredient/{id}/show");
		model.addAttribute(
				"ingredient",
				ingredientService.findByRecipeIdAndIngredientId(
						Long.valueOf(recipeId), Long.valueOf(id)));

		return "recipe/ingredient/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		System.out.println("recipe/{recipeId}/ingredient/new");
		// RecipeCommand
		// recipeCommand=recipeService.findCommandById(Long.valueOf(recipeId));
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model) {
		System.out.println("recipe/{recipeId}/ingredient/{id}/update");
		model.addAttribute(
				"ingredient",
				ingredientService.findByRecipeIdAndIngredientId(
						Long.valueOf(recipeId), Long.valueOf(id)));

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		System.out.println("recipe/{recipeId}/ingredient" + " saveOrUpdate ");
		IngredientCommand savedCommand = ingredientService
				.saveIngredientCommand(command);

		System.out.println("saved receipe id:" + savedCommand.getRecipeId());
		System.out.println("saved ingredient id:" + savedCommand.getId());

		return "redirect:/recipe/" + savedCommand.getRecipeId()
				+ "/ingredient/" + savedCommand.getId() + "/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId,
			@PathVariable String id) {
		System.out.println("deleting the ingredient id " + id);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
