package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatCapitulosCie10Converter;
import net.amentum.niomedic.catalogos.exception.CatCapitulosCie10Exception;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatCapitulosCie10;
import net.amentum.niomedic.catalogos.persistence.CatCapitulosCie10Repository;
import net.amentum.niomedic.catalogos.service.CatCapitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatCapitulosCie10View;
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
public class CatCapitulosCie10ServiceImpl implements CatCapitulosCie10Service {
   private final Logger logger = LoggerFactory.getLogger(CatCapitulosCie10ServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatCapitulosCie10Repository catCapitulosCie10Repository;
   private CatCapitulosCie10Converter catCapitulosCie10Converter;

   {
      colOrderNames.put("codigosCapitulosCie10", "codigosCapitulosCie10");
      colOrderNames.put("descripcionCapitulosCie10", "descripcionCapitulosCie10");
   }

   @Autowired
   public void setCatCapitulosCie10Repository(CatCapitulosCie10Repository catCapitulosCie10Repository) {
      this.catCapitulosCie10Repository = catCapitulosCie10Repository;
   }

   @Autowired
   public void setCatCapitulosCie10Converter(CatCapitulosCie10Converter catCapitulosCie10Converter) {
      this.catCapitulosCie10Converter = catCapitulosCie10Converter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCapitulosCie10Exception.class})
   @Override
   public CatCapitulosCie10View getDetailsByIdCatCapitulosCie10(Integer idCatCapitulosCie10) throws CatCapitulosCie10Exception {
      try {
         if (!catCapitulosCie10Repository.exists(idCatCapitulosCie10)) {
            logger.error("===>>>idCatCapitulosCie10 no encontrado: {}", idCatCapitulosCie10);
            CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("No se encuentra en el sistema CatCapitulosCie10", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_VALIDATE);
            cCapE.addError("idCatCapitulosCie10 no encontrado: " + idCatCapitulosCie10);
            throw cCapE;
         }
         CatCapitulosCie10 catCapitulosCie10 = catCapitulosCie10Repository.findOne(idCatCapitulosCie10);
         return catCapitulosCie10Converter.toView(catCapitulosCie10, Boolean.TRUE);
      } catch (CatCapitulosCie10Exception cCapE) {
         throw cCapE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("Error en la validacion", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cCapE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cCapE;
      } catch (DataIntegrityViolationException dive) {
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("No fue posible obtener  CatCapitulosCie10", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_INSERT);
         cCapE.addError("Ocurrio un error al obtener CatCapitulosCie10");
         logger.error("===>>>Error al obtener CatCapitulosCie10 - CODE: {} - {}", cCapE.getExceptionCode(), idCatCapitulosCie10, dive);
         throw cCapE;
      } catch (Exception ex) {
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("Error inesperado al obtener  CatCapitulosCie10", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_INSERT);
         cCapE.addError("Ocurrio un error al obtener CatCapitulosCie10");
         logger.error("===>>>Error al obtener CatCapitulosCie10 - CODE: {} - {}", cCapE.getExceptionCode(), idCatCapitulosCie10, ex);
         throw cCapE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatCapitulosCie10Exception.class})
   @Override
   public List<CatCapitulosCie10View> findAll() throws CatCapitulosCie10Exception {
      try {
         List<CatCapitulosCie10> catCapitulosCie10List = catCapitulosCie10Repository.findAll();
         List<CatCapitulosCie10View> catCapitulosCie10ViewList = new ArrayList<>();
         for (CatCapitulosCie10 cn : catCapitulosCie10List) {
            catCapitulosCie10ViewList.add(catCapitulosCie10Converter.toView(cn, Boolean.TRUE));
         }
         return catCapitulosCie10ViewList;
      } catch (Exception ex) {
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("Error inesperado al obtener todos los registros de  CatCapitulosCie10", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_INSERT);
         cCapE.addError("Ocurrio un error al obtener CatCapitulosCie10");
         logger.error("===>>>Error al obtener todos los registros de CatCapitulosCie10 - CODE: {} - {}", cCapE.getExceptionCode(), ex);
         throw cCapE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatCapitulosCie10Exception.class})
   @Override
   public Page<CatCapitulosCie10View> getCatCapitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatCapitulosCie10Exception {
      try {
         logger.info("===>>>getCatCapitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("No se encuentra en el sistema CatCapitulosCie10", CatCapitulosCie10Exception.LAYER_DAO, CatCapitulosCie10Exception.ACTION_VALIDATE);
//            cCapE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cCapE;
//         }

         List<CatCapitulosCie10View> catCapitulosCie10ViewList = new ArrayList<>();
         Page<CatCapitulosCie10> catCapitulosCie10Page = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("codigosCapitulosCie10"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatCapitulosCie10> spec = Specifications.where(
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
            catCapitulosCie10Page = catCapitulosCie10Repository.findAll(request);
         } else {
            catCapitulosCie10Page = catCapitulosCie10Repository.findAll(spec, request);
         }

         catCapitulosCie10Page.getContent().forEach(catCapitulosCie10 -> {
            catCapitulosCie10ViewList.add(catCapitulosCie10Converter.toView(catCapitulosCie10, Boolean.TRUE));
         });
         PageImpl<CatCapitulosCie10View> catCapitulosCie10ViewPage = new PageImpl<CatCapitulosCie10View>(catCapitulosCie10ViewList, request, catCapitulosCie10Page.getTotalElements());
         return catCapitulosCie10ViewPage;
//      } catch (CatCapitulosCie10Exception cCapE) {
//         throw cCapE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatCapitulosCie10Exception pe = new CatCapitulosCie10Exception("Algun parametro no es correcto:", CatCapitulosCie10Exception.LAYER_SERVICE, CatCapitulosCie10Exception.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("Ocurrio un error al seleccionar lista CatCapitulosCie10 paginable", CatCapitulosCie10Exception.LAYER_SERVICE, CatCapitulosCie10Exception.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatCapitulosCie10 paginable - CODE: {}", cCapE.getExceptionCode(), ex);
         throw cCapE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
