package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatResultadoEdiPSPException;
import net.amentum.niomedic.catalogos.service.CatResultadoEdiPSPService;
import net.amentum.niomedic.catalogos.views.CatResultadoEdiView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("resultado-edi")
public class CatResultadoEdiPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatResultadoEdiPSPRest.class);

    private CatResultadoEdiPSPService CatResultadoEdiPSPService;

    @Autowired
    public void setCatResultadoEdiService(CatResultadoEdiPSPService CatResultadoEdiPSPService) {
        this.CatResultadoEdiPSPService = CatResultadoEdiPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idResultadoEdi}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatResultadoEdiView getDetailsByidResultadoEdi(@PathVariable() Integer idResultadoEdi) throws CatResultadoEdiPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatResultadoEdi por Id: {}", idResultadoEdi);
            return CatResultadoEdiPSPService.getDetailsByidEdi(idResultadoEdi);
        } catch (CatResultadoEdiPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatResultadoEdiPSPException cJor = new CatResultadoEdiPSPException("No fue posible obtener los detalles del CatResultadoEdi por Id", CatResultadoEdiPSPException.LAYER_REST, CatResultadoEdiPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatResultadoEdi por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatResultadoEdiView> findAll() throws CatResultadoEdiPSPException {
        return CatResultadoEdiPSPService.findAll();
    }




}
