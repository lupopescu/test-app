package guru.springframework.services;

import java.io.IOException;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	private final RecipeRepository recipeRepository;

    public ImageServiceImpl( RecipeRepository recipeService) {

        this.recipeRepository = recipeService;
    }

	@Override
	@Transactional
	public void saveImageFile(Long recipeId, MultipartFile file) {
		System.out.println("Received a file");
		 try {
	            Recipe recipe = recipeRepository.findById(recipeId).get();

	            Byte[] byteObjects = new Byte[file.getBytes().length];

	            int i = 0;

	            for (byte b : file.getBytes()){
	                byteObjects[i++] = b;
	            }

	            recipe.setImage(byteObjects);

	            recipeRepository.save(recipe);
	        } catch (IOException e) {
	            //todo handle better
	        	System.out.println("Error occurred"+ e);

	            e.printStackTrace();
	        }
	}
}