package net.amentum.niomedic.catalogos.service.impl;


import net.amentum.niomedic.catalogos.converter.CatTipoplazaConverter;
import net.amentum.niomedic.catalogos.exception.CatTipoplazaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatTipoplaza;
import net.amentum.niomedic.catalogos.persistence.CatTipoplazaRepository;
import net.amentum.niomedic.catalogos.service.CatTipoplazaService;
import net.amentum.niomedic.catalogos.views.CatTipoplazaView;
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

public class CatTipoplazaServiceImpl implements CatTipoplazaService{

    private final Logger logger = LoggerFactory.getLogger(CatTipoplazaServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatTipoplazaRepository CatTipoplazaRepository;
    private CatTipoplazaConverter CatTipoplazaConverter;

    {
        colOrderNames.put("tipo", "tipo");

    }

    @Autowired
    public void setCatTipoplazaRepository(CatTipoplazaRepository CatTipoplazaRepository) {
        this.CatTipoplazaRepository = CatTipoplazaRepository;
    }

    @Autowired
    public void setCatTipoplazaConverter(CatTipoplazaConverter CatTipoplazaConverter) {
        this.CatTipoplazaConverter = CatTipoplazaConverter;
    }


       @Transactional(readOnly = false, rollbackFor = {CatTipoplazaException.class})
    @Override
    public CatTipoplazaView getDetailsByplaid(Integer plaid) throws CatTipoplazaException {
        try {
            if (!CatTipoplazaRepository.exists(plaid)) {
                logger.error("===>>>plaid no encontrado: {}", plaid);
                CatTipoplazaException cTipP = new CatTipoplazaException("No se encuentra en el sistema CatTipoplaza", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_VALIDATE);
                cTipP.addError("plaid no encontrado: " + plaid);
                throw cTipP;
            }
            CatTipoplaza CatTipoplaza = CatTipoplazaRepository.findOne(plaid);
            return CatTipoplazaConverter.toView(CatTipoplaza, Boolean.TRUE);
        } catch (CatTipoplazaException cTipP) {
            throw cTipP;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatTipoplazaException cTipP = new CatTipoplazaException("Error en la validacion", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cTipP.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cTipP;
        } catch (DataIntegrityViolationException dive) {
            CatTipoplazaException cTipP = new CatTipoplazaException("No fue posible obtener  CatTipoplaza", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_INSERT);
            cTipP.addError("Ocurrio un error al obtener CatTipoplaza");
            logger.error("===>>>Error al obtener CatTipoplaza - CODE: {} - {}", cTipP.getExceptionCode(), plaid, dive);
            throw cTipP;
        } catch (Exception ex) {
            CatTipoplazaException cTipP = new CatTipoplazaException("Error inesperado al obtener  CatTipoplaza", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_INSERT);
            cTipP.addError("Ocurrio un error al obtener CatTipoplaza");
            logger.error("===>>>Error al obtener CatTipoplaza - CODE: {} - {}", cTipP.getExceptionCode(), plaid, ex);
            throw cTipP;
        }

    }

       @Transactional(readOnly = false, rollbackFor = {CatTipoplazaException.class})
    @Override
    public List<CatTipoplazaView> findAll() throws CatTipoplazaException {
        try {
            List<CatTipoplaza> CatTipoplazaList = CatTipoplazaRepository.findAll();
            List<CatTipoplazaView> CatTipoplazaViewList = new ArrayList<>();
            for (CatTipoplaza cn : CatTipoplazaList) {
                CatTipoplazaViewList.add(CatTipoplazaConverter.toView(cn, Boolean.TRUE));
            }
            return CatTipoplazaViewList;
        } catch (Exception ex) {
            CatTipoplazaException cTipP = new CatTipoplazaException("Error inesperado al obtener todos los registros de  CatTipoplaza", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_INSERT);
            cTipP.addError("Ocurrio un error al obtener CatTipoplaza");
            logger.error("===>>>Error al obtener todos los registros de CatTipoplaza - CODE: {} - {}", cTipP.getExceptionCode(), ex);
            throw cTipP;
        }
    }


      @Transactional(readOnly = false, rollbackFor = {CatTipoplazaException.class})
    @Override
    public Page<CatTipoplazaView> getCatTipoplazaSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatTipoplazaException {
        try {
            logger.info("===>>>getCatTipoplazaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatTipoplazaException cTipP = new CatTipoplazaException("No se encuentra en el sistema CatTipoplaza", CatTipoplazaException.LAYER_DAO, CatTipoplazaException.ACTION_VALIDATE);
//            cTipP.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cTipP;
//         }

            List<CatTipoplazaView> CatTipoplazaViewList = new ArrayList<>();
            Page<CatTipoplaza> CatTipoplazaPage = null;
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
            Specifications<CatTipoplaza> spec = Specifications.where(
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
                CatTipoplazaPage = CatTipoplazaRepository.findAll(request);
            } else {
                CatTipoplazaPage = CatTipoplazaRepository.findAll(spec, request);
            }

            CatTipoplazaPage.getContent().forEach(CatTipoplaza -> {
                CatTipoplazaViewList.add(CatTipoplazaConverter.toView(CatTipoplaza, Boolean.TRUE));
            });
            PageImpl<CatTipoplazaView> CatTipoplazaViewPage = new PageImpl<CatTipoplazaView>(CatTipoplazaViewList, request, CatTipoplazaPage.getTotalElements());
            return CatTipoplazaViewPage;
//      } catch (CatTipoplazaException cTipP) {
//         throw cTipP;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatTipoplazaException pe = new CatTipoplazaException("Algun parametro no es correcto:", CatTipoplazaException.LAYER_SERVICE, CatTipoplazaException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatTipoplazaException cTipP = new CatTipoplazaException("Ocurrio un error al seleccionar lista CatTipoplaza paginable", CatTipoplazaException.LAYER_SERVICE, CatTipoplazaException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatTipoplaza paginable - CODE: {}", cTipP.getExceptionCode(), ex);
            throw cTipP;
        }
    }

    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


}
