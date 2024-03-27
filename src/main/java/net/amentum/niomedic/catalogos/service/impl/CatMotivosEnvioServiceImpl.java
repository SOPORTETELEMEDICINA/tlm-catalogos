package net.amentum.niomedic.catalogos.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.catalogos.converter.CatMotivosEnvioConverter;
import net.amentum.niomedic.catalogos.exception.CatMotivosEnvioException;
import net.amentum.niomedic.catalogos.model.CatMotivosEnvio;
import net.amentum.niomedic.catalogos.persistence.CatMotivosEnvioRepository;
import net.amentum.niomedic.catalogos.service.CatMotivosEnvioService;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
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
public class CatMotivosEnvioServiceImpl implements CatMotivosEnvioService {

   private final Map<String, Object> colOrderNames = new HashMap<>();
   private CatMotivosEnvioRepository catMotivosEnvioRepository;
   private CatMotivosEnvioConverter catMotivosEnvioConverter;


   {
      colOrderNames.put("idCatMotivosEnvio", "idCatMotivosEnvio");
      colOrderNames.put("motivosEnvioDescripcion", "motivosEnvioDescripcion");
      colOrderNames.put("activo", "activo");
   }

   @Autowired
   public void setCatMotivosEnvioRepository(CatMotivosEnvioRepository catMotivosEnvioRepository) {
      this.catMotivosEnvioRepository = catMotivosEnvioRepository;
   }

   @Autowired
   public void setCatMotivosEnvioConverter(CatMotivosEnvioConverter catMotivosEnvioConverter) {
      this.catMotivosEnvioConverter = catMotivosEnvioConverter;
   }

   private CatMotivosEnvio exists(Integer idCatMotivosEnvio) throws CatMotivosEnvioException {
      log.info("===>>>exists() - revisando si existe el motivo envio por id: {}", idCatMotivosEnvio);
      if (!catMotivosEnvioRepository.exists(idCatMotivosEnvio)) {
         log.error("===>>>exists() - No se encuentra el idCatMotivosEnvio: {}", idCatMotivosEnvio);
         throw new CatMotivosEnvioException(HttpStatus.NOT_FOUND, String.format(CatMotivosEnvioException.ITEM_NOT_FOUND, idCatMotivosEnvio));
      }
      return catMotivosEnvioRepository.findOne(idCatMotivosEnvio);
   }


