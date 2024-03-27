package net.amentum.niomedic.catalogos.service.impl;


import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.catalogos.converter.CatCifConverter;
import net.amentum.niomedic.catalogos.exception.CatCifException;
import net.amentum.niomedic.catalogos.model.CatCif;
import net.amentum.niomedic.catalogos.persistence.CatCifRepository;
import net.amentum.niomedic.catalogos.service.CatCifService;
import net.amentum.niomedic.catalogos.views.CatCifView;
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
public class CatCifServiceImpl implements CatCifService {

   private final Map<String, Object> colOrderNames = new HashMap<>();
   private CatCifRepository catCifRepository;
   private CatCifConverter catCifConverter;


   {
      colOrderNames.put("idCatCif", "idCatCif");
      colOrderNames.put("discCodigo", "discCodigo");
      colOrderNames.put("discDescripcion", "discDescripcion");
      colOrderNames.put("activo", "activo");
   }

   @Autowired
   public void setCatCifRepository(CatCifRepository catCifRepository) {
      this.catCifRepository = catCifRepository;
   }

   @Autowired
   public void setCatCifConverter(CatCifConverter catCifConverter) {
      this.catCifConverter = catCifConverter;
   }

   private CatCif exists(Integer idCatCif) throws CatCifException {
      log.info("===>>>exists() - revisando si existe el cif por id: {}", idCatCif);
      if (!catCifRepository.exists(idCatCif)) {
         log.error("===>>>exists() - No se encuentra el idCatCif: {}", idCatCif);
         throw new CatCifException(HttpStatus.NOT_FOUND, String.format(CatCifException.ITEM_NOT_FOUND, idCatCif));
      }
      return catCifRepository.findOne(idCatCif);
   }


   @Override
   public CatCifView getDetailsByIdCatCif(Integer idCatCif) throws CatCifException {
      try {
         CatCif catCif = exists(idCatCif);
         log.info("===>>>getDetailsByIdCif() - {}", catCif);
         return catCifConverter.toView(catCif, Boolean.TRUE);
      } catch (CatCifException cife) {
         throw cife;
      } catch (Exception ex) {
         log.error("Error al obtener registro - error: {}", ex);
         throw new CatCifException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatCifException.SERVER_ERROR, "obtener por ID"));
      }
   }

   @Override
   public List<CatCifView> findAll() throws CatCifException {
      try {
         List<CatCif> catCifList = catCifRepository.findAll();
         List<CatCifView> catCifViewList = new ArrayList<>();
         for (CatCif cpl : catCifList) {
            catCifViewList.add(catCifConverter.toView(cpl, Boolean.TRUE));
         }
         return catCifViewList;
      } catch (Exception ex) {
         log.error("Error al obtener todos los registros - error: {}", ex);
         throw new CatCifException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener todos los registros");
      }
   }

   @Override
   public Page<CatCifView> getCatCifSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatCifException {
      try {
         log.info("===>>>getCatCifSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
            datosBusqueda, activo, page, size, orderColumn, orderType);

         if (!colOrderNames.containsKey(orderColumn)) {
            orderColumn = "idCatCif";
         }

         List<CatCifView> catCifViewList = new ArrayList<>();
         Page<CatCif> catCifPage = null;
         log.info("===>>>getCatCifPage() - creando SORT");
         Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));

         if (orderType.equalsIgnoreCase("asc")) {
            sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
         } else {
            sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
         }

         PageRequest request = new PageRequest(page, size, sort);
         final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
         Specifications<CatCif> spec = Specifications.where(
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

         log.info("===>>>getCatCifPage() - realizando la búsqueda");
         catCifPage = catCifRepository.findAll(spec, request);

         catCifPage.getContent().forEach(catCif -> {
            catCifViewList.add(catCifConverter.toView(catCif, Boolean.TRUE));
         });

         PageImpl<CatCifView> catCifViewPage = new PageImpl<CatCifView>(catCifViewList, request, catCifPage.getTotalElements());
         return catCifViewPage;

      } catch (IllegalArgumentException iae) {
         log.error("===>>>Algún parámetro no es correcto: {}", iae);
         throw new CatCifException(HttpStatus.BAD_REQUEST, "Algún parámetro es null, vacío o incorrecto");
      } catch (Exception ex) {
         log.error("Error al obtener la lista paginable - error: {}", ex);
         throw new CatCifException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener obtener la lista paginable");
      }
   }

   private String sinAcentos(String cadena) {
      return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
   }

   @Transactional(readOnly = false, rollbackFor = {CatCifException.class})
   @Override
   public CatCifView createCatCif(CatCifView catCifView) throws CatCifException {
      try {

         CatCif catCif = catCifConverter.toEntity(catCifView, new CatCif(), Boolean.FALSE);
         log.info("===>>>Insertar nuevo catCif: {}", catCif);
         catCifRepository.save(catCif);
         return catCifConverter.toView(catCif, Boolean.TRUE);

      } catch (ConstraintViolationException cve) {
         log.error("===>>>createCatCif() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatCifException(HttpStatus.BAD_REQUEST, String.format(CatCifException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>createCatCif() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatCifException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>createCatCif() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatCifException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatCifException.SERVER_ERROR, "Crear"));
      }
   }

   @Transactional(readOnly = false, rollbackFor = {CatCifException.class})
   @Override
   public CatCifView updateCatCif(CatCifView catCifView) throws CatCifException {
      try {

         CatCif catCif = exists(catCifView.getIdCatCif());
         catCif = catCifConverter.toEntity(catCifView, catCif, Boolean.TRUE);
         log.info("===>>>updateCatCif() - Editar catCif: {}", catCif);
         catCifRepository.save(catCif);
         return catCifConverter.toView(catCif, Boolean.TRUE);

      } catch (CatCifException cife) {
         throw cife;
      } catch (ConstraintViolationException cve) {
         log.error("===>>>updateCatCif() - Ocurrió un error al validar algunos campos - error :{}", cve);
         throw new CatCifException(HttpStatus.BAD_REQUEST, String.format(CatCifException.SERVER_ERROR, "Crear"));
      } catch (DataIntegrityViolationException dive) {
         log.error("===>>>updateCatCif() - Ocurrió un error de integridad - error :{}", dive);
         throw new CatCifException(HttpStatus.CONFLICT, dive.getCause().getMessage());
      } catch (Exception ex) {
         log.error("===>>>updateCatCif() -  Ocurrio un error inesperado - error:{}", ex);
         throw new CatCifException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatCifException.SERVER_ERROR, "Crear"));
      }
   }
}

