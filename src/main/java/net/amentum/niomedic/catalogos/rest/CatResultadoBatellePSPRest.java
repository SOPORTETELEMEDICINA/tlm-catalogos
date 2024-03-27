package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatResultadoBatellePSPException;
import net.amentum.niomedic.catalogos.service.CatResultadoBatellePSPService;
import net.amentum.niomedic.catalogos.views.CatResultadoBatelleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("resultado-batelle")
public class CatResultadoBatellePSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatResultadoBatellePSPRest.class);

    private CatResultadoBatellePSPService CatResultadoBatellePSPService;

    @Autowired
    public void setCatResultadoBatelleService(CatResultadoBatellePSPService CatResultadoBatellePSPService) {
        this.CatResultadoBatellePSPService = CatResultadoBatellePSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idResultadoBatelle}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatResultadoBatelleView getDetailsByidResultadoBatelle(@PathVariable() Integer idResultadoBatelle) throws CatResultadoBatellePSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatResultadoBatelle por Id: {}", idResultadoBatelle);
            return CatResultadoBatellePSPService.getDetailsByidBatelle(idResultadoBatelle);
        } catch (CatResultadoBatellePSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatResultadoBatellePSPException cJor = new CatResultadoBatellePSPException("No fue posible obtener los detalles del CatResultadoBatelle por Id", CatResultadoBatellePSPException.LAYER_REST, CatResultadoBatellePSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatResultadoBatelle por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatResultadoBatelleView> findAll() throws CatResultadoBatellePSPException {
        return CatResultadoBatellePSPService.findAll();
    }




}
