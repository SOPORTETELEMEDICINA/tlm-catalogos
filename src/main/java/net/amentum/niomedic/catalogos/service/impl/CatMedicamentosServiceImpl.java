package net.amentum.niomedic.catalogos.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.catalogos.converter.CatMedicamentosConverter;
import net.amentum.niomedic.catalogos.exception.CatMedicamentosException;
import net.amentum.niomedic.catalogos.model.CatMedicamentos;
import net.amentum.niomedic.catalogos.persistence.CatMedicamentosRepository;
import net.amentum.niomedic.catalogos.service.CatMedicamentosService;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
@Slf4j
public class CatMedicamentosServiceImpl implements CatMedicamentosService {

   private final Map<String, Object> colOrderNames = new HashMap<>();
   private CatMedicamentosRepository catMedicamentosRepository;
   private CatMedicamentosConverter catMedicamentosConverter;


   {
      colOrderNames.put("idCatMedicamentos", "idCatMedicamentos");
      colOrderNames.put("cveCodigo", "cveCodigo");
      colOrderNames.put("subCveCodigo", "subCveCodigo");
      colOrderNames.put("nombreGenerico", "nombreGenerico");
      colOrderNames.put("formaFarmaceutica", "formaFarmaceutica");
      colOrderNames.put("tipoActualizacion", "tipoActualizacion");
      colOrderNames.put("numActualizacion", "numActualizacion");
      colOrderNames.put("activo", "activo");
   }

   @Autowired
   public void setCatMedicamentosRepository(CatMedicamentosRepository catMedicamentosRepository) {
      this.catMedicamentosRepository = catMedicamentosRepository;
   }

   @Autowired
   public void setCatMedicamentosConverter(CatMedicamentosConverter catMedicamentosConverter) {
      this.catMedicamentosConverter = catMedicamentosConverter;
   }

   private CatMedicamentos exists(Integer idCatMedicamentos) throws CatMedicamentosException {
      log.info("===>>>exists() - revisando si existe el motivo envio por id: {}", idCatMedicamentos);
      if (!catMedicamentosRepository.exists(idCatMedicamentos)) {
         log.error("===>>>exists() - No se encuentra el idCatMedicamentos: {}", idCatMedicamentos);
         throw new CatMedicamentosException(HttpStatus.NOT_FOUND, String.format(CatMedicamentosException.ITEM_NOT_FOUND, idCatMedicamentos));
      }
      return catMedicamentosRepository.findOne(idCatMedicamentos);
   }


