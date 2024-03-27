package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatProgramaConverter;
import net.amentum.niomedic.catalogos.exception.CatProgramaException;
import net.amentum.niomedic.catalogos.model.CatPrograma;
import net.amentum.niomedic.catalogos.persistence.CatProgramaRepository;
import net.amentum.niomedic.catalogos.service.CatProgramaService;
import net.amentum.niomedic.catalogos.views.CatProgramaView;
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
public class CatProgramaImpl implements CatProgramaService {
    private final Logger logger = LoggerFactory.getLogger(CatProgramaService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatProgramaRepository CatProgramaRepository;
    private CatProgramaConverter CatProgramaConverter;

    {
        colOrderNames.put("descripcion_programa", "descripcion_programa");
    }

    @Autowired
    public void setCatProgramaRepository(CatProgramaRepository CatProgramaRepository) {
        this.CatProgramaRepository = CatProgramaRepository;
    }

    @Autowired
    public void setCatProgramaConverter(CatProgramaConverter CatProgramaConverter) {
        this.CatProgramaConverter = CatProgramaConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatProgramaException.class})
    @Override
    public CatProgramaView getDetailsByidPrograma(Integer idPrograma) throws CatProgramaException {
        try {
            if (!CatProgramaRepository.exists(idPrograma)) {
                logger.error("===>>>idPrograma no encontrado: {}", idPrograma);
                CatProgramaException cJor = new CatProgramaException("No se encuentra en el sistema CatPrograma", CatProgramaException.LAYER_DAO, CatProgramaException.ACTION_VALIDATE);
                cJor.addError("idPrograma no encontrado: " + idPrograma);
                throw cJor;
            }
            CatPrograma CatPrograma = CatProgramaRepository.findOne(idPrograma);
            return CatProgramaConverter.toview(CatPrograma, Boolean.TRUE);
        } catch (CatProgramaException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatProgramaException cJor = new CatProgramaException("Error en la validacion", CatProgramaException.LAYER_DAO, CatProgramaException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatProgramaException cJor = new CatProgramaException("No fue posible obtener  CatPrograma", CatProgramaException.LAYER_DAO, CatProgramaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPrograma");
            logger.error("===>>>Error al obtener CatPrograma - CODE: {} - {}", cJor.getExceptionCode(), idPrograma, dive);
            throw cJor;
        } catch (Exception ex) {
            CatProgramaException cJor = new CatProgramaException("Error inesperado al obtener  CatPrograma", CatProgramaException.LAYER_DAO, CatProgramaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPrograma");
            logger.error("===>>>Error al obtener CatPrograma - CODE: {} - {}", cJor.getExceptionCode(), idPrograma, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatProgramaException.class})
    @Override
    public List<CatProgramaView> findAll() throws CatProgramaException {
        try {
            List<CatPrograma> CatProgramaList = CatProgramaRepository.findAll();
            List<CatProgramaView> CatProgramaViewList = new ArrayList<>();
            for (CatPrograma cn : CatProgramaList) {
                CatProgramaViewList.add(CatProgramaConverter.toview(cn, Boolean.TRUE));
            }
            return CatProgramaViewList;
        } catch (Exception ex) {
            CatProgramaException cJor = new CatProgramaException("Error inesperado al obtener todos los registros de  CatPrograma", CatProgramaException.LAYER_DAO, CatProgramaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPrograma");
            logger.error("===>>>Error al obtener todos los registros de CatPrograma - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
