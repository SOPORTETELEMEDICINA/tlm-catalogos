package net.amentum.niomedic.catalogos.service.impl;



import net.amentum.niomedic.catalogos.converter.CatTipocontratoConverter;
import net.amentum.niomedic.catalogos.exception.CatTipocontratoException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTipocontrato;
import net.amentum.niomedic.catalogos.persistence.CatTipocontratoRepository;
import net.amentum.niomedic.catalogos.service.CatTipocontratoService;
import net.amentum.niomedic.catalogos.views.CatTipocontratoView;
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

public class CatTipocontratoServiceImpl implements CatTipocontratoService {

    private final Logger logger = LoggerFactory.getLogger(CatTipocontratoServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatTipocontratoRepository CatTipocontratoRepository;
    private CatTipocontratoConverter CatTipocontratoConverter;

    {
        colOrderNames.put("tipo", "tipo");

    }

    @Autowired
    public void setCatTipocontratoRepository(CatTipocontratoRepository CatTipocontratoRepository) {
        this.CatTipocontratoRepository = CatTipocontratoRepository;
    }

    @Autowired
    public void setCatTipocontratoConverter(CatTipocontratoConverter CatTipocontratoConverter) {
        this.CatTipocontratoConverter = CatTipocontratoConverter;
    }


       @Transactional(readOnly = false, rollbackFor = {CatTipocontratoException.class})
    @Override
    public CatTipocontratoView getDetailsByconid(Integer conid) throws CatTipocontratoException {
        try {
            if (!CatTipocontratoRepository.exists(conid)) {
                logger.error("===>>>conid no encontrado: {}", conid);
                CatTipocontratoException cTipC = new CatTipocontratoException("No se encuentra en el sistema CatTipocontrato", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_VALIDATE);
                cTipC.addError("conid no encontrado: " + conid);
                throw cTipC;
            }
            CatTipocontrato CatTipocontrato = CatTipocontratoRepository.findOne(conid);
            return CatTipocontratoConverter.toView(CatTipocontrato, Boolean.TRUE);
        } catch (CatTipocontratoException cTipC) {
            throw cTipC;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatTipocontratoException cTipC = new CatTipocontratoException("Error en la validacion", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cTipC.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cTipC;
        } catch (DataIntegrityViolationException dive) {
            CatTipocontratoException cTipC = new CatTipocontratoException("No fue posible obtener  CatTipocontrato", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_INSERT);
            cTipC.addError("Ocurrio un error al obtener CatTipocontrato");
            logger.error("===>>>Error al obtener CatTipocontrato - CODE: {} - {}", cTipC.getExceptionCode(), conid, dive);
            throw cTipC;
        } catch (Exception ex) {
            CatTipocontratoException cTipC = new CatTipocontratoException("Error inesperado al obtener  CatTipocontrato", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_INSERT);
            cTipC.addError("Ocurrio un error al obtener CatTipocontrato");
            logger.error("===>>>Error al obtener CatTipocontrato - CODE: {} - {}", cTipC.getExceptionCode(), conid, ex);
            throw cTipC;
        }

    }

       @Transactional(readOnly = false, rollbackFor = {CatTipocontratoException.class})
    @Override
    public List<CatTipocontratoView> findAll() throws CatTipocontratoException {
        try {
            List<CatTipocontrato> CatTipocontratoList = CatTipocontratoRepository.findAll();
            List<CatTipocontratoView> CatTipocontratoViewList = new ArrayList<>();
            for (CatTipocontrato cn : CatTipocontratoList) {
                CatTipocontratoViewList.add(CatTipocontratoConverter.toView(cn, Boolean.TRUE));
            }
            return CatTipocontratoViewList;
        } catch (Exception ex) {
            CatTipocontratoException cTipC = new CatTipocontratoException("Error inesperado al obtener todos los registros de  CatTipocontrato", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_INSERT);
            cTipC.addError("Ocurrio un error al obtener CatTipocontrato");
            logger.error("===>>>Error al obtener todos los registros de CatTipocontrato - CODE: {} - {}", cTipC.getExceptionCode(), ex);
            throw cTipC;
        }
    }


      @Transactional(readOnly = false, rollbackFor = {CatTipocontratoException.class})
    @Override
    public Page<CatTipocontratoView> getCatTipocontratoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipocontratoException {
        try {
            logger.info("===>>>getCatTipocontratoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTipocontratoException cTipC = new CatTipocontratoException("No se encuentra en el sistema CatTipocontrato", CatTipocontratoException.LAYER_DAO, CatTipocontratoException.ACTION_VALIDATE);
//            cTipC.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cTipC;
//         }

            List<CatTipocontratoView> CatTipocontratoViewList = new ArrayList<>();
            Page<CatTipocontrato> CatTipocontratoPage = null;
            Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("tipo"));

            if (orderColumn != null && orderType != null) {
                if (orderType.equalsIgnoreCase("asc")) {
                    sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
                } else {
                    sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
                }
            }
            PageRequest request = new PageRequest(page, size, sort);
            final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
            Specifications<CatTipocontrato> spec = Specifications.where(
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
                CatTipocontratoPage = CatTipocontratoRepository.findAll(request);
            } else {
                CatTipocontratoPage = CatTipocontratoRepository.findAll(spec, request);
            }

            CatTipocontratoPage.getContent().forEach(CatTipocontrato -> {
                CatTipocontratoViewList.add(CatTipocontratoConverter.toView(CatTipocontrato, Boolean.TRUE));
            });
            PageImpl<CatTipocontratoView> CatTipocontratoViewPage = new PageImpl<CatTipocontratoView>(CatTipocontratoViewList, request, CatTipocontratoPage.getTotalElements());
            return CatTipocontratoViewPage;
//      } catch (CatTipocontratoException cTipC) {
//         throw cTipC;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatTipocontratoException pe = new CatTipocontratoException("Algun parametro no es correcto:", CatTipocontratoException.LAYER_SERVICE, CatTipocontratoException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatTipocontratoException cTipC = new CatTipocontratoException("Ocurrio un error al seleccionar lista CatTipocontrato paginable", CatTipocontratoException.LAYER_SERVICE, CatTipocontratoException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTipocontrato paginable - CODE: {}", cTipC.getExceptionCode(), ex);
            throw cTipC;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
