package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatNumerosEmergenciaConverter;
import net.amentum.niomedic.catalogos.exception.CatNumerosEmergenciaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatNumerosEmergencia;
import net.amentum.niomedic.catalogos.persistence.CatNumerosEmergenciaRepository;
import net.amentum.niomedic.catalogos.service.CatNumerosEmergenciaService;
import net.amentum.niomedic.catalogos.views.CatNumerosEmergenciaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class CatNumerosEmergenciaServiceImpl implements CatNumerosEmergenciaService {
   private final Logger logger = LoggerFactory.getLogger(CatNumerosEmergenciaServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatNumerosEmergenciaRepository catNumerosEmergenciaRepository;
   private CatNumerosEmergenciaConverter catNumerosEmergenciaConverter;

   {
      colOrderNames.put("numeroTelefonico", "numeroTelefonico");
      colOrderNames.put("numeroEmergenciaDescripcion", "numeroEmergenciaDescripcion");
   }

   @Autowired
   public void setCatNumerosEmergenciaRepository(CatNumerosEmergenciaRepository catNumerosEmergenciaRepository) {
      this.catNumerosEmergenciaRepository = catNumerosEmergenciaRepository;
   }

   @Autowired
   public void setCatNumerosEmergenciaConverter(CatNumerosEmergenciaConverter catNumerosEmergenciaConverter) {
      this.catNumerosEmergenciaConverter = catNumerosEmergenciaConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatNumerosEmergenciaException.class})
   @Override
   public CatNumerosEmergenciaView getDetailsByIdCatNumerosEmergencia(Integer idCatNumerosEmergencia) throws CatNumerosEmergenciaException {
      try {
         if (!catNumerosEmergenciaRepository.exists(idCatNumerosEmergencia)) {
            logger.error("===>>>idCatNumerosEmergencia no encontrado: {}", idCatNumerosEmergencia);
            CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("No se encuentra en el sistema CatNumerosEmergencia", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_VALIDATE);
            numEmerE.addError("idCatNumerosEmergencia no encontrado: " + idCatNumerosEmergencia);
            throw numEmerE;
         }
         CatNumerosEmergencia catNumerosEmergencia = catNumerosEmergenciaRepository.findOne(idCatNumerosEmergencia);
         return catNumerosEmergenciaConverter.toView(catNumerosEmergencia, Boolean.TRUE);
      } catch (CatNumerosEmergenciaException numEmerE) {
         throw numEmerE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("Error en la validacion", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            numEmerE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw numEmerE;
      } catch (DataIntegrityViolationException dive) {
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("No fue posible obtener  CatNumerosEmergencia", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_INSERT);
         numEmerE.addError("Ocurrio un error al obtener CatNumerosEmergencia");
         logger.error("===>>>Error al obtener CatNumerosEmergencia - CODE: {} - {}", numEmerE.getExceptionCode(), idCatNumerosEmergencia, dive);
         throw numEmerE;
      } catch (Exception ex) {
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("Error inesperado al obtener  CatNumerosEmergencia", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_INSERT);
         numEmerE.addError("Ocurrio un error al obtener CatNumerosEmergencia");
         logger.error("===>>>Error al obtener CatNumerosEmergencia - CODE: {} - {}", numEmerE.getExceptionCode(), idCatNumerosEmergencia, ex);
         throw numEmerE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatNumerosEmergenciaException.class})
   @Override
   public List<CatNumerosEmergenciaView> findAll() throws CatNumerosEmergenciaException {
      try {
         List<CatNumerosEmergencia> catNumerosEmergenciaList = catNumerosEmergenciaRepository.findAll();
         List<CatNumerosEmergenciaView> catNumerosEmergenciaViewList = new ArrayList<>();
         for (CatNumerosEmergencia cn : catNumerosEmergenciaList) {
            catNumerosEmergenciaViewList.add(catNumerosEmergenciaConverter.toView(cn, Boolean.TRUE));
         }
         return catNumerosEmergenciaViewList;
      } catch (Exception ex) {
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("Error inesperado al obtener todos los registros de  CatNumerosEmergencia", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_INSERT);
         numEmerE.addError("Ocurrio un error al obtener CatNumerosEmergencia");
         logger.error("===>>>Error al obtener todos los registros de CatNumerosEmergencia - CODE: {} - {}", numEmerE.getExceptionCode(), ex);
         throw numEmerE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatNumerosEmergenciaException.class})
   @Override
   public Page<CatNumerosEmergenciaView> getCatNumerosEmergenciaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatNumerosEmergenciaException {
      try {
         logger.info("===>>>getCatNumerosEmergenciaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("No se encuentra en el sistema CatNumerosEmergencia", CatNumerosEmergenciaException.LAYER_DAO, CatNumerosEmergenciaException.ACTION_VALIDATE);
//            numEmerE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw numEmerE;
//         }

         List<CatNumerosEmergenciaView> catNumerosEmergenciaViewList = new ArrayList<>();
         Page<CatNumerosEmergencia> catNumerosEmergenciaPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("numeroEmergenciaDescripcion"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatNumerosEmergencia> spec = Specifications.where(
            (root, query, cb) -> {
               Predicate tc = null;
               if (datosBusqueda != null && !datosBusqueda.isEmpty()) {
                  tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch)));
               }
//               if (active != null) {
//                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
//               }
               return tc;
            }
         );

         if (spec == null) {
            catNumerosEmergenciaPage = catNumerosEmergenciaRepository.findAll(request);
         } else {
            catNumerosEmergenciaPage = catNumerosEmergenciaRepository.findAll(spec, request);
         }

         catNumerosEmergenciaPage.getContent().forEach(catNumerosEmergencia -> {
            catNumerosEmergenciaViewList.add(catNumerosEmergenciaConverter.toView(catNumerosEmergencia, Boolean.TRUE));
         });
         PageImpl<CatNumerosEmergenciaView> catNumerosEmergenciaViewPage = new PageImpl<CatNumerosEmergenciaView>(catNumerosEmergenciaViewList, request, catNumerosEmergenciaPage.getTotalElements());
         return catNumerosEmergenciaViewPage;
//      } catch (CatNumerosEmergenciaException numEmerE) {
//         throw numEmerE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatNumerosEmergenciaException pe = new CatNumerosEmergenciaException("Algun parametro no es correcto:", CatNumerosEmergenciaException.LAYER_SERVICE, CatNumerosEmergenciaException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("Ocurrio un error al seleccionar lista CatNumerosEmergencia paginable", CatNumerosEmergenciaException.LAYER_SERVICE, CatNumerosEmergenciaException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatNumerosEmergencia paginable - CODE: {}", numEmerE.getExceptionCode(), ex);
         throw numEmerE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
