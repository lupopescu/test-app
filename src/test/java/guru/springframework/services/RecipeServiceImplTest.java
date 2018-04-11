package guru.springframework.services;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

public class RecipeServiceImplTest {
	@Mock
	RecipeRepository recipeRepository;

	RecipeServiceImpl recipeService;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService=new RecipeServiceImpl(recipeRepository, null, null);
			}

	@Test
	public void getRecipes() throws Exception{
		Recipe recipe= new Recipe();
		HashSet<Recipe> recipeData=new HashSet<Recipe>();
		recipeData.add(recipe);
		when(recipeService.getRecipes()).thenReturn(recipeData);
		Set<Recipe> recipes=recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
	}

}
