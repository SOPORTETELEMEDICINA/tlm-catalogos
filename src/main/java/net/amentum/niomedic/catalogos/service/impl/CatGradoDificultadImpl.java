package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatGradoDificultadConverter;
import net.amentum.niomedic.catalogos.exception.CatGradoDificultadException;
import net.amentum.niomedic.catalogos.model.CatGradoDificultad;
import net.amentum.niomedic.catalogos.persistence.CatGradoDificultadRepository;
import net.amentum.niomedic.catalogos.service.CatGradoDificultadService;
import net.amentum.niomedic.catalogos.views.CatGradoDificultadView;
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
public class CatGradoDificultadImpl implements CatGradoDificultadService {
    private final Logger logger = LoggerFactory.getLogger(CatGradoDificultadService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatGradoDificultadRepository CatGradoDificultadRepository;
    private CatGradoDificultadConverter CatGradoDificultadConverter;

    {
        colOrderNames.put("descripcion_grado_dificultad", "descripcion_grado_dificultad");
    }

    @Autowired
    public void setCatGradoDificultadRepository(CatGradoDificultadRepository CatGradoDificultadRepository) {
        this.CatGradoDificultadRepository = CatGradoDificultadRepository;
    }

    @Autowired
    public void setCatGradoDificultadConverter(CatGradoDificultadConverter CatGradoDificultadConverter) {
        this.CatGradoDificultadConverter = CatGradoDificultadConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatGradoDificultadException.class})
    @Override
    public CatGradoDificultadView getDetailsByidGradoDificultad(Integer idGradoDificultad) throws CatGradoDificultadException {
        try {
            if (!CatGradoDificultadRepository.exists(idGradoDificultad)) {
                logger.error("===>>>idGradoDificultad no encontrado: {}", idGradoDificultad);
                CatGradoDificultadException cJor = new CatGradoDificultadException("No se encuentra en el sistema CatGradoDificultad", CatGradoDificultadException.LAYER_DAO, CatGradoDificultadException.ACTION_VALIDATE);
                cJor.addError("idGradoDificultad no encontrado: " + idGradoDificultad);
                throw cJor;
            }
            CatGradoDificultad CatGradoDificultad = CatGradoDificultadRepository.findOne(idGradoDificultad);
            return CatGradoDificultadConverter.toview(CatGradoDificultad, Boolean.TRUE);
        } catch (CatGradoDificultadException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatGradoDificultadException cJor = new CatGradoDificultadException("Error en la validacion", CatGradoDificultadException.LAYER_DAO, CatGradoDificultadException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatGradoDificultadException cJor = new CatGradoDificultadException("No fue posible obtener  CatGradoDificultad", CatGradoDificultadException.LAYER_DAO, CatGradoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGradoDificultad");
            logger.error("===>>>Error al obtener CatGradoDificultad - CODE: {} - {}", cJor.getExceptionCode(), idGradoDificultad, dive);
            throw cJor;
        } catch (Exception ex) {
            CatGradoDificultadException cJor = new CatGradoDificultadException("Error inesperado al obtener  CatGradoDificultad", CatGradoDificultadException.LAYER_DAO, CatGradoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGradoDificultad");
            logger.error("===>>>Error al obtener CatGradoDificultad - CODE: {} - {}", cJor.getExceptionCode(), idGradoDificultad, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatGradoDificultadException.class})
    @Override
    public List<CatGradoDificultadView> findAll() throws CatGradoDificultadException {
        try {
            List<CatGradoDificultad> CatGradoDificultadList = CatGradoDificultadRepository.findAll();
            List<CatGradoDificultadView> CatGradoDificultadViewList = new ArrayList<>();
            for (CatGradoDificultad cn : CatGradoDificultadList) {
                CatGradoDificultadViewList.add(CatGradoDificultadConverter.toview(cn, Boolean.TRUE));
            }
            return CatGradoDificultadViewList;
        } catch (Exception ex) {
            CatGradoDificultadException cJor = new CatGradoDificultadException("Error inesperado al obtener todos los registros de  CatGradoDificultad", CatGradoDificultadException.LAYER_DAO, CatGradoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGradoDificultad");
            logger.error("===>>>Error al obtener todos los registros de CatGradoDificultad - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
