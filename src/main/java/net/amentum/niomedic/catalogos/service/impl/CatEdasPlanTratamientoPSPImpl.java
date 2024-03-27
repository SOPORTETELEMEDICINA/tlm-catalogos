package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatEdasPlanTratamientoPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatEdasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.model.CatEdasPlanTratamientoPSP;
import net.amentum.niomedic.catalogos.persistence.CatEdasPlanTratamientoPSPRepository;
import net.amentum.niomedic.catalogos.service.CatEdasPlanTratamientoPSPService;
import net.amentum.niomedic.catalogos.views.CatEdasPlanTratamientoView;
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
public class CatEdasPlanTratamientoPSPImpl implements CatEdasPlanTratamientoPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatEdasPlanTratamientoPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatEdasPlanTratamientoPSPRepository CatEdasPlanTratamientoPSPRepository;
    private CatEdasPlanTratamientoPSPConverter CatEdasPlanTratamientoPSPConverter;

    {
        colOrderNames.put("descripcion_edas_plan_tratamiento", "descripcion_edas_plan_tratamiento");
    }

    @Autowired
    public void setCatEdasPlanTratamientoRepository(CatEdasPlanTratamientoPSPRepository CatEdasPlanTratamientoPSPRepository) {
        this.CatEdasPlanTratamientoPSPRepository = CatEdasPlanTratamientoPSPRepository;
    }

    @Autowired
    public void setCatEdasPlanTratamientoConverter(CatEdasPlanTratamientoPSPConverter CatEdasPlanTratamientoPSPConverter) {
        this.CatEdasPlanTratamientoPSPConverter = CatEdasPlanTratamientoPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatEdasPlanTratamientoPSPException.class})
    @Override
    public CatEdasPlanTratamientoView getDetailsByidPlanTratamiento(Integer idEdasPlanTratamiento) throws CatEdasPlanTratamientoPSPException {
        try {
            if (!CatEdasPlanTratamientoPSPRepository.exists(idEdasPlanTratamiento)) {
                logger.error("===>>>idPlanTratamiento no encontrado: {}", idEdasPlanTratamiento);
                CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("No se encuentra en el sistema CatEdasPlanTratamiento", CatEdasPlanTratamientoPSPException.LAYER_DAO, CatEdasPlanTratamientoPSPException.ACTION_VALIDATE);
                cJor.addError("idPlanTratamiento no encontrado: " + idEdasPlanTratamiento);
                throw cJor;
            }
            CatEdasPlanTratamientoPSP CatEdasPlanTratamiento = CatEdasPlanTratamientoPSPRepository.findOne(idEdasPlanTratamiento);
            return CatEdasPlanTratamientoPSPConverter.toview(CatEdasPlanTratamiento, Boolean.TRUE);
        } catch (CatEdasPlanTratamientoPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("Error en la validacion", CatEdasPlanTratamientoPSPException.LAYER_DAO, CatEdasPlanTratamientoPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("No fue posible obtener  CatEdasPlanTratamiento", CatEdasPlanTratamientoPSPException.LAYER_DAO, CatEdasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatEdasPlanTratamiento");
            logger.error("===>>>Error al obtener CatEdasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), idEdasPlanTratamiento, dive);
            throw cJor;
        } catch (Exception ex) {
            CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("Error inesperado al obtener  CatEdasPlanTratamiento", CatEdasPlanTratamientoPSPException.LAYER_DAO, CatEdasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatEdasPlanTratamiento");
            logger.error("===>>>Error al obtener CatEdasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), idEdasPlanTratamiento, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatEdasPlanTratamientoPSPException.class})
    @Override
    public List<CatEdasPlanTratamientoView> findAll() throws CatEdasPlanTratamientoPSPException {
        try {
            List<CatEdasPlanTratamientoPSP> CatEdasPlanTratamientoList = CatEdasPlanTratamientoPSPRepository.findAll();
            List<CatEdasPlanTratamientoView> CatEdasPlanTratamientoViewList = new ArrayList<>();
            for (CatEdasPlanTratamientoPSP cn : CatEdasPlanTratamientoList) {
                CatEdasPlanTratamientoViewList.add(CatEdasPlanTratamientoPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatEdasPlanTratamientoViewList;
        } catch (Exception ex) {
            CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("Error inesperado al obtener todos los registros de  CatEdasPlanTratamiento", CatEdasPlanTratamientoPSPException.LAYER_DAO, CatEdasPlanTratamientoPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatEdasPlanTratamiento");
            logger.error("===>>>Error al obtener todos los registros de CatEdasPlanTratamiento - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
