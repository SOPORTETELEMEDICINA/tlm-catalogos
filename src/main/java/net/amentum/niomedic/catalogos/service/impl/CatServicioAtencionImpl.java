package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatServicioAtencionConverter;
import net.amentum.niomedic.catalogos.exception.CatServicioAtencionException;
import net.amentum.niomedic.catalogos.model.CatServicioAtencion;
import net.amentum.niomedic.catalogos.persistence.CatServicioAtencionRepository;
import net.amentum.niomedic.catalogos.service.CatServicioAtencionService;
import net.amentum.niomedic.catalogos.views.CatServicioAtencionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class CatServicioAtencionImpl implements CatServicioAtencionService {
    private final Logger logger = LoggerFactory.getLogger(CatServicioAtencionService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatServicioAtencionRepository CatServicioAtencionRepository;
    private CatServicioAtencionConverter CatServicioAtencionConverter;

    {
        colOrderNames.put("descripcion_genero", "descripcion_genero");
    }

    @Autowired
    public void setCatServicioAtencionRepository(CatServicioAtencionRepository CatServicioAtencionRepository) {
        this.CatServicioAtencionRepository = CatServicioAtencionRepository;
    }

    @Autowired
    public void setCatServicioAtencionConverter(CatServicioAtencionConverter CatServicioAtencionConverter) {
        this.CatServicioAtencionConverter = CatServicioAtencionConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatServicioAtencionException.class})
    @Override
    public CatServicioAtencionView getDetailsByidServicioAtencion(Integer idServicioAtencion) throws CatServicioAtencionException {
        try {
            if (!CatServicioAtencionRepository.exists(idServicioAtencion)) {
                logger.error("===>>>idServicioAtencion no encontrado: {}", idServicioAtencion);
                CatServicioAtencionException cJor = new CatServicioAtencionException("No se encuentra en el sistema CatServicioAtencion", CatServicioAtencionException.LAYER_DAO, CatServicioAtencionException.ACTION_VALIDATE);
                cJor.addError("idServicioAtencion no encontrado: " + idServicioAtencion);
                throw cJor;
            }
            CatServicioAtencion CatServicioAtencion = CatServicioAtencionRepository.findOne(idServicioAtencion);
            return CatServicioAtencionConverter.toview(CatServicioAtencion, Boolean.TRUE);
        } catch (CatServicioAtencionException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatServicioAtencionException cJor = new CatServicioAtencionException("Error en la validacion", CatServicioAtencionException.LAYER_DAO, CatServicioAtencionException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatServicioAtencionException cJor = new CatServicioAtencionException("No fue posible obtener  CatServicioAtencion", CatServicioAtencionException.LAYER_DAO, CatServicioAtencionException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatServicioAtencion");
            logger.error("===>>>Error al obtener CatServicioAtencion - CODE: {} - {}", cJor.getExceptionCode(), idServicioAtencion, dive);
            throw cJor;
        } catch (Exception ex) {
            CatServicioAtencionException cJor = new CatServicioAtencionException("Error inesperado al obtener  CatServicioAtencion", CatServicioAtencionException.LAYER_DAO, CatServicioAtencionException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatServicioAtencion");
            logger.error("===>>>Error al obtener CatServicioAtencion - CODE: {} - {}", cJor.getExceptionCode(), idServicioAtencion, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatServicioAtencionException.class})
    @Override
    public List<CatServicioAtencionView> findAll() throws CatServicioAtencionException {
        try {
            List<CatServicioAtencion> CatServicioAtencionList = CatServicioAtencionRepository.findAll();
            List<CatServicioAtencionView> CatServicioAtencionViewList = new ArrayList<>();
            for (CatServicioAtencion cn : CatServicioAtencionList) {
                CatServicioAtencionViewList.add(CatServicioAtencionConverter.toview(cn, Boolean.TRUE));
            }
            return CatServicioAtencionViewList;
        } catch (Exception ex) {
            CatServicioAtencionException cJor = new CatServicioAtencionException("Error inesperado al obtener todos los registros de  CatServicioAtencion", CatServicioAtencionException.LAYER_DAO, CatServicioAtencionException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatServicioAtencion");
            logger.error("===>>>Error al obtener todos los registros de CatServicioAtencion - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
