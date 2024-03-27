package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatIrasPlanTratamientoPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatIrasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.model.CatIrasPlanTratamientoPSP;
import net.amentum.niomedic.catalogos.persistence.CatIrasPlanTratamientoPSPRepository;
import net.amentum.niomedic.catalogos.service.CatIrasPlanTratamientoPSPService;
import net.amentum.niomedic.catalogos.views.CatIrasPlanTratamientoView;
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
public class CatIrasPlanTratamientoPSPImpl implements CatIrasPlanTratamientoPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatIrasPlanTratamientoPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatIrasPlanTratamientoPSPRepository CatIrasPlanTratamientoPSPRepository;
    private CatIrasPlanTratamientoPSPConverter CatIrasPlanTratamientoPSPConverter;

    {
        colOrderNames.put("descripcion_edas_plan_tratamiento", "descripcion_edas_plan_tratamiento");
    }

    @Autowired
    public void setCatIrasPlanTratamientoRepository(CatIrasPlanTratamientoPSPRepository CatIrasPlanTratamientoPSPRepository) {
        this.CatIrasPlanTratamientoPSPRepository = CatIrasPlanTratamientoPSPRepository;
    }

    @Autowired
    public void getDetailsByidIras(CatIrasPlanTratamientoPSPConverter CatIrasPlanTratamientoPSPConverter) {
        this.CatIrasPlanTratamientoPSPConverter = CatIrasPlanTratamientoPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatIrasPlanTratamientoPSPException.class})
    @Override
    public CatIrasPlanTratamientoView getDetailsByidIras(Integer idIrasPlanTratamiento) throws CatIrasPlanTratamientoPSPException {
        try {
            if (!CatIrasPlanTratamientoPSPRepository.exists(idIrasPlanTratamiento)) {
                logger.error("===>>>idIras no encontrado: {}", idIrasPlanTratamiento);
                CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("No se encuentra en el sistema CatIrasPlanTratamiento", CatIrasPlanTratamientoPSPException.LAYER_DAO, CatIrasPlanTratamientoPSPException.ACTION_VALIDATE);
                cJor.addError("idIras no encontrado: " + idIrasPlanTratamiento);
                throw cJor;
            }
            CatIrasPlanTratamientoPSP CatIrasPlanTratamientoPSP = CatIrasPlanTratamientoPSPRepository.findOne(idIrasPlanTratamiento);
            return CatIrasPlanTratamientoPSPConverter.toview(CatIrasPlanTratamientoPSP, Boolean.TRUE);
        } catch (CatIrasPlanTratamientoPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("Error en la validacion", CatIrasPlanTratamientoPSPException.LAYER_DAO, CatIrasPlanTratamientoPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("No fue posible obtener  CatIrasPlanTratamiento", CatIrasPlanTratamientoPSPException.LAYER_DAO, CatIrasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatIrasPlanTratamiento");
            logger.error("===>>>Error al obtener CatIrasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), idIrasPlanTratamiento, dive);
            throw cJor;
        } catch (Exception ex) {
            CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("Error inesperado al obtener  CatIrasPlanTratamiento", CatIrasPlanTratamientoPSPException.LAYER_DAO, CatIrasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatIrasPlanTratamiento");
            logger.error("===>>>Error al obtener CatIrasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), idIrasPlanTratamiento, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatIrasPlanTratamientoPSPException.class})
    @Override
    public List<CatIrasPlanTratamientoView> findAll() throws CatIrasPlanTratamientoPSPException {
        try {
            List<CatIrasPlanTratamientoPSP> catIrasPlanTratamientoPSPList = CatIrasPlanTratamientoPSPRepository.findAll();
            List<CatIrasPlanTratamientoView> CatIrasPlanTratamientoViewList = new ArrayList<>();
            for (CatIrasPlanTratamientoPSP cn : catIrasPlanTratamientoPSPList) {
                CatIrasPlanTratamientoViewList.add(CatIrasPlanTratamientoPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatIrasPlanTratamientoViewList;
        } catch (Exception ex) {
            CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("Error inesperado al obtener todos los registros de  CatIrasPlanTratamiento", CatIrasPlanTratamientoPSPException.LAYER_DAO, CatIrasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatIrasPlanTratamiento");
            logger.error("===>>>Error al obtener todos los registros de CatIrasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
