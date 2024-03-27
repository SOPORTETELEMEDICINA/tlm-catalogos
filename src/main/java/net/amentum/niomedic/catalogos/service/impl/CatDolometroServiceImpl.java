package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatDolometroConverter;
import net.amentum.niomedic.catalogos.exception.CatDolometroException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatDolometro;
import net.amentum.niomedic.catalogos.persistence.CatDolometroRepository;
import net.amentum.niomedic.catalogos.service.CatDolometroService;
import net.amentum.niomedic.catalogos.views.CatDolometroView;
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
public class CatDolometroServiceImpl implements CatDolometroService {
   private final Logger logger = LoggerFactory.getLogger(CatDolometroServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatDolometroRepository catDolometroRepository;
   private CatDolometroConverter catDolometroConverter;

   {
      colOrderNames.put("nivel", "nivel");
      colOrderNames.put("doloDescripcion", "doloDescripcion");
   }

   @Autowired
   public void setCatDolometroRepository(CatDolometroRepository catDolometroRepository) {
      this.catDolometroRepository = catDolometroRepository;
   }

   @Autowired
   public void setCatDolometroConverter(CatDolometroConverter catDolometroConverter) {
      this.catDolometroConverter = catDolometroConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatDolometroException.class})
   @Override
   public CatDolometroView getDetailsByIdCatDolometro(Integer idCatDolometro) throws CatDolometroException {
      try {
         if (!catDolometroRepository.exists(idCatDolometro)) {
            logger.error("===>>>idCatDolometro no encontrado: {}", idCatDolometro);
            CatDolometroException cDoloE = new CatDolometroException("No se encuentra en el sistema CatDolometro", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_VALIDATE);
            cDoloE.addError("idCatDolometro no encontrado: " + idCatDolometro);
            throw cDoloE;
         }
         CatDolometro catDolometro = catDolometroRepository.findOne(idCatDolometro);
         return catDolometroConverter.toView(catDolometro, Boolean.TRUE);
      } catch (CatDolometroException cDoloE) {
         throw cDoloE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatDolometroException cDoloE = new CatDolometroException("Error en la validacion", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cDoloE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cDoloE;
      } catch (DataIntegrityViolationException dive) {
         CatDolometroException cDoloE = new CatDolometroException("No fue posible obtener  CatDolometro", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_INSERT);
         cDoloE.addError("Ocurrio un error al obtener CatDolometro");
         logger.error("===>>>Error al obtener CatDolometro - CODE: {} - {}", cDoloE.getExceptionCode(), idCatDolometro, dive);
         throw cDoloE;
      } catch (Exception ex) {
         CatDolometroException cDoloE = new CatDolometroException("Error inesperado al obtener  CatDolometro", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_INSERT);
         cDoloE.addError("Ocurrio un error al obtener CatDolometro");
         logger.error("===>>>Error al obtener CatDolometro - CODE: {} - {}", cDoloE.getExceptionCode(), idCatDolometro, ex);
         throw cDoloE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatDolometroException.class})
   @Override
   public List<CatDolometroView> findAll() throws CatDolometroException {
      try {
         List<CatDolometro> catDolometroList = catDolometroRepository.findAll();
         List<CatDolometroView> catDolometroViewList = new ArrayList<>();
         for (CatDolometro cn : catDolometroList) {
            catDolometroViewList.add(catDolometroConverter.toView(cn, Boolean.TRUE));
         }
         return catDolometroViewList;
      } catch (Exception ex) {
         CatDolometroException cDoloE = new CatDolometroException("Error inesperado al obtener todos los registros de  CatDolometro", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_INSERT);
         cDoloE.addError("Ocurrio un error al obtener CatDolometro");
         logger.error("===>>>Error al obtener todos los registros de CatDolometro - CODE: {} - {}", cDoloE.getExceptionCode(), ex);
         throw cDoloE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatDolometroException.class})
   @Override
   public Page<CatDolometroView> getCatDolometroSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatDolometroException {
      try {
         logger.info("===>>>getCatDolometroSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatDolometroException cDoloE = new CatDolometroException("No se encuentra en el sistema CatDolometro", CatDolometroException.LAYER_DAO, CatDolometroException.ACTION_VALIDATE);
//            cDoloE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cDoloE;
//         }

         List<CatDolometroView> catDolometroViewList = new ArrayList<>();
         Page<CatDolometro> catDolometroPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("doloDescripcion"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatDolometro> spec = Specifications.where(
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
            catDolometroPage = catDolometroRepository.findAll(request);
         } else {
            catDolometroPage = catDolometroRepository.findAll(spec, request);
         }

         catDolometroPage.getContent().forEach(catDolometro -> {
            catDolometroViewList.add(catDolometroConverter.toView(catDolometro, Boolean.TRUE));
         });
         PageImpl<CatDolometroView> catDolometroViewPage = new PageImpl<CatDolometroView>(catDolometroViewList, request, catDolometroPage.getTotalElements());
         return catDolometroViewPage;
//      } catch (CatDolometroException cDoloE) {
//         throw cDoloE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatDolometroException pe = new CatDolometroException("Algun parametro no es correcto:", CatDolometroException.LAYER_SERVICE, CatDolometroException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatDolometroException cDoloE = new CatDolometroException("Ocurrio un error al seleccionar lista CatDolometro paginable", CatDolometroException.LAYER_SERVICE, CatDolometroException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatDolometro paginable - CODE: {}", cDoloE.getExceptionCode(), ex);
         throw cDoloE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
