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
	
	 private final RecipeRepository recipeRepository;
	

	public IngredientController(IngredientService ingredientService,
			RecipeService recipeService,
			UnitOfMeasureService unitOfMeasureService,
			RecipeRepository recipeRepository) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
		this.unitOfMeasureService = unitOfMeasureService;
		this.recipeRepository = recipeRepository;
	}
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		System.out
				.println("Getting ingredient list for recipe id: " + recipeId);
		model.addAttribute("recipe",
				recipeService.findCommandById(Long.valueOf(recipeId)));

		return "recipe/ingredient/list";
	}
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		System.out.println("recipe/{recipeId}/ingredient/{id}/show");
		model.addAttribute(
				"ingredient",
				ingredientService.findByRecipeIdAndIngredientId(
						Long.valueOf(recipeId), Long.valueOf(id)));

		return "recipe/ingredient/show";
	}
	@GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
		System.out.println("recipe/{recipeId}/ingredient/new");
		//RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(recipeId));
		IngredientCommand ingredientCommand=new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	 @GetMapping
	    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	    public String updateRecipeIngredient(@PathVariable String recipeId,
	                                         @PathVariable String id, Model model){
		 System.out.println("recipe/{recipeId}/ingredient/{id}/update");
	        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

	        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
	        return "recipe/ingredient/ingredientform";
	    }
	
	 @PostMapping("recipe/{recipeId}/ingredient")
	    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
		 System.out.println("recipe/{recipeId}/ingredient"+" saveOrUpdate ");
	        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

	       System.out.println("saved receipe id:" + savedCommand.getRecipeId());
	       System.out.println("saved ingredient id:" + savedCommand.getId());

	        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	    }
	 @GetMapping
	    @RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
	    public void deleteIngredient(@PathVariable String recipeId,
	                                         @PathVariable String id){
		 System.out.println("deleting the ingredient id "+id);
		 Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

	        if(recipeOptional.isPresent()){
	            Recipe recipe = recipeOptional.get();
	            System.out.println("found recipe");

	            Optional<Ingredient> ingredientOptional = recipe
	                    .getIngredients()
	                    .stream()
	                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
	                    .findFirst();

	            if(ingredientOptional.isPresent()){
	            	 System.out.println("found Ingredient");
	                Ingredient ingredientToDelete = ingredientOptional.get();
	                ingredientToDelete.setRecipe(null);
	                recipe.getIngredients().remove(ingredientOptional.get());
	                recipeRepository.save(recipe);
	            }
	        } else {
	        	 System.out.println("Recipe Id Not found. Id:" + recipeId);
	        }
	 }
}
