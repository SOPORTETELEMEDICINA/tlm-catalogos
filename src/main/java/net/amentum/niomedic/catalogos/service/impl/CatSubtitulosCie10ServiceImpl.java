package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatSubtitulosCie10Converter;
import net.amentum.niomedic.catalogos.exception.CatSubtitulosCie10Exception;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatSubtitulosCie10;
import net.amentum.niomedic.catalogos.persistence.CatSubtitulosCie10Repository;
import net.amentum.niomedic.catalogos.service.CatSubtitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatSubtitulosCie10View;
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
public class CatSubtitulosCie10ServiceImpl implements CatSubtitulosCie10Service {
   private final Logger logger = LoggerFactory.getLogger(CatSubtitulosCie10ServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatSubtitulosCie10Repository catSubtitulosCie10Repository;
   private CatSubtitulosCie10Converter catSubtitulosCie10Converter;

   {
      colOrderNames.put("codigosSubtitulosCie10", "codigosSubtitulosCie10");
      colOrderNames.put("descripcionSubtitulosCie10", "descripcionSubtitulosCie10");
   }

   @Autowired
   public void setCatSubtitulosCie10Repository(CatSubtitulosCie10Repository catSubtitulosCie10Repository) {
      this.catSubtitulosCie10Repository = catSubtitulosCie10Repository;
   }

   @Autowired
   public void setCatSubtitulosCie10Converter(CatSubtitulosCie10Converter catSubtitulosCie10Converter) {
      this.catSubtitulosCie10Converter = catSubtitulosCie10Converter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatSubtitulosCie10Exception.class})
   @Override
   public CatSubtitulosCie10View getDetailsByIdCatSubtitulosCie10(Integer idCatSubtitulosCie10) throws CatSubtitulosCie10Exception {
      try {
         if (!catSubtitulosCie10Repository.exists(idCatSubtitulosCie10)) {
            logger.error("===>>>idCatSubtitulosCie10 no encontrado: {}", idCatSubtitulosCie10);
            CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("No se encuentra en el sistema CatSubtitulosCie10", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_VALIDATE);
            cSubE.addError("idCatSubtitulosCie10 no encontrado: " + idCatSubtitulosCie10);
            throw cSubE;
         }
         CatSubtitulosCie10 catSubtitulosCie10 = catSubtitulosCie10Repository.findOne(idCatSubtitulosCie10);
         return catSubtitulosCie10Converter.toView(catSubtitulosCie10, Boolean.TRUE);
      } catch (CatSubtitulosCie10Exception cSubE) {
         throw cSubE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("Error en la validacion", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cSubE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cSubE;
      } catch (DataIntegrityViolationException dive) {
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("No fue posible obtener  CatSubtitulosCie10", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_INSERT);
         cSubE.addError("Ocurrio un error al obtener CatSubtitulosCie10");
         logger.error("===>>>Error al obtener CatSubtitulosCie10 - CODE: {} - {}", cSubE.getExceptionCode(), idCatSubtitulosCie10, dive);
         throw cSubE;
      } catch (Exception ex) {
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("Error inesperado al obtener  CatSubtitulosCie10", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_INSERT);
         cSubE.addError("Ocurrio un error al obtener CatSubtitulosCie10");
         logger.error("===>>>Error al obtener CatSubtitulosCie10 - CODE: {} - {}", cSubE.getExceptionCode(), idCatSubtitulosCie10, ex);
         throw cSubE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatSubtitulosCie10Exception.class})
   @Override
   public List<CatSubtitulosCie10View> findAll() throws CatSubtitulosCie10Exception {
      try {
         List<CatSubtitulosCie10> catSubtitulosCie10List = catSubtitulosCie10Repository.findAll();
         List<CatSubtitulosCie10View> catSubtitulosCie10ViewList = new ArrayList<>();
         for (CatSubtitulosCie10 cn : catSubtitulosCie10List) {
            catSubtitulosCie10ViewList.add(catSubtitulosCie10Converter.toView(cn, Boolean.TRUE));
         }
         return catSubtitulosCie10ViewList;
      } catch (Exception ex) {
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("Error inesperado al obtener todos los registros de  CatSubtitulosCie10", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_INSERT);
         cSubE.addError("Ocurrio un error al obtener CatSubtitulosCie10");
         logger.error("===>>>Error al obtener todos los registros de CatSubtitulosCie10 - CODE: {} - {}", cSubE.getExceptionCode(), ex);
         throw cSubE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatSubtitulosCie10Exception.class})
   @Override
   public Page<CatSubtitulosCie10View> getCatSubtitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatSubtitulosCie10Exception {
      try {
         logger.info("===>>>getCatSubtitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("No se encuentra en el sistema CatSubtitulosCie10", CatSubtitulosCie10Exception.LAYER_DAO, CatSubtitulosCie10Exception.ACTION_VALIDATE);
//            cSubE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cSubE;
//         }

         List<CatSubtitulosCie10View> catSubtitulosCie10ViewList = new ArrayList<>();
         Page<CatSubtitulosCie10> catSubtitulosCie10Page = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("codigosSubtitulosCie10"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatSubtitulosCie10> spec = Specifications.where(
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
            catSubtitulosCie10Page = catSubtitulosCie10Repository.findAll(request);
         } else {
            catSubtitulosCie10Page = catSubtitulosCie10Repository.findAll(spec, request);
         }

         catSubtitulosCie10Page.getContent().forEach(catSubtitulosCie10 -> {
            catSubtitulosCie10ViewList.add(catSubtitulosCie10Converter.toView(catSubtitulosCie10, Boolean.TRUE));
         });
         PageImpl<CatSubtitulosCie10View> catSubtitulosCie10ViewPage = new PageImpl<CatSubtitulosCie10View>(catSubtitulosCie10ViewList, request, catSubtitulosCie10Page.getTotalElements());
         return catSubtitulosCie10ViewPage;
//      } catch (CatSubtitulosCie10Exception cSubE) {
//         throw cSubE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatSubtitulosCie10Exception pe = new CatSubtitulosCie10Exception("Algun parametro no es correcto:", CatSubtitulosCie10Exception.LAYER_SERVICE, CatSubtitulosCie10Exception.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("Ocurrio un error al seleccionar lista CatSubtitulosCie10 paginable", CatSubtitulosCie10Exception.LAYER_SERVICE, CatSubtitulosCie10Exception.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatSubtitulosCie10 paginable - CODE: {}", cSubE.getExceptionCode(), ex);
         throw cSubE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
