package net.amentum.niomedic.catalogos.catFormacion;

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
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.amentum.niomedic.catalogos.converter.CatFormacionConverter;
import net.amentum.niomedic.catalogos.exception.CatFormacionException;
import net.amentum.niomedic.catalogos.model.CatFormacion;
import net.amentum.niomedic.catalogos.persistence.CatFormacionRepository;
import net.amentum.niomedic.catalogos.service.impl.CatFormacionServiceImpl;
import net.amentum.niomedic.catalogos.views.CatFormacionView;

public class CatFormacionTestException {
	private CatFormacionServiceImpl catFormacionServiceImpl;
	@Mock
	private CatFormacionRepository catFormacionRepository;
	@Mock
	private CatFormacionConverter catFormacionConverter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		catFormacionServiceImpl = new CatFormacionServiceImpl();
		catFormacionServiceImpl.setCatFormacionConverter(catFormacionConverter);
		catFormacionServiceImpl.setCatFormacionRepository(catFormacionRepository);
	}
	///////////////////////////////////////////////Create/////////////////////////////////////////////////////////////
	@Test(expected = CatFormacionException.class)
	public void createCatFormacionException()throws Exception {
		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catFormacionServiceImpl.creteCatFormacion(new CatFormacionView());
	}
	@Test(expected = CatFormacionException.class)
	public void createCatFormacionConstrain()throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatFormacion entity = new CatFormacion();
		Set<ConstraintViolation<CatFormacion>> violations = validator.validate(entity);

		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenReturn(new CatFormacion());
		Mockito.when(catFormacionRepository.save(Matchers.any(CatFormacion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catFormacionServiceImpl.creteCatFormacion(new CatFormacionView());

	}

	@Test(expected = CatFormacionException.class)
	public void createCatFormacionDataIntegrity()throws Exception {
		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenReturn(new CatFormacion());
		Mockito.when(catFormacionRepository.save(Matchers.any(CatFormacion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable("Error la insertar")));
		catFormacionServiceImpl.creteCatFormacion(new CatFormacionView());
	}
	////////////////////////////////////////update////////////////////////////////////////////////////////////////
	@Test(expected = CatFormacionException.class)
	public void updateCatFormacionException()throws Exception {
		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatFormacion());	
		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catFormacionServiceImpl.updateCatFormacion(1, new CatFormacionView());
	}

	@Test(expected = CatFormacionException.class)
	public void updateCatFormacionConstrain()throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatFormacion entity = new CatFormacion();
		Set<ConstraintViolation<CatFormacion>> violations = validator.validate(entity);

		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatFormacion());

		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenReturn(new CatFormacion());
		Mockito.when(catFormacionRepository.save(Matchers.any(CatFormacion.class)))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catFormacionServiceImpl.updateCatFormacion(1, new CatFormacionView());

	}
	@Test(expected = CatFormacionException.class)
	public void updateCatFormacionDataIntegrity()throws Exception {
		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatFormacion());
		Mockito.when(catFormacionConverter.toEntity(Matchers.any(CatFormacion.class), Matchers.any(CatFormacionView.class), Matchers.anyBoolean()))
		.thenReturn(new CatFormacion());
		Mockito.when(catFormacionRepository.save(Matchers.any(CatFormacion.class)))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable("Error la insertar")));
		catFormacionServiceImpl.updateCatFormacion(1, new CatFormacionView());
	}
	/////////////////////////////////////search//////////////////////////////////////////////////
	@Test(expected = CatFormacionException.class)
	public void searchCatFormacionException()throws Exception {
		Mockito.when(((JpaSpecificationExecutor<?>)catFormacionRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
		.thenThrow(new IllegalArgumentException());
		catFormacionServiceImpl.getCatFormacionSearch("", null, 0, 10, "idCatFormacion", "asc");
	}
	///////////////////////////////////////findAll////////////////////////////////////////////////
	@Test(expected = CatFormacionException.class)
	public void finAllCatFormacionException()throws Exception {
		Mockito.when(catFormacionRepository.findAll())
		.thenThrow(new IllegalArgumentException());
		catFormacionServiceImpl.findAll();
	}
	/////////////////////////////////////////ByID//////////////////////////////////////////////////////////
	@Test(expected = CatFormacionException.class)
	public void byIdCatFormacionException()throws Exception {
		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenReturn(new CatFormacion());	
		Mockito.when(catFormacionConverter.toView(Matchers.any(CatFormacion.class), Matchers.anyBoolean()))
		.thenThrow(new IllegalArgumentException());
		catFormacionServiceImpl.getDetailsByIdCatFormacion(4);
	}

	@Test(expected = CatFormacionException.class)
	public void byIdCatFormacionConstrain()throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		CatFormacion entity = new CatFormacion();
		Set<ConstraintViolation<CatFormacion>> violations = validator.validate(entity);

		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new ConstraintViolationException("Error de validacion", violations));
		catFormacionServiceImpl.getDetailsByIdCatFormacion(4);

	}
	@Test(expected = CatFormacionException.class)
	public void byIdCatFormacionDataIntegrity()throws Exception {
		Mockito.when(catFormacionRepository.exists(Matchers.anyInt()))
		.thenReturn(true);
		Mockito.when(catFormacionRepository.findOne(Matchers.anyInt()))
		.thenThrow(new DataIntegrityViolationException("Error de Integridad",new Throwable("Error la insertar")));
		catFormacionServiceImpl.getDetailsByIdCatFormacion(4);
	}
}
