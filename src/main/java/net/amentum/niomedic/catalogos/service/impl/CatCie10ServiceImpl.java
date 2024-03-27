package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatCie10Converter;
import net.amentum.niomedic.catalogos.exception.CatCie10Exception;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatCie10;
import net.amentum.niomedic.catalogos.persistence.CatCie10Repository;
import net.amentum.niomedic.catalogos.service.CatCie10Service;
import net.amentum.niomedic.catalogos.views.CatCie10View;
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
public class CatCie10ServiceImpl implements CatCie10Service {
   private final Logger logger = LoggerFactory.getLogger(CatCie10ServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatCie10Repository catCie10Repository;
   private CatCie10Converter catCie10Converter;

   {
      colOrderNames.put("catalogKey", "catalogKey");
      colOrderNames.put("epiClaveDesc", "epiClaveDesc");
      colOrderNames.put("nombre", "nombre");
   }

   @Autowired
   public void setCatCie10Repository(CatCie10Repository catCie10Repository) {
      this.catCie10Repository = catCie10Repository;
   }

   @Autowired
   public void setCatCie10Converter(CatCie10Converter catCie10Converter) {
      this.catCie10Converter = catCie10Converter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCie10Exception.class})
   @Override
   public CatCie10View getDetailsByIdCatCie10(Integer idCatCie10) throws CatCie10Exception {
      try {
         if (!catCie10Repository.exists(idCatCie10)) {
            logger.error("===>>>idCatCie10 no encontrado: {}", idCatCie10);
            CatCie10Exception cCie10E = new CatCie10Exception("No se encuentra en el sistema CatCie10", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_VALIDATE);
            cCie10E.addError("idCatCie10 no encontrado: " + idCatCie10);
            throw cCie10E;
         }
         CatCie10 catCie10 = catCie10Repository.findOne(idCatCie10);
         return catCie10Converter.toView(catCie10, Boolean.TRUE);
      } catch (CatCie10Exception cCie10E) {
         throw cCie10E;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatCie10Exception cCie10E = new CatCie10Exception("Error en la validacion", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cCie10E.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cCie10E;
      } catch (DataIntegrityViolationException dive) {
         CatCie10Exception cCie10E = new CatCie10Exception("No fue posible obtener  CatCie10", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_INSERT);
         cCie10E.addError("Ocurrio un error al obtener CatCie10");
         logger.error("===>>>Error al obtener CatCie10 - CODE: {} - {}", cCie10E.getExceptionCode(), idCatCie10, dive);
         throw cCie10E;
      } catch (Exception ex) {
         CatCie10Exception cCie10E = new CatCie10Exception("Error inesperado al obtener  CatCie10", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_INSERT);
         cCie10E.addError("Ocurrio un error al obtener CatCie10");
         logger.error("===>>>Error al obtener CatCie10 - CODE: {} - {}", cCie10E.getExceptionCode(), idCatCie10, ex);
         throw cCie10E;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatCie10Exception.class})
   @Override
   public List<CatCie10View> findAll() throws CatCie10Exception {
      try {
         List<CatCie10> catCie10List = catCie10Repository.findAll();
         List<CatCie10View> catCie10ViewList = new ArrayList<>();
         for (CatCie10 cn : catCie10List) {
            catCie10ViewList.add(catCie10Converter.toView(cn, Boolean.TRUE));
         }
         return catCie10ViewList;
      } catch (Exception ex) {
         CatCie10Exception cCie10E = new CatCie10Exception("Error inesperado al obtener todos los registros de  CatCie10", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_INSERT);
         cCie10E.addError("Ocurrio un error al obtener CatCie10");
         logger.error("===>>>Error al obtener todos los registros de CatCie10 - CODE: {} - {}", cCie10E.getExceptionCode(), ex);
         throw cCie10E;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCie10Exception.class})
   @Override
   public Page<CatCie10View> getCatCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCie10Exception {
      try {
         logger.info("===>>>getCatCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatCie10Exception cCie10E = new CatCie10Exception("No se encuentra en el sistema CatCie10", CatCie10Exception.LAYER_DAO, CatCie10Exception.ACTION_VALIDATE);
//            cCie10E.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cCie10E;
//         }

         List<CatCie10View> catCie10ViewList = new ArrayList<>();
         Page<CatCie10> catCie10Page = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("catalogKey"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatCie10> spec = Specifications.where(
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
            catCie10Page = catCie10Repository.findAll(request);
         } else {
            catCie10Page = catCie10Repository.findAll(spec, request);
         }

         catCie10Page.getContent().forEach(catCie10 -> {
            catCie10ViewList.add(catCie10Converter.toView(catCie10, Boolean.TRUE));
         });
         PageImpl<CatCie10View> catCie10ViewPage = new PageImpl<CatCie10View>(catCie10ViewList, request, catCie10Page.getTotalElements());
         return catCie10ViewPage;
//      } catch (CatCie10Exception cCie10E) {
//         throw cCie10E;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatCie10Exception pe = new CatCie10Exception("Algun parametro no es correcto:", CatCie10Exception.LAYER_SERVICE, CatCie10Exception.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatCie10Exception cCie10E = new CatCie10Exception("Ocurrio un error al seleccionar lista CatCie10 paginable", CatCie10Exception.LAYER_SERVICE, CatCie10Exception.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatCie10 paginable - CODE: {}", cCie10E.getExceptionCode(), ex);
         throw cCie10E;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
