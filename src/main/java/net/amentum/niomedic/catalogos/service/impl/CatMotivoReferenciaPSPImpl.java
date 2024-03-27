package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatMotivoReferenciaPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatMotivoReferenciaPSPException;
import net.amentum.niomedic.catalogos.model.CatMotivoReferenciaPSP;
import net.amentum.niomedic.catalogos.persistence.CatMotivoReferenciaPSPRepository;
import net.amentum.niomedic.catalogos.service.CatMotivoReferenciaPSPService;
import net.amentum.niomedic.catalogos.views.CatMotivoReferenciaView;
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
public class CatMotivoReferenciaPSPImpl implements CatMotivoReferenciaPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatMotivoReferenciaPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatMotivoReferenciaPSPRepository CatMotivoReferenciaPSPRepository;
    private CatMotivoReferenciaPSPConverter CatMotivoReferenciaPSPConverter;

    {
        colOrderNames.put("descripcion_motivo_de_referencia", "descripcion_motivo_de_referencia");
    }

    @Autowired
    public void setCatMotivoReferenciaRepository(CatMotivoReferenciaPSPRepository CatMotivoReferenciaPSPRepository) {
        this.CatMotivoReferenciaPSPRepository = CatMotivoReferenciaPSPRepository;
    }

    @Autowired
    public void setCatMotivoReferenciaConverter(CatMotivoReferenciaPSPConverter CatMotivoReferenciaPSPConverter) {
        this.CatMotivoReferenciaPSPConverter = CatMotivoReferenciaPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatMotivoReferenciaPSPException.class})
    @Override
    public CatMotivoReferenciaView getDetailsByidMotivoReferencia(Integer idMotivoReferencia) throws CatMotivoReferenciaPSPException {
        try {
            if (!CatMotivoReferenciaPSPRepository.exists(idMotivoReferencia)) {
                logger.error("===>>>idMotivoReferencia no encontrado: {}", idMotivoReferencia);
                CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("No se encuentra en el sistema CatMotivoReferencia", CatMotivoReferenciaPSPException.LAYER_DAO, CatMotivoReferenciaPSPException.ACTION_VALIDATE);
                cJor.addError("idMotivoReferencia no encontrado: " + idMotivoReferencia);
                throw cJor;
            }
            CatMotivoReferenciaPSP CatMotivoReferenciaPSP = CatMotivoReferenciaPSPRepository.findOne(idMotivoReferencia);
            return CatMotivoReferenciaPSPConverter.toview(CatMotivoReferenciaPSP, Boolean.TRUE);
        } catch (CatMotivoReferenciaPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("Error en la validacion", CatMotivoReferenciaPSPException.LAYER_DAO, CatMotivoReferenciaPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("No fue posible obtener  CatMotivoReferencia", CatMotivoReferenciaPSPException.LAYER_DAO, CatMotivoReferenciaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatMotivoReferencia");
            logger.error("===>>>Error al obtener CatMotivoReferencia - CODE: {} - {}", cJor.getExceptionCode(), idMotivoReferencia, dive);
            throw cJor;
        } catch (Exception ex) {
            CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("Error inesperado al obtener  CatMotivoReferencia", CatMotivoReferenciaPSPException.LAYER_DAO, CatMotivoReferenciaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatMotivoReferencia");
            logger.error("===>>>Error al obtener CatMotivoReferencia - CODE: {} - {}", cJor.getExceptionCode(), idMotivoReferencia, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatMotivoReferenciaPSPException.class})
    @Override
    public List<CatMotivoReferenciaView> findAll() throws CatMotivoReferenciaPSPException {
        try {
            List<CatMotivoReferenciaPSP> catMotivoReferenciaPSPList = CatMotivoReferenciaPSPRepository.findAll();
            List<CatMotivoReferenciaView> CatMotivoReferenciaViewList = new ArrayList<>();
            for (CatMotivoReferenciaPSP cn : catMotivoReferenciaPSPList) {
                CatMotivoReferenciaViewList.add(CatMotivoReferenciaPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatMotivoReferenciaViewList;
        } catch (Exception ex) {
            CatMotivoReferenciaPSPException cJor = new CatMotivoReferenciaPSPException("Error inesperado al obtener todos los registros de  CatMotivoReferencia", CatMotivoReferenciaPSPException.LAYER_DAO, CatMotivoReferenciaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatMotivoReferencia");
            logger.error("===>>>Error al obtener todos los registros de CatMotivoReferencia - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
