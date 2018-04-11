package guru.springframework.controller;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.ui.Model;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

public class IndexControllerTest {
	@Mock
	RecipeService recipeService;
	@Mock
	Model model;

	IndexController controller;
	Set<Recipe> mockedList = mock(Set.class);
	Set<Recipe> recipes = new HashSet<Recipe>();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(recipeService);

		
		
	}

	@Test
	public void getIndexPageReturn() throws Exception {

		// when
		String viewName = controller.getIndexPage(model);

		// then
		assertEquals(viewName, "index");
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), anySet());

	}

	@Test
	public void getIndexPage() throws Exception {
		recipes.add(new Recipe());
		Recipe recipe = new Recipe();

		recipe.setUrl("http");
		recipes.add(recipe);
		when(recipeService.getRecipes()).thenReturn(recipes);

		mockedList.addAll(recipes);

		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor
				.forClass(Set.class);

		verify(mockedList).addAll(argumentCaptor.capture());

		Set<Recipe> capturedArgument = argumentCaptor.getValue();

		assertEquals(2, capturedArgument.size());
	}
	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

}
