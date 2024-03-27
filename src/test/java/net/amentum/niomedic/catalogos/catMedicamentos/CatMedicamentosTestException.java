package net.amentum.niomedic.catalogos.catMedicamentos;

import net.amentum.niomedic.catalogos.converter.CatMedicamentosConverter;
import net.amentum.niomedic.catalogos.exception.CatMedicamentosException;
import net.amentum.niomedic.catalogos.model.CatMedicamentos;
import net.amentum.niomedic.catalogos.persistence.CatMedicamentosRepository;
import net.amentum.niomedic.catalogos.service.impl.CatMedicamentosServiceImpl;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
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

public class CatMedicamentosTestException {

   private CatMedicamentosServiceImpl catMedicamentosServiceImpl;
   @Mock
   private CatMedicamentosRepository catMedicamentosRepository;
   @Mock
   private CatMedicamentosConverter catMedicamentosConverter;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      catMedicamentosServiceImpl = new CatMedicamentosServiceImpl();
      catMedicamentosServiceImpl.setCatMedicamentosConverter(catMedicamentosConverter);
      catMedicamentosServiceImpl.setCatMedicamentosRepository(catMedicamentosRepository);
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FIND-BY-ID
   @Test(expected = CatMedicamentosException.class)
   public void getDetailsByIdCatMedicamentosException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#getDetailsByIdCatMedicamentosException test
      Mockito.when(catMedicamentosRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMedicamentosConverter.toView(Matchers.any(CatMedicamentos.class), Matchers.anyBoolean()))
         .thenThrow(new NullPointerException());
      catMedicamentosServiceImpl.getDetailsByIdCatMedicamentos(Matchers.anyInt());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FINDALL
   @Test(expected = CatMedicamentosException.class)
   public void findAllException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#findAllException test
      Mockito.when(catMedicamentosRepository.findAll())
         .thenThrow(new NullPointerException());
      catMedicamentosServiceImpl.findAll();
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////SEARCH
   @Test(expected = CatMedicamentosException.class)
   public void getCatMedicamentosSearchIllegalArgumentException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#getCatMedicamentosSearchIllegalArgumentException test
      Mockito.when(((JpaSpecificationExecutor<?>) catMedicamentosRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new IllegalArgumentException());
      catMedicamentosServiceImpl.getCatMedicamentosSearch("", null, 0, 10, "idCatMedicamentos", "asc");
   }

   @Test(expected = CatMedicamentosException.class)
   public void getCatMedicamentosSearchException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#getCatMedicamentosSearchException test
      Mockito.when(((JpaSpecificationExecutor<?>) catMedicamentosRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new NullPointerException());
      catMedicamentosServiceImpl.getCatMedicamentosSearch("", null, 0, 10, "idCatMedicamentos", "asc");
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////POST
   @Test(expected = CatMedicamentosException.class)
   public void createCatMedicamentosException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#createCatMedicamentosException test
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new NullPointerException());
      catMedicamentosServiceImpl.createCatMedicamentos(new CatMedicamentosView());
   }

   @Test(expected = CatMedicamentosException.class)
   public void createCatMedicamentosConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#createCatMedicamentosConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatMedicamentos entity = new CatMedicamentos();
//      Set<ConstraintViolation<CatMedicamentos>> violations = validator.validate(entity);
      Mockito.when(catMedicamentosConverter.toEntity(Matchers.any(CatMedicamentosView.class),Matchers.any(CatMedicamentos.class), Matchers.anyBoolean()))
         .thenReturn(new CatMedicamentos());
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catMedicamentosServiceImpl.createCatMedicamentos(new CatMedicamentosView());
   }

   @Test(expected = CatMedicamentosException.class)
   public void createCatMedicamentosDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#createCatMedicamentosDataIntegrityViolationException test
      Mockito.when(catMedicamentosConverter.toEntity(Matchers.any(CatMedicamentosView.class),Matchers.any(CatMedicamentos.class), Matchers.anyBoolean()))
         .thenReturn(new CatMedicamentos());
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catMedicamentosServiceImpl.createCatMedicamentos(new CatMedicamentosView());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////PUT
   @Test(expected = CatMedicamentosException.class)
   public void updateCatMedicamentosException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#updateCatMedicamentosException test
      Mockito.when(catMedicamentosRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new NullPointerException());
      catMedicamentosServiceImpl.updateCatMedicamentos(new CatMedicamentosView());
   }

   @Test(expected = CatMedicamentosException.class)
   public void updateCatMedicamentosConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#updateCatMedicamentosConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatMedicamentos entity = new CatMedicamentos();
//      Set<ConstraintViolation<CatMedicamentos>> violations = validator.validate(entity);
      Mockito.when(catMedicamentosRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMedicamentosConverter.toEntity(Matchers.any(CatMedicamentosView.class),Matchers.any(CatMedicamentos.class), Matchers.anyBoolean()))
         .thenReturn(new CatMedicamentos());
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catMedicamentosServiceImpl.updateCatMedicamentos(new CatMedicamentosView());
   }

   @Test(expected = CatMedicamentosException.class)
   public void updateCatMedicamentosDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatMedicamentosTestException#updateCatMedicamentosDataIntegrityViolationException test
      Mockito.when(catMedicamentosRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catMedicamentosConverter.toEntity(Matchers.any(CatMedicamentosView.class),Matchers.any(CatMedicamentos.class), Matchers.anyBoolean()))
         .thenReturn(new CatMedicamentos());
      Mockito.when(catMedicamentosRepository.save(Matchers.any(CatMedicamentos.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catMedicamentosServiceImpl.updateCatMedicamentos(new CatMedicamentosView());
   }

}

