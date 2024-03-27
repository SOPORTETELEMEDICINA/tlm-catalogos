package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatCuadroIoMedConverter;
import net.amentum.niomedic.catalogos.exception.CatCuadroIoMedException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatCuadroIoMed;
import net.amentum.niomedic.catalogos.persistence.CatCuadroIoMedRepository;
import net.amentum.niomedic.catalogos.service.CatCuadroIoMedService;
import net.amentum.niomedic.catalogos.views.CatCuadroIoMedView;
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
public class CatCuadroIoMedServiceImpl implements CatCuadroIoMedService {
   private final Logger logger = LoggerFactory.getLogger(CatCuadroIoMedServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatCuadroIoMedRepository catCuadroIoMedRepository;
   private CatCuadroIoMedConverter catCuadroIoMedConverter;

   {
      colOrderNames.put("descripcionCuadroIo", "descripcionCuadroIo");
   }

   @Autowired
   public void setCatCuadroIoMedRepository(CatCuadroIoMedRepository catCuadroIoMedRepository) {
      this.catCuadroIoMedRepository = catCuadroIoMedRepository;
   }

   @Autowired
   public void setCatCuadroIoMedConverter(CatCuadroIoMedConverter catCuadroIoMedConverter) {
      this.catCuadroIoMedConverter = catCuadroIoMedConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCuadroIoMedException.class})
   @Override
   public CatCuadroIoMedView getDetailsByIdCatCuadroIoMed(Integer idCatCuadroIoMed) throws CatCuadroIoMedException {
      try {
         if (!catCuadroIoMedRepository.exists(idCatCuadroIoMed)) {
            logger.error("===>>>idCatCuadroIoMed no encontrado: {}", idCatCuadroIoMed);
            CatCuadroIoMedException cCioE = new CatCuadroIoMedException("No se encuentra en el sistema CatCuadroIoMed", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_VALIDATE);
            cCioE.addError("idCatCuadroIoMed no encontrado: " + idCatCuadroIoMed);
            throw cCioE;
         }
         CatCuadroIoMed catCuadroIoMed = catCuadroIoMedRepository.findOne(idCatCuadroIoMed);
         return catCuadroIoMedConverter.toView(catCuadroIoMed, Boolean.TRUE);
      } catch (CatCuadroIoMedException cCioE) {
         throw cCioE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("Error en la validacion", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cCioE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cCioE;
      } catch (DataIntegrityViolationException dive) {
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("No fue posible obtener  CatCuadroIoMed", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_INSERT);
         cCioE.addError("Ocurrio un error al obtener CatCuadroIoMed");
         logger.error("===>>>Error al obtener CatCuadroIoMed - CODE: {} - {}", cCioE.getExceptionCode(), idCatCuadroIoMed, dive);
         throw cCioE;
      } catch (Exception ex) {
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("Error inesperado al obtener  CatCuadroIoMed", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_INSERT);
         cCioE.addError("Ocurrio un error al obtener CatCuadroIoMed");
         logger.error("===>>>Error al obtener CatCuadroIoMed - CODE: {} - {}", cCioE.getExceptionCode(), idCatCuadroIoMed, ex);
         throw cCioE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatCuadroIoMedException.class})
   @Override
   public List<CatCuadroIoMedView> findAll() throws CatCuadroIoMedException {
      try {
         List<CatCuadroIoMed> catCuadroIoMedList = catCuadroIoMedRepository.findAll();
         List<CatCuadroIoMedView> catCuadroIoMedViewList = new ArrayList<>();
         for (CatCuadroIoMed cn : catCuadroIoMedList) {
            catCuadroIoMedViewList.add(catCuadroIoMedConverter.toView(cn, Boolean.TRUE));
         }
         return catCuadroIoMedViewList;
      } catch (Exception ex) {
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("Error inesperado al obtener todos los registros de  CatCuadroIoMed", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_INSERT);
         cCioE.addError("Ocurrio un error al obtener CatCuadroIoMed");
         logger.error("===>>>Error al obtener todos los registros de CatCuadroIoMed - CODE: {} - {}", cCioE.getExceptionCode(), ex);
         throw cCioE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCuadroIoMedException.class})
   @Override
   public Page<CatCuadroIoMedView> getCatCuadroIoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCuadroIoMedException {
      try {
         logger.info("===>>>getCatCuadroIoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatCuadroIoMedException cCioE = new CatCuadroIoMedException("No se encuentra en el sistema CatCuadroIoMed", CatCuadroIoMedException.LAYER_DAO, CatCuadroIoMedException.ACTION_VALIDATE);
//            cCioE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cCioE;
//         }

         List<CatCuadroIoMedView> catCuadroIoMedViewList = new ArrayList<>();
         Page<CatCuadroIoMed> catCuadroIoMedPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionCuadroIo"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatCuadroIoMed> spec = Specifications.where(
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
            catCuadroIoMedPage = catCuadroIoMedRepository.findAll(request);
         } else {
            catCuadroIoMedPage = catCuadroIoMedRepository.findAll(spec, request);
         }

         catCuadroIoMedPage.getContent().forEach(catCuadroIoMed -> {
            catCuadroIoMedViewList.add(catCuadroIoMedConverter.toView(catCuadroIoMed, Boolean.TRUE));
         });
         PageImpl<CatCuadroIoMedView> catCuadroIoMedViewPage = new PageImpl<CatCuadroIoMedView>(catCuadroIoMedViewList, request, catCuadroIoMedPage.getTotalElements());
         return catCuadroIoMedViewPage;
//      } catch (CatCuadroIoMedException cCioE) {
//         throw cCioE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatCuadroIoMedException pe = new CatCuadroIoMedException("Algun parametro no es correcto:", CatCuadroIoMedException.LAYER_SERVICE, CatCuadroIoMedException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("Ocurrio un error al seleccionar lista CatCuadroIoMed paginable", CatCuadroIoMedException.LAYER_SERVICE, CatCuadroIoMedException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatCuadroIoMed paginable - CODE: {}", cCioE.getExceptionCode(), ex);
         throw cCioE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
