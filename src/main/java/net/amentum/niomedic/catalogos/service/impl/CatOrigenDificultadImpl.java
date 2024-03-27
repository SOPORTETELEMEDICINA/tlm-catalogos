package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatOrigenDificultadConverter;
import net.amentum.niomedic.catalogos.exception.CatOrigenDificultadException;
import net.amentum.niomedic.catalogos.model.CatOrigenDificultad;
import net.amentum.niomedic.catalogos.persistence.CatOrigenDificultadRepository;
import net.amentum.niomedic.catalogos.service.CatOrigenDificultadService;
import net.amentum.niomedic.catalogos.views.CatOrigenDificultadView;
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
public class CatOrigenDificultadImpl implements CatOrigenDificultadService {
    private final Logger logger = LoggerFactory.getLogger(CatOrigenDificultadService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatOrigenDificultadRepository CatOrigenDificultadRepository;
    private CatOrigenDificultadConverter CatOrigenDificultadConverter;

    {
        colOrderNames.put("descripcion_origen_dificultad", "descripcion_origen_dificultad");
    }

    @Autowired
    public void setCatOrigenDificultadRepository(CatOrigenDificultadRepository CatOrigenDificultadRepository) {
        this.CatOrigenDificultadRepository = CatOrigenDificultadRepository;
    }

    @Autowired
    public void setCatOrigenDificultadConverter(CatOrigenDificultadConverter CatOrigenDificultadConverter) {
        this.CatOrigenDificultadConverter = CatOrigenDificultadConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatOrigenDificultadException.class})
    @Override
    public CatOrigenDificultadView getDetailsByidOrigenDificultad(Integer idOrigenDificultad) throws CatOrigenDificultadException {
        try {
            if (!CatOrigenDificultadRepository.exists(idOrigenDificultad)) {
                logger.error("===>>>idOrigenDificultad no encontrado: {}", idOrigenDificultad);
                CatOrigenDificultadException cJor = new CatOrigenDificultadException("No se encuentra en el sistema CatOrigenDificultad", CatOrigenDificultadException.LAYER_DAO, CatOrigenDificultadException.ACTION_VALIDATE);
                cJor.addError("idOrigenDificultad no encontrado: " + idOrigenDificultad);
                throw cJor;
            }
            CatOrigenDificultad CatOrigenDificultad = CatOrigenDificultadRepository.findOne(idOrigenDificultad);
            return CatOrigenDificultadConverter.toview(CatOrigenDificultad, Boolean.TRUE);
        } catch (CatOrigenDificultadException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatOrigenDificultadException cJor = new CatOrigenDificultadException("Error en la validacion", CatOrigenDificultadException.LAYER_DAO, CatOrigenDificultadException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatOrigenDificultadException cJor = new CatOrigenDificultadException("No fue posible obtener  CatOrigenDificultad", CatOrigenDificultadException.LAYER_DAO, CatOrigenDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatOrigenDificultad");
            logger.error("===>>>Error al obtener CatOrigenDificultad - CODE: {} - {}", cJor.getExceptionCode(), idOrigenDificultad, dive);
            throw cJor;
        } catch (Exception ex) {
            CatOrigenDificultadException cJor = new CatOrigenDificultadException("Error inesperado al obtener  CatOrigenDificultad", CatOrigenDificultadException.LAYER_DAO, CatOrigenDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatOrigenDificultad");
            logger.error("===>>>Error al obtener CatOrigenDificultad - CODE: {} - {}", cJor.getExceptionCode(), idOrigenDificultad, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatOrigenDificultadException.class})
    @Override
    public List<CatOrigenDificultadView> findAll() throws CatOrigenDificultadException {
        try {
            List<CatOrigenDificultad> CatOrigenDificultadList = CatOrigenDificultadRepository.findAll();
            List<CatOrigenDificultadView> CatOrigenDificultadViewList = new ArrayList<>();
            for (CatOrigenDificultad cn : CatOrigenDificultadList) {
                CatOrigenDificultadViewList.add(CatOrigenDificultadConverter.toview(cn, Boolean.TRUE));
            }
            return CatOrigenDificultadViewList;
        } catch (Exception ex) {
            CatOrigenDificultadException cJor = new CatOrigenDificultadException("Error inesperado al obtener todos los registros de  CatOrigenDificultad", CatOrigenDificultadException.LAYER_DAO, CatOrigenDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatOrigenDificultad");
            logger.error("===>>>Error al obtener todos los registros de CatOrigenDificultad - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
