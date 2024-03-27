package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatPesoParaTallaPSPConverter;
import net.amentum.niomedic.catalogos.exception.CatPesoParaTallaPSPException;
import net.amentum.niomedic.catalogos.model.CatPesoParaTallaPSP;
import net.amentum.niomedic.catalogos.persistence.CatPesoParaTallaPSPRepository;
import net.amentum.niomedic.catalogos.service.CatPesoParaTallaPSPService;
import net.amentum.niomedic.catalogos.views.CatPesoParaTallaView;
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
public class CatPesoParaTallaPSPImpl implements CatPesoParaTallaPSPService {
    private final Logger logger = LoggerFactory.getLogger(CatPesoParaTallaPSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatPesoParaTallaPSPRepository CatPesoParaTallaPSPRepository;
    private CatPesoParaTallaPSPConverter CatPesoParaTallaPSPConverter;

    {
        colOrderNames.put("descripcion_peso_para_talla", "descripcion_peso_para_talla");
    }

    @Autowired
    public void setCatPesoParaTallaRepository(CatPesoParaTallaPSPRepository CatPesoParaTallaPSPRepository) {
        this.CatPesoParaTallaPSPRepository = CatPesoParaTallaPSPRepository;
    }

    @Autowired
    public void setCatPesoParaTallaConverter(CatPesoParaTallaPSPConverter CatPesoParaTallaPSPConverter) {
        this.CatPesoParaTallaPSPConverter = CatPesoParaTallaPSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatPesoParaTallaPSPException.class})
    @Override
    public CatPesoParaTallaView getDetailsByidPeso(Integer idPesoParaTalla) throws CatPesoParaTallaPSPException {
        try {
            if (!CatPesoParaTallaPSPRepository.exists(idPesoParaTalla)) {
                logger.error("===>>>idPeso no encontrado: {}", idPesoParaTalla);
                CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("No se encuentra en el sistema CatPesoParaTalla", CatPesoParaTallaPSPException.LAYER_DAO, CatPesoParaTallaPSPException.ACTION_VALIDATE);
                cJor.addError("idPeso no encontrado: " + idPesoParaTalla);
                throw cJor;
            }
            CatPesoParaTallaPSP CatPesoParaTallaPSP = CatPesoParaTallaPSPRepository.findOne(idPesoParaTalla);
            return CatPesoParaTallaPSPConverter.toview(CatPesoParaTallaPSP, Boolean.TRUE);
        } catch (CatPesoParaTallaPSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("Error en la validacion", CatPesoParaTallaPSPException.LAYER_DAO, CatPesoParaTallaPSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("No fue posible obtener  CatPesoParaTalla", CatPesoParaTallaPSPException.LAYER_DAO, CatPesoParaTallaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPesoParaTalla");
            logger.error("===>>>Error al obtener CatPesoParaTalla - CODE: {} - {}", cJor.getExceptionCode(), idPesoParaTalla, dive);
            throw cJor;
        } catch (Exception ex) {
            CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("Error inesperado al obtener  CatPesoParaTalla", CatPesoParaTallaPSPException.LAYER_DAO, CatPesoParaTallaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPesoParaTalla");
            logger.error("===>>>Error al obtener CatPesoParaTalla - CODE: {} - {}", cJor.getExceptionCode(), idPesoParaTalla, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatPesoParaTallaPSPException.class})
    @Override
    public List<CatPesoParaTallaView> findAll() throws CatPesoParaTallaPSPException {
        try {
            List<CatPesoParaTallaPSP> catPesoParaTallaPSPList = CatPesoParaTallaPSPRepository.findAll();
            List<CatPesoParaTallaView> CatPesoParaTallaViewList = new ArrayList<>();
            for (CatPesoParaTallaPSP cn : catPesoParaTallaPSPList) {
                CatPesoParaTallaViewList.add(CatPesoParaTallaPSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatPesoParaTallaViewList;
        } catch (Exception ex) {
            CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("Error inesperado al obtener todos los registros de  CatPesoParaTalla", CatPesoParaTallaPSPException.LAYER_DAO, CatPesoParaTallaPSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatPesoParaTalla");
            logger.error("===>>>Error al obtener todos los registros de CatPesoParaTalla - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
