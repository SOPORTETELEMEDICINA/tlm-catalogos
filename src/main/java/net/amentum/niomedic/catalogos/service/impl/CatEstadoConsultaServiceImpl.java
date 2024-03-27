package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatEstadoConsultaConverter;
import net.amentum.niomedic.catalogos.exception.CatEstadoConsultaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatEstadoConsulta;
import net.amentum.niomedic.catalogos.persistence.CatEstadoConsultaRepository;
import net.amentum.niomedic.catalogos.service.CatEstadoConsultaService;
import net.amentum.niomedic.catalogos.views.CatEstadoConsultaView;
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
public class CatEstadoConsultaServiceImpl implements CatEstadoConsultaService {
   private final Logger logger = LoggerFactory.getLogger(CatEstadoConsultaServiceImpl.class);
   private final Map<String, Object> colOrderNames = new HashMap<>();

   private CatEstadoConsultaRepository catEstadoConsultaRepository;
   private CatEstadoConsultaConverter catEstadoConsultaConverter;

   {
      colOrderNames.put("descripcion", "descripcion");
      colOrderNames.put("color", "color");
   }

   @Autowired
   public void setCatEstadoConsultaRepository(CatEstadoConsultaRepository catEstadoConsultaRepository) {
      this.catEstadoConsultaRepository = catEstadoConsultaRepository;
   }

   @Autowired
   public void setCatEstadoConsultaConverter(CatEstadoConsultaConverter catEstadoConsultaConverter) {
      this.catEstadoConsultaConverter = catEstadoConsultaConverter;
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatEstadoConsultaException.class})
   @Override
   public CatEstadoConsultaView getDetailsByIdCatEstadoConsulta(Integer idEstadoConsulta) throws CatEstadoConsultaException {
      try {
         if (!catEstadoConsultaRepository.exists(idEstadoConsulta)) {
            logger.error("===>>>idEstadoConsulta no encontrado: {}", idEstadoConsulta);
            CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("No se encuentra en el sistema CatEstadoConsulta", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_VALIDATE);
            cEdoConE.addError("idEstadoConsulta no encontrado: " + idEstadoConsulta);
            throw cEdoConE;
         }
         CatEstadoConsulta catEstadoConsulta = catEstadoConsultaRepository.findOne(idEstadoConsulta);
         return catEstadoConsultaConverter.toView(catEstadoConsulta, Boolean.TRUE);
      } catch (CatEstadoConsultaException cEdoConE) {
         throw cEdoConE;
      } catch (ConstraintViolationException cve) {
         logger.error("===>>>Error en la validacion");
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("Error en la validacion", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_VALIDATE);
         final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
         for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
            ConstraintViolation<?> siguiente = iterator.next();
            cEdoConE.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
         }
         throw cEdoConE;
      } catch (DataIntegrityViolationException dive) {
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("No fue posible obtener  CatEstadoConsulta", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_INSERT);
         cEdoConE.addError("Ocurrio un error al obtener CatEstadoConsulta");
         logger.error("===>>>Error al obtener CatEstadoConsulta - CODE: {} - {}", cEdoConE.getExceptionCode(), idEstadoConsulta, dive);
         throw cEdoConE;
      } catch (Exception ex) {
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("Error inesperado al obtener  CatEstadoConsulta", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_INSERT);
         cEdoConE.addError("Ocurrio un error al obtener CatEstadoConsulta");
         logger.error("===>>>Error al obtener CatEstadoConsulta - CODE: {} - {}", cEdoConE.getExceptionCode(), idEstadoConsulta, ex);
         throw cEdoConE;
      }

   }

   //   @Transactional(readOnly = false, rollbackFor = {CatEstadoConsultaException.class})
   @Override
   public List<CatEstadoConsultaView> findAll() throws CatEstadoConsultaException {
      try {
         List<CatEstadoConsulta> catEstadoConsultaList = catEstadoConsultaRepository.findAll();
         List<CatEstadoConsultaView> catEstadoConsultaViewList = new ArrayList<>();
         for (CatEstadoConsulta cn : catEstadoConsultaList) {
            catEstadoConsultaViewList.add(catEstadoConsultaConverter.toView(cn, Boolean.TRUE));
         }
         return catEstadoConsultaViewList;
      } catch (Exception ex) {
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("Error inesperado al obtener todos los registros de  CatEstadoConsulta", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_INSERT);
         cEdoConE.addError("Ocurrio un error al obtener CatEstadoConsulta");
         logger.error("===>>>Error al obtener todos los registros de CatEstadoConsulta - CODE: {} - {}", cEdoConE.getExceptionCode(), ex);
         throw cEdoConE;
      }
   }


   //   @Transactional(readOnly = false, rollbackFor = {CatEstadoConsultaException.class})
   @Override
   public Page<CatEstadoConsultaView> getCatEstadoConsultaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatEstadoConsultaException {
      try {
         logger.info("===>>>getCatEstadoConsultaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("No se encuentra en el sistema CatEstadoConsulta", CatEstadoConsultaException.LAYER_DAO, CatEstadoConsultaException.ACTION_VALIDATE);
//            cEdoConE.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cEdoConE;
//         }

         List<CatEstadoConsultaView> catEstadoConsultaViewList = new ArrayList<>();
         Page<CatEstadoConsulta> catEstadoConsultaPage = null;
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("descripcion"));

         if (orderColumn != null && orderType != null) {
            if (orderType.equalsIgnoreCase("asc")) {
               sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
            } else {
               sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
            }
         }
         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatEstadoConsulta> spec = Specifications.where(
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
            catEstadoConsultaPage = catEstadoConsultaRepository.findAll(request);
         } else {
            catEstadoConsultaPage = catEstadoConsultaRepository.findAll(spec, request);
         }

         catEstadoConsultaPage.getContent().forEach(catEstadoConsulta -> {
            catEstadoConsultaViewList.add(catEstadoConsultaConverter.toView(catEstadoConsulta, Boolean.TRUE));
         });
         PageImpl<CatEstadoConsultaView> catEstadoConsultaViewPage = new PageImpl<CatEstadoConsultaView>(catEstadoConsultaViewList, request, catEstadoConsultaPage.getTotalElements());
         return catEstadoConsultaViewPage;
//      } catch (CatEstadoConsultaException cEdoConE) {
//         throw cEdoConE;
      } catch (IllegalArgumentException iae) {
         logger.error("===>>>Algun parametro no es correcto");
         CatEstadoConsultaException pe = new CatEstadoConsultaException("Algun parametro no es correcto:", CatEstadoConsultaException.LAYER_SERVICE, CatEstadoConsultaException.ACTION_VALIDATE);
         pe.addError("Puede que sea null, vacio o valor incorrecto");
         throw pe;
      } catch (Exception ex) {
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("Ocurrio un error al seleccionar lista CatEstadoConsulta paginable", CatEstadoConsultaException.LAYER_SERVICE, CatEstadoConsultaException.ACTION_SELECT);
         logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatEstadoConsulta paginable - CODE: {}", cEdoConE.getExceptionCode(), ex);
         throw cEdoConE;
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }


}
