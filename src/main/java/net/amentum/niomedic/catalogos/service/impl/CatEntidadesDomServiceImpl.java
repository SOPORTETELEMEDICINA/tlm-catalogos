package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatEntidadesDomConverter;
import net.amentum.niomedic.catalogos.exception.CatEntidadesDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatEntidadesDom;
import net.amentum.niomedic.catalogos.persistence.CatEntidadesDomRepository;
import net.amentum.niomedic.catalogos.service.CatEntidadesDomService;
import net.amentum.niomedic.catalogos.views.CatEntidadesDomView;
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
public class CatEntidadesDomServiceImpl implements CatEntidadesDomService {
   private final Logger logger = LoggerFactory.getLogger(CatEntidadesDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatEntidadesDomRepository catEntidadesDomRepository;
   private CatEntidadesDomConverter catEntidadesDomConverter;

   {
      colOrderNames.put("descripcionEntidades", "descripcionEntidades");
   }

   @Autowired
   public void setCatEntidadesDomRepository(CatEntidadesDomRepository catEntidadesDomRepository) {
      this.catEntidadesDomRepository = catEntidadesDomRepository;
   }

   @Autowired
   public void setCatEntidadesDomConverter(CatEntidadesDomConverter catEntidadesDomConverter) {
      this.catEntidadesDomConverter = catEntidadesDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatEntidadesDomException.class})
   @Override
   public CatEntidadesDomView getDetailsByIdCatEntidadesDom(Integer idCatEntidadesDom) throws CatEntidadesDomException {
      try {
         if (!catEntidadesDomRepository.exists(idCatEntidadesDom)) {
            logger.error("===>>>idCatEntidadesDom no encontrado: {}", idCatEntidadesDom);
            CatEntidadesDomException cEntiE = new CatEntidadesDomException("No se encuentra en el sistema CatEntidadesDom", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_VALIDATE);
            cEntiE.addError("idCatEntidadesDom no encontrado: " + idCatEntidadesDom);
            throw cEntiE;
         }
         CatEntidadesDom catEntidadesDom = catEntidadesDomRepository.findOne(idCatEntidadesDom);
         return catEntidadesDomConverter.toView(catEntidadesDom, Boolean.TRUE);
      } catch (CatEntidadesDomException cEntiE) {
         throw cEntiE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("Error en la validacion", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cEntiE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cEntiE;
      } catch (DataIntegrityViolationException dive) {
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("No fue posible obtener  CatEntidadesDom", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_INSERT);
         cEntiE.addError("Ocurrio un error al obtener CatEntidadesDom");
         logger.error("===>>>Error al obtener CatEntidadesDom - CODE: {} - {}", cEntiE.getExceptionCode(), idCatEntidadesDom, dive);
         throw cEntiE;
      } catch (Exception ex) {
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("Error inesperado al obtener  CatEntidadesDom", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_INSERT);
         cEntiE.addError("Ocurrio un error al obtener CatEntidadesDom");
         logger.error("===>>>Error al obtener CatEntidadesDom - CODE: {} - {}", cEntiE.getExceptionCode(), idCatEntidadesDom, ex);
         throw cEntiE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatEntidadesDomException.class})
   @Override
   public List<CatEntidadesDomView> findAll() throws CatEntidadesDomException {
      try {
         List<CatEntidadesDom> catEntidadesDomList = catEntidadesDomRepository.findAll();
         List<CatEntidadesDomView> catEntidadesDomViewList = new ArrayList<>();
         for (CatEntidadesDom cn : catEntidadesDomList) {
            catEntidadesDomViewList.add(catEntidadesDomConverter.toView(cn, Boolean.TRUE));
         }
         return catEntidadesDomViewList;
      } catch (Exception ex) {
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("Error inesperado al obtener todos los registros de  CatEntidadesDom", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_INSERT);
         cEntiE.addError("Ocurrio un error al obtener CatEntidadesDom");
         logger.error("===>>>Error al obtener todos los registros de CatEntidadesDom - CODE: {} - {}", cEntiE.getExceptionCode(), ex);
         throw cEntiE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatEntidadesDomException.class})
   @Override
   public Page<CatEntidadesDomView> getCatEntidadesDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatEntidadesDomException {
      try {
         logger.info("===>>>getCatEntidadesDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatEntidadesDomException cEntiE = new CatEntidadesDomException("No se encuentra en el sistema CatEntidadesDom", CatEntidadesDomException.LAYER_DAO, CatEntidadesDomException.ACTION_VALIDATE);
//            cEntiE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cEntiE;
//         }

         List<CatEntidadesDomView> catEntidadesDomViewList = new ArrayList<>();
         Page<CatEntidadesDom> catEntidadesDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionEntidades"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatEntidadesDom> spec = Specifications.where(
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
            catEntidadesDomPage = catEntidadesDomRepository.findAll(request);
         } else {
            catEntidadesDomPage = catEntidadesDomRepository.findAll(spec, request);
         }

         catEntidadesDomPage.getContent().forEach(catEntidadesDom -> {
            catEntidadesDomViewList.add(catEntidadesDomConverter.toView(catEntidadesDom, Boolean.TRUE));
         });
         PageImpl<CatEntidadesDomView> catEntidadesDomViewPage = new PageImpl<CatEntidadesDomView>(catEntidadesDomViewList, request, catEntidadesDomPage.getTotalElements());
         return catEntidadesDomViewPage;
//      } catch (CatEntidadesDomException cEntiE) {
//         throw cEntiE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatEntidadesDomException pe = new CatEntidadesDomException("Algun parametro no es correcto:", CatEntidadesDomException.LAYER_SERVICE, CatEntidadesDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("Ocurrio un error al seleccionar lista CatEntidadesDom paginable", CatEntidadesDomException.LAYER_SERVICE, CatEntidadesDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatEntidadesDom paginable - CODE: {}", cEntiE.getExceptionCode(), ex);
         throw cEntiE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
