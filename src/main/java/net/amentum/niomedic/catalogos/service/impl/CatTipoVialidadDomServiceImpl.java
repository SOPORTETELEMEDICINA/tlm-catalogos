package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatTipoVialidadDomConverter;
import net.amentum.niomedic.catalogos.exception.CatTipoVialidadDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTipoVialidadDom;
import net.amentum.niomedic.catalogos.persistence.CatTipoVialidadDomRepository;
import net.amentum.niomedic.catalogos.service.CatTipoVialidadDomService;
import net.amentum.niomedic.catalogos.views.CatTipoVialidadDomView;
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
public class CatTipoVialidadDomServiceImpl implements CatTipoVialidadDomService {
   private final Logger logger = LoggerFactory.getLogger(CatTipoVialidadDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatTipoVialidadDomRepository catTipoVialidadDomRepository;
   private CatTipoVialidadDomConverter catTipoVialidadDomConverter;

   {
      colOrderNames.put("descripcionVialidad", "descripcionVialidad");
   }

   @Autowired
   public void setCatTipoVialidadDomRepository(CatTipoVialidadDomRepository catTipoVialidadDomRepository) {
      this.catTipoVialidadDomRepository = catTipoVialidadDomRepository;
   }

   @Autowired
   public void setCatTipoVialidadDomConverter(CatTipoVialidadDomConverter catTipoVialidadDomConverter) {
      this.catTipoVialidadDomConverter = catTipoVialidadDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoVialidadDomException.class})
   @Override
   public CatTipoVialidadDomView getDetailsByIdCatTipoVialidadDom(Integer idCatTipoVialidadDom) throws CatTipoVialidadDomException {
      try {
         if (!catTipoVialidadDomRepository.exists(idCatTipoVialidadDom)) {
            logger.error("===>>>idCatTipoVialidadDom no encontrado: {}", idCatTipoVialidadDom);
            CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("No se encuentra en el sistema CatTipoVialidadDom", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_VALIDATE);
            cVialE.addError("idCatTipoVialidadDom no encontrado: " + idCatTipoVialidadDom);
            throw cVialE;
         }
         CatTipoVialidadDom catTipoVialidadDom = catTipoVialidadDomRepository.findOne(idCatTipoVialidadDom);
         return catTipoVialidadDomConverter.toView(catTipoVialidadDom, Boolean.TRUE);
      } catch (CatTipoVialidadDomException cVialE) {
         throw cVialE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("Error en la validacion", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cVialE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cVialE;
      } catch (DataIntegrityViolationException dive) {
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("No fue posible obtener  CatTipoVialidadDom", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_INSERT);
         cVialE.addError("Ocurrio un error al obtener CatTipoVialidadDom");
         logger.error("===>>>Error al obtener CatTipoVialidadDom - CODE: {} - {}", cVialE.getExceptionCode(), idCatTipoVialidadDom, dive);
         throw cVialE;
      } catch (Exception ex) {
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("Error inesperado al obtener  CatTipoVialidadDom", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_INSERT);
         cVialE.addError("Ocurrio un error al obtener CatTipoVialidadDom");
         logger.error("===>>>Error al obtener CatTipoVialidadDom - CODE: {} - {}", cVialE.getExceptionCode(), idCatTipoVialidadDom, ex);
         throw cVialE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatTipoVialidadDomException.class})
   @Override
   public List<CatTipoVialidadDomView> findAll() throws CatTipoVialidadDomException {
      try {
         List<CatTipoVialidadDom> catTipoVialidadDomList = catTipoVialidadDomRepository.findAll();
         List<CatTipoVialidadDomView> catTipoVialidadDomViewList = new ArrayList<>();
         for (CatTipoVialidadDom cn : catTipoVialidadDomList) {
            catTipoVialidadDomViewList.add(catTipoVialidadDomConverter.toView(cn, Boolean.TRUE));
         }
         return catTipoVialidadDomViewList;
      } catch (Exception ex) {
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("Error inesperado al obtener todos los registros de  CatTipoVialidadDom", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_INSERT);
         cVialE.addError("Ocurrio un error al obtener CatTipoVialidadDom");
         logger.error("===>>>Error al obtener todos los registros de CatTipoVialidadDom - CODE: {} - {}", cVialE.getExceptionCode(), ex);
         throw cVialE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoVialidadDomException.class})
   @Override
   public Page<CatTipoVialidadDomView> getCatTipoVialidadDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoVialidadDomException {
      try {
         logger.info("===>>>getCatTipoVialidadDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("No se encuentra en el sistema CatTipoVialidadDom", CatTipoVialidadDomException.LAYER_DAO, CatTipoVialidadDomException.ACTION_VALIDATE);
//            cVialE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cVialE;
//         }

         List<CatTipoVialidadDomView> catTipoVialidadDomViewList = new ArrayList<>();
         Page<CatTipoVialidadDom> catTipoVialidadDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionVialidad"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatTipoVialidadDom> spec = Specifications.where(
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
            catTipoVialidadDomPage = catTipoVialidadDomRepository.findAll(request);
         } else {
            catTipoVialidadDomPage = catTipoVialidadDomRepository.findAll(spec, request);
         }

         catTipoVialidadDomPage.getContent().forEach(catTipoVialidadDom -> {
            catTipoVialidadDomViewList.add(catTipoVialidadDomConverter.toView(catTipoVialidadDom, Boolean.TRUE));
         });
         PageImpl<CatTipoVialidadDomView> catTipoVialidadDomViewPage = new PageImpl<CatTipoVialidadDomView>(catTipoVialidadDomViewList, request, catTipoVialidadDomPage.getTotalElements());
         return catTipoVialidadDomViewPage;
//      } catch (CatTipoVialidadDomException cVialE) {
//         throw cVialE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatTipoVialidadDomException pe = new CatTipoVialidadDomException("Algun parametro no es correcto:", CatTipoVialidadDomException.LAYER_SERVICE, CatTipoVialidadDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("Ocurrio un error al seleccionar lista CatTipoVialidadDom paginable", CatTipoVialidadDomException.LAYER_SERVICE, CatTipoVialidadDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTipoVialidadDom paginable - CODE: {}", cVialE.getExceptionCode(), ex);
         throw cVialE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
