package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatAppConfigurationException;
import net.amentum.niomedic.catalogos.service.CatAppConfigurationService;
import net.amentum.niomedic.catalogos.views.CatAppConfigurationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("AppConfiguration")
public class CatAppConfigurationRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatAppConfigurationRest.class);
    @Autowired
    private CatAppConfigurationService service;

//    @RequestMapping(value = "IdAppConfiguration/{idCliente}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public CatAppConfigurationView getUserByidCliente(@PathVariable("idCliente") Integer idCliente) throws CatAppConfigurationException {
//        try {
//            if(idCliente == null )
//                throw new CatAppConfigurationException("Usuario vacio", CatAppConfigurationException.LAYER_SERVICE, CatAppConfigurationException.ACTION_INSERT);
//            return service.getUserByidCliente(idCliente);
//        } catch (CatAppConfigurationException uae) {
//            logger.error("===>>>Error validando datos - " + uae.getMessage());
//            throw uae;
//        } catch (Exception ex) {
//            CatAppConfigurationException uae = new CatAppConfigurationException("No fue posible obtener el usuario", CatAppConfigurationException.LAYER_SERVICE, CatAppConfigurationException.ACTION_INSERT);
//            assert idCliente != null;
//            uae.addError("Ocurrio un error al obtener el usuario: {" + idCliente.toString() + "}");
//            logger.error("Error al obtener usuario - CODE: {} - {}", uae.getExceptionCode(), idCliente, ex);
//            throw uae;
//        }
//    }
//


    @RequestMapping(value = "CustomerConfig/{cliente}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatAppConfigurationView getUserByCliente(@PathVariable("cliente") String cliente) throws CatAppConfigurationException {
        try {
            if(cliente == null || cliente.isEmpty())
                throw new CatAppConfigurationException("Cliente vacío", CatAppConfigurationException.LAYER_SERVICE, CatAppConfigurationException.ACTION_INSERT);
            return service.getConfigByCliente(cliente);
        } catch (CatAppConfigurationException uae) {
            logger.error("===>>>Error validando datos - " + uae.getMessage());
            throw uae;
        } catch (Exception ex) {
            CatAppConfigurationException uae = new CatAppConfigurationException("No fue posible obtener el usuario", CatAppConfigurationException.LAYER_SERVICE, CatAppConfigurationException.ACTION_INSERT);
            uae.addError("Ocurrió un error al obtener el usuario: {" + cliente + "}");
            logger.error("Error al obtener usuario - CODE: {} - {}", uae.getExceptionCode(), cliente, ex);
            throw uae;
        }
    }
}
