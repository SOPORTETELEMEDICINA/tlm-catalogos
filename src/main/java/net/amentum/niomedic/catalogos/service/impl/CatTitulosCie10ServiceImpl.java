package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatTitulosCie10Converter;
import net.amentum.niomedic.catalogos.exception.CatTitulosCie10Exception;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTitulosCie10;
import net.amentum.niomedic.catalogos.persistence.CatTitulosCie10Repository;
import net.amentum.niomedic.catalogos.service.CatTitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatTitulosCie10View;
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
public class CatTitulosCie10ServiceImpl implements CatTitulosCie10Service {
   private final Logger logger = LoggerFactory.getLogger(CatTitulosCie10ServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatTitulosCie10Repository catTitulosCie10Repository;
   private CatTitulosCie10Converter catTitulosCie10Converter;

   {
      colOrderNames.put("descripcionTitulosCie10", "descripcionTitulosCie10");
      colOrderNames.put("codigosTitulosCie10", "codigosTitulosCie10");
   }

   @Autowired
   public void setCatTitulosCie10Repository(CatTitulosCie10Repository catTitulosCie10Repository) {
      this.catTitulosCie10Repository = catTitulosCie10Repository;
   }

   @Autowired
   public void setCatTitulosCie10Converter(CatTitulosCie10Converter catTitulosCie10Converter) {
      this.catTitulosCie10Converter = catTitulosCie10Converter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTitulosCie10Exception.class})
   @Override
   public CatTitulosCie10View getDetailsByIdCatTitulosCie10(Integer idCatTitulosCie10) throws CatTitulosCie10Exception {
      try {
         if (!catTitulosCie10Repository.exists(idCatTitulosCie10)) {
            logger.error("===>>>idCatTitulosCie10 no encontrado: {}", idCatTitulosCie10);
            CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("No se encuentra en el sistema CatTitulosCie10", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_VALIDATE);
            cTitE.addError("idCatTitulosCie10 no encontrado: " + idCatTitulosCie10);
            throw cTitE;
         }
         CatTitulosCie10 catTitulosCie10 = catTitulosCie10Repository.findOne(idCatTitulosCie10);
         return catTitulosCie10Converter.toView(catTitulosCie10, Boolean.TRUE);
      } catch (CatTitulosCie10Exception cTitE) {
         throw cTitE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("Error en la validacion", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cTitE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cTitE;
      } catch (DataIntegrityViolationException dive) {
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("No fue posible obtener  CatTitulosCie10", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_INSERT);
         cTitE.addError("Ocurrio un error al obtener CatTitulosCie10");
         logger.error("===>>>Error al obtener CatTitulosCie10 - CODE: {} - {}", cTitE.getExceptionCode(), idCatTitulosCie10, dive);
         throw cTitE;
      } catch (Exception ex) {
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("Error inesperado al obtener  CatTitulosCie10", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_INSERT);
         cTitE.addError("Ocurrio un error al obtener CatTitulosCie10");
         logger.error("===>>>Error al obtener CatTitulosCie10 - CODE: {} - {}", cTitE.getExceptionCode(), idCatTitulosCie10, ex);
         throw cTitE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatTitulosCie10Exception.class})
   @Override
   public List<CatTitulosCie10View> findAll() throws CatTitulosCie10Exception {
      try {
         List<CatTitulosCie10> catTitulosCie10List = catTitulosCie10Repository.findAll();
         List<CatTitulosCie10View> catTitulosCie10ViewList = new ArrayList<>();
         for (CatTitulosCie10 cn : catTitulosCie10List) {
            catTitulosCie10ViewList.add(catTitulosCie10Converter.toView(cn, Boolean.TRUE));
         }
         return catTitulosCie10ViewList;
      } catch (Exception ex) {
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("Error inesperado al obtener todos los registros de  CatTitulosCie10", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_INSERT);
         cTitE.addError("Ocurrio un error al obtener CatTitulosCie10");
         logger.error("===>>>Error al obtener todos los registros de CatTitulosCie10 - CODE: {} - {}", cTitE.getExceptionCode(), ex);
         throw cTitE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatTitulosCie10Exception.class})
   @Override
   public Page<CatTitulosCie10View> getCatTitulosCie10Search(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTitulosCie10Exception {
      try {
         logger.info("===>>>getCatTitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("No se encuentra en el sistema CatTitulosCie10", CatTitulosCie10Exception.LAYER_DAO, CatTitulosCie10Exception.ACTION_VALIDATE);
//            cTitE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cTitE;
//         }

         List<CatTitulosCie10View> catTitulosCie10ViewList = new ArrayList<>();
         Page<CatTitulosCie10> catTitulosCie10Page = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("codigosTitulosCie10"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatTitulosCie10> spec = Specifications.where(
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
            catTitulosCie10Page = catTitulosCie10Repository.findAll(request);
         } else {
            catTitulosCie10Page = catTitulosCie10Repository.findAll(spec, request);
         }

         catTitulosCie10Page.getContent().forEach(catTitulosCie10 -> {
            catTitulosCie10ViewList.add(catTitulosCie10Converter.toView(catTitulosCie10, Boolean.TRUE));
         });
         PageImpl<CatTitulosCie10View> catTitulosCie10ViewPage = new PageImpl<CatTitulosCie10View>(catTitulosCie10ViewList, request, catTitulosCie10Page.getTotalElements());
         return catTitulosCie10ViewPage;
//      } catch (CatTitulosCie10Exception cTitE) {
//         throw cTitE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatTitulosCie10Exception pe = new CatTitulosCie10Exception("Algun parametro no es correcto:", CatTitulosCie10Exception.LAYER_SERVICE, CatTitulosCie10Exception.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("Ocurrio un error al seleccionar lista CatTitulosCie10 paginable", CatTitulosCie10Exception.LAYER_SERVICE, CatTitulosCie10Exception.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTitulosCie10 paginable - CODE: {}", cTitE.getExceptionCode(), ex);
         throw cTitE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
