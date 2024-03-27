package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatRiesgosDetectadosPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatRiesgosDetectadosPSPException;
import net.amentum.niomedic.catalogos.model.CatRiesgosDetectadosPSP;
import net.amentum.niomedic.catalogos.persistence.CatRiesgosDetectadosPSPRepository;
import net.amentum.niomedic.catalogos.service.CatRiesgosDetectadosPSPService;
import net.amentum.niomedic.catalogos.views.CatRiesgosDetectadosView;
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
public class CatRiesgosDetectadosPSPImpl implements CatRiesgosDetectadosPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatRiesgosDetectadosPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatRiesgosDetectadosPSPRepository CatRiesgosDetectadosPSPRepository;
    private CatRiesgosDetectadosPSPConverter CatRiesgosDetectadosPSPConverter;

    {
        colOrderNames.put("descripcion_riesgos_detectados", "descripcion_riesgos_detectados");
    }

    @Autowired
    public void setCatRiesgosDetectadosRepository(CatRiesgosDetectadosPSPRepository CatRiesgosDetectadosPSPRepository) {
        this.CatRiesgosDetectadosPSPRepository = CatRiesgosDetectadosPSPRepository;
    }

    @Autowired
    public void setCatRiesgosDetectadosConverter(CatRiesgosDetectadosPSPConverter CatRiesgosDetectadosPSPConverter) {
        this.CatRiesgosDetectadosPSPConverter = CatRiesgosDetectadosPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatRiesgosDetectadosPSPException.class})
    @Override
    public CatRiesgosDetectadosView getDetailsByidRiesgo(Integer idRiesgosDetectados) throws CatRiesgosDetectadosPSPException {
        try {
            if (!CatRiesgosDetectadosPSPRepository.exists(idRiesgosDetectados)) {
                logger.error("===>>>idRiesgo no encontrado: {}", idRiesgosDetectados);
                CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("No se encuentra en el sistema CatRiesgosDetectados", CatRiesgosDetectadosPSPException.LAYER_DAO, CatRiesgosDetectadosPSPException.ACTION_VALIDATE);
                cJor.addError("idRiesgo no encontrado: " + idRiesgosDetectados);
                throw cJor;
            }
            CatRiesgosDetectadosPSP CatRiesgosDetectadosPSP = CatRiesgosDetectadosPSPRepository.findOne(idRiesgosDetectados);
            return CatRiesgosDetectadosPSPConverter.toview(CatRiesgosDetectadosPSP, Boolean.TRUE);
        } catch (CatRiesgosDetectadosPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("Error en la validacion", CatRiesgosDetectadosPSPException.LAYER_DAO, CatRiesgosDetectadosPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("No fue posible obtener  CatRiesgosDetectados", CatRiesgosDetectadosPSPException.LAYER_DAO, CatRiesgosDetectadosPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatRiesgosDetectados");
            logger.error("===>>>Error al obtener CatRiesgosDetectados - CODE: {} - {}", cJor.getExceptionCode(), idRiesgosDetectados, dive);
            throw cJor;
        } catch (Exception ex) {
            CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("Error inesperado al obtener  CatRiesgosDetectados", CatRiesgosDetectadosPSPException.LAYER_DAO, CatRiesgosDetectadosPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatRiesgosDetectados");
            logger.error("===>>>Error al obtener CatRiesgosDetectados - CODE: {} - {}", cJor.getExceptionCode(), idRiesgosDetectados, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatRiesgosDetectadosPSPException.class})
    @Override
    public List<CatRiesgosDetectadosView> findAll() throws CatRiesgosDetectadosPSPException {
        try {
            List<CatRiesgosDetectadosPSP> catRiesgosDetectadosPSPList = CatRiesgosDetectadosPSPRepository.findAll();
            List<CatRiesgosDetectadosView> CatRiesgosDetectadosViewList = new ArrayList<>();
            for (CatRiesgosDetectadosPSP cn : catRiesgosDetectadosPSPList) {
                CatRiesgosDetectadosViewList.add(CatRiesgosDetectadosPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatRiesgosDetectadosViewList;
        } catch (Exception ex) {
            CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("Error inesperado al obtener todos los registros de  CatRiesgosDetectados", CatRiesgosDetectadosPSPException.LAYER_DAO, CatRiesgosDetectadosPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatRiesgosDetectados");
            logger.error("===>>>Error al obtener todos los registros de CatRiesgosDetectados - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
