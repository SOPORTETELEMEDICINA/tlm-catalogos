package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatTipoAsentamientoDomConverter;
import net.amentum.niomedic.catalogos.exception.CatTipoAsentamientoDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTipoAsentamientoDom;
import net.amentum.niomedic.catalogos.persistence.CatTipoAsentamientoDomRepository;
import net.amentum.niomedic.catalogos.service.CatTipoAsentamientoDomService;
import net.amentum.niomedic.catalogos.views.CatTipoAsentamientoDomView;
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
public class CatTipoAsentamientoDomServiceImpl implements CatTipoAsentamientoDomService {
   private final Logger logger = LoggerFactory.getLogger(CatTipoAsentamientoDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatTipoAsentamientoDomRepository catTipoAsentamientoDomRepository;
   private CatTipoAsentamientoDomConverter catTipoAsentamientoDomConverter;

   {
      colOrderNames.put("descripcionAsentamiento", "descripcionAsentamiento");
      colOrderNames.put("abreviatura", "abreviatura");
   }

   @Autowired
   public void setCatTipoAsentamientoDomRepository(CatTipoAsentamientoDomRepository catTipoAsentamientoDomRepository) {
      this.catTipoAsentamientoDomRepository = catTipoAsentamientoDomRepository;
   }

   @Autowired
   public void setCatTipoAsentamientoDomConverter(CatTipoAsentamientoDomConverter catTipoAsentamientoDomConverter) {
      this.catTipoAsentamientoDomConverter = catTipoAsentamientoDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoAsentamientoDomException.class})
   @Override
   public CatTipoAsentamientoDomView getDetailsByIdCatTipoAsentamientoDom(Integer idCatTipoAsentamientoDom) throws CatTipoAsentamientoDomException {
      try {
         if (!catTipoAsentamientoDomRepository.exists(idCatTipoAsentamientoDom)) {
            logger.error("===>>>idCatTipoAsentamientoDom no encontrado: {}", idCatTipoAsentamientoDom);
            CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("No se encuentra en el sistema CatTipoAsentamientoDom", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_VALIDATE);
            cTAseE.addError("idCatTipoAsentamientoDom no encontrado: " + idCatTipoAsentamientoDom);
            throw cTAseE;
         }
         CatTipoAsentamientoDom catTipoAsentamientoDom = catTipoAsentamientoDomRepository.findOne(idCatTipoAsentamientoDom);
         return catTipoAsentamientoDomConverter.toView(catTipoAsentamientoDom, Boolean.TRUE);
      } catch (CatTipoAsentamientoDomException cTAseE) {
         throw cTAseE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("Error en la validacion", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cTAseE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cTAseE;
      } catch (DataIntegrityViolationException dive) {
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("No fue posible obtener  CatTipoAsentamientoDom", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_INSERT);
         cTAseE.addError("Ocurrio un error al obtener CatTipoAsentamientoDom");
         logger.error("===>>>Error al obtener CatTipoAsentamientoDom - CODE: {} - {}", cTAseE.getExceptionCode(), idCatTipoAsentamientoDom, dive);
         throw cTAseE;
      } catch (Exception ex) {
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("Error inesperado al obtener  CatTipoAsentamientoDom", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_INSERT);
         cTAseE.addError("Ocurrio un error al obtener CatTipoAsentamientoDom");
         logger.error("===>>>Error al obtener CatTipoAsentamientoDom - CODE: {} - {}", cTAseE.getExceptionCode(), idCatTipoAsentamientoDom, ex);
         throw cTAseE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatTipoAsentamientoDomException.class})
   @Override
   public List<CatTipoAsentamientoDomView> findAll() throws CatTipoAsentamientoDomException {
      try {
         List<CatTipoAsentamientoDom> catTipoAsentamientoDomList = catTipoAsentamientoDomRepository.findAll();
         List<CatTipoAsentamientoDomView> catTipoAsentamientoDomViewList = new ArrayList<>();
         for (CatTipoAsentamientoDom cn : catTipoAsentamientoDomList) {
            catTipoAsentamientoDomViewList.add(catTipoAsentamientoDomConverter.toView(cn, Boolean.TRUE));
         }
         return catTipoAsentamientoDomViewList;
      } catch (Exception ex) {
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("Error inesperado al obtener todos los registros de  CatTipoAsentamientoDom", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_INSERT);
         cTAseE.addError("Ocurrio un error al obtener CatTipoAsentamientoDom");
         logger.error("===>>>Error al obtener todos los registros de CatTipoAsentamientoDom - CODE: {} - {}", cTAseE.getExceptionCode(), ex);
         throw cTAseE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTipoAsentamientoDomException.class})
   @Override
   public Page<CatTipoAsentamientoDomView> getCatTipoAsentamientoDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoAsentamientoDomException {
      try {
         logger.info("===>>>getCatTipoAsentamientoDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("No se encuentra en el sistema CatTipoAsentamientoDom", CatTipoAsentamientoDomException.LAYER_DAO, CatTipoAsentamientoDomException.ACTION_VALIDATE);
//            cTAseE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cTAseE;
//         }

         List<CatTipoAsentamientoDomView> catTipoAsentamientoDomViewList = new ArrayList<>();
         Page<CatTipoAsentamientoDom> catTipoAsentamientoDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionAsentamiento"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatTipoAsentamientoDom> spec = Specifications.where(
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
            catTipoAsentamientoDomPage = catTipoAsentamientoDomRepository.findAll(request);
         } else {
            catTipoAsentamientoDomPage = catTipoAsentamientoDomRepository.findAll(spec, request);
         }

         catTipoAsentamientoDomPage.getContent().forEach(catTipoAsentamientoDom -> {
            catTipoAsentamientoDomViewList.add(catTipoAsentamientoDomConverter.toView(catTipoAsentamientoDom, Boolean.TRUE));
         });
         PageImpl<CatTipoAsentamientoDomView> catTipoAsentamientoDomViewPage = new PageImpl<CatTipoAsentamientoDomView>(catTipoAsentamientoDomViewList, request, catTipoAsentamientoDomPage.getTotalElements());
         return catTipoAsentamientoDomViewPage;
//      } catch (CatTipoAsentamientoDomException cTAseE) {
//         throw cTAseE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatTipoAsentamientoDomException pe = new CatTipoAsentamientoDomException("Algun parametro no es correcto:", CatTipoAsentamientoDomException.LAYER_SERVICE, CatTipoAsentamientoDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("Ocurrio un error al seleccionar lista CatTipoAsentamientoDom paginable", CatTipoAsentamientoDomException.LAYER_SERVICE, CatTipoAsentamientoDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTipoAsentamientoDom paginable - CODE: {}", cTAseE.getExceptionCode(), ex);
         throw cTAseE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
