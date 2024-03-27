package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatActividadprinConverter;
import net.amentum.niomedic.catalogos.exception.CatActividadprinException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatActividadprin;
import net.amentum.niomedic.catalogos.persistence.CatActividadprinRepository;
import net.amentum.niomedic.catalogos.service.CatActividadprinService;
import net.amentum.niomedic.catalogos.views.CatActividadprinView;
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

public class CatActividadprinServiceImpl implements CatActividadprinService {

    private final Logger logger = LoggerFactory.getLogger(CatActividadprinServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatActividadprinRepository CatActividadprinRepository;
    private CatActividadprinConverter CatActividadprinConverter;

    {
         colOrderNames.put("descripcion", "descripcion");
    }

    @Autowired
    public void setCatActividadprinRepository(CatActividadprinRepository catActividadprinRepository) {
        this.CatActividadprinRepository = catActividadprinRepository;
    }

    @Autowired
    public void setCatActividadprinConverter(CatActividadprinConverter CatActividadprinConverter) {
        this.CatActividadprinConverter = CatActividadprinConverter;
    }


     @Transactional(readOnly = false, rollbackFor = {CatActividadprinException.class})
    @Override
    public CatActividadprinView getDetailsByactid(Integer actid) throws CatActividadprinException {
        try {
            if (!CatActividadprinRepository.exists(actid)) {
                logger.error("===>>>actid no encontrado: {}", actid);
                CatActividadprinException CActp = new CatActividadprinException("No se encuentra en el sistema CatActividadprin", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_VALIDATE);
                CActp.addError("actid no encontrado: " + actid);
                throw CActp;
            }
            CatActividadprin CatActividadprin = CatActividadprinRepository.findOne(actid);
            return CatActividadprinConverter.toView(CatActividadprin, Boolean.TRUE);
        } catch (CatActividadprinException CActp) {
            throw CActp;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatActividadprinException CActp = new CatActividadprinException("Error en la validacion", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                CActp.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw CActp;
        } catch (DataIntegrityViolationException dive) {
            CatActividadprinException CActp = new CatActividadprinException("No fue posible obtener  CatActividadprin", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_INSERT);
            CActp.addError("Ocurrio un error al obtener CatActividadprin");
            logger.error("===>>>Error al obtener CatActividadprin - CODE: {} - {}", CActp.getExceptionCode(), actid, dive);
            throw CActp;
        } catch (Exception ex) {
            CatActividadprinException CActp = new CatActividadprinException("Error inesperado al obtener  CatActividadprin", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_INSERT);
            CActp.addError("Ocurrio un error al obtener CatActividadprin");
            logger.error("===>>>Error al obtener CatActividadprin - CODE: {} - {}", CActp.getExceptionCode(), actid, ex);
            throw CActp;
        }

    }

       @Transactional(readOnly = false, rollbackFor = {CatActividadprinException.class})
    @Override
    public List<CatActividadprinView> findAll() throws CatActividadprinException {
        try {
            List<CatActividadprin> CatActividadprinList = CatActividadprinRepository.findAll();
            List<CatActividadprinView> CatActividadprinViewList = new ArrayList<>();
            for (CatActividadprin cn : CatActividadprinList) {
                CatActividadprinViewList.add(CatActividadprinConverter.toView(cn, Boolean.TRUE));
            }
            return CatActividadprinViewList;
        } catch (Exception ex) {
            CatActividadprinException CActp = new CatActividadprinException("Error inesperado al obtener todos los registros de  CatActividadprin", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_INSERT);
            CActp.addError("Ocurrio un error al obtener CatActividadprin");
            logger.error("===>>>Error al obtener todos los registros de CatActividadprin - CODE: {} - {}", CActp.getExceptionCode(), ex);
            throw CActp;
        }
    }


      @Transactional(readOnly = false, rollbackFor = {CatActividadprinException.class})
    @Override
    public Page<CatActividadprinView> getCatActividadprinSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatActividadprinException {
        try {
            logger.info("===>>>getCatActividadprinSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatActividadprinException CActp = new CatActividadprinException("No se encuentra en el sistema CatActividadprin", CatActividadprinException.LAYER_DAO, CatActividadprinException.ACTION_VALIDATE);
//            CActp.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw CActp;
//         }

            List<CatActividadprinView> CatActividadprinViewList = new ArrayList<>();
            Page<CatActividadprin> CatActividadprinPage = null;
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
            Specifications<CatActividadprin> spec = Specifications.where(
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
                CatActividadprinPage = CatActividadprinRepository.findAll(request);
            } else {
                CatActividadprinPage = CatActividadprinRepository.findAll(spec, request);
            }

            CatActividadprinPage.getContent().forEach(CatActividadprin -> {
                CatActividadprinViewList.add(CatActividadprinConverter.toView(CatActividadprin, Boolean.TRUE));
            });
            PageImpl<CatActividadprinView> CatActividadprinViewPage = new PageImpl<CatActividadprinView>(CatActividadprinViewList, request, CatActividadprinPage.getTotalElements());
            return CatActividadprinViewPage;
//      } catch (CatActividadprinException CActp) {
//         throw CActp;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatActividadprinException pe = new CatActividadprinException("Algun parametro no es correcto:", CatActividadprinException.LAYER_SERVICE, CatActividadprinException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatActividadprinException CActp = new CatActividadprinException("Ocurrio un error al seleccionar lista CatActividadprin paginable", CatActividadprinException.LAYER_SERVICE, CatActividadprinException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatActividadprin paginable - CODE: {}", CActp.getExceptionCode(), ex);
            throw CActp;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
