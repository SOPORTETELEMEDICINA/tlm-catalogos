package net.amentum.niomedic.catalogos.catEstudios;

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
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.amentum.niomedic.catalogos.converter.CatEstudiosConverter;
import net.amentum.niomedic.catalogos.exception.CatEstudiosException;
import net.amentum.niomedic.catalogos.model.CatEstudios;
import net.amentum.niomedic.catalogos.persistence.CatEstudiosRepository;
import net.amentum.niomedic.catalogos.service.impl.CatEstudiosServiceImpl;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;

public class CatEstudiosExceptionTest {
	private CatEstudiosServiceImpl catEstudiosServicerImpl;
	@Mock
	private CatEstudiosConverter catEstudiosConverter;
	@Mock
	private CatEstudiosRepository catEstudiosRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catEstudiosServicerImpl = new CatEstudiosServiceImpl();
		catEstudiosServicerImpl.setCatEstudiosConverter(catEstudiosConverter);
		catEstudiosServicerImpl.SetCatEstudiosRepository(catEstudiosRepository);
	}
	/////////////////////////////////////create////////////////////////////////////////////
	@Test(expected = CatEstudiosException.class)
	public void createException() throws Exception {
		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catEstudiosServicerImpl.createCatEstudio(new CatEstudiosView());
	}

	@Test(expected = CatEstudiosException.class)
	public void createConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatEstudios entity = new CatEstudios();
		Set<ConstraintViolation<CatEstudios>> violations = validator.validate(entity);

		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosRepository.save(Matchers.any(CatEstudios.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		catEstudiosServicerImpl.createCatEstudio(new CatEstudiosView());
	}

	@Test(expected = CatEstudiosException.class)
	public void createDataIntegrity() throws Exception {

		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosRepository.save(Matchers.any(CatEstudios.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad", new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"), new Throwable(" Detail: error al crear un nuevo estudio"))));

		catEstudiosServicerImpl.createCatEstudio(new CatEstudiosView());
	}

	/////////////////////////////////////update////////////////////////////////////////////
	@Test(expected = CatEstudiosException.class)
	public void updateException() throws Exception {
		
		Mockito.when(catEstudiosRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catEstudiosRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatEstudios());
		
		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		
		catEstudiosServicerImpl.updateCatEstudio(1,new CatEstudiosView());
	}

	@Test(expected = CatEstudiosException.class)
	public void updateConstraint() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatEstudios entity = new CatEstudios();
		Set<ConstraintViolation<CatEstudios>> violations = validator.validate(entity);

		Mockito.when(catEstudiosRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catEstudiosRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosRepository.save(Matchers.any(CatEstudios.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		catEstudiosServicerImpl.updateCatEstudio(1,new CatEstudiosView());
	}

	@Test(expected = CatEstudiosException.class)
	public void updateDataIntegrity() throws Exception {

		Mockito.when(catEstudiosRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catEstudiosRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosConverter.toEntity(Matchers.any(CatEstudiosView.class), Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosRepository.save(Matchers.any(CatEstudios.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad", new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"), new Throwable(" Detail: error al actializar el estudio"))));

		catEstudiosServicerImpl.updateCatEstudio(1,new CatEstudiosView());
	}
	/////////////////////////////////////////////////GETBYID///////////////////////////////////////////////

	@Test(expected = CatEstudiosException.class)
	public void getByIdException() throws Exception {

		Mockito.when(catEstudiosRepository.exists(Matchers.anyInt()))
		.thenReturn(true);

		Mockito.when(catEstudiosRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatEstudios());

		Mockito.when(catEstudiosConverter.toView(Matchers.any(CatEstudios.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		catEstudiosServicerImpl.getCatEstudios(1);
	}

	/////////////////////////////////////////////////search///////////////////////////////////////////////

	@Test(expected = CatEstudiosException.class)
	public void searchException() throws Exception {

		Mockito.when(((JpaSpecificationExecutor<?>)catEstudiosRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
		.thenThrow(new IllegalArgumentException());

		catEstudiosServicerImpl.searchCatEstudios("", null, 0, 10, "descripcion", "asc");

	}


}
