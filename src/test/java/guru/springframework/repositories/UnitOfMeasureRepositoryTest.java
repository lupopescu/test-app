package guru.springframework.repositories;

import static org.junit.Assert.*;
import guru.springframework.domain.UnitOfMeasure;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testfindByDescriprion() {
		Optional<UnitOfMeasure>uomOpptional=unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uomOpptional.get().getDescription());
	}
	@Test
	public void testfindByDescriprionCup() {
		Optional<UnitOfMeasure>uomOpptional=unitOfMeasureRepository.findByDescription("Cup");
		assertEquals("Cup", uomOpptional.get().getDescription());
	}

}
