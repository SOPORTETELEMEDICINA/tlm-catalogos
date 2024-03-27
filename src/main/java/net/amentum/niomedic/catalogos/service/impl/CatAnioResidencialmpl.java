package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatAnioResidenciaConverter;
import net.amentum.niomedic.catalogos.exception.CatAnioResidenciaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatAnioResidencia;
import net.amentum.niomedic.catalogos.persistence.CatAnioResidenciaRepository;
import net.amentum.niomedic.catalogos.service.CatAnioResidenciaService;
import net.amentum.niomedic.catalogos.views.CatAnioResidenciaView;
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
import java.util.*;

@Service
@Transactional(readOnly = true)
public class CatAnioResidencialmpl implements CatAnioResidenciaService {
    private final Logger logger = LoggerFactory.getLogger(CatAnioResidenciaService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatAnioResidenciaRepository CatAnioResidenciaRepository;
    private CatAnioResidenciaConverter CatAnioResidenciaConverter;

    {
        colOrderNames.put("clave_anio_de_residencia", "clave_anio_de_residencia");
    }

    @Autowired
    public void setCatAnioResidenciaRepository(CatAnioResidenciaRepository CatAnioResidenciaRepository) {
        this.CatAnioResidenciaRepository = CatAnioResidenciaRepository;
    }

    @Autowired
    public void setCatAnioResidenciaConverter(CatAnioResidenciaConverter CatAnioResidenciaConverter) {
        this.CatAnioResidenciaConverter = CatAnioResidenciaConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatAnioResidenciaException.class})
    @Override
    public CatAnioResidenciaView getDetailsByidAnio(Integer idAnioResidencia) throws CatAnioResidenciaException {
        try {
            if (!CatAnioResidenciaRepository.exists(idAnioResidencia)) {
                logger.error("===>>>idAnio no encontrado: {}", idAnioResidencia);
                CatAnioResidenciaException cJor = new CatAnioResidenciaException("No se encuentra en el sistema CatAnioResidencia", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_VALIDATE);
                cJor.addError("idAnio no encontrado: " + idAnioResidencia);
                throw cJor;
            }
            CatAnioResidencia CatAnioResidencia = CatAnioResidenciaRepository.findOne(idAnioResidencia);
            return CatAnioResidenciaConverter.toview(CatAnioResidencia, Boolean.TRUE);
        } catch (CatAnioResidenciaException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("Error en la validacion", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("No fue posible obtener  CatAnioResidencia", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatAnioResidencia");
            logger.error("===>>>Error al obtener CatAnioResidencia - CODE: {} - {}", cJor.getExceptionCode(), idAnioResidencia, dive);
            throw cJor;
        } catch (Exception ex) {
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("Error inesperado al obtener  CatAnioResidencia", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatAnioResidencia");
            logger.error("===>>>Error al obtener CatAnioResidencia - CODE: {} - {}", cJor.getExceptionCode(), idAnioResidencia, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatAnioResidenciaException.class})
    @Override
    public List<CatAnioResidenciaView> findAll() throws CatAnioResidenciaException {
        try {
            List<CatAnioResidencia> CatAnioResidenciaList = CatAnioResidenciaRepository.findAll();
            List<CatAnioResidenciaView> CatAnioResidenciaViewList = new ArrayList<>();
            for (CatAnioResidencia cn : CatAnioResidenciaList) {
                CatAnioResidenciaViewList.add(CatAnioResidenciaConverter.toview(cn, Boolean.TRUE));
            }
            return CatAnioResidenciaViewList;
        } catch (Exception ex) {
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("Error inesperado al obtener todos los registros de  CatAnioResidencia", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatAnioResidencia");
            logger.error("===>>>Error al obtener todos los registros de CatAnioResidencia - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

    @Transactional(readOnly = false, rollbackFor = {CatAnioResidenciaException.class})
    @Override
    public Page<CatAnioResidenciaView> getCatAnioResidenciaSearch(String clave_anio_de_residencia, Integer page, Integer size, String orderColumn, String orderType) throws CatAnioResidenciaException {
        try {
            logger.info("===>>>getCatAnioResidenciaSearch(): - clave_anio_de_residencia: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                    clave_anio_de_residencia, page, size, orderColumn, orderType);

//         if (clave_anio_de_residencia.trim().isEmpty() || clave_anio_de_residencia == null) {
//            logger.error("===>>>clave_anio_de_residencia viene NULO/VACIO: {}", clave_anio_de_residencia);
//            CatAnioResidenciaException cJor = new CatAnioResidenciaException("No se encuentra en el sistema CatAnioResidencia", CatAnioResidenciaException.LAYER_DAO, CatAnioResidenciaException.ACTION_VALIDATE);
//            cJor.addError("clave_anio_de_residencia viene NULO/VACIO: " + clave_anio_de_residencia);
//            throw cJor;
//         }

            List<CatAnioResidenciaView> CatAnioResidenciaViewList = new ArrayList<>();
            Page<CatAnioResidencia> CatAnioResidenciaPage = null;
            Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("clave_anio_de_residencia"));

            if (orderColumn != null && orderType != null) {
                if (orderType.equalsIgnoreCase("asc")) {
                    sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
                } else {
                    sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
                }
            }
            PageRequest request = new PageRequest(page, size, sort);
            final String patternSearch = "%" + clave_anio_de_residencia.toLowerCase() + "%";
            Specifications<CatAnioResidencia> spec = Specifications.where(
                    (root, query, cb) -> {
                        Predicate tc = null;
                        if (clave_anio_de_residencia != null && !clave_anio_de_residencia.isEmpty()) {
                            tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("clave_anio_de_residencia"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("clave_anio_de_residencia"))), sinAcentos(patternSearch)));
                        }
//               if (active != null) {
//                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
//               }
                        return tc;
                    }
            );

            if (spec == null) {
                CatAnioResidenciaPage = CatAnioResidenciaRepository.findAll(request);
            } else {
                CatAnioResidenciaPage = CatAnioResidenciaRepository.findAll(spec, request);
            }

            CatAnioResidenciaPage.getContent().forEach(CatAnioResidencia -> {
                CatAnioResidenciaViewList.add(CatAnioResidenciaConverter.toview(CatAnioResidencia, Boolean.TRUE));
            });
            PageImpl<CatAnioResidenciaView> CatAnioResidenciaViewPage = new PageImpl<CatAnioResidenciaView>(CatAnioResidenciaViewList, request, CatAnioResidenciaPage.getTotalElements());
            return CatAnioResidenciaViewPage;
//      } catch (CatAnioResidenciaException cJor) {
//         throw cJor;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatAnioResidenciaException pe = new CatAnioResidenciaException("Algun parametro no es correcto:", CatAnioResidenciaException.LAYER_SERVICE, CatAnioResidenciaException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatAnioResidenciaException cJor = new CatAnioResidenciaException("Ocurrio un error al seleccionar lista CatAnioResidencia paginable", CatAnioResidenciaException.LAYER_SERVICE, CatAnioResidenciaException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatAnioResidencia paginable - CODE: {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }
    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
