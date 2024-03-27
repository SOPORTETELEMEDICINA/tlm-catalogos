package net.amentum.niomedic.catalogos.catLengIndigenas;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.amentum.niomedic.catalogos.exception.CatLenguasIndigenasException;
import net.amentum.niomedic.catalogos.converter.CatLenguasIndigenasConverter;
import net.amentum.niomedic.catalogos.model.CatLenguasIndigenas;
import net.amentum.niomedic.catalogos.persistence.CatLenguasIndigenasRepository;
import net.amentum.niomedic.catalogos.service.impl.CatLenguasIndigenasServiceImpl;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;

public class CatLenguasIndigenasExceptionSuite {

	private CatLenguasIndigenasServiceImpl catLenguasIndigenasServiceImpl;

	@Mock
	private CatLenguasIndigenasConverter catLenguasIndigenasConverter;
	@Mock
	private CatLenguasIndigenasRepository catLenguasIndigenasRepository;
	@Mock
	private CatLenguasIndigenasView catlenguasIndigenasView;
	@Mock
	private CatLenguasIndigenas catLenguasIndigenas;
	@Mock
	private Sort sort;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catLenguasIndigenasServiceImpl = new CatLenguasIndigenasServiceImpl();
		catLenguasIndigenasServiceImpl.setCatLenguasIndigenasConverter(catLenguasIndigenasConverter);
		catLenguasIndigenasServiceImpl.setCatLenguasIndigenasRepository(catLenguasIndigenasRepository);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void createCatLengIndigenaException() throws Exception {
		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.createCatLenguasIndigenas(view);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void createCatLengIndigenaConstraintViolation() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatLenguasIndigenas entity = new CatLenguasIndigenas();

		Set<ConstraintViolation<CatLenguasIndigenas>> violations = validator.validate(entity);

		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenReturn(new CatLenguasIndigenas());
		Mockito.when(catLenguasIndigenasRepository.save(Matchers.any(CatLenguasIndigenas.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.createCatLenguasIndigenas(view);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void createCatLengIndigenaDataIntegrity() throws Exception {
		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenReturn(new CatLenguasIndigenas());

		Mockito.when(catLenguasIndigenasRepository.save(Matchers.any(CatLenguasIndigenas.class))).thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.createCatLenguasIndigenas(view);
	}

	//UPDATE
	@Test(expected = CatLenguasIndigenasException.class)
	public void updateCatLengIndigenaException() throws Exception {
		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);
		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatLenguasIndigenas());
		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.updateCatLenguasIndigenas(1,view);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void updateCatLengIndigenaConstraintViolation() throws Exception {

		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);
		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatLenguasIndigenas());

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatLenguasIndigenas entity = new CatLenguasIndigenas();

		Set<ConstraintViolation<CatLenguasIndigenas>> violations = validator.validate(entity);

		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenReturn(new CatLenguasIndigenas());
		Mockito.when(catLenguasIndigenasRepository.save(Matchers.any(CatLenguasIndigenas.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.updateCatLenguasIndigenas(1,view);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void updateCatLengIndigenaDataIntegrity() throws Exception {

		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);
		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatLenguasIndigenas());

		Mockito.when(catLenguasIndigenasConverter.toEntity(Matchers.any(CatLenguasIndigenasView.class), Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenReturn(new CatLenguasIndigenas());

		Mockito.when(catLenguasIndigenasRepository.save(Matchers.any(CatLenguasIndigenas.class))).thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		CatLenguasIndigenasView view = new CatLenguasIndigenasView();
		catLenguasIndigenasServiceImpl.updateCatLenguasIndigenas(1,view);
	}

	//get por id
	@Test(expected = CatLenguasIndigenasException.class)
	public void getByIdCatLengIndigenaException() throws Exception {
		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);
		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatLenguasIndigenas());
		Mockito.when(catLenguasIndigenasConverter.toView(Matchers.any(CatLenguasIndigenas.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		catLenguasIndigenasServiceImpl.getDetailsByIdCatLenguasIndigenas(1);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void getByIdCatLengIndigenaConstraintViolation() throws Exception {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatLenguasIndigenas entity = new CatLenguasIndigenas();

		Set<ConstraintViolation<CatLenguasIndigenas>> violations = validator.validate(entity);

		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));	

		catLenguasIndigenasServiceImpl.getDetailsByIdCatLenguasIndigenas(1);
	}

	@Test(expected = CatLenguasIndigenasException.class)
	public void getByIdCatLengIndigenaDataIntegrity() throws Exception {

		Mockito.when(catLenguasIndigenasRepository.exists(Matchers.anyInt())).thenReturn(true);
		Mockito.when(catLenguasIndigenasRepository.findOne(Matchers.anyInt()))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable()));

		catLenguasIndigenasServiceImpl.getDetailsByIdCatLenguasIndigenas(1);
	}
	//get all
	@Test(expected = CatLenguasIndigenasException.class)
	public void getAllCatLengIndigenaException() throws Exception {
		Mockito.when(catLenguasIndigenasRepository.findAll())
		.thenThrow(new IllegalArgumentException());
		catLenguasIndigenasServiceImpl.findAll();
	}

	//search
	@Test(expected = CatLenguasIndigenasException.class)
	public void searchCatLenguasIndigenasException() throws Exception {
			Mockito.when(((JpaSpecificationExecutor<?>)catLenguasIndigenasRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
			.thenThrow(new IllegalArgumentException());
			catLenguasIndigenasServiceImpl.getCatLenguasIndigenasSearch("", false, 0, 10, "", "asc");
	}
	
	@Test(expected = CatLenguasIndigenasException.class)
	public void searchCatLenguasIndigenasDataIntegrity() throws Exception {
			Mockito.when(((JpaSpecificationExecutor<?>)catLenguasIndigenasRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
			.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable("Error al filtrar")));
			catLenguasIndigenasServiceImpl.getCatLenguasIndigenasSearch("", false, 0, 10, "", "asc");
	}
}