   @Override
   public CatMotivosEnvioView getDetailsByIdCatMotivosEnvio(Integer idCatMotivosEnvio) throws CatMotivosEnvioException {
      try {
         CatMotivosEnvio catMotivosEnvio = exists(idCatMotivosEnvio);
         log.info("===>>>getDetailsByIdCatMotivosEnvio() - {}", catMotivosEnvio);
         return catMotivosEnvioConverter.toView(catMotivosEnvio, Boolean.TRUE);
      } catch (CatMotivosEnvioException motenviE) {
         throw motenviE;
      } catch (Exception ex) {
         log.error("Error al obtener registro - error: {}", ex);
         throw new CatMotivosEnvioException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMotivosEnvioException.SERVER_ERROR, "obtener por ID"));
      }
   }

   @Override
   public List<CatMotivosEnvioView> findAll() throws CatMotivosEnvioException {
      try {
         List<CatMotivosEnvio> catMotivosEnvioList = catMotivosEnvioRepository.findAll();
         List<CatMotivosEnvioView> catMotivosEnvioViewList = new ArrayList<>();
         for (CatMotivosEnvio cpl : catMotivosEnvioList) {
            catMotivosEnvioViewList.add(catMotivosEnvioConverter.toView(cpl, Boolean.TRUE));
         }
         return catMotivosEnvioViewList;
      } catch (Exception ex) {
         log.error("Error al obtener todos los registros - error: {}", ex);
         throw new CatMotivosEnvioException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener todos los registros");
      }
   }

   @Override
   public Page<CatMotivosEnvioView> getCatMotivosEnvioSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatMotivosEnvioException {
      try {
         log.info("===>>>getCatMotivosEnvioSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, activo, page, size, orderColumn, orderType);

         if (!colOrderNames.containsKey(orderColumn)) {
            orderColumn = "idCatMotivosEnvio";
         }

         List<CatMotivosEnvioView> catMotivosEnvioViewList = new ArrayList<>();
         Page<CatMotivosEnvio> catMotivosEnvioPage = null;
         log.info("===>>>getCatMotivosEnvioSearch() - creando SORT");
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));

         if (orderType.equalsIgnoreCase("asc")) {
            sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
         } else {
            sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
         }

         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatMotivosEnvio> spec = Specifications.where(
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

         log.info("===>>>getCatMotivosEnvioSearch() - realizando la búsqueda");
         catMotivosEnvioPage = catMotivosEnvioRepository.findAll(spec, request);

         catMotivosEnvioPage.getContent().forEach(catMotivosEnvio -> {
            catMotivosEnvioViewList.add(catMotivosEnvioConverter.toView(catMotivosEnvio, Boolean.TRUE));
         });

         PageImpl<CatMotivosEnvioView> catMotivosEnvioViewPage = new PageImpl<CatMotivosEnvioView>(catMotivosEnvioViewList, request, catMotivosEnvioPage.getTotalElements());
         return catMotivosEnvioViewPage;

      } catch (IllegalArgumentException iae) {
         log.error("===>>>Algún parámetro no es correcto: {}", iae);
         throw new CatMotivosEnvioException(HttpStatus.BAD_REQUEST, "Algún parámetro es null, vacío o incorrecto");
      } catch (Exception ex) {
         log.error("Error al obtener la lista paginable - error: {}", ex);
         throw new CatMotivosEnvioException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener obtener la lista paginable");
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }

   @Transactional(readOnly = false, rollbackFor = {CatMotivosEnvioException.class})
   @Override
   public CatMotivosEnvioView createCatMotivosEnvio(CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException {
      try {

         CatMotivosEnvio catMotivosEnvio = catMotivosEnvioConverter.toEntity(catMotivosEnvioView, new CatMotivosEnvio(), Boolean.FALSE);
         log.info("===>>>Insertar nuevo catMotivosEnvio: {}", catMotivosEnvio);
         catMotivosEnvioRepository.save(catMotivosEnvio);
         return catMotivosEnvioConverter.toView(catMotivosEnvio, Boolean.TRUE);

      } catch (ConstraintViolationException cve) {
         log.error("===>>>createCatMotivosEnvio() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatMotivosEnvioException(HttpStatus.BAD_REQUEST, String.format(CatMotivosEnvioException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>createCatMotivosEnvio() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatMotivosEnvioException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>createCatMotivosEnvio() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatMotivosEnvioException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMotivosEnvioException.SERVER_ERROR, "Crear"));
      }
   }

   @Transactional(readOnly = false, rollbackFor = {CatMotivosEnvioException.class})
   @Override
   public CatMotivosEnvioView updateCatMotivosEnvio(CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException {
      try {

         CatMotivosEnvio catMotivosEnvio = exists(catMotivosEnvioView.getIdCatMotivosEnvio());
         catMotivosEnvio = catMotivosEnvioConverter.toEntity(catMotivosEnvioView, catMotivosEnvio, Boolean.TRUE);
         log.info("===>>>updateCatMotivosEnvio() - Editar catMotivosEnvio: {}", catMotivosEnvio);
         catMotivosEnvioRepository.save(catMotivosEnvio);
         return catMotivosEnvioConverter.toView(catMotivosEnvio, Boolean.TRUE);

      } catch (CatMotivosEnvioException motenviE) {
         throw motenviE;
      } catch (ConstraintViolationException cve) {
         log.error("===>>>updateCatMotivosEnvio() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatMotivosEnvioException(HttpStatus.BAD_REQUEST, String.format(CatMotivosEnvioException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>updateCatMotivosEnvio() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatMotivosEnvioException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>updateCatMotivosEnvio() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatMotivosEnvioException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatMotivosEnvioException.SERVER_ERROR, "Crear"));
      }
   }
}

