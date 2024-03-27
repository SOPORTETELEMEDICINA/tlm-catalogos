package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatSeConsideraIndigenaConverter;
import net.amentum.niomedic.catalogos.exception.CatSeConsideraIndigenaException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatSeConsideraIndigena;
import net.amentum.niomedic.catalogos.persistence.CatSeConsideraIndigenaRepository;
import net.amentum.niomedic.catalogos.service.CatSeConsideraIndigenaService;
import net.amentum.niomedic.catalogos.views.CatSeConsideraIndigenaView;
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
public class CatSeConsideraIndigenaImpl implements CatSeConsideraIndigenaService {
    private final Logger logger = LoggerFactory.getLogger(CatSeConsideraIndigenaService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatSeConsideraIndigenaRepository CatSeConsideraIndigenaRepository;
    private CatSeConsideraIndigenaConverter CatSeConsideraIndigenaConverter;

    {
        colOrderNames.put("descripcion_se_considera_indigena", "descripcion_se_considera_indigena");
    }

    @Autowired
    public void setCatSeConsideraIndigenaRepository(CatSeConsideraIndigenaRepository CatSeConsideraIndigenaRepository) {
        this.CatSeConsideraIndigenaRepository = CatSeConsideraIndigenaRepository;
    }

    @Autowired
    public void setCatSeConsideraIndigenaConverter(CatSeConsideraIndigenaConverter CatSeConsideraIndigenaConverter) {
        this.CatSeConsideraIndigenaConverter = CatSeConsideraIndigenaConverter;
    }



    @Transactional(readOnly = false, rollbackFor = {CatSeConsideraIndigenaException.class})
    @Override
    public CatSeConsideraIndigenaView getDetailsByidSeConsideraIndigena(Integer idSeConsideraIndigena) throws CatSeConsideraIndigenaException {
        try {
            if (!CatSeConsideraIndigenaRepository.exists(idSeConsideraIndigena)) {
                logger.error("===>>>idSeConsideraIndigena no encontrado: {}", idSeConsideraIndigena);
                CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("No se encuentra en el sistema CatSeConsideraIndigena", CatSeConsideraIndigenaException.LAYER_DAO, CatSeConsideraIndigenaException.ACTION_VALIDATE);
                cJor.addError("idSeConsideraIndigena no encontrado: " + idSeConsideraIndigena);
                throw cJor;
            }
            CatSeConsideraIndigena CatSeConsideraIndigena = CatSeConsideraIndigenaRepository.findOne(idSeConsideraIndigena);
            return CatSeConsideraIndigenaConverter.toview(CatSeConsideraIndigena, Boolean.TRUE);
        } catch (CatSeConsideraIndigenaException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("Error en la validacion", CatSeConsideraIndigenaException.LAYER_DAO, CatSeConsideraIndigenaException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("No fue posible obtener  CatSeConsideraIndigena", CatSeConsideraIndigenaException.LAYER_DAO, CatSeConsideraIndigenaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeConsideraIndigena");
            logger.error("===>>>Error al obtener CatSeConsideraIndigena - CODE: {} - {}", cJor.getExceptionCode(), idSeConsideraIndigena, dive);
            throw cJor;
        } catch (Exception ex) {
            CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("Error inesperado al obtener  CatSeConsideraIndigena", CatSeConsideraIndigenaException.LAYER_DAO, CatSeConsideraIndigenaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeConsideraIndigena");
            logger.error("===>>>Error al obtener CatSeConsideraIndigena - CODE: {} - {}", cJor.getExceptionCode(), idSeConsideraIndigena, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatSeConsideraIndigenaException.class})
    @Override
    public List<CatSeConsideraIndigenaView> findAll() throws CatSeConsideraIndigenaException {
        try {
            List<CatSeConsideraIndigena> CatSeConsideraIndigenaList = CatSeConsideraIndigenaRepository.findAll();
            List<CatSeConsideraIndigenaView> CatSeConsideraIndigenaViewList = new ArrayList<>();
            for (CatSeConsideraIndigena cn : CatSeConsideraIndigenaList) {
                CatSeConsideraIndigenaViewList.add(CatSeConsideraIndigenaConverter.toview(cn, Boolean.TRUE));
            }
            return CatSeConsideraIndigenaViewList;
        } catch (Exception ex) {
            CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("Error inesperado al obtener todos los registros de  CatSeConsideraIndigena", CatSeConsideraIndigenaException.LAYER_DAO, CatSeConsideraIndigenaException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeConsideraIndigena");
            logger.error("===>>>Error al obtener todos los registros de CatSeConsideraIndigena - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }


}
