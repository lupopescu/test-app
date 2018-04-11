package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final CategoryCommandToCategory categoryConveter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final NotesCommandToNotes notesConverter;

	public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter,
			IngredientCommandToIngredient ingredientConverter,
			NotesCommandToNotes notesConverter) {

		this.categoryConveter = categoryConveter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}

		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setCookTime(source.getCookTime());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setNotes(notesConverter.convert(source.getNotes()));
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(
					category -> recipe.getCategories().add(
							categoryConveter.convert(category)));
		}

		if (source.getIngredients() != null
				&& source.getIngredients().size() > 0) {
			source.getIngredients().forEach(
					ingredient -> recipe.getIngredients().add(
							ingredientConverter.convert(ingredient)));
		}

		return recipe;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		System.out.println("unimplemented " + Object.class.getName() + "  "
				+ Object.class.getClass().getMethods());
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		System.out.println("unimplemented " + Object.class.getName() + "  "
				+ Object.class.getClass().getMethods());
		return null;
	}

}
