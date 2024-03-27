package net.amentum.niomedic.catalogos.catViaAdministracion;

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

import net.amentum.niomedic.catalogos.converter.CatViaAdministracionConverter;
import net.amentum.niomedic.catalogos.exception.CatViaAdministracionException;
import net.amentum.niomedic.catalogos.model.CatViaAdministracion;
import net.amentum.niomedic.catalogos.persistence.CatViaAdministracionRepository;
import net.amentum.niomedic.catalogos.service.impl.CatViaAdministracionServiceImpl;
import net.amentum.niomedic.catalogos.views.CatViaAdministracionView;

public class catViaAdministracionTestException {
	private CatViaAdministracionServiceImpl catViaAdministracionServiceImpl;
	@Mock
	private CatViaAdministracionConverter catViaAdministracionConverter;
	@Mock
	private CatViaAdministracionRepository catViaAdministracionRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catViaAdministracionServiceImpl = new CatViaAdministracionServiceImpl();
		catViaAdministracionServiceImpl.setCatViaAdministracionConverter(catViaAdministracionConverter);
		catViaAdministracionServiceImpl.setCatViaAdministracionRepository(catViaAdministracionRepository);
	}
	//////////////////////////////////post//////////////////////////////////////////////
	@Test(expected = CatViaAdministracionException.class)
	public void createViaAdministracionException() throws Exception {
		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catViaAdministracionServiceImpl.createViaAdministracion(new CatViaAdministracionView());
	}

	@Test(expected = CatViaAdministracionException.class)
	public void createViaAdministracionConstraintViolation() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatViaAdministracion entity = new CatViaAdministracion();
		Set<ConstraintViolation<CatViaAdministracion>> violations = validator.validate(entity);


		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionRepository.save(Matchers.any(CatViaAdministracion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catViaAdministracionServiceImpl.createViaAdministracion(new CatViaAdministracionView());
	}

	@Test(expected = CatViaAdministracionException.class)
	public void createViaAdministracionDataIntegrity() throws Exception {

		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionRepository.save(Matchers.any(CatViaAdministracion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",
				new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"),
						new Throwable(" Detail: No se pudo insertar un nuevo registro"))));
		catViaAdministracionServiceImpl.createViaAdministracion(new CatViaAdministracionView());
	}
	//////////////////////////////////put//////////////////////////////////////////////
	@Test(expected = CatViaAdministracionException.class)
	public void updateViaAdministracionException() throws Exception {
		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catViaAdministracionServiceImpl.updateViaAdministracion(1,new CatViaAdministracionView());
	}

	@Test(expected = CatViaAdministracionException.class)
	public void updateViaAdministracionConstraintViolation() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatViaAdministracion entity = new CatViaAdministracion();
		Set<ConstraintViolation<CatViaAdministracion>> violations = validator.validate(entity);

		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionRepository.save(Matchers.any(CatViaAdministracion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catViaAdministracionServiceImpl.updateViaAdministracion(1,new CatViaAdministracionView());
	}

	@Test(expected = CatViaAdministracionException.class)
	public void updateViaAdministracionDataIntegrity() throws Exception {

		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatViaAdministracion());


		Mockito.when(catViaAdministracionConverter.toEntity(Matchers.any(CatViaAdministracionView.class), Matchers.any(CatViaAdministracion.class),Matchers.anyBoolean()))
		.thenReturn(new CatViaAdministracion());

		Mockito.when(catViaAdministracionRepository.save(Matchers.any(CatViaAdministracion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",
				new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"),
						new Throwable(" Detail: No se pudo actualziar el registro"))));
		catViaAdministracionServiceImpl.updateViaAdministracion(1,new CatViaAdministracionView());
	}
	//////////////////////////////////////////////findAll////////////////////////////////////
	@Test(expected = CatViaAdministracionException.class)
	public void getAllViaAdministracionException() throws Exception {
		Mockito.when(catViaAdministracionRepository.findAll())
		.thenThrow(new IllegalArgumentException());

		catViaAdministracionServiceImpl.findAll();
	}

	//////////////////////////////////getById//////////////////////////////////////////////////////////
	@Test(expected = CatViaAdministracionException.class)
	public void getByIdViaAdministracionException() throws Exception {
		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new IllegalArgumentException());

		catViaAdministracionServiceImpl.getDetailsByIdCatViaAdministracion(1);
	}

	@Test(expected = CatViaAdministracionException.class)
	public void getByIdViaAdministracionConstraintViolation() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatViaAdministracion entity = new CatViaAdministracion();
		Set<ConstraintViolation<CatViaAdministracion>> violations = validator.validate(entity);

		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));

		catViaAdministracionServiceImpl.getDetailsByIdCatViaAdministracion(1);
	}

	@Test(expected = CatViaAdministracionException.class)
	public void getByIdViaAdministracionDataIntegrity() throws Exception {

		Mockito.when(catViaAdministracionRepository.exists(Matchers.anyInt())).thenReturn(true);

		Mockito.when(catViaAdministracionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",
				new PSQLException("org.postgresql.util.PSQLException: ERROR:",new PSQLState("324351453"),
						new Throwable(" Detail: No se pudo actualziar el registro"))));

		catViaAdministracionServiceImpl.getDetailsByIdCatViaAdministracion(1);
	}
	//////////////////////////////////////////search//////////////////////////////////////////////////
	@Test(expected = CatViaAdministracionException.class)
	public void searchViaAdministracionException() throws Exception {
		Mockito.when(((JpaSpecificationExecutor<?>)catViaAdministracionRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
		.thenThrow(new IllegalArgumentException());
		catViaAdministracionServiceImpl.getCatViaAdministracionSearch("", null, 0, 10, "vaDescripcion", "asc");
	}



}
