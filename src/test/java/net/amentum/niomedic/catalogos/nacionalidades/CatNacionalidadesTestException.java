package net.amentum.niomedic.catalogos.nacionalidades;

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

import net.amentum.niomedic.catalogos.converter.CatNacionalidadesConverter;
import net.amentum.niomedic.catalogos.exception.CatNacionalidadesException;
import net.amentum.niomedic.catalogos.model.CatNacionalidades;
import net.amentum.niomedic.catalogos.persistence.CatNacionalidadesRepository;
import net.amentum.niomedic.catalogos.service.impl.CatNacionalidadesServiceImpl;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;

public class CatNacionalidadesTestException {
	private CatNacionalidadesServiceImpl catNacionalidadesServiceImpl;
	@Mock
	private CatNacionalidadesConverter catNacionalidadesConverter;
	@Mock
	private CatNacionalidadesRepository catNacionalidadesRepository;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catNacionalidadesServiceImpl = new CatNacionalidadesServiceImpl();
		catNacionalidadesServiceImpl.setCatNacionalidadesRepository(catNacionalidadesRepository);
		catNacionalidadesServiceImpl.setCatNacionalidadesConverter(catNacionalidadesConverter);
	}
	/////////////////////////////////////create///////////////////////////////////////////////
	@Test(expected = CatNacionalidadesException.class )
	public void createNacionalidadExeption() throws Exception{
		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		catNacionalidadesServiceImpl.createNacionalidades(new CatNacionalidadesView());
	}
	@Test(expected = CatNacionalidadesException.class )
	public void createNacionalidadConstraint() throws Exception{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatNacionalidades entity = new CatNacionalidades();
		Set<ConstraintViolation<CatNacionalidades>> violations = validator.validate(entity);

		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesRepository.save(Matchers.any(CatNacionalidades.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catNacionalidadesServiceImpl.createNacionalidades(new CatNacionalidadesView());
	}

	@Test(expected = CatNacionalidadesException.class )
	public void createNacionalidadDataIntegrity() throws Exception{

		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesRepository.save(Matchers.any(CatNacionalidades.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad", new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"), new Throwable(" Detail: Key (nac_pais_codigo)=(8652749) already exists."))));
		catNacionalidadesServiceImpl.createNacionalidades(new CatNacionalidadesView());
	}
	///////////////////////////////////////////UPDATE//////////////////////////////////////////////////////

	@Test(expected = CatNacionalidadesException.class )
	public void updateNacionalidadExeption() throws Exception{

		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		catNacionalidadesServiceImpl.updateNacionalidades(1,new CatNacionalidadesView());
	}
	@Test(expected = CatNacionalidadesException.class )
	public void updateNacionalidadConstraint() throws Exception{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatNacionalidades entity = new CatNacionalidades();
		Set<ConstraintViolation<CatNacionalidades>> violations = validator.validate(entity);

		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesRepository.save(Matchers.any(CatNacionalidades.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catNacionalidadesServiceImpl.updateNacionalidades(1,new CatNacionalidadesView());
	}

	@Test(expected = CatNacionalidadesException.class )
	public void updateNacionalidadDataIntegrity() throws Exception{
		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesConverter.toEntity(Matchers.any(CatNacionalidadesView.class), Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenReturn(new CatNacionalidades());
		Mockito.when(catNacionalidadesRepository.save(Matchers.any(CatNacionalidades.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad", new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"), new Throwable(" Detail: Key (nac_pais_codigo)=(8652749) already exists."))));
		catNacionalidadesServiceImpl.updateNacionalidades(1,new CatNacionalidadesView());
	}
	/////////////////////////////////////////////////////////search/////////////////////////////////////////////
	@Test(expected = CatNacionalidadesException.class )
	public void searcNacionalidadException() throws Exception{	
		Mockito.when(((JpaSpecificationExecutor<?>)catNacionalidadesRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
		.thenThrow(new IllegalArgumentException());
		catNacionalidadesServiceImpl.getCatNacionalidadesSearch("", null, 0, 10, "nacPaisDescripcion", "asc");
	}
	///////////////////////////////////////////FindAll///////////////////////////////////////////////
	@Test(expected = CatNacionalidadesException.class )
	public void findALlNacionalidadException() throws Exception{	
		Mockito.when(catNacionalidadesRepository.findAll())
		.thenThrow(new IllegalArgumentException());
		catNacionalidadesServiceImpl.findAll();
	}


	///////////////////////////////////////////////////GetByID///////////////////////////////////////////////////


	@Test(expected = CatNacionalidadesException.class )
	public void byIdNacionalidadExeption() throws Exception{

		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatNacionalidades());

		Mockito.when(catNacionalidadesConverter.toView(Matchers.any(CatNacionalidades.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());

		catNacionalidadesServiceImpl.getDetailsByIdCatNacionalidades(1);
	}

	@Test(expected = CatNacionalidadesException.class )
	public void byIdNacionalidadConstraint() throws Exception{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatNacionalidades entity = new CatNacionalidades();
		Set<ConstraintViolation<CatNacionalidades>> violations = validator.validate(entity);

		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		catNacionalidadesServiceImpl.getDetailsByIdCatNacionalidades(1);
	}


	@Test(expected = CatNacionalidadesException.class )
	public void byIdNacionalidadDataIntegrity() throws Exception{
		Mockito.when(catNacionalidadesRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catNacionalidadesRepository.findOne(Matchers.anyInt()))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad", new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"), new Throwable(" Detail: Key (nac_pais_codigo)=(8652749) already exists."))));
		catNacionalidadesServiceImpl.getDetailsByIdCatNacionalidades(1);
	}
}