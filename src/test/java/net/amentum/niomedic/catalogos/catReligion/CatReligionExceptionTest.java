package net.amentum.niomedic.catalogos.catReligion;

import static org.mockito.Matchers.any;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.amentum.niomedic.catalogos.converter.CatReligionConverter;
import net.amentum.niomedic.catalogos.model.CatReligion;
import net.amentum.niomedic.catalogos.persistence.CatReligionRepository;
import net.amentum.niomedic.catalogos.service.impl.CatReligionServiceImpl;
import net.amentum.niomedic.catalogos.views.CatReligionView;
import net.amentum.niomedic.catalogos.exception.CatReligionException;

public class CatReligionExceptionTest {
	private CatReligionServiceImpl catReligionServiceImpl; 
	@Mock
	private CatReligionConverter catReligionConverter;
	@Mock
	private CatReligionRepository catReligionRepository;
	@Mock 
	private CatReligionView catReligionView;
	@Mock
	private CatReligion catReligon;
	@Mock 
	private Specifications specifications;
	@Mock
	private PageRequest  pageRequest;
	@Mock
	private Specifications<CatReligion> Specifications;
	@Mock 
	private Sort sort;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catReligionServiceImpl = new CatReligionServiceImpl();
		catReligionServiceImpl.setCatReligionConverter(catReligionConverter);
		catReligionServiceImpl.setCatReligionRepository(catReligionRepository);
	}
	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void createReligonException() throws Exception {
		Mockito.when(catReligionConverter.toEntity(Matchers.any(CatReligionView.class), Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.createCatReligion(catReligionView);
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void createReligonConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatReligion entity = new CatReligion();

		Set<ConstraintViolation<CatReligion>> violations = validator.validate(entity);

		Mockito.when(catReligionConverter.toEntity(Matchers.any(CatReligionView.class), Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenReturn( new CatReligion());

		Mockito.when(catReligionRepository.save(Matchers.any(CatReligion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.createCatReligion(catReligionView);
	}


	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void createReligonIntegrity() throws Exception {
		Mockito.when(catReligionConverter.toEntity(Matchers.any(CatReligionView.class), Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenReturn( new CatReligion());

		Mockito.when(catReligionRepository.save(Matchers.any(CatReligion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.createCatReligion(catReligionView);
	}

	//UPDATE


	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void updateReligonException() throws Exception {
		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatReligion());

		Mockito.when(catReligionConverter.toView(Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.updateCatReligion(catReligionView, 1);
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void updateReligonConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatReligion entity = new CatReligion();

		Set<ConstraintViolation<CatReligion>> violations = validator.validate(entity);


		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatReligion());

		Mockito.when(catReligionConverter.toEntity(Matchers.any(CatReligionView.class), Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenReturn( new CatReligion());

		Mockito.when(catReligionRepository.save(Matchers.any(CatReligion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.updateCatReligion(catReligionView, 1);
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void updateReligonIntegrity() throws Exception {
		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatReligion());

		Mockito.when(catReligionConverter.toEntity(Matchers.any(CatReligionView.class), Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenReturn( new CatReligion());

		Mockito.when(catReligionRepository.save(Matchers.any(CatReligion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.updateCatReligion(catReligionView, 1);
	}

	//GET por ID

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void getByIdReligonException() throws Exception {
		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatReligion());

		Mockito.when(catReligionConverter.toView(Matchers.any(CatReligion.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		
		catReligionServiceImpl.getDetailsByIdCatReligion(1);
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void getByIdReligonConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatReligion entity = new CatReligion();

		Set<ConstraintViolation<CatReligion>> violations = validator.validate(entity);


		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenThrow( new ConstraintViolationException("Error de validacion", violations));


		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.getDetailsByIdCatReligion(1);
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void getByIdReligonIntegrity() throws Exception {
		Mockito.when(catReligionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catReligionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.getDetailsByIdCatReligion(1);
	}

	//busqueda de religiones
	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void searchReligonException() throws Exception {
		
		Mockito.when(((JpaSpecificationExecutor)catReligionRepository).findAll(Specifications,sort))
		.thenThrow(new IllegalArgumentException());
	
		catReligionServiceImpl.getCatReligionSearch("", true, 0, 10, "relDescripcion", "asc");
	}
	
	//finAll
	
	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void findAllReligonException() throws Exception {
		Mockito.when(catReligionRepository.findAll())
		.thenThrow(new IllegalArgumentException());

		CatReligionView catReligionView = new CatReligionView();
		catReligionServiceImpl.findAll();
	}

	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void findAllReligonConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatReligion entity = new CatReligion();

		Set<ConstraintViolation<CatReligion>> violations = validator.validate(entity);

		Mockito.when(catReligionRepository.findAll())
		.thenThrow( new ConstraintViolationException("Error de validacion", violations));
		
		catReligionServiceImpl.findAll();
	}

	
	@Test( expected = CatReligionException.class)
	@SuppressWarnings("unchecked")
	public void findAllReligonIntegrity() throws Exception {
		Mockito.when(catReligionRepository.findAll())
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));
		catReligionServiceImpl.findAll();
	}

}
