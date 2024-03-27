package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatTipoInsumoMedConverter;
import net.amentum.niomedic.catalogos.exception.CatTipoInsumoMedException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTipoInsumoMed;
import net.amentum.niomedic.catalogos.persistence.CatTipoInsumoMedRepository;
import net.amentum.niomedic.catalogos.service.CatTipoInsumoMedService;
import net.amentum.niomedic.catalogos.views.CatTipoInsumoMedView;
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
public class CatTipoInsumoMedServiceImpl implements CatTipoInsumoMedService {
   private final Logger logger = LoggerFactory.getLogger(CatTipoInsumoMedServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatTipoInsumoMedRepository catTipoInsumoMedRepository;
   private CatTipoInsumoMedConverter catTipoInsumoMedConverter;

   {
      colOrderNames.put("descripcionTipoInsumo", "descripcionTipoInsumo");
   }

   @Autowired
   public void setCatTipoInsumoMedRepository(CatTipoInsumoMedRepository catTipoInsumoMedRepository) {
      this.catTipoInsumoMedRepository = catTipoInsumoMedRepository;
   }

   @Autowired
   public void setCatTipoInsumoMedConverter(CatTipoInsumoMedConverter catTipoInsumoMedConverter) {
      this.catTipoInsumoMedConverter = catTipoInsumoMedConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoInsumoMedException.class})
   @Override
   public CatTipoInsumoMedView getDetailsByIdCatTipoInsumoMed(Integer idCatTipoInsumoMed) throws CatTipoInsumoMedException {
      try {
         if (!catTipoInsumoMedRepository.exists(idCatTipoInsumoMed)) {
            logger.error("===>>>idCatTipoInsumoMed no encontrado: {}", idCatTipoInsumoMed);
            CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("No se encuentra en el sistema CatTipoInsumoMed", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_VALIDATE);
            cTpoIE.addError("idCatTipoInsumoMed no encontrado: " + idCatTipoInsumoMed);
            throw cTpoIE;
         }
         CatTipoInsumoMed catTipoInsumoMed = catTipoInsumoMedRepository.findOne(idCatTipoInsumoMed);
         return catTipoInsumoMedConverter.toView(catTipoInsumoMed, Boolean.TRUE);
      } catch (CatTipoInsumoMedException cTpoIE) {
         throw cTpoIE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("Error en la validacion", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cTpoIE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cTpoIE;
      } catch (DataIntegrityViolationException dive) {
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("No fue posible obtener  CatTipoInsumoMed", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_INSERT);
         cTpoIE.addError("Ocurrio un error al obtener CatTipoInsumoMed");
         logger.error("===>>>Error al obtener CatTipoInsumoMed - CODE: {} - {}", cTpoIE.getExceptionCode(), idCatTipoInsumoMed, dive);
         throw cTpoIE;
      } catch (Exception ex) {
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("Error inesperado al obtener  CatTipoInsumoMed", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_INSERT);
         cTpoIE.addError("Ocurrio un error al obtener CatTipoInsumoMed");
         logger.error("===>>>Error al obtener CatTipoInsumoMed - CODE: {} - {}", cTpoIE.getExceptionCode(), idCatTipoInsumoMed, ex);
         throw cTpoIE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatTipoInsumoMedException.class})
   @Override
   public List<CatTipoInsumoMedView> findAll() throws CatTipoInsumoMedException {
      try {
         List<CatTipoInsumoMed> catTipoInsumoMedList = catTipoInsumoMedRepository.findAll();
         List<CatTipoInsumoMedView> catTipoInsumoMedViewList = new ArrayList<>();
         for (CatTipoInsumoMed cn : catTipoInsumoMedList) {
            catTipoInsumoMedViewList.add(catTipoInsumoMedConverter.toView(cn, Boolean.TRUE));
         }
         return catTipoInsumoMedViewList;
      } catch (Exception ex) {
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("Error inesperado al obtener todos los registros de  CatTipoInsumoMed", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_INSERT);
         cTpoIE.addError("Ocurrio un error al obtener CatTipoInsumoMed");
         logger.error("===>>>Error al obtener todos los registros de CatTipoInsumoMed - CODE: {} - {}", cTpoIE.getExceptionCode(), ex);
         throw cTpoIE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoInsumoMedException.class})
   @Override
   public Page<CatTipoInsumoMedView> getCatTipoInsumoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoInsumoMedException {
      try {
         logger.info("===>>>getCatTipoInsumoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("No se encuentra en el sistema CatTipoInsumoMed", CatTipoInsumoMedException.LAYER_DAO, CatTipoInsumoMedException.ACTION_VALIDATE);
//            cTpoIE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cTpoIE;
//         }

         List<CatTipoInsumoMedView> catTipoInsumoMedViewList = new ArrayList<>();
         Page<CatTipoInsumoMed> catTipoInsumoMedPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionTipoInsumo"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatTipoInsumoMed> spec = Specifications.where(
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
            catTipoInsumoMedPage = catTipoInsumoMedRepository.findAll(request);
         } else {
            catTipoInsumoMedPage = catTipoInsumoMedRepository.findAll(spec, request);
         }

         catTipoInsumoMedPage.getContent().forEach(catTipoInsumoMed -> {
            catTipoInsumoMedViewList.add(catTipoInsumoMedConverter.toView(catTipoInsumoMed, Boolean.TRUE));
         });
         PageImpl<CatTipoInsumoMedView> catTipoInsumoMedViewPage = new PageImpl<CatTipoInsumoMedView>(catTipoInsumoMedViewList, request, catTipoInsumoMedPage.getTotalElements());
         return catTipoInsumoMedViewPage;
//      } catch (CatTipoInsumoMedException cTpoIE) {
//         throw cTpoIE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatTipoInsumoMedException pe = new CatTipoInsumoMedException("Algun parametro no es correcto:", CatTipoInsumoMedException.LAYER_SERVICE, CatTipoInsumoMedException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("Ocurrio un error al seleccionar lista CatTipoInsumoMed paginable", CatTipoInsumoMedException.LAYER_SERVICE, CatTipoInsumoMedException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTipoInsumoMed paginable - CODE: {}", cTpoIE.getExceptionCode(), ex);
         throw cTpoIE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
