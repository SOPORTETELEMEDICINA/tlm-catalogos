package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatImc519PSPConverter;
import net.amentum.niomedic.catalogos.exception.CatImc519PSPException;
import net.amentum.niomedic.catalogos.model.CatImc519PSP;
import net.amentum.niomedic.catalogos.persistence.CatImc519PSPRepository;
import net.amentum.niomedic.catalogos.service.CatImc519PSPService;
import net.amentum.niomedic.catalogos.views.CatImc519View;
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
public class CatImc519PSPImpl implements CatImc519PSPService {
    private final Logger logger = LoggerFactory.getLogger(CatImc519PSPService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatImc519PSPRepository CatImc519PSPRepository;
    private CatImc519PSPConverter CatImc519PSPConverter;

    {
        colOrderNames.put("descripcion_imc5_19", "descripcion_imc5_19");
    }

    @Autowired
    public void setCatImc519Repository(CatImc519PSPRepository CatImc519PSPRepository) {
        this.CatImc519PSPRepository = CatImc519PSPRepository;
    }

    @Autowired
    public void setCatImc519Converter(CatImc519PSPConverter CatImc519PSPConverter) {
        this.CatImc519PSPConverter = CatImc519PSPConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatImc519PSPException.class})
    @Override
    public CatImc519View getDetailsByidImc(Integer idImc519) throws CatImc519PSPException {
        try {
            if (!CatImc519PSPRepository.exists(idImc519)) {
                logger.error("===>>>idImc no encontrado: {}", idImc519);
                CatImc519PSPException cJor = new CatImc519PSPException("No se encuentra en el sistema CatImc519", CatImc519PSPException.LAYER_DAO, CatImc519PSPException.ACTION_VALIDATE);
                cJor.addError("idImc no encontrado: " + idImc519);
                throw cJor;
            }
            CatImc519PSP CatImc519PSP = CatImc519PSPRepository.findOne(idImc519);
            return CatImc519PSPConverter.toview(CatImc519PSP, Boolean.TRUE);
        } catch (CatImc519PSPException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatImc519PSPException cJor = new CatImc519PSPException("Error en la validacion", CatImc519PSPException.LAYER_DAO, CatImc519PSPException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatImc519PSPException cJor = new CatImc519PSPException("No fue posible obtener  CatImc519", CatImc519PSPException.LAYER_DAO, CatImc519PSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatImc519");
            logger.error("===>>>Error al obtener CatImc519 - CODE: {} - {}", cJor.getExceptionCode(), idImc519, dive);
            throw cJor;
        } catch (Exception ex) {
            CatImc519PSPException cJor = new CatImc519PSPException("Error inesperado al obtener  CatImc519", CatImc519PSPException.LAYER_DAO, CatImc519PSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatImc519");
            logger.error("===>>>Error al obtener CatImc519 - CODE: {} - {}", cJor.getExceptionCode(), idImc519, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatImc519PSPException.class})
    @Override
    public List<CatImc519View> findAll() throws CatImc519PSPException {
        try {
            List<CatImc519PSP> catImc519PSPList = CatImc519PSPRepository.findAll();
            List<CatImc519View> CatImc519ViewList = new ArrayList<>();
            for (CatImc519PSP cn : catImc519PSPList) {
                CatImc519ViewList.add(CatImc519PSPConverter.toview(cn, Boolean.TRUE));
            }
            return CatImc519ViewList;
        } catch (Exception ex) {
            CatImc519PSPException cJor = new CatImc519PSPException("Error inesperado al obtener todos los registros de  CatImc519", CatImc519PSPException.LAYER_DAO, CatImc519PSPException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatImc519");
            logger.error("===>>>Error al obtener todos los registros de CatImc519 - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

   
}
