package net.amentum.niomedic.catalogos.service.impl;


import net.amentum.niomedic.catalogos.converter.CatAreatrabajoConverter;
import net.amentum.niomedic.catalogos.exception.CatAreatrabajoException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatAreatrabajo;
import net.amentum.niomedic.catalogos.persistence.CatAreatrabajoRepository;
import net.amentum.niomedic.catalogos.service.CatAreatrabajoService;
import net.amentum.niomedic.catalogos.views.CatAreatrabajoView;
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

public class CatAreatrabajoServiceImpl implements CatAreatrabajoService {

    private final Logger logger = LoggerFactory.getLogger(CatAreatrabajoServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatAreatrabajoRepository CatAreatrabajoRepository;
    private CatAreatrabajoConverter CatAreatrabajoConverter;

    {
        colOrderNames.put("nombre", "nombre");
    }

    @Autowired
    public void setCatAreatrabajoRepository(CatAreatrabajoRepository CatAreatrabajoRepository) {
        this.CatAreatrabajoRepository = CatAreatrabajoRepository;
    }

    @Autowired
    public void setCatAreatrabajoConverter(CatAreatrabajoConverter CatAreatrabajoConverter) {
        this.CatAreatrabajoConverter = CatAreatrabajoConverter;
    }


      @Transactional(readOnly = false, rollbackFor = {CatAreatrabajoException.class})
    @Override
    public CatAreatrabajoView getDetailsByatrid(Integer atrid) throws CatAreatrabajoException {
        try {
            if (!CatAreatrabajoRepository.exists(atrid)) {
                logger.error("===>>>atrid no encontrado: {}", atrid);
                CatAreatrabajoException cAreaT = new CatAreatrabajoException("No se encuentra en el sistema CatAreatrabajo", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_VALIDATE);
                cAreaT.addError("atrid no encontrado: " + atrid);
                throw cAreaT;
            }
            CatAreatrabajo CatAreatrabajo = CatAreatrabajoRepository.findOne(atrid);
            return CatAreatrabajoConverter.toView(CatAreatrabajo, Boolean.TRUE);
        } catch (CatAreatrabajoException cAreaT) {
            throw cAreaT;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("Error en la validacion", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cAreaT.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cAreaT;
        } catch (DataIntegrityViolationException dive) {
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("No fue posible obtener  CatAreatrabajo", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_INSERT);
            cAreaT.addError("Ocurrio un error al obtener CatAreatrabajo");
            logger.error("===>>>Error al obtener CatAreatrabajo - CODE: {} - {}", cAreaT.getExceptionCode(), atrid, dive);
            throw cAreaT;
        } catch (Exception ex) {
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("Error inesperado al obtener  CatAreatrabajo", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_INSERT);
            cAreaT.addError("Ocurrio un error al obtener CatAreatrabajo");
            logger.error("===>>>Error al obtener CatAreatrabajo - CODE: {} - {}", cAreaT.getExceptionCode(), atrid, ex);
            throw cAreaT;
        }

    }

      @Transactional(readOnly = false, rollbackFor = {CatAreatrabajoException.class})
    @Override
    public List<CatAreatrabajoView> findAll() throws CatAreatrabajoException {
        try {
            List<CatAreatrabajo> CatAreatrabajoList = CatAreatrabajoRepository.findAll();
            List<CatAreatrabajoView> CatAreatrabajoViewList = new ArrayList<>();
            for (CatAreatrabajo cn : CatAreatrabajoList) {
                CatAreatrabajoViewList.add(CatAreatrabajoConverter.toView(cn, Boolean.TRUE));
            }
            return CatAreatrabajoViewList;
        } catch (Exception ex) {
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("Error inesperado al obtener todos los registros de  CatAreatrabajo", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_INSERT);
            cAreaT.addError("Ocurrio un error al obtener CatAreatrabajo");
            logger.error("===>>>Error al obtener todos los registros de CatAreatrabajo - CODE: {} - {}", cAreaT.getExceptionCode(), ex);
            throw cAreaT;
        }
    }


      @Transactional(readOnly = false, rollbackFor = {CatAreatrabajoException.class})
    @Override
    public Page<CatAreatrabajoView> getCatAreatrabajoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatAreatrabajoException {
        try {
            logger.info("===>>>getCatAreatrabajoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatAreatrabajoException cAreaT = new CatAreatrabajoException("No se encuentra en el sistema CatAreatrabajo", CatAreatrabajoException.LAYER_DAO, CatAreatrabajoException.ACTION_VALIDATE);
//            cAreaT.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cAreaT;
//         }

            List<CatAreatrabajoView> CatAreatrabajoViewList = new ArrayList<>();
            Page<CatAreatrabajo> CatAreatrabajoPage = null;
            Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nombre"));

            if (orderColumn != null && orderType != null) {
                if (orderType.equalsIgnoreCase("asc")) {
                    sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
                } else {
                    sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
                }
            }
            PageRequest request = new PageRequest(page, size, sort);
            final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
            Specifications<CatAreatrabajo> spec = Specifications.where(
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
                CatAreatrabajoPage = CatAreatrabajoRepository.findAll(request);
            } else {
                CatAreatrabajoPage = CatAreatrabajoRepository.findAll(spec, request);
            }

            CatAreatrabajoPage.getContent().forEach(CatAreatrabajo -> {
                CatAreatrabajoViewList.add(CatAreatrabajoConverter.toView(CatAreatrabajo, Boolean.TRUE));
            });
            PageImpl<CatAreatrabajoView> CatAreatrabajoViewPage = new PageImpl<CatAreatrabajoView>(CatAreatrabajoViewList, request, CatAreatrabajoPage.getTotalElements());
            return CatAreatrabajoViewPage;
//      } catch (CatAreatrabajoException cAreaT) {
//         throw cAreaT;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatAreatrabajoException pe = new CatAreatrabajoException("Algun parametro no es correcto:", CatAreatrabajoException.LAYER_SERVICE, CatAreatrabajoException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("Ocurrio un error al seleccionar lista CatAreatrabajo paginable", CatAreatrabajoException.LAYER_SERVICE, CatAreatrabajoException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatAreatrabajo paginable - CODE: {}", cAreaT.getExceptionCode(), ex);
            throw cAreaT;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
