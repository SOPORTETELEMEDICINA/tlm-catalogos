package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatIrasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.service.CatIrasPlanTratamientoPSPService;
import net.amentum.niomedic.catalogos.views.CatIrasPlanTratamientoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("iras-plan-tratamiento")
public class CatIrasPlanTratamientoPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatIrasPlanTratamientoPSPRest.class);

    private CatIrasPlanTratamientoPSPService CatIrasPlanTratamientoPSPService;

    @Autowired
    public void setCatIrasPlanTratamientoService(CatIrasPlanTratamientoPSPService CatIrasPlanTratamientoPSPService) {
        this.CatIrasPlanTratamientoPSPService = CatIrasPlanTratamientoPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idIrasPlanTratamiento}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatIrasPlanTratamientoView getDetailsByidIrasPlanTratamiento(@PathVariable() Integer idIrasPlanTratamiento) throws CatIrasPlanTratamientoPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatIrasPlanTratamiento por Id: {}", idIrasPlanTratamiento);
            return CatIrasPlanTratamientoPSPService.getDetailsByidIras(idIrasPlanTratamiento);
        } catch (CatIrasPlanTratamientoPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatIrasPlanTratamientoPSPException cJor = new CatIrasPlanTratamientoPSPException("No fue posible obtener los detalles del CatIrasPlanTratamiento por Id", CatIrasPlanTratamientoPSPException.LAYER_REST, CatIrasPlanTratamientoPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatIrasPlanTratamiento por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatIrasPlanTratamientoView> findAll() throws CatIrasPlanTratamientoPSPException {
        return CatIrasPlanTratamientoPSPService.findAll();
    }




}
