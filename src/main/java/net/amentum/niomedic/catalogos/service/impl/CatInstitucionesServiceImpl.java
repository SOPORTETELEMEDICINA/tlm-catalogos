package net.amentum.niomedic.catalogos.service.impl;


import net.amentum.niomedic.catalogos.converter.CatInstitucionesConverter;
import net.amentum.niomedic.catalogos.exception.CatInstitucionesException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatInstituciones;
import net.amentum.niomedic.catalogos.persistence.CatInstitucionesRepository;
import net.amentum.niomedic.catalogos.service.CatInstitucionesService;
import net.amentum.niomedic.catalogos.views.CatInstitucionesView;
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

public class CatInstitucionesServiceImpl implements CatInstitucionesService{

    private final Logger logger = LoggerFactory.getLogger(CatInstitucionesServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatInstitucionesRepository CatInstitucionesRepository;
    private CatInstitucionesConverter CatInstitucionesConverter;

    {
        colOrderNames.put("instcve", "instcve");
        colOrderNames.put("instcvecorta", "instcvecorta");
        colOrderNames.put("instnombre", "instnombre");
    }

    @Autowired
    public void setCatInstitucionesRepository(CatInstitucionesRepository catInstitucionesRepository) {
        this.CatInstitucionesRepository = catInstitucionesRepository;
    }

    @Autowired
    public void setCatInstitucionesConverter(CatInstitucionesConverter catInstitucionesConverter) {
        this.CatInstitucionesConverter = catInstitucionesConverter;
    }


       @Transactional(readOnly = false, rollbackFor = {CatInstitucionesException.class})
    @Override
    public CatInstitucionesView getDetailsByidinstitucion(Integer idinstitucion) throws CatInstitucionesException {
        try {
            if (!CatInstitucionesRepository.exists(idinstitucion)) {
                logger.error("===>>>idinstitucion no encontrado: {}", idinstitucion);
                CatInstitucionesException cInt = new CatInstitucionesException("No se encuentra en el sistema CatInstituciones", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_VALIDATE);
                cInt.addError("idinstitucion no encontrado: " + idinstitucion);
                throw cInt;
            }
            CatInstituciones CatInstituciones = CatInstitucionesRepository.findOne(idinstitucion);
            return CatInstitucionesConverter.toView(CatInstituciones, Boolean.TRUE);
        } catch (CatInstitucionesException cInt) {
            throw cInt;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatInstitucionesException cInt = new CatInstitucionesException("Error en la validacion", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cInt.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cInt;
        } catch (DataIntegrityViolationException dive) {
            CatInstitucionesException cInt = new CatInstitucionesException("No fue posible obtener  CatInstituciones", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_INSERT);
            cInt.addError("Ocurrio un error al obtener CatInstituciones");
            logger.error("===>>>Error al obtener CatInstituciones - CODE: {} - {}", cInt.getExceptionCode(), idinstitucion, dive);
            throw cInt;
        } catch (Exception ex) {
            CatInstitucionesException cInt = new CatInstitucionesException("Error inesperado al obtener  CatInstituciones", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_INSERT);
            cInt.addError("Ocurrio un error al obtener CatInstituciones");
            logger.error("===>>>Error al obtener CatInstituciones - CODE: {} - {}", cInt.getExceptionCode(), idinstitucion, ex);
            throw cInt;
        }

    }

       @Transactional(readOnly = false, rollbackFor = {CatInstitucionesException.class})
    @Override
    public List<CatInstitucionesView> findAll() throws CatInstitucionesException {
        try {
            List<CatInstituciones> CatInstitucionesList = CatInstitucionesRepository.findAll();
            List<CatInstitucionesView> CatInstitucionesViewList = new ArrayList<>();
            for (CatInstituciones cn : CatInstitucionesList) {
                CatInstitucionesViewList.add(CatInstitucionesConverter.toView(cn, Boolean.TRUE));
            }
            return CatInstitucionesViewList;
        } catch (Exception ex) {
            CatInstitucionesException cInt = new CatInstitucionesException("Error inesperado al obtener todos los registros de  CatInstituciones", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_INSERT);
            cInt.addError("Ocurrio un error al obtener CatInstituciones");
            logger.error("===>>>Error al obtener todos los registros de CatInstituciones - CODE: {} - {}", cInt.getExceptionCode(), ex);
            throw cInt;
        }
    }


       @Transactional(readOnly = false, rollbackFor = {CatInstitucionesException.class})
    @Override
    public Page<CatInstitucionesView> getCatInstitucionesSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatInstitucionesException {
        try {
            logger.info("===>>>getCatInstitucionesSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatInstitucionesException cInt = new CatInstitucionesException("No se encuentra en el sistema CatInstituciones", CatInstitucionesException.LAYER_DAO, CatInstitucionesException.ACTION_VALIDATE);
//            cInt.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cInt;
//         }

            List<CatInstitucionesView> CatInstitucionesViewList = new ArrayList<>();
            Page<CatInstituciones> CatInstitucionesPage = null;
            Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));


            if (orderColumn != null && orderType != null) {
                if (orderType.equalsIgnoreCase("asc")) {
                    sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
                } else {
                    sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
                }
            }
            PageRequest request = new PageRequest(page, size, sort);
            final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
            Specifications<CatInstituciones> spec = Specifications.where(
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
                CatInstitucionesPage = CatInstitucionesRepository.findAll(request);
            } else {
                CatInstitucionesPage = CatInstitucionesRepository.findAll(spec, request);
            }

            CatInstitucionesPage.getContent().forEach(CatInstituciones -> {
                CatInstitucionesViewList.add(CatInstitucionesConverter.toView(CatInstituciones, Boolean.TRUE));
            });
            PageImpl<CatInstitucionesView> CatInstitucionesViewPage = new PageImpl<CatInstitucionesView>(CatInstitucionesViewList, request, CatInstitucionesPage.getTotalElements());
            return CatInstitucionesViewPage;
//      } catch (CatInstitucionesException cInt) {
//         throw cInt;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatInstitucionesException pe = new CatInstitucionesException("Algun parametro no es correcto:", CatInstitucionesException.LAYER_SERVICE, CatInstitucionesException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatInstitucionesException cInt = new CatInstitucionesException("Ocurrio un error al seleccionar lista CatInstituciones paginable", CatInstitucionesException.LAYER_SERVICE, CatInstitucionesException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatInstituciones paginable - CODE: {}", cInt.getExceptionCode(), ex);
            throw cInt;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
