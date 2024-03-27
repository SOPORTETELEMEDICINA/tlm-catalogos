package net.amentum.niomedic.catalogos.service.impl;


import net.amentum.niomedic.catalogos.converter.CatPersonalmedicoConverter;
import net.amentum.niomedic.catalogos.exception.CatPersonalmedicoException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatPersonalmedico;
import net.amentum.niomedic.catalogos.persistence.CatPersonalmedicoRepository;
import net.amentum.niomedic.catalogos.service.CatPersonalmedicoService;
import net.amentum.niomedic.catalogos.views.CatPersonalmedicoView;
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

public class CatPersonalmedicoServiceImpl implements CatPersonalmedicoService {

        private final Logger logger = LoggerFactory.getLogger(CatPersonalmedicoServiceImpl.class);
        private final Map<String, Object> colOrderNames = new HashMap<>();

        private CatPersonalmedicoRepository CatPersonalmedicoRepository;
        private CatPersonalmedicoConverter CatPersonalmedicoConverter;

        {
            colOrderNames.put("descripcion", "descripcion");
        }

        @Autowired
        public void setCatPersonalmedicoRepository(CatPersonalmedicoRepository catPersonalmedicoRepository) {
            this.CatPersonalmedicoRepository = catPersonalmedicoRepository ;
        }

        @Autowired
        public void setCatPersonalmedicoConverter(CatPersonalmedicoConverter catPersonalmedicoConverter) {
            this.CatPersonalmedicoConverter = catPersonalmedicoConverter;
        }


           @Transactional(readOnly = false, rollbackFor = {CatPersonalmedicoException.class})
        @Override
        public CatPersonalmedicoView getDetailsByperid(Integer perid) throws CatPersonalmedicoException {
            try {
                if (!CatPersonalmedicoRepository.exists(perid)) {
                    logger.error("===>>>perid no encontrado: {}", perid);
                    CatPersonalmedicoException cPerM = new CatPersonalmedicoException("No se encuentra en el sistema CatPersonalmedico", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_VALIDATE);
                    cPerM.addError("perid no encontrado: " + perid);
                    throw cPerM;
                }
                CatPersonalmedico CatPersonalmedico = CatPersonalmedicoRepository.findOne(perid);
                return CatPersonalmedicoConverter.toView(CatPersonalmedico, Boolean.TRUE);
            } catch (CatPersonalmedicoException cPerM) {
                throw cPerM;
            } catch (ConstraintViolationException cve) {
                logger.error("===>>>Error en la validacion");
                CatPersonalmedicoException cPerM = new CatPersonalmedicoException("Error en la validacion", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_VALIDATE);
                final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
                for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                    ConstraintViolation<?> siguiente = iterator.next();
                    cPerM.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
                }
                throw cPerM;
            } catch (DataIntegrityViolationException dive) {
                CatPersonalmedicoException cPerM = new CatPersonalmedicoException("No fue posible obtener  CatPersonalmedico", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_INSERT);
                cPerM.addError("Ocurrio un error al obtener CatPersonalmedico");
                logger.error("===>>>Error al obtener CatPersonalmedico - CODE: {} - {}", cPerM.getExceptionCode(), perid, dive);
                throw cPerM;
            } catch (Exception ex) {
                CatPersonalmedicoException cPerM = new CatPersonalmedicoException("Error inesperado al obtener  CatPersonalmedico", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_INSERT);
                cPerM.addError("Ocurrio un error al obtener CatPersonalmedico");
                logger.error("===>>>Error al obtener CatPersonalmedico - CODE: {} - {}", cPerM.getExceptionCode(), perid, ex);
                throw cPerM;
            }

        }

          @Transactional(readOnly = false, rollbackFor = {CatPersonalmedicoException.class})
        @Override
        public List<CatPersonalmedicoView> findAll() throws CatPersonalmedicoException {
            try {
                List<CatPersonalmedico> CatPersonalmedicoList = CatPersonalmedicoRepository.findAll();
                List<CatPersonalmedicoView> CatPersonalmedicoViewList = new ArrayList<>();
                for (CatPersonalmedico cn : CatPersonalmedicoList) {
                    CatPersonalmedicoViewList.add(CatPersonalmedicoConverter.toView(cn, Boolean.TRUE));
                }
                return CatPersonalmedicoViewList;
            } catch (Exception ex) {
                CatPersonalmedicoException cPerM = new CatPersonalmedicoException("Error inesperado al obtener todos los registros de  CatPersonalmedico", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_INSERT);
                cPerM.addError("Ocurrio un error al obtener CatPersonalmedico");
                logger.error("===>>>Error al obtener todos los registros de CatPersonalmedico - CODE: {} - {}", cPerM.getExceptionCode(), ex);
                throw cPerM;
            }
        }


         @Transactional(readOnly = false, rollbackFor = {CatPersonalmedicoException.class})
        @Override
        public Page<CatPersonalmedicoView> getCatPersonalmedicoSearch(String datosBusqueda, Integer page, Integer size, String orderColumn, String orderType) throws CatPersonalmedicoException {
            try {
                logger.info("===>>>getCatPersonalmedicoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                        datosBusqueda, page, size, orderColumn, orderType);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatPersonalmedicoException cPerM = new CatPersonalmedicoException("No se encuentra en el sistema CatPersonalmedico", CatPersonalmedicoException.LAYER_DAO, CatPersonalmedicoException.ACTION_VALIDATE);
//            cPerM.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw cPerM;
//         }

                List<CatPersonalmedicoView> CatPersonalmedicoViewList = new ArrayList<>();
                Page<CatPersonalmedico> CatPersonalmedicoPage = null;
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
                Specifications<CatPersonalmedico> spec = Specifications.where(
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
                    CatPersonalmedicoPage = CatPersonalmedicoRepository.findAll(request);
                } else {
                    CatPersonalmedicoPage = CatPersonalmedicoRepository.findAll(spec, request);
                }

                CatPersonalmedicoPage.getContent().forEach(CatPersonalmedico -> {
                    CatPersonalmedicoViewList.add(CatPersonalmedicoConverter.toView(CatPersonalmedico, Boolean.TRUE));
                });
                PageImpl<CatPersonalmedicoView> CatPersonalmedicoViewPage = new PageImpl<CatPersonalmedicoView>(CatPersonalmedicoViewList, request, CatPersonalmedicoPage.getTotalElements());
                return CatPersonalmedicoViewPage;
//      } catch (CatPersonalmedicoException cPerM) {
//         throw cPerM;
            } catch (IllegalArgumentException iae) {
                logger.error("===>>>Algun parametro no es correcto");
                CatPersonalmedicoException pe = new CatPersonalmedicoException("Algun parametro no es correcto:", CatPersonalmedicoException.LAYER_SERVICE, CatPersonalmedicoException.ACTION_VALIDATE);
                pe.addError("Puede que sea null, vacio o valor incorrecto");
                throw pe;
            } catch (Exception ex) {
                CatPersonalmedicoException cPerM = new CatPersonalmedicoException("Ocurrio un error al seleccionar lista CatPersonalmedico paginable", CatPersonalmedicoException.LAYER_SERVICE, CatPersonalmedicoException.ACTION_SELECT);
                logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatPersonalmedico paginable - CODE: {}", cPerM.getExceptionCode(), ex);
                throw cPerM;
            }
        }

        private String sinAcentos(String cadena) {
            return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
}
