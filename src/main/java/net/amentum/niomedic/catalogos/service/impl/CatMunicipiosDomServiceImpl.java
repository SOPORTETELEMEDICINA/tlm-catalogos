package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatMunicipiosDomConverter;
import net.amentum.niomedic.catalogos.exception.CatMunicipiosDomException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatMunicipiosDom;
import net.amentum.niomedic.catalogos.persistence.CatMunicipiosDomRepository;
import net.amentum.niomedic.catalogos.service.CatMunicipiosDomService;
import net.amentum.niomedic.catalogos.views.CatMunicipiosDomView;
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
public class CatMunicipiosDomServiceImpl implements CatMunicipiosDomService {
   private final Logger logger = LoggerFactory.getLogger(CatMunicipiosDomServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatMunicipiosDomRepository catMunicipiosDomRepository;
   private CatMunicipiosDomConverter catMunicipiosDomConverter;

   {
      colOrderNames.put("descripcionMunicipios", "descripcionMunicipios");
   }

   @Autowired
   public void setCatMunicipiosDomRepository(CatMunicipiosDomRepository catMunicipiosDomRepository) {
      this.catMunicipiosDomRepository = catMunicipiosDomRepository;
   }

   @Autowired
   public void setCatMunicipiosDomConverter(CatMunicipiosDomConverter catMunicipiosDomConverter) {
      this.catMunicipiosDomConverter = catMunicipiosDomConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatMunicipiosDomException.class})
   @Override
   public CatMunicipiosDomView getDetailsByIdCatMunicipiosDom(Integer idCatMunicipiosDom) throws CatMunicipiosDomException {
      try {
         if (!catMunicipiosDomRepository.exists(idCatMunicipiosDom)) {
            logger.error("===>>>idCatMunicipiosDom no encontrado: {}", idCatMunicipiosDom);
            CatMunicipiosDomException cMunE = new CatMunicipiosDomException("No se encuentra en el sistema CatMunicipiosDom", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_VALIDATE);
            cMunE.addError("idCatMunicipiosDom no encontrado: " + idCatMunicipiosDom);
            throw cMunE;
         }
         CatMunicipiosDom catMunicipiosDom = catMunicipiosDomRepository.findOne(idCatMunicipiosDom);
         return catMunicipiosDomConverter.toView(catMunicipiosDom, Boolean.TRUE);
      } catch (CatMunicipiosDomException cMunE) {
         throw cMunE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("Error en la validacion", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cMunE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cMunE;
      } catch (DataIntegrityViolationException dive) {
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("No fue posible obtener  CatMunicipiosDom", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_INSERT);
         cMunE.addError("Ocurrio un error al obtener CatMunicipiosDom");
         logger.error("===>>>Error al obtener CatMunicipiosDom - CODE: {} - {}", cMunE.getExceptionCode(), idCatMunicipiosDom, dive);
         throw cMunE;
      } catch (Exception ex) {
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("Error inesperado al obtener  CatMunicipiosDom", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_INSERT);
         cMunE.addError("Ocurrio un error al obtener CatMunicipiosDom");
         logger.error("===>>>Error al obtener CatMunicipiosDom - CODE: {} - {}", cMunE.getExceptionCode(), idCatMunicipiosDom, ex);
         throw cMunE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatMunicipiosDomException.class})
   @Override
   public List<CatMunicipiosDomView> findAll() throws CatMunicipiosDomException {
      try {
         List<CatMunicipiosDom> catMunicipiosDomList = catMunicipiosDomRepository.findAll();
         List<CatMunicipiosDomView> catMunicipiosDomViewList = new ArrayList<>();
         for (CatMunicipiosDom cn : catMunicipiosDomList) {
            catMunicipiosDomViewList.add(catMunicipiosDomConverter.toView(cn, Boolean.TRUE));
         }
         return catMunicipiosDomViewList;
      } catch (Exception ex) {
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("Error inesperado al obtener todos los registros de  CatMunicipiosDom", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_INSERT);
         cMunE.addError("Ocurrio un error al obtener CatMunicipiosDom");
         logger.error("===>>>Error al obtener todos los registros de CatMunicipiosDom - CODE: {} - {}", cMunE.getExceptionCode(), ex);
         throw cMunE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatMunicipiosDomException.class})
   @Override
   public Page<CatMunicipiosDomView> getCatMunicipiosDomSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatMunicipiosDomException {
      try {
         logger.info("===>>>getCatMunicipiosDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatMunicipiosDomException cMunE = new CatMunicipiosDomException("No se encuentra en el sistema CatMunicipiosDom", CatMunicipiosDomException.LAYER_DAO, CatMunicipiosDomException.ACTION_VALIDATE);
//            cMunE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cMunE;
//         }

         List<CatMunicipiosDomView> catMunicipiosDomViewList = new ArrayList<>();
         Page<CatMunicipiosDom> catMunicipiosDomPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcionMunicipios"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatMunicipiosDom> spec = Specifications.where(
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
            catMunicipiosDomPage = catMunicipiosDomRepository.findAll(request);
         } else {
            catMunicipiosDomPage = catMunicipiosDomRepository.findAll(spec, request);
         }

         catMunicipiosDomPage.getContent().forEach(catMunicipiosDom -> {
            catMunicipiosDomViewList.add(catMunicipiosDomConverter.toView(catMunicipiosDom, Boolean.TRUE));
         });
         PageImpl<CatMunicipiosDomView> catMunicipiosDomViewPage = new PageImpl<CatMunicipiosDomView>(catMunicipiosDomViewList, request, catMunicipiosDomPage.getTotalElements());
         return catMunicipiosDomViewPage;
//      } catch (CatMunicipiosDomException cMunE) {
//         throw cMunE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatMunicipiosDomException pe = new CatMunicipiosDomException("Algun parametro no es correcto:", CatMunicipiosDomException.LAYER_SERVICE, CatMunicipiosDomException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("Ocurrio un error al seleccionar lista CatMunicipiosDom paginable", CatMunicipiosDomException.LAYER_SERVICE, CatMunicipiosDomException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatMunicipiosDom paginable - CODE: {}", cMunE.getExceptionCode(), ex);
         throw cMunE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
