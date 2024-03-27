package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatTipoDificultadConverter;
import net.amentum.niomedic.catalogos.exception.CatTipoDificultadException;
import net.amentum.niomedic.catalogos.model.CatTipoDificultad;
import net.amentum.niomedic.catalogos.persistence.CatTipoDificultadRepository;
import net.amentum.niomedic.catalogos.service.CatTipoDificultadService;
import net.amentum.niomedic.catalogos.views.CatTipoDificultadView;
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
public class CatTIpoDificultadImpl implements CatTipoDificultadService {
    private final Logger logger = LoggerFactory.getLogger(CatTipoDificultadService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatTipoDificultadRepository CatTipoDificultadRepository;
    private CatTipoDificultadConverter CatTipoDificultadConverter;

    {
        colOrderNames.put("descripcion_tipo_dificultad", "descripcion_tipo_dificultad");
    }

    @Autowired
    public void setCatTipoDificultadRepository(CatTipoDificultadRepository CatTipoDificultadRepository) {
        this.CatTipoDificultadRepository = CatTipoDificultadRepository;
    }

    @Autowired
    public void setCatTipoDificultadConverter(CatTipoDificultadConverter CatTipoDificultadConverter) {
        this.CatTipoDificultadConverter = CatTipoDificultadConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatTipoDificultadException.class})
    @Override
    public CatTipoDificultadView getDetailsByidTipoDificultad(Integer idTipoDificultad) throws CatTipoDificultadException {
        try {
            if (!CatTipoDificultadRepository.exists(idTipoDificultad)) {
                logger.error("===>>>idTipoDificultad no encontrado: {}", idTipoDificultad);
                CatTipoDificultadException cJor = new CatTipoDificultadException("No se encuentra en el sistema CatTipoDificultad", CatTipoDificultadException.LAYER_DAO, CatTipoDificultadException.ACTION_VALIDATE);
                cJor.addError("idTipoDificultad no encontrado: " + idTipoDificultad);
                throw cJor;
            }
            CatTipoDificultad CatTipoDificultad = CatTipoDificultadRepository.findOne(idTipoDificultad);
            return CatTipoDificultadConverter.toview(CatTipoDificultad, Boolean.TRUE);
        } catch (CatTipoDificultadException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatTipoDificultadException cJor = new CatTipoDificultadException("Error en la validacion", CatTipoDificultadException.LAYER_DAO, CatTipoDificultadException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatTipoDificultadException cJor = new CatTipoDificultadException("No fue posible obtener  CatTipoDificultad", CatTipoDificultadException.LAYER_DAO, CatTipoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatTipoDificultad");
            logger.error("===>>>Error al obtener CatTipoDificultad - CODE: {} - {}", cJor.getExceptionCode(), idTipoDificultad, dive);
            throw cJor;
        } catch (Exception ex) {
            CatTipoDificultadException cJor = new CatTipoDificultadException("Error inesperado al obtener  CatTipoDificultad", CatTipoDificultadException.LAYER_DAO, CatTipoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatTipoDificultad");
            logger.error("===>>>Error al obtener CatTipoDificultad - CODE: {} - {}", cJor.getExceptionCode(), idTipoDificultad, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatTipoDificultadException.class})
    @Override
    public List<CatTipoDificultadView> findAll() throws CatTipoDificultadException {
        try {
            List<CatTipoDificultad> CatTipoDificultadList = CatTipoDificultadRepository.findAll();
            List<CatTipoDificultadView> CatTipoDificultadViewList = new ArrayList<>();
            for (CatTipoDificultad cn : CatTipoDificultadList) {
                CatTipoDificultadViewList.add(CatTipoDificultadConverter.toview(cn, Boolean.TRUE));
            }
            return CatTipoDificultadViewList;
        } catch (Exception ex) {
            CatTipoDificultadException cJor = new CatTipoDificultadException("Error inesperado al obtener todos los registros de  CatTipoDificultad", CatTipoDificultadException.LAYER_DAO, CatTipoDificultadException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatTipoDificultad");
            logger.error("===>>>Error al obtener todos los registros de CatTipoDificultad - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
