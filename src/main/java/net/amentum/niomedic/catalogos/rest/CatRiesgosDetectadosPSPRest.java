package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatRiesgosDetectadosPSPException;
import net.amentum.niomedic.catalogos.service.CatRiesgosDetectadosPSPService;
import net.amentum.niomedic.catalogos.views.CatRiesgosDetectadosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("riesgos-detectados")
public class CatRiesgosDetectadosPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatRiesgosDetectadosPSPRest.class);

    private CatRiesgosDetectadosPSPService CatRiesgosDetectadosPSPService;

    @Autowired
    public void setCatRiesgosDetectadosService(CatRiesgosDetectadosPSPService CatRiesgosDetectadosPSPService) {
        this.CatRiesgosDetectadosPSPService = CatRiesgosDetectadosPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idRiesgosDetectados}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatRiesgosDetectadosView getDetailsByidRiesgosDetectados(@PathVariable() Integer idRiesgosDetectados) throws CatRiesgosDetectadosPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatRiesgosDetectados por Id: {}", idRiesgosDetectados);
            return CatRiesgosDetectadosPSPService.getDetailsByidRiesgo(idRiesgosDetectados);
        } catch (CatRiesgosDetectadosPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatRiesgosDetectadosPSPException cJor = new CatRiesgosDetectadosPSPException("No fue posible obtener los detalles del CatRiesgosDetectados por Id", CatRiesgosDetectadosPSPException.LAYER_REST, CatRiesgosDetectadosPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatRiesgosDetectados por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatRiesgosDetectadosView> findAll() throws CatRiesgosDetectadosPSPException {
        return CatRiesgosDetectadosPSPService.findAll();
    }




}
