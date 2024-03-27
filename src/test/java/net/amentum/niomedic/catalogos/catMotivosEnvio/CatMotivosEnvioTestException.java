package net.amentum.niomedic.catalogos.catMotivosEnvio;

import net.amentum.niomedic.catalogos.converter.CatMotivosEnvioConverter;
import net.amentum.niomedic.catalogos.exception.CatMotivosEnvioException;
import net.amentum.niomedic.catalogos.model.CatMotivosEnvio;
import net.amentum.niomedic.catalogos.persistence.CatMotivosEnvioRepository;
import net.amentum.niomedic.catalogos.service.impl.CatMotivosEnvioServiceImpl;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.SQLException;

//import javax.validation.ConstraintViolationException;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Set;

public class CatMotivosEnvioTestException {

   private CatMotivosEnvioServiceImpl catMotivosEnvioServiceImpl;
   @Mock
   private CatMotivosEnvioRepository catMotivosEnvioRepository;
   @Mock
   private CatMotivosEnvioConverter catMotivosEnvioConverter;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      catMotivosEnvioServiceImpl = new CatMotivosEnvioServiceImpl();
      catMotivosEnvioServiceImpl.setCatMotivosEnvioConverter(catMotivosEnvioConverter);
      catMotivosEnvioServiceImpl.setCatMotivosEnvioRepository(catMotivosEnvioRepository);
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FIND-BY-ID
   @Test(expected = CatMotivosEnvioException.class)
   public void getDetailsByIdCatMotivosEnvioException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#getDetailsByIdCatMotivosEnvioException test
      Mockito.when(catMotivosEnvioRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMotivosEnvioConverter.toView(Matchers.any(CatMotivosEnvio.class), Matchers.anyBoolean()))
         .thenThrow(new NullPointerException());
      catMotivosEnvioServiceImpl.getDetailsByIdCatMotivosEnvio(Matchers.anyInt());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FINDALL
   @Test(expected = CatMotivosEnvioException.class)
   public void findAllException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#findAllException test
      Mockito.when(catMotivosEnvioRepository.findAll())
         .thenThrow(new NullPointerException());
      catMotivosEnvioServiceImpl.findAll();
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////SEARCH
   @Test(expected = CatMotivosEnvioException.class)
   public void getCatMotivosEnvioSearchIllegalArgumentException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#getCatMotivosEnvioSearchIllegalArgumentException test
      Mockito.when(((JpaSpecificationExecutor<?>) catMotivosEnvioRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new IllegalArgumentException());
      catMotivosEnvioServiceImpl.getCatMotivosEnvioSearch("", null, 0, 10, "idCatMotivosEnvio", "asc");
   }

   @Test(expected = CatMotivosEnvioException.class)
   public void getCatMotivosEnvioSearchException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#getCatMotivosEnvioSearchException test
      Mockito.when(((JpaSpecificationExecutor<?>) catMotivosEnvioRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new NullPointerException());
      catMotivosEnvioServiceImpl.getCatMotivosEnvioSearch("", null, 0, 10, "idCatMotivosEnvio", "asc");
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////POST
   @Test(expected = CatMotivosEnvioException.class)
   public void createCatMotivosEnvioException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#createCatMotivosEnvioException test
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new NullPointerException());
      catMotivosEnvioServiceImpl.createCatMotivosEnvio(new CatMotivosEnvioView());
   }

   @Test(expected = CatMotivosEnvioException.class)
   public void createCatMotivosEnvioConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#createCatMotivosEnvioConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatMotivosEnvio entity = new CatMotivosEnvio();
//      Set<ConstraintViolation<CatMotivosEnvio>> violations = validator.validate(entity);
      Mockito.when(catMotivosEnvioConverter.toEntity(Matchers.any(CatMotivosEnvioView.class),Matchers.any(CatMotivosEnvio.class), Matchers.anyBoolean()))
         .thenReturn(new CatMotivosEnvio());
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catMotivosEnvioServiceImpl.createCatMotivosEnvio(new CatMotivosEnvioView());
   }

   @Test(expected = CatMotivosEnvioException.class)
   public void createCatMotivosEnvioDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#createCatMotivosEnvioDataIntegrityViolationException test
      Mockito.when(catMotivosEnvioConverter.toEntity(Matchers.any(CatMotivosEnvioView.class),Matchers.any(CatMotivosEnvio.class), Matchers.anyBoolean()))
         .thenReturn(new CatMotivosEnvio());
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catMotivosEnvioServiceImpl.createCatMotivosEnvio(new CatMotivosEnvioView());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////PUT
   @Test(expected = CatMotivosEnvioException.class)
   public void updateCatMotivosEnvioException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#updateCatMotivosEnvioException test
      Mockito.when(catMotivosEnvioRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new NullPointerException());
      catMotivosEnvioServiceImpl.updateCatMotivosEnvio(new CatMotivosEnvioView());
   }

   @Test(expected = CatMotivosEnvioException.class)
   public void updateCatMotivosEnvioConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#updateCatMotivosEnvioConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatMotivosEnvio entity = new CatMotivosEnvio();
//      Set<ConstraintViolation<CatMotivosEnvio>> violations = validator.validate(entity);
      Mockito.when(catMotivosEnvioRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMotivosEnvioConverter.toEntity(Matchers.any(CatMotivosEnvioView.class),Matchers.any(CatMotivosEnvio.class), Matchers.anyBoolean()))
         .thenReturn(new CatMotivosEnvio());
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catMotivosEnvioServiceImpl.updateCatMotivosEnvio(new CatMotivosEnvioView());
   }

   @Test(expected = CatMotivosEnvioException.class)
   public void updateCatMotivosEnvioDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatMotivosEnvioTestException#updateCatMotivosEnvioDataIntegrityViolationException test
      Mockito.when(catMotivosEnvioRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMotivosEnvioConverter.toEntity(Matchers.any(CatMotivosEnvioView.class),Matchers.any(CatMotivosEnvio.class), Matchers.anyBoolean()))
         .thenReturn(new CatMotivosEnvio());
      Mockito.when(catMotivosEnvioRepository.save(Matchers.any(CatMotivosEnvio.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catMotivosEnvioServiceImpl.updateCatMotivosEnvio(new CatMotivosEnvioView());
   }

}

