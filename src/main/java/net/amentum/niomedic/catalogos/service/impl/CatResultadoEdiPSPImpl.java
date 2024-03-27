package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatResultadoEdiPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatResultadoEdiPSPException;
import net.amentum.niomedic.catalogos.model.CatResultadoEdiPSP;
import net.amentum.niomedic.catalogos.persistence.CatResultadoEdiPSPRepository;
import net.amentum.niomedic.catalogos.service.CatResultadoEdiPSPService;
import net.amentum.niomedic.catalogos.views.CatResultadoEdiView;
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
public class CatResultadoEdiPSPImpl implements CatResultadoEdiPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatResultadoEdiPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatResultadoEdiPSPRepository CatResultadoEdiPSPRepository;
    private CatResultadoEdiPSPConverter CatResultadoEdiPSPConverter;

    {
        colOrderNames.put("descripcion_resultado_edi", "descripcion_resultado_edi");
    }

    @Autowired
    public void setCatResultadoEdiRepository(CatResultadoEdiPSPRepository CatResultadoEdiPSPRepository) {
        this.CatResultadoEdiPSPRepository = CatResultadoEdiPSPRepository;
    }

    @Autowired
    public void setCatResultadoEdiConverter(CatResultadoEdiPSPConverter CatResultadoEdiPSPConverter) {
        this.CatResultadoEdiPSPConverter = CatResultadoEdiPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatResultadoEdiPSPException.class})
    @Override
    public CatResultadoEdiView getDetailsByidEdi(Integer idResultadoEdi) throws CatResultadoEdiPSPException {
        try {
            if (!CatResultadoEdiPSPRepository.exists(idResultadoEdi)) {
                logger.error("===>>>idEdi no encontrado: {}", idResultadoEdi);
                CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("No se encuentra en el sistema CatResultadoEdi", CatResultadoEdiPSPException.LAYER_DAO, CatResultadoEdiPSPException.ACTION_VALIDATE);
                cJor.addError("idEdi no encontrado: " + idResultadoEdi);
                throw cJor;
            }
            CatResultadoEdiPSP CatResultadoEdiPSP = CatResultadoEdiPSPRepository.findOne(idResultadoEdi);
            return CatResultadoEdiPSPConverter.toview(CatResultadoEdiPSP, Boolean.TRUE);
        } catch (CatResultadoEdiPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("Error en la validacion", CatResultadoEdiPSPException.LAYER_DAO, CatResultadoEdiPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("No fue posible obtener  CatResultadoEdi", CatResultadoEdiPSPException.LAYER_DAO, CatResultadoEdiPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoEdi");
            logger.error("===>>>Error al obtener CatResultadoEdi - CODE: {} - {}", cJor.getExceptionCode(), idResultadoEdi, dive);
            throw cJor;
        } catch (Exception ex) {
            CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("Error inesperado al obtener  CatResultadoEdi", CatResultadoEdiPSPException.LAYER_DAO, CatResultadoEdiPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoEdi");
            logger.error("===>>>Error al obtener CatResultadoEdi - CODE: {} - {}", cJor.getExceptionCode(), idResultadoEdi, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatResultadoEdiPSPException.class})
    @Override
    public List<CatResultadoEdiView> findAll() throws CatResultadoEdiPSPException {
        try {
            List<CatResultadoEdiPSP> catResultadoEdiPSPList = CatResultadoEdiPSPRepository.findAll();
            List<CatResultadoEdiView> CatResultadoEdiViewList = new ArrayList<>();
            for (CatResultadoEdiPSP cn : catResultadoEdiPSPList) {
                CatResultadoEdiViewList.add(CatResultadoEdiPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatResultadoEdiViewList;
        } catch (Exception ex) {
            CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("Error inesperado al obtener todos los registros de  CatResultadoEdi", CatResultadoEdiPSPException.LAYER_DAO, CatResultadoEdiPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatResultadoEdi");
            logger.error("===>>>Error al obtener todos los registros de CatResultadoEdi - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
