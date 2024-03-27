package net.amentum.niomedic.catalogos.catEspecialidades;

import net.amentum.niomedic.catalogos.converter.CatEspecialidadesConverter;
import net.amentum.niomedic.catalogos.exception.CatEspecialidadesException;
import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import net.amentum.niomedic.catalogos.persistence.CatEspecialidadesRepository;
import net.amentum.niomedic.catalogos.service.impl.CatEspecialidadesServiceImpl;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
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

public class CatEspecialidadesTestException {

   private CatEspecialidadesServiceImpl catEspecialidadesServiceImpl;
   @Mock
   private CatEspecialidadesRepository catEspecialidadesRepository;
   @Mock
   private CatEspecialidadesConverter catEspecialidadesConverter;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      catEspecialidadesServiceImpl = new CatEspecialidadesServiceImpl();
      catEspecialidadesServiceImpl.setCatEspecialidadesConverter(catEspecialidadesConverter);
      catEspecialidadesServiceImpl.setCatEspecialidadesRepository(catEspecialidadesRepository);
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FIND-BY-ID
   @Test(expected = CatEspecialidadesException.class)
   public void getDetailsByIdCatEspecialidadesException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#getDetailsByIdCatEspecialidadesException test
      Mockito.when(catEspecialidadesRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catEspecialidadesConverter.toView(Matchers.any(CatEspecialidades.class), Matchers.anyBoolean()))
         .thenThrow(new NullPointerException());
      catEspecialidadesServiceImpl.getDetailsByIdCatEspecialidades(Matchers.anyInt());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////FINDALL
   @Test(expected = CatEspecialidadesException.class)
   public void findAllException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#findAllException test
      Mockito.when(catEspecialidadesRepository.findAll())
         .thenThrow(new NullPointerException());
      catEspecialidadesServiceImpl.findAll();
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////SEARCH
   @Test(expected = CatEspecialidadesException.class)
   public void getCatEspecialidadesSearchIllegalArgumentException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#getCatEspecialidadesSearchIllegalArgumentException test
      Mockito.when(((JpaSpecificationExecutor<?>) catEspecialidadesRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new IllegalArgumentException());
      catEspecialidadesServiceImpl.getCatEspecialidadesSearch("", null, 0, 10, "idCatEspecialidades", "asc");
   }

   @Test(expected = CatEspecialidadesException.class)
   public void getCatEspecialidadesSearchException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#getCatEspecialidadesSearchException test
      Mockito.when(((JpaSpecificationExecutor<?>) catEspecialidadesRepository).findAll(Matchers.anyObject(), Matchers.any(PageRequest.class)))
         .thenThrow(new NullPointerException());
      catEspecialidadesServiceImpl.getCatEspecialidadesSearch("", null, 0, 10, "idCatEspecialidades", "asc");
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////POST
   @Test(expected = CatEspecialidadesException.class)
   public void createCatEspecialidadesException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#createCatEspecialidadesException test
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new NullPointerException());
      catEspecialidadesServiceImpl.createCatEspecialidades(new CatEspecialidadesView());
   }

   @Test(expected = CatEspecialidadesException.class)
   public void createCatEspecialidadesConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#createCatEspecialidadesConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatEspecialidades entity = new CatEspecialidades();
//      Set<ConstraintViolation<CatEspecialidades>> violations = validator.validate(entity);
      Mockito.when(catEspecialidadesConverter.toEntity(Matchers.any(CatEspecialidadesView.class),Matchers.any(CatEspecialidades.class), Matchers.anyBoolean()))
         .thenReturn(new CatEspecialidades());
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catEspecialidadesServiceImpl.createCatEspecialidades(new CatEspecialidadesView());
   }

   @Test(expected = CatEspecialidadesException.class)
   public void createCatEspecialidadesDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#createCatEspecialidadesDataIntegrityViolationException test
      Mockito.when(catEspecialidadesConverter.toEntity(Matchers.any(CatEspecialidadesView.class),Matchers.any(CatEspecialidades.class), Matchers.anyBoolean()))
         .thenReturn(new CatEspecialidades());
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catEspecialidadesServiceImpl.createCatEspecialidades(new CatEspecialidadesView());
   }

   /////////////////////////////////////////////////////////////////////////////////////////////////////PUT
   @Test(expected = CatEspecialidadesException.class)
   public void updateCatEspecialidadesException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#updateCatEspecialidadesException test
      Mockito.when(catEspecialidadesRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new NullPointerException());
      catEspecialidadesServiceImpl.updateCatEspecialidades(new CatEspecialidadesView());
   }

   @Test(expected = CatEspecialidadesException.class)
   public void updateCatEspecialidadesConstraintViolationException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#updateCatEspecialidadesConstraintViolationException test
//      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//      Validator validator = factory.getValidator();
//      CatEspecialidades entity = new CatEspecialidades();
//      Set<ConstraintViolation<CatEspecialidades>> violations = validator.validate(entity);
      Mockito.when(catEspecialidadesRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catEspecialidadesConverter.toEntity(Matchers.any(CatEspecialidadesView.class),Matchers.any(CatEspecialidades.class), Matchers.anyBoolean()))
         .thenReturn(new CatEspecialidades());
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new ConstraintViolationException("Error al insertar", new SQLException("ConstrainViolation"), "No inserta"));
      catEspecialidadesServiceImpl.updateCatEspecialidades(new CatEspecialidadesView());
   }

   @Test(expected = CatEspecialidadesException.class)
   public void updateCatEspecialidadesDataIntegrityViolationException() throws Exception {
//      mvn test -Dtest=CatEspecialidadesTestException#updateCatEspecialidadesDataIntegrityViolationException test
      Mockito.when(catEspecialidadesRepository.exists(Matchers.anyInt()))
         .thenReturn(true);
      Mockito.when(catEspecialidadesConverter.toEntity(Matchers.any(CatEspecialidadesView.class),Matchers.any(CatEspecialidades.class), Matchers.anyBoolean()))
         .thenReturn(new CatEspecialidades());
      Mockito.when(catEspecialidadesRepository.save(Matchers.any(CatEspecialidades.class)))
         .thenThrow(new DataIntegrityViolationException("ERROR", new Throwable("Error al insertar")));
      catEspecialidadesServiceImpl.updateCatEspecialidades(new CatEspecialidadesView());
   }

}

