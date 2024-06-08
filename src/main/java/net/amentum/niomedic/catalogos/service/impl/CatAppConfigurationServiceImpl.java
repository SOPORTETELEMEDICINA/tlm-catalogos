package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatAppConfigurationConverter;
import net.amentum.niomedic.catalogos.exception.CatAppConfigurationException;
import net.amentum.niomedic.catalogos.model.CatAppConfiguration;
import net.amentum.niomedic.catalogos.persistence.CatAppConfigurationRepository;
import net.amentum.niomedic.catalogos.service.CatAppConfigurationService;
import net.amentum.niomedic.catalogos.views.CatAppConfigurationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional(readOnly = true, noRollbackFor = CatAppConfigurationException.class)
public class CatAppConfigurationServiceImpl implements CatAppConfigurationService {
    private final Logger logger = LoggerFactory.getLogger(CatAppConfigurationServiceImpl.class);

    @Autowired
    private CatAppConfigurationRepository repository;

    @Autowired
    private CatAppConfigurationConverter converter;

    @Override
    public CatAppConfigurationView getUserByidCliente(Integer idCliente) throws CatAppConfigurationException {
        try {
            CatAppConfiguration client = repository.findByidCliente(idCliente);
            logger.debug("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
            if(client == null)
                throw new CatAppConfigurationException("Cliente no encontrado: " + idCliente, CatAppConfigurationException.LAYER_DAO, CatAppConfigurationException.ACTION_SELECT);
            return converter.toView(client);
        } catch (CatAppConfigurationException uae) {
            logger.debug("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
            logger.error("===>>>Error validando datos - " + uae.getMessage());
            throw uae;
        } catch (Exception ex) {
            logger.debug("Is rollbackOnly: " + TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
            CatAppConfigurationException uae = new CatAppConfigurationException("No fue posible obtener el usuario", CatAppConfigurationException.LAYER_DAO, CatAppConfigurationException.ACTION_SELECT);
            uae.addError("Ocurrio un error al obtener el usuario: {" + idCliente + "}");
            logger.error("Error al obtener usuario - CODE: {} - {}", uae.getExceptionCode(), idCliente, ex);
            throw uae;
        }
    }

}
