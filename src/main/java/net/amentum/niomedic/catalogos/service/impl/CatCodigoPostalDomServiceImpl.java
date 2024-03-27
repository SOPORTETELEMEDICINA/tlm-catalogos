package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatCodigoPostalDomConverter;
import net.amentum.niomedic.catalogos.exception.CatCodigoPostalDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatCodigoPostalDom;
import net.amentum.niomedic.catalogos.persistence.CatCodigoPostalDomRepository;
import net.amentum.niomedic.catalogos.service.CatCodigoPostalDomService;
import net.amentum.niomedic.catalogos.views.CatCodigoPostalDomView;
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
public class CatCodigoPostalDomServiceImpl implements CatCodigoPostalDomService {
   private final Logger logger = LoggerFactory.getLogger(CatCodigoPostalDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatCodigoPostalDomRepository catCodigoPostalDomRepository;
   private CatCodigoPostalDomConverter catCodigoPostalDomConverter;

   {
      colOrderNames.put("codigoPostal", "codigoPostal");
      colOrderNames.put("asentamiento", "asentamiento");
   }

   @Autowired
   public void setCatCodigoPostalDomRepository(CatCodigoPostalDomRepository catCodigoPostalDomRepository) {
      this.catCodigoPostalDomRepository = catCodigoPostalDomRepository;
   }

   @Autowired
   public void setCatCodigoPostalDomConverter(CatCodigoPostalDomConverter catCodigoPostalDomConverter) {
      this.catCodigoPostalDomConverter = catCodigoPostalDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCodigoPostalDomException.class})
   @Override
   public CatCodigoPostalDomView getDetailsByIdCatCodigoPostalDom(Long idCatCodigoPostalDom) throws CatCodigoPostalDomException {
      try {
         if (!catCodigoPostalDomRepository.exists(idCatCodigoPostalDom)) {
            logger.error("===>>>idCatCodigoPostalDom no encontrado: {}", idCatCodigoPostalDom);
            CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("No se encuentra en el sistema CatCodigoPostalDom", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_VALIDATE);
            cCPE.addError("idCatCodigoPostalDom no encontrado: " + idCatCodigoPostalDom);
            throw cCPE;
         }
         CatCodigoPostalDom catCodigoPostalDom = catCodigoPostalDomRepository.findOne(idCatCodigoPostalDom);
         return catCodigoPostalDomConverter.toView(catCodigoPostalDom, Boolean.TRUE);
      } catch (CatCodigoPostalDomException cCPE) {
         throw cCPE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("Error en la validacion", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cCPE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cCPE;
      } catch (DataIntegrityViolationException dive) {
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("No fue posible obtener  CatCodigoPostalDom", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_INSERT);
         cCPE.addError("Ocurrio un error al obtener CatCodigoPostalDom");
         logger.error("===>>>Error al obtener CatCodigoPostalDom - CODE: {} - {}", cCPE.getExceptionCode(), idCatCodigoPostalDom, dive);
         throw cCPE;
      } catch (Exception ex) {
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("Error inesperado al obtener  CatCodigoPostalDom", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_INSERT);
         cCPE.addError("Ocurrio un error al obtener CatCodigoPostalDom");
         logger.error("===>>>Error al obtener CatCodigoPostalDom - CODE: {} - {}", cCPE.getExceptionCode(), idCatCodigoPostalDom, ex);
         throw cCPE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatCodigoPostalDomException.class})
   @Override
   public List<CatCodigoPostalDomView> findAll() throws CatCodigoPostalDomException {
      try {
         List<CatCodigoPostalDom> catCodigoPostalDomList = catCodigoPostalDomRepository.findAll();
         List<CatCodigoPostalDomView> catCodigoPostalDomViewList = new ArrayList<>();
         for (CatCodigoPostalDom cn : catCodigoPostalDomList) {
            catCodigoPostalDomViewList.add(catCodigoPostalDomConverter.toView(cn, Boolean.TRUE));
         }
         return catCodigoPostalDomViewList;
      } catch (Exception ex) {
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("Error inesperado al obtener todos los registros de  CatCodigoPostalDom", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_INSERT);
         cCPE.addError("Ocurrio un error al obtener CatCodigoPostalDom");
         logger.error("===>>>Error al obtener todos los registros de CatCodigoPostalDom - CODE: {} - {}", cCPE.getExceptionCode(), ex);
         throw cCPE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCodigoPostalDomException.class})
   @Override
   public Page<CatCodigoPostalDomView> getCatCodigoPostalDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCodigoPostalDomException {
      try {
         logger.info("===>>>getCatCodigoPostalDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("No se encuentra en el sistema CatCodigoPostalDom", CatCodigoPostalDomException.LAYER_DAO, CatCodigoPostalDomException.ACTION_VALIDATE);
//            cCPE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cCPE;
//         }

         List<CatCodigoPostalDomView> catCodigoPostalDomViewList = new ArrayList<>();
         Page<CatCodigoPostalDom> catCodigoPostalDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("codigoPostal"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatCodigoPostalDom> spec = Specifications.where(
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
            catCodigoPostalDomPage = catCodigoPostalDomRepository.findAll(request);
         } else {
            catCodigoPostalDomPage = catCodigoPostalDomRepository.findAll(spec, request);
         }

         catCodigoPostalDomPage.getContent().forEach(catCodigoPostalDom -> {
            catCodigoPostalDomViewList.add(catCodigoPostalDomConverter.toView(catCodigoPostalDom, Boolean.TRUE));
         });
         PageImpl<CatCodigoPostalDomView> catCodigoPostalDomViewPage = new PageImpl<CatCodigoPostalDomView>(catCodigoPostalDomViewList, request, catCodigoPostalDomPage.getTotalElements());
         return catCodigoPostalDomViewPage;
//      } catch (CatCodigoPostalDomException cCPE) {
//         throw cCPE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatCodigoPostalDomException pe = new CatCodigoPostalDomException("Algun parametro no es correcto:", CatCodigoPostalDomException.LAYER_SERVICE, CatCodigoPostalDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("Ocurrio un error al seleccionar lista CatCodigoPostalDom paginable", CatCodigoPostalDomException.LAYER_SERVICE, CatCodigoPostalDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatCodigoPostalDom paginable - CODE: {}", cCPE.getExceptionCode(), ex);
         throw cCPE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
