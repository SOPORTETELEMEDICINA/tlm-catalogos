package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatResultadoBatellePSPConverter;
import net.amentum.niomedic.catalogos.exception.CatResultadoBatellePSPException;
import net.amentum.niomedic.catalogos.model.CatResultadoBatellePSP;
import net.amentum.niomedic.catalogos.persistence.CatResultadoBatellePSPRepository;
import net.amentum.niomedic.catalogos.service.CatResultadoBatellePSPService;
import net.amentum.niomedic.catalogos.views.CatResultadoBatelleView;
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
public class CatResultadoBatellePSPImpl implements CatResultadoBatellePSPService {
    private final Logger logger = LoggerFactory.getLogger(CatResultadoBatellePSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatResultadoBatellePSPRepository CatResultadoBatellePSPRepository;
    private CatResultadoBatellePSPConverter CatResultadoBatellePSPConverter;

    {
        colOrderNames.put("descripcion_resultado_batelle", "descripcion_resultado_batelle");
    }

    @Autowired
    public void setCatResultadoBatelleRepository(CatResultadoBatellePSPRepository CatResultadoBatellePSPRepository) {
        this.CatResultadoBatellePSPRepository = CatResultadoBatellePSPRepository;
    }

    @Autowired
    public void setCatResultadoBatelleConverter(CatResultadoBatellePSPConverter CatResultadoBatellePSPConverter) {
        this.CatResultadoBatellePSPConverter = CatResultadoBatellePSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatResultadoBatellePSPException.class})
    @Override
    public CatResultadoBatelleView getDetailsByidBatelle(Integer idResultadoBatelle) throws CatResultadoBatellePSPException {
        try {
            if (!CatResultadoBatellePSPRepository.exists(idResultadoBatelle)) {
                logger.error("===>>>idBatelle no encontrado: {}", idResultadoBatelle);
                CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("No se encuentra en el sistema CatResultadoBatelle", CatResultadoBatellePSPException.LAYER_DAO, CatResultadoBatellePSPException.ACTION_VALIDATE);
                cJor.addError("idBatelle no encontrado: " + idResultadoBatelle);
                throw cJor;
            }
            CatResultadoBatellePSP CatResultadoBatellePSP = CatResultadoBatellePSPRepository.findOne(idResultadoBatelle);
            return CatResultadoBatellePSPConverter.toview(CatResultadoBatellePSP, Boolean.TRUE);
        } catch (CatResultadoBatellePSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("Error en la validacion", CatResultadoBatellePSPException.LAYER_DAO, CatResultadoBatellePSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("No fue posible obtener  CatResultadoBatelle", CatResultadoBatellePSPException.LAYER_DAO, CatResultadoBatellePSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoBatelle");
            logger.error("===>>>Error al obtener CatResultadoBatelle - CODE: {} - {}", cJor.getExceptionCode(), idResultadoBatelle, dive);
            throw cJor;
        } catch (Exception ex) {
            CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("Error inesperado al obtener  CatResultadoBatelle", CatResultadoBatellePSPException.LAYER_DAO, CatResultadoBatellePSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoBatelle");
            logger.error("===>>>Error al obtener CatResultadoBatelle - CODE: {} - {}", cJor.getExceptionCode(), idResultadoBatelle, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatResultadoBatellePSPException.class})
    @Override
    public List<CatResultadoBatelleView> findAll() throws CatResultadoBatellePSPException {
        try {
            List<CatResultadoBatellePSP> catResultadoBatellePSPList = CatResultadoBatellePSPRepository.findAll();
            List<CatResultadoBatelleView> CatResultadoBatelleViewList = new ArrayList<>();
            for (CatResultadoBatellePSP cn : catResultadoBatellePSPList) {
                CatResultadoBatelleViewList.add(CatResultadoBatellePSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatResultadoBatelleViewList;
        } catch (Exception ex) {
            CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("Error inesperado al obtener todos los registros de  CatResultadoBatelle", CatResultadoBatellePSPException.LAYER_DAO, CatResultadoBatellePSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoBatelle");
            logger.error("===>>>Error al obtener todos los registros de CatResultadoBatelle - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