   @Override
   public CatMedicamentosView getDetailsByIdCatMedicamentos(Integer idCatMedicamentos) throws CatMedicamentosException {
      try {
         CatMedicamentos catMedicamentos = exists(idCatMedicamentos);
         log.info("===>>>getDetailsByIdCatMedicamentos() - {}", catMedicamentos);
         return catMedicamentosConverter.toView(catMedicamentos, Boolean.TRUE);
      } catch (CatMedicamentosException catMediE) {
         throw catMediE;
      } catch (Exception ex) {
         log.error("Error al obtener registro - error: {}", ex);
         throw new CatMedicamentosException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMedicamentosException.SERVER_ERROR, "obtener por ID"));
      }
   }

   @Override
   public List<CatMedicamentosView> findAll() throws CatMedicamentosException {
      try {
         List<CatMedicamentos> catMedicamentosList = catMedicamentosRepository.findAll();
         List<CatMedicamentosView> catMedicamentosViewList = new ArrayList<>();
         for (CatMedicamentos cpl : catMedicamentosList) {
            catMedicamentosViewList.add(catMedicamentosConverter.toView(cpl, Boolean.TRUE));
         }
         return catMedicamentosViewList;
      } catch (Exception ex) {
         log.error("Error al obtener todos los registros - error: {}", ex);
         throw new CatMedicamentosException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener todos los registros");
      }
   }

   @Override
   public Page<CatMedicamentosView> getCatMedicamentosSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatMedicamentosException {
      try {
         log.info("===>>>getCatMedicamentosSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, activo, page, size, orderColumn, orderType);

         if (!colOrderNames.containsKey(orderColumn)) {
            orderColumn = "idCatMedicamentos";
         }

         List<CatMedicamentosView> catMedicamentosViewList = new ArrayList<>();
         Page<CatMedicamentos> catMedicamentosPage = null;
         log.info("===>>>getCatMedicamentosSearch() - creando SORT");
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));

         if (orderType.equalsIgnoreCase("asc")) {
            sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
         } else {
            sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
         }

         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatMedicamentos> spec = Specifications.where(
            (root, query, cb) -> {
               Predicate tc = null;
               if (!datosBusqueda.isEmpty()) {
                  tc = cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch));
               }
               if (activo != null) {
                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), activo)) : cb.equal(root.get("activo"), activo));
               }
               return tc;
            }
         );

         log.info("===>>>getCatMedicamentosSearch() - realizando la búsqueda");
         catMedicamentosPage = catMedicamentosRepository.findAll(spec, request);

         catMedicamentosPage.getContent().forEach(catMedicamentos -> {
            catMedicamentosViewList.add(catMedicamentosConverter.toView(catMedicamentos, Boolean.TRUE));
         });

         PageImpl<CatMedicamentosView> catMedicamentosViewPage = new PageImpl<CatMedicamentosView>(catMedicamentosViewList, request, catMedicamentosPage.getTotalElements());
         return catMedicamentosViewPage;

      } catch (IllegalArgumentException iae) {
         log.error("===>>>Algún parámetro no es correcto: {}", iae);
         throw new CatMedicamentosException(HttpStatus.BAD_REQUEST, "Algún parámetro es null, vacío o incorrecto");
      } catch (Exception ex) {
         log.error("Error al obtener la lista paginable - error: {}", ex);
         throw new CatMedicamentosException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener obtener la lista paginable");
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }

   @Transactional(readOnly = false, rollbackFor = {CatMedicamentosException.class})
   @Override
   public CatMedicamentosView createCatMedicamentos(CatMedicamentosView catMedicamentosView) throws CatMedicamentosException {
      try {

         CatMedicamentos catMedicamentos = catMedicamentosConverter.toEntity(catMedicamentosView, new CatMedicamentos(), Boolean.FALSE);
         log.info("===>>>Insertar nuevo catMedicamentos: {}", catMedicamentos);
         catMedicamentosRepository.save(catMedicamentos);
         return catMedicamentosConverter.toView(catMedicamentos, Boolean.TRUE);

      } catch (ConstraintViolationException cve) {
         log.error("===>>>createCatMedicamentos() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatMedicamentosException(HttpStatus.BAD_REQUEST, String.format(CatMedicamentosException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>createCatMedicamentos() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatMedicamentosException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>createCatMedicamentos() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatMedicamentosException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMedicamentosException.SERVER_ERROR, "Crear"));
      }
   }

   @Transactional(readOnly = false, rollbackFor = {CatMedicamentosException.class})
   @Override
   public CatMedicamentosView updateCatMedicamentos(CatMedicamentosView catMedicamentosView) throws CatMedicamentosException {
      try {

         CatMedicamentos catMedicamentos = exists(catMedicamentosView.getIdCatMedicamentos());
         catMedicamentos = catMedicamentosConverter.toEntity(catMedicamentosView, catMedicamentos, Boolean.TRUE);
         log.info("===>>>updateCatMedicamentos() - Editar catMedicamentos: {}", catMedicamentos);
         catMedicamentosRepository.save(catMedicamentos);
         return catMedicamentosConverter.toView(catMedicamentos, Boolean.TRUE);

      } catch (CatMedicamentosException catMediE) {
         throw catMediE;
      } catch (ConstraintViolationException cve) {
         log.error("===>>>updateCatMedicamentos() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatMedicamentosException(HttpStatus.BAD_REQUEST, String.format(CatMedicamentosException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>updateCatMedicamentos() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatMedicamentosException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>updateCatMedicamentos() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatMedicamentosException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMedicamentosException.SERVER_ERROR, "Crear"));
      }
   }
}

