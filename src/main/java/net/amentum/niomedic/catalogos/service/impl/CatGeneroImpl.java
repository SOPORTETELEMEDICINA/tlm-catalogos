package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatGeneroConverter;
import net.amentum.niomedic.catalogos.exception.CatGeneroException;
import net.amentum.niomedic.catalogos.model.CatGenero;
import net.amentum.niomedic.catalogos.persistence.CatGeneroRepository;
import net.amentum.niomedic.catalogos.service.CatGeneroService;
import net.amentum.niomedic.catalogos.views.CatGeneroView;
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
public class CatGeneroImpl implements CatGeneroService {
    private final Logger logger = LoggerFactory.getLogger(CatGeneroService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatGeneroRepository CatGeneroRepository;
    private CatGeneroConverter CatGeneroConverter;

    {
        colOrderNames.put("descripcion_genero", "descripcion_genero");
    }

    @Autowired
    public void setCatGeneroRepository(CatGeneroRepository CatGeneroRepository) {
        this.CatGeneroRepository = CatGeneroRepository;
    }

    @Autowired
    public void setCatGeneroConverter(CatGeneroConverter CatGeneroConverter) {
        this.CatGeneroConverter = CatGeneroConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatGeneroException.class})
    @Override
    public CatGeneroView getDetailsByidGenero(Integer idGenero) throws CatGeneroException {
        try {
            if (!CatGeneroRepository.exists(idGenero)) {
                logger.error("===>>>idGenero no encontrado: {}", idGenero);
                CatGeneroException cJor = new CatGeneroException("No se encuentra en el sistema CatGenero", CatGeneroException.LAYER_DAO, CatGeneroException.ACTION_VALIDATE);
                cJor.addError("idGenero no encontrado: " + idGenero);
                throw cJor;
            }
            CatGenero CatGenero = CatGeneroRepository.findOne(idGenero);
            return CatGeneroConverter.toview(CatGenero, Boolean.TRUE);
        } catch (CatGeneroException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatGeneroException cJor = new CatGeneroException("Error en la validacion", CatGeneroException.LAYER_DAO, CatGeneroException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatGeneroException cJor = new CatGeneroException("No fue posible obtener  CatGenero", CatGeneroException.LAYER_DAO, CatGeneroException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGenero");
            logger.error("===>>>Error al obtener CatGenero - CODE: {} - {}", cJor.getExceptionCode(), idGenero, dive);
            throw cJor;
        } catch (Exception ex) {
            CatGeneroException cJor = new CatGeneroException("Error inesperado al obtener  CatGenero", CatGeneroException.LAYER_DAO, CatGeneroException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGenero");
            logger.error("===>>>Error al obtener CatGenero - CODE: {} - {}", cJor.getExceptionCode(), idGenero, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatGeneroException.class})
    @Override
    public List<CatGeneroView> findAll() throws CatGeneroException {
        try {
            List<CatGenero> CatGeneroList = CatGeneroRepository.findAll();
            List<CatGeneroView> CatGeneroViewList = new ArrayList<>();
            for (CatGenero cn : CatGeneroList) {
                CatGeneroViewList.add(CatGeneroConverter.toview(cn, Boolean.TRUE));
            }
            return CatGeneroViewList;
        } catch (Exception ex) {
            CatGeneroException cJor = new CatGeneroException("Error inesperado al obtener todos los registros de  CatGenero", CatGeneroException.LAYER_DAO, CatGeneroException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatGenero");
            logger.error("===>>>Error al obtener todos los registros de CatGenero - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
