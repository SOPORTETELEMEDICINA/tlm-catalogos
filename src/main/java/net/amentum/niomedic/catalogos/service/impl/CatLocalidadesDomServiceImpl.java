package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatLocalidadesDomConverter;
import net.amentum.niomedic.catalogos.exception.CatLocalidadesDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatLocalidadesDom;
import net.amentum.niomedic.catalogos.persistence.CatLocalidadesDomRepository;
import net.amentum.niomedic.catalogos.service.CatLocalidadesDomService;
import net.amentum.niomedic.catalogos.views.CatLocalidadesDomView;
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
public class CatLocalidadesDomServiceImpl implements CatLocalidadesDomService {
   private final Logger logger = LoggerFactory.getLogger(CatLocalidadesDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatLocalidadesDomRepository catLocalidadesDomRepository;
   private CatLocalidadesDomConverter catLocalidadesDomConverter;

   {
      colOrderNames.put("descripcionLocalidades", "descripcionLocalidades");
   }

   @Autowired
   public void setCatLocalidadesDomRepository(CatLocalidadesDomRepository catLocalidadesDomRepository) {
      this.catLocalidadesDomRepository = catLocalidadesDomRepository;
   }

   @Autowired
   public void setCatLocalidadesDomConverter(CatLocalidadesDomConverter catLocalidadesDomConverter) {
      this.catLocalidadesDomConverter = catLocalidadesDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatLocalidadesDomException.class})
   @Override
   public CatLocalidadesDomView getDetailsByIdCatLocalidadesDom(Integer idCatLocalidadesDom) throws CatLocalidadesDomException {
      try {
         if (!catLocalidadesDomRepository.exists(idCatLocalidadesDom)) {
            logger.error("===>>>idCatLocalidadesDom no encontrado: {}", idCatLocalidadesDom);
            CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("No se encuentra en el sistema CatLocalidadesDom", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_VALIDATE);
            cLocalE.addError("idCatLocalidadesDom no encontrado: " + idCatLocalidadesDom);
            throw cLocalE;
         }
         CatLocalidadesDom catLocalidadesDom = catLocalidadesDomRepository.findOne(idCatLocalidadesDom);
         return catLocalidadesDomConverter.toView(catLocalidadesDom, Boolean.TRUE);
      } catch (CatLocalidadesDomException cLocalE) {
         throw cLocalE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("Error en la validacion", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cLocalE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cLocalE;
      } catch (DataIntegrityViolationException dive) {
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("No fue posible obtener  CatLocalidadesDom", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_INSERT);
         cLocalE.addError("Ocurrio un error al obtener CatLocalidadesDom");
         logger.error("===>>>Error al obtener CatLocalidadesDom - CODE: {} - {}", cLocalE.getExceptionCode(), idCatLocalidadesDom, dive);
         throw cLocalE;
      } catch (Exception ex) {
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("Error inesperado al obtener  CatLocalidadesDom", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_INSERT);
         cLocalE.addError("Ocurrio un error al obtener CatLocalidadesDom");
         logger.error("===>>>Error al obtener CatLocalidadesDom - CODE: {} - {}", cLocalE.getExceptionCode(), idCatLocalidadesDom, ex);
         throw cLocalE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatLocalidadesDomException.class})
   @Override
   public List<CatLocalidadesDomView> findAll() throws CatLocalidadesDomException {
      try {
         List<CatLocalidadesDom> catLocalidadesDomList = catLocalidadesDomRepository.findAll();
         List<CatLocalidadesDomView> catLocalidadesDomViewList = new ArrayList<>();
         for (CatLocalidadesDom cn : catLocalidadesDomList) {
            catLocalidadesDomViewList.add(catLocalidadesDomConverter.toView(cn, Boolean.TRUE));
         }
         return catLocalidadesDomViewList;
      } catch (Exception ex) {
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("Error inesperado al obtener todos los registros de  CatLocalidadesDom", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_INSERT);
         cLocalE.addError("Ocurrio un error al obtener CatLocalidadesDom");
         logger.error("===>>>Error al obtener todos los registros de CatLocalidadesDom - CODE: {} - {}", cLocalE.getExceptionCode(), ex);
         throw cLocalE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatLocalidadesDomException.class})
   @Override
   public Page<CatLocalidadesDomView> getCatLocalidadesDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatLocalidadesDomException {
      try {
         logger.info("===>>>getCatLocalidadesDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("No se encuentra en el sistema CatLocalidadesDom", CatLocalidadesDomException.LAYER_DAO, CatLocalidadesDomException.ACTION_VALIDATE);
//            cLocalE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cLocalE;
//         }

         List<CatLocalidadesDomView> catLocalidadesDomViewList = new ArrayList<>();
         Page<CatLocalidadesDom> catLocalidadesDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionLocalidades"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatLocalidadesDom> spec = Specifications.where(
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
            catLocalidadesDomPage = catLocalidadesDomRepository.findAll(request);
         } else {
            catLocalidadesDomPage = catLocalidadesDomRepository.findAll(spec, request);
         }

         catLocalidadesDomPage.getContent().forEach(catLocalidadesDom -> {
            catLocalidadesDomViewList.add(catLocalidadesDomConverter.toView(catLocalidadesDom, Boolean.TRUE));
         });
         PageImpl<CatLocalidadesDomView> catLocalidadesDomViewPage = new PageImpl<CatLocalidadesDomView>(catLocalidadesDomViewList, request, catLocalidadesDomPage.getTotalElements());
         return catLocalidadesDomViewPage;
//      } catch (CatLocalidadesDomException cLocalE) {
//         throw cLocalE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatLocalidadesDomException pe = new CatLocalidadesDomException("Algun parametro no es correcto:", CatLocalidadesDomException.LAYER_SERVICE, CatLocalidadesDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("Ocurrio un error al seleccionar lista CatLocalidadesDom paginable", CatLocalidadesDomException.LAYER_SERVICE, CatLocalidadesDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatLocalidadesDom paginable - CODE: {}", cLocalE.getExceptionCode(), ex);
         throw cLocalE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
