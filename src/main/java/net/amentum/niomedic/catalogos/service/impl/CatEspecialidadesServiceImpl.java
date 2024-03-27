package net.amentum.niomedic.catalogos.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.catalogos.converter.CatEspecialidadesConverter;
import net.amentum.niomedic.catalogos.exception.CatEspecialidadesException;
import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import net.amentum.niomedic.catalogos.persistence.CatEspecialidadesRepository;
import net.amentum.niomedic.catalogos.service.CatEspecialidadesService;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
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
public class CatEspecialidadesServiceImpl implements CatEspecialidadesService {

   private final Map<String, Object> colOrderNames = new HashMap<>();
   private CatEspecialidadesRepository catEspecialidadesRepository;
   private CatEspecialidadesConverter catEspecialidadesConverter;


   {
      colOrderNames.put("idCatEspecialidades", "idCatEspecialidades");
      colOrderNames.put("especialidadCodigo", "especialidadCodigo");
      colOrderNames.put("especialidadDescripcion", "especialidadDescripcion");
      colOrderNames.put("activo", "activo");
   }

   @Autowired
   public void setCatEspecialidadesRepository(CatEspecialidadesRepository catEspecialidadesRepository) {
      this.catEspecialidadesRepository = catEspecialidadesRepository;
   }

   @Autowired
   public void setCatEspecialidadesConverter(CatEspecialidadesConverter catEspecialidadesConverter) {
      this.catEspecialidadesConverter = catEspecialidadesConverter;
   }

   private CatEspecialidades exists(Integer idCatEspecialidades) throws CatEspecialidadesException {
      log.info("===>>>exists() - revisando si existe el motivo envio por id: {}", idCatEspecialidades);
      if (!catEspecialidadesRepository.exists(idCatEspecialidades)) {
         log.error("===>>>exists() - No se encuentra el idCatEspecialidades: {}", idCatEspecialidades);
         throw new CatEspecialidadesException(HttpStatus.NOT_FOUND, String.format(CatEspecialidadesException.ITEM_NOT_FOUND, idCatEspecialidades));
      }
      return catEspecialidadesRepository.findOne(idCatEspecialidades);
   }


   @Override
   public CatEspecialidadesView getDetailsByIdCatEspecialidades(Integer idCatEspecialidades) throws CatEspecialidadesException {
      try {
         CatEspecialidades catEspecialidades = exists(idCatEspecialidades);
         log.info("===>>>getDetailsByIdCatEspecialidades() - {}", catEspecialidades);
         return catEspecialidadesConverter.toView(catEspecialidades, Boolean.TRUE);
      } catch (CatEspecialidadesException catEspeE) {
         throw catEspeE;
      } catch (Exception ex) {
         log.error("Error al obtener registro - error: {}", ex);
         throw new CatEspecialidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatEspecialidadesException.SERVER_ERROR, "obtener por ID"));
      }
   }

   @Override
   public List<CatEspecialidadesView> findAll() throws CatEspecialidadesException {
      try {
         List<CatEspecialidades> catEspecialidadesList = catEspecialidadesRepository.findAll();
         List<CatEspecialidadesView> catEspecialidadesViewList = new ArrayList<>();
         for (CatEspecialidades cpl : catEspecialidadesList) {
            catEspecialidadesViewList.add(catEspecialidadesConverter.toView(cpl, Boolean.TRUE));
         }
         return catEspecialidadesViewList;
      } catch (Exception ex) {
         log.error("Error al obtener todos los registros - error: {}", ex);
         throw new CatEspecialidadesException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener todos los registros");
      }
   }

   @Override
   public Page<CatEspecialidadesView> getCatEspecialidadesSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatEspecialidadesException {
      try {
         log.info("===>>>getCatEspecialidadesSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, activo, page, size, orderColumn, orderType);

         if (!colOrderNames.containsKey(orderColumn)) {
            orderColumn = "idCatEspecialidades";
         }

         List<CatEspecialidadesView> catEspecialidadesViewList = new ArrayList<>();
         Page<CatEspecialidades> catEspecialidadesPage = null;
         log.info("===>>>getCatEspecialidadesSearch() - creando SORT");
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));

         if (orderType.equalsIgnoreCase("asc")) {
            sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
         } else {
            sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
         }

         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatEspecialidades> spec = Specifications.where(
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

         log.info("===>>>getCatEspecialidadesSearch() - realizando la búsqueda");
         catEspecialidadesPage = catEspecialidadesRepository.findAll(spec, request);

         catEspecialidadesPage.getContent().forEach(catEspecialidades -> {
            catEspecialidadesViewList.add(catEspecialidadesConverter.toView(catEspecialidades, Boolean.TRUE));
         });

         PageImpl<CatEspecialidadesView> catEspecialidadesViewPage = new PageImpl<CatEspecialidadesView>(catEspecialidadesViewList, request, catEspecialidadesPage.getTotalElements());
         return catEspecialidadesViewPage;

      } catch (IllegalArgumentException iae) {
         log.error("===>>>Algún parámetro no es correcto: {}", iae);
         throw new CatEspecialidadesException(HttpStatus.BAD_REQUEST, "Algún parámetro es null, vacío o incorrecto");
      } catch (Exception ex) {
         log.error("Error al obtener la lista paginable - error: {}", ex);
         throw new CatEspecialidadesException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener obtener la lista paginable");
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }

   @Transactional(readOnly = false, rollbackFor = {CatEspecialidadesException.class})
   @Override
   public CatEspecialidadesView createCatEspecialidades(CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException {
      try {

         CatEspecialidades catEspecialidades = catEspecialidadesConverter.toEntity(catEspecialidadesView, new CatEspecialidades(), Boolean.FALSE);
         log.info("===>>>Insertar nuevo catEspecialidades: {}", catEspecialidades);
         catEspecialidadesRepository.save(catEspecialidades);
         return catEspecialidadesConverter.toView(catEspecialidades, Boolean.TRUE);

      } catch (ConstraintViolationException cve) {
         log.error("===>>>createCatEspecialidades() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatEspecialidadesException(HttpStatus.BAD_REQUEST, String.format(CatEspecialidadesException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>createCatEspecialidades() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatEspecialidadesException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>createCatEspecialidades() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatEspecialidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatEspecialidadesException.SERVER_ERROR, "Crear"));
      }
   }

   @Transactional(readOnly = false, rollbackFor = {CatEspecialidadesException.class})
   @Override
   public CatEspecialidadesView updateCatEspecialidades(CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException {
      try {

         CatEspecialidades catEspecialidades = exists(catEspecialidadesView.getIdCatEspecialidades());
         catEspecialidades = catEspecialidadesConverter.toEntity(catEspecialidadesView, catEspecialidades, Boolean.TRUE);
         log.info("===>>>updateCatEspecialidades() - Editar catEspecialidades: {}", catEspecialidades);
         catEspecialidadesRepository.save(catEspecialidades);
         return catEspecialidadesConverter.toView(catEspecialidades, Boolean.TRUE);

      } catch (CatEspecialidadesException catEspeE) {
         throw catEspeE;
      } catch (ConstraintViolationException cve) {
         log.error("===>>>updateCatEspecialidades() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatEspecialidadesException(HttpStatus.BAD_REQUEST, String.format(CatEspecialidadesException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>updateCatEspecialidades() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatEspecialidadesException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>updateCatEspecialidades() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatEspecialidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatEspecialidadesException.SERVER_ERROR, "Crear"));
      }
   }
}

