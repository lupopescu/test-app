package guru.springframework.converters;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>{
	 private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	 
	public IngredientCommandToIngredient(
			UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		
		this.uomConverter = uomConverter;
	}
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		  if (source == null) {
	            return null;
	        }
		  final Ingredient ingredient = new Ingredient();
		  
		  if(source.getRecipeId()!=null){
			  Recipe recipe=new Recipe();
			  recipe.setId(source.getRecipeId());
			  ingredient.setRecipe(recipe);
			  recipe.addIngredient(ingredient);
		  }
		  
		  ingredient.setAmount(source.getAmount());
		  ingredient.setDescription(source.getDescription());
		  ingredient.setUom(uomConverter.convert(source.getUom()));
		return ingredient;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		System.out.println("unimplementeg IngredientCommandToIngredient getInputType");
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		System.out.println("unimplementeg IngredientCommandToIngredient getOutputType");
		return null;
	}

}
