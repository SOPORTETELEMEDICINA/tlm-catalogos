package net.amentum.niomedic.catalogos.service.impl;


import net.amentum.niomedic.catalogos.converter.CatJornadaConverter;
import net.amentum.niomedic.catalogos.exception.CatJornadaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatJornada;
import net.amentum.niomedic.catalogos.persistence.CatJornadaRepository;
import net.amentum.niomedic.catalogos.service.CatJornadaService;
import net.amentum.niomedic.catalogos.views.CatJornadaView;
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

public class CatJornadaServiceImpl implements CatJornadaService {

    private final Logger logger = LoggerFactory.getLogger(CatJornadaServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatJornadaRepository CatJornadaRepository;
    private CatJornadaConverter CatJornadaConverter;

    {
        colOrderNames.put("descripcion", "descripcion");
    }

    @Autowired
    public void setCatJornadaRepository(CatJornadaRepository CatJornadaRepository) {
        this.CatJornadaRepository = CatJornadaRepository;
    }

    @Autowired
    public void setCatJornadaConverter(CatJornadaConverter CatJornadaConverter) {
        this.CatJornadaConverter = CatJornadaConverter;
    }


     @Transactional(readOnly = false, rollbackFor = {CatJornadaException.class})
    @Override
    public CatJornadaView getDetailsByjorid(Integer jorid) throws CatJornadaException {
        try {
            if (!CatJornadaRepository.exists(jorid)) {
                logger.error("===>>>jorid no encontrado: {}", jorid);
                CatJornadaException cJor = new CatJornadaException("No se encuentra en el sistema CatJornada", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_VALIDATE);
                cJor.addError("jorid no encontrado: " + jorid);
                throw cJor;
            }
            CatJornada CatJornada = CatJornadaRepository.findOne(jorid);
            return CatJornadaConverter.toView(CatJornada, Boolean.TRUE);
        } catch (CatJornadaException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatJornadaException cJor = new CatJornadaException("Error en la validacion", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatJornadaException cJor = new CatJornadaException("No fue posible obtener  CatJornada", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatJornada");
            logger.error("===>>>Error al obtener CatJornada - CODE: {} - {}", cJor.getExceptionCode(), jorid, dive);
            throw cJor;
        } catch (Exception ex) {
            CatJornadaException cJor = new CatJornadaException("Error inesperado al obtener  CatJornada", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatJornada");
            logger.error("===>>>Error al obtener CatJornada - CODE: {} - {}", cJor.getExceptionCode(), jorid, ex);
            throw cJor;
        }

    }

    @Transactional(readOnly = false, rollbackFor = {CatJornadaException.class})
    @Override
    public List<CatJornadaView> findAll() throws CatJornadaException {
        try {
            List<CatJornada> CatJornadaList = CatJornadaRepository.findAll();
            List<CatJornadaView> CatJornadaViewList = new ArrayList<>();
            for (CatJornada cn : CatJornadaList) {
                CatJornadaViewList.add(CatJornadaConverter.toView(cn, Boolean.TRUE));
            }
            return CatJornadaViewList;
        } catch (Exception ex) {
            CatJornadaException cJor = new CatJornadaException("Error inesperado al obtener todos los registros de  CatJornada", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatJornada");
            logger.error("===>>>Error al obtener todos los registros de CatJornada - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }


     @Transactional(readOnly = false, rollbackFor = {CatJornadaException.class})
    @Override
    public Page<CatJornadaView> getCatJornadaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatJornadaException {
        try {
            logger.info("===>>>getCatJornadaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatJornadaException cJor = new CatJornadaException("No se encuentra en el sistema CatJornada", CatJornadaException.LAYER_DAO, CatJornadaException.ACTION_VALIDATE);
//            cJor.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cJor;
//         }

            List<CatJornadaView> CatJornadaViewList = new ArrayList<>();
            Page<CatJornada> CatJornadaPage = null;
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
            Specifications<CatJornada> spec = Specifications.where(
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
                CatJornadaPage = CatJornadaRepository.findAll(request);
            } else {
                CatJornadaPage = CatJornadaRepository.findAll(spec, request);
            }

            CatJornadaPage.getContent().forEach(CatJornada -> {
                CatJornadaViewList.add(CatJornadaConverter.toView(CatJornada, Boolean.TRUE));
            });
            PageImpl<CatJornadaView> CatJornadaViewPage = new PageImpl<CatJornadaView>(CatJornadaViewList, request, CatJornadaPage.getTotalElements());
            return CatJornadaViewPage;
//      } catch (CatJornadaException cJor) {
//         throw cJor;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatJornadaException pe = new CatJornadaException("Algun parametro no es correcto:", CatJornadaException.LAYER_SERVICE, CatJornadaException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatJornadaException cJor = new CatJornadaException("Ocurrio un error al seleccionar lista CatJornada paginable", CatJornadaException.LAYER_SERVICE, CatJornadaException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatJornada paginable - CODE: {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
