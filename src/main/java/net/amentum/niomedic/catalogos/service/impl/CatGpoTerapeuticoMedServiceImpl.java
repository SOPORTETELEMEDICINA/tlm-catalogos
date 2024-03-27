package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatGpoTerapeuticoMedConverter;
import net.amentum.niomedic.catalogos.exception.CatGpoTerapeuticoMedException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatGpoTerapeuticoMed;
import net.amentum.niomedic.catalogos.persistence.CatGpoTerapeuticoMedRepository;
import net.amentum.niomedic.catalogos.service.CatGpoTerapeuticoMedService;
import net.amentum.niomedic.catalogos.views.CatGpoTerapeuticoMedView;
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
public class CatGpoTerapeuticoMedServiceImpl implements CatGpoTerapeuticoMedService {
   private final Logger logger = LoggerFactory.getLogger(CatGpoTerapeuticoMedServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatGpoTerapeuticoMedRepository catGpoTerapeuticoMedRepository;
   private CatGpoTerapeuticoMedConverter catGpoTerapeuticoMedConverter;

   {
      colOrderNames.put("descripcionGpoTerapeutico", "descripcionGpoTerapeutico");
   }

   @Autowired
   public void setCatGpoTerapeuticoMedRepository(CatGpoTerapeuticoMedRepository catGpoTerapeuticoMedRepository) {
      this.catGpoTerapeuticoMedRepository = catGpoTerapeuticoMedRepository;
   }

   @Autowired
   public void setCatGpoTerapeuticoMedConverter(CatGpoTerapeuticoMedConverter catGpoTerapeuticoMedConverter) {
      this.catGpoTerapeuticoMedConverter = catGpoTerapeuticoMedConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatGpoTerapeuticoMedException.class})
   @Override
   public CatGpoTerapeuticoMedView getDetailsByIdCatGpoTerapeuticoMed(Integer idCatGpoTerapeuticoMed) throws CatGpoTerapeuticoMedException {
      try {
         if (!catGpoTerapeuticoMedRepository.exists(idCatGpoTerapeuticoMed)) {
            logger.error("===>>>idCatGpoTerapeuticoMed no encontrado: {}", idCatGpoTerapeuticoMed);
            CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("No se encuentra en el sistema CatGpoTerapeuticoMed", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_VALIDATE);
            cGTeE.addError("idCatGpoTerapeuticoMed no encontrado: " + idCatGpoTerapeuticoMed);
            throw cGTeE;
         }
         CatGpoTerapeuticoMed catGpoTerapeuticoMed = catGpoTerapeuticoMedRepository.findOne(idCatGpoTerapeuticoMed);
         return catGpoTerapeuticoMedConverter.toView(catGpoTerapeuticoMed, Boolean.TRUE);
      } catch (CatGpoTerapeuticoMedException cGTeE) {
         throw cGTeE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("Error en la validacion", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cGTeE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cGTeE;
      } catch (DataIntegrityViolationException dive) {
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("No fue posible obtener  CatGpoTerapeuticoMed", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_INSERT);
         cGTeE.addError("Ocurrio un error al obtener CatGpoTerapeuticoMed");
         logger.error("===>>>Error al obtener CatGpoTerapeuticoMed - CODE: {} - {}", cGTeE.getExceptionCode(), idCatGpoTerapeuticoMed, dive);
         throw cGTeE;
      } catch (Exception ex) {
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("Error inesperado al obtener  CatGpoTerapeuticoMed", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_INSERT);
         cGTeE.addError("Ocurrio un error al obtener CatGpoTerapeuticoMed");
         logger.error("===>>>Error al obtener CatGpoTerapeuticoMed - CODE: {} - {}", cGTeE.getExceptionCode(), idCatGpoTerapeuticoMed, ex);
         throw cGTeE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatGpoTerapeuticoMedException.class})
   @Override
   public List<CatGpoTerapeuticoMedView> findAll() throws CatGpoTerapeuticoMedException {
      try {
         List<CatGpoTerapeuticoMed> catGpoTerapeuticoMedList = catGpoTerapeuticoMedRepository.findAll();
         List<CatGpoTerapeuticoMedView> catGpoTerapeuticoMedViewList = new ArrayList<>();
         for (CatGpoTerapeuticoMed cn : catGpoTerapeuticoMedList) {
            catGpoTerapeuticoMedViewList.add(catGpoTerapeuticoMedConverter.toView(cn, Boolean.TRUE));
         }
         return catGpoTerapeuticoMedViewList;
      } catch (Exception ex) {
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("Error inesperado al obtener todos los registros de  CatGpoTerapeuticoMed", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_INSERT);
         cGTeE.addError("Ocurrio un error al obtener CatGpoTerapeuticoMed");
         logger.error("===>>>Error al obtener todos los registros de CatGpoTerapeuticoMed - CODE: {} - {}", cGTeE.getExceptionCode(), ex);
         throw cGTeE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatGpoTerapeuticoMedException.class})
   @Override
   public Page<CatGpoTerapeuticoMedView> getCatGpoTerapeuticoMedSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatGpoTerapeuticoMedException {
      try {
         logger.info("===>>>getCatGpoTerapeuticoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("No se encuentra en el sistema CatGpoTerapeuticoMed", CatGpoTerapeuticoMedException.LAYER_DAO, CatGpoTerapeuticoMedException.ACTION_VALIDATE);
//            cGTeE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cGTeE;
//         }

         List<CatGpoTerapeuticoMedView> catGpoTerapeuticoMedViewList = new ArrayList<>();
         Page<CatGpoTerapeuticoMed> catGpoTerapeuticoMedPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionGpoTerapeutico"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatGpoTerapeuticoMed> spec = Specifications.where(
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
            catGpoTerapeuticoMedPage = catGpoTerapeuticoMedRepository.findAll(request);
         } else {
            catGpoTerapeuticoMedPage = catGpoTerapeuticoMedRepository.findAll(spec, request);
         }

         catGpoTerapeuticoMedPage.getContent().forEach(catGpoTerapeuticoMed -> {
            catGpoTerapeuticoMedViewList.add(catGpoTerapeuticoMedConverter.toView(catGpoTerapeuticoMed, Boolean.TRUE));
         });
         PageImpl<CatGpoTerapeuticoMedView> catGpoTerapeuticoMedViewPage = new PageImpl<CatGpoTerapeuticoMedView>(catGpoTerapeuticoMedViewList, request, catGpoTerapeuticoMedPage.getTotalElements());
         return catGpoTerapeuticoMedViewPage;
//      } catch (CatGpoTerapeuticoMedException cGTeE) {
//         throw cGTeE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatGpoTerapeuticoMedException pe = new CatGpoTerapeuticoMedException("Algun parametro no es correcto:", CatGpoTerapeuticoMedException.LAYER_SERVICE, CatGpoTerapeuticoMedException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("Ocurrio un error al seleccionar lista CatGpoTerapeuticoMed paginable", CatGpoTerapeuticoMedException.LAYER_SERVICE, CatGpoTerapeuticoMedException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatGpoTerapeuticoMed paginable - CODE: {}", cGTeE.getExceptionCode(), ex);
         throw cGTeE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
