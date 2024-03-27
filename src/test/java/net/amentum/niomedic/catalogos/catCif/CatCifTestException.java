//package net.amentum.niomedic.catalogos.catCif;
//
//public class CatCifTestException {
//}


package net.amentum.niomedic.catalogos.catCif;

import net.amentum.niomedic.catalogos.converter.CatCifConverter;
import net.amentum.niomedic.catalogos.exception.CatCifException;
import net.amentum.niomedic.catalogos.model.CatCif;
import net.amentum.niomedic.catalogos.persistence.CatCifRepository;
import net.amentum.niomedic.catalogos.service.impl.CatCifServiceImpl;
import net.amentum.niomedic.catalogos.views.CatCifView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.hibernate.exception.ConstraintViolationException;
//import javax.validation.ConstraintViolationException;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
import java.sql.SQLException;
//import java.util.Set;

public class CatCifTestException {

   private CatCifServiceImpl catCifServiceImpl;
   @Mock
   private CatCifRepository catCifRepository;
   @Mock
   private CatCifConverter catCifConverter;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      catCifServiceImpl = new CatCifServiceImpl();
      catCifServiceImpl.setCatCifConverter(catCifConverter);
      catCifServiceImpl.setCatCifRepository(catCifRepository);
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FIND-BY-ID
   @Test(expected = CatCifException.class)
   public void getDetailsByIdCifException() throws Exception {
//      mvn test -Dtest=CatCifTestException#getDetailsByIdCifException test
      Mockito.when(catCifRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catCifConverter.toView(Matchers.any(CatCif.class), Matchers.anyBoolean()))
         .thenThrow(new NullPointerException());
      catCifServiceImpl.getDetailsByIdCatCif(Matchers.anyInt());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FINDALL
   @Test(expected = CatCifException.class)
   public void findAllException() throws Exception {
//      mvn test -Dtest=CatCifTestException#findAllException test
      Mockito.when(catCifRepository.findAll())
         .thenThrow(new NullPointerException());
      catCifServiceImpl.findAll();
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////SEARCH
   @Test(expected = CatCifException.class)
   public void getCatCifSearchIllegalArgumentException() throws Exception {
//      mvn test -Dtest=CatCifTestException#getCatCifSearchIllegalArgumentException test
      Mockito.when(((JpaSpecificationExecutor<?>) catCifRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new IllegalArgumentException());
      catCifServiceImpl.getCatCifSearch("", null, 0, 10, "idCatCif", "asc");
   }

   @Test(expected = CatCifException.class)
   public void getCatCifSearchException() throws Exception {
//      mvn test -Dtest=CatCifTestException#getCatCifSearchException test
      Mockito.when(((JpaSpecificationExecutor<?>) catCifRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new NullPointerException());
      catCifServiceImpl.getCatCifSearch("", null, 0, 10, "idCatCif", "asc");
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////POST
   @Test(expected = CatCifException.class)
   public void createCatCifException() throws Exception {
//      mvn test -Dtest=CatCifTestException#createCatCifException test
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new NullPointerException());
      catCifServiceImpl.createCatCif(new CatCifView());
   }

   @Test(expected = CatCifException.class)
   public void createCatCifConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatCifTestException#createCatCifConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatCif entity = new CatCif();
//      Set<ConstraintViolation<CatCif>> violations = validator.validate(entity);
      Mockito.when(catCifConverter.toEntity(Matchers.any(CatCifView.class),Matchers.any(CatCif.class), Matchers.anyBoolean()))
         .thenReturn(new CatCif());
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catCifServiceImpl.createCatCif(new CatCifView());
   }

   @Test(expected = CatCifException.class)
   public void createCatCifDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatCifTestException#createCatCifDataIntegrityViolationException test
      Mockito.when(catCifConverter.toEntity(Matchers.any(CatCifView.class),Matchers.any(CatCif.class), Matchers.anyBoolean()))
         .thenReturn(new CatCif());
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catCifServiceImpl.createCatCif(new CatCifView());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////PUT
   @Test(expected = CatCifException.class)
   public void updateCatCifException() throws Exception {
//      mvn test -Dtest=CatCifTestException#updateCatCifException test
      Mockito.when(catCifRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new NullPointerException());
      catCifServiceImpl.updateCatCif(new CatCifView());
   }

   @Test(expected = CatCifException.class)
   public void updateCatCifConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatCifTestException#updateCatCifConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatCif entity = new CatCif();
//      Set<ConstraintViolation<CatCif>> violations = validator.validate(entity);
      Mockito.when(catCifRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catCifConverter.toEntity(Matchers.any(CatCifView.class),Matchers.any(CatCif.class), Matchers.anyBoolean()))
         .thenReturn(new CatCif());
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catCifServiceImpl.updateCatCif(new CatCifView());
   }

   @Test(expected = CatCifException.class)
   public void updateCatCifDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatCifTestException#updateCatCifDataIntegrityViolationException test
      Mockito.when(catCifRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catCifConverter.toEntity(Matchers.any(CatCifView.class),Matchers.any(CatCif.class), Matchers.anyBoolean()))
         .thenReturn(new CatCif());
      Mockito.when(catCifRepository.save(Matchers.any(CatCif.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catCifServiceImpl.updateCatCif(new CatCifView());
   }

}

