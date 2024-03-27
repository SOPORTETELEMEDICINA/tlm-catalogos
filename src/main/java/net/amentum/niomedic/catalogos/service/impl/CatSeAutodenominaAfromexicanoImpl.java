package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatSeAutodenominaAfromexicanoConverter;
import net.amentum.niomedic.catalogos.exception.CatSeAutodenominaAfromexicanoException;
import net.amentum.niomedic.catalogos.model.CatSeAutodenominaAfromexicano;
import net.amentum.niomedic.catalogos.persistence.CatSeAutodenominaAfromexicanoRepository;
import net.amentum.niomedic.catalogos.service.CatSeAutodenominaAfromexicanoService;
import net.amentum.niomedic.catalogos.views.CatSeAutodenominaAfromexicanoView;
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
public class CatSeAutodenominaAfromexicanoImpl implements CatSeAutodenominaAfromexicanoService {
    private final Logger logger = LoggerFactory.getLogger(CatSeAutodenominaAfromexicanoService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatSeAutodenominaAfromexicanoRepository CatSeAutodenominaAfromexicanoRepository;
    private CatSeAutodenominaAfromexicanoConverter CatSeAutodenominaAfromexicanoConverter;

    {
        colOrderNames.put("descripcion_se_autodenomina_afromexicano", "descripcion_se_denomina_afromexicano");
    }

    @Autowired
    public void setCatSeAutodenominaAfromexicanoRepository(CatSeAutodenominaAfromexicanoRepository CatSeAutodenominaAfromexicanoRepository) {
        this.CatSeAutodenominaAfromexicanoRepository = CatSeAutodenominaAfromexicanoRepository;
    }

    @Autowired
    public void setCatSeAutodenominaAfromexicanoConverter(CatSeAutodenominaAfromexicanoConverter CatSeAutodenominaAfromexicanoConverter) {
        this.CatSeAutodenominaAfromexicanoConverter = CatSeAutodenominaAfromexicanoConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatSeAutodenominaAfromexicanoException.class})
    @Override
    public CatSeAutodenominaAfromexicanoView getDetailsByidSeAutodenominaAfromexicano(Integer idSeAutodenominaAfromexicano) throws CatSeAutodenominaAfromexicanoException {
        try {
            if (!CatSeAutodenominaAfromexicanoRepository.exists(idSeAutodenominaAfromexicano)) {
                logger.error("===>>>idSeAutodenominaAfromexicano no encontrado: {}", idSeAutodenominaAfromexicano);
                CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("No se encuentra en el sistema CatSeAutodenominaAfromexicano", CatSeAutodenominaAfromexicanoException.LAYER_DAO, CatSeAutodenominaAfromexicanoException.ACTION_VALIDATE);
                cJor.addError("idSeAutodenominaAfromexicano no encontrado: " + idSeAutodenominaAfromexicano);
                throw cJor;
            }
            CatSeAutodenominaAfromexicano CatSeAutodenominaAfromexicano = CatSeAutodenominaAfromexicanoRepository.findOne(idSeAutodenominaAfromexicano);
            return CatSeAutodenominaAfromexicanoConverter.toview(CatSeAutodenominaAfromexicano, Boolean.TRUE);
        } catch (CatSeAutodenominaAfromexicanoException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("Error en la validacion", CatSeAutodenominaAfromexicanoException.LAYER_DAO, CatSeAutodenominaAfromexicanoException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("No fue posible obtener  CatSeAutodenominaAfromexicano", CatSeAutodenominaAfromexicanoException.LAYER_DAO, CatSeAutodenominaAfromexicanoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeAutodenominaAfromexicano");
            logger.error("===>>>Error al obtener CatSeAutodenominaAfromexicano - CODE: {} - {}", cJor.getExceptionCode(), idSeAutodenominaAfromexicano, dive);
            throw cJor;
        } catch (Exception ex) {
            CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("Error inesperado al obtener  CatSeAutodenominaAfromexicano", CatSeAutodenominaAfromexicanoException.LAYER_DAO, CatSeAutodenominaAfromexicanoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeAutodenominaAfromexicano");
            logger.error("===>>>Error al obtener CatSeAutodenominaAfromexicano - CODE: {} - {}", cJor.getExceptionCode(), idSeAutodenominaAfromexicano, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatSeAutodenominaAfromexicanoException.class})
    @Override
    public List<CatSeAutodenominaAfromexicanoView> findAll() throws CatSeAutodenominaAfromexicanoException {
        try {
            List<CatSeAutodenominaAfromexicano> CatSeAutodenominaAfromexicanoList = CatSeAutodenominaAfromexicanoRepository.findAll();
            List<CatSeAutodenominaAfromexicanoView> CatSeAutodenominaAfromexicanoViewList = new ArrayList<>();
            for (CatSeAutodenominaAfromexicano cn : CatSeAutodenominaAfromexicanoList) {
                CatSeAutodenominaAfromexicanoViewList.add(CatSeAutodenominaAfromexicanoConverter.toview(cn, Boolean.TRUE));
            }
            return CatSeAutodenominaAfromexicanoViewList;
        } catch (Exception ex) {
            CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("Error inesperado al obtener todos los registros de  CatSeAutodenominaAfromexicano", CatSeAutodenominaAfromexicanoException.LAYER_DAO, CatSeAutodenominaAfromexicanoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSeAutodenominaAfromexicano");
            logger.error("===>>>Error al obtener todos los registros de CatSeAutodenominaAfromexicano - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
