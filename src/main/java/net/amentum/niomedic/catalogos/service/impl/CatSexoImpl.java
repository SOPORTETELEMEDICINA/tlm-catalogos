package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatSexoConverter;
import net.amentum.niomedic.catalogos.exception.CatSexoException;
import net.amentum.niomedic.catalogos.model.CatSexo;
import net.amentum.niomedic.catalogos.persistence.CatSexoRepository;
import net.amentum.niomedic.catalogos.service.CatSexoService;
import net.amentum.niomedic.catalogos.views.CatSexoView;
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
public class CatSexoImpl implements CatSexoService {
    private final Logger logger = LoggerFactory.getLogger(CatSexoService.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatSexoRepository CatSexoRepository;
    private CatSexoConverter CatSexoConverter;

    {
        colOrderNames.put("descripcion_genero", "descripcion_genero");
    }

    @Autowired
    public void setCatSexoRepository(CatSexoRepository CatSexoRepository) {
        this.CatSexoRepository = CatSexoRepository;
    }

    @Autowired
    public void setCatSexoConverter(CatSexoConverter CatSexoConverter) {
        this.CatSexoConverter = CatSexoConverter;
    }

    

    @Transactional(readOnly = false, rollbackFor = {CatSexoException.class})
    @Override
    public CatSexoView getDetailsByidSexo(Integer idSexo) throws CatSexoException {
        try {
            if (!CatSexoRepository.exists(idSexo)) {
                logger.error("===>>>idSexo no encontrado: {}", idSexo);
                CatSexoException cJor = new CatSexoException("No se encuentra en el sistema CatSexo", CatSexoException.LAYER_DAO, CatSexoException.ACTION_VALIDATE);
                cJor.addError("idSexo no encontrado: " + idSexo);
                throw cJor;
            }
            CatSexo CatSexo = CatSexoRepository.findOne(idSexo);
            return CatSexoConverter.toview(CatSexo, Boolean.TRUE);
        } catch (CatSexoException cJor) {
            throw cJor;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatSexoException cJor = new CatSexoException("Error en la validacion", CatSexoException.LAYER_DAO, CatSexoException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                cJor.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw cJor;
        } catch (DataIntegrityViolationException dive) {
            CatSexoException cJor = new CatSexoException("No fue posible obtener  CatSexo", CatSexoException.LAYER_DAO, CatSexoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSexo");
            logger.error("===>>>Error al obtener CatSexo - CODE: {} - {}", cJor.getExceptionCode(), idSexo, dive);
            throw cJor;
        } catch (Exception ex) {
            CatSexoException cJor = new CatSexoException("Error inesperado al obtener  CatSexo", CatSexoException.LAYER_DAO, CatSexoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSexo");
            logger.error("===>>>Error al obtener CatSexo - CODE: {} - {}", cJor.getExceptionCode(), idSexo, ex);
            throw cJor;
        }    }

    @Transactional(readOnly = false, rollbackFor = {CatSexoException.class})
    @Override
    public List<CatSexoView> findAll() throws CatSexoException {
        try {
            List<CatSexo> CatSexoList = CatSexoRepository.findAll();
            List<CatSexoView> CatSexoViewList = new ArrayList<>();
            for (CatSexo cn : CatSexoList) {
                CatSexoViewList.add(CatSexoConverter.toview(cn, Boolean.TRUE));
            }
            return CatSexoViewList;
        } catch (Exception ex) {
            CatSexoException cJor = new CatSexoException("Error inesperado al obtener todos los registros de  CatSexo", CatSexoException.LAYER_DAO, CatSexoException.ACTION_INSERT);
            cJor.addError("Ocurrio un error al obtener CatSexo");
            logger.error("===>>>Error al obtener todos los registros de CatSexo - CODE: {} - {}", cJor.getExceptionCode(), ex);
            throw cJor;
        }        }

  
}
