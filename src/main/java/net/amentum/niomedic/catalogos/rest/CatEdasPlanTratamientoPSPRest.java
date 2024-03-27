package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatEdasPlanTratamientoPSPException;
import net.amentum.niomedic.catalogos.service.CatEdasPlanTratamientoPSPService;
import net.amentum.niomedic.catalogos.views.CatEdasPlanTratamientoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("edas-plan-tratamiento")
public class CatEdasPlanTratamientoPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatEdasPlanTratamientoPSPRest.class);

    private CatEdasPlanTratamientoPSPService CatEdasPlanTratamientoPSPService;

    @Autowired
    public void setCatEdasPlanTratamientoService(CatEdasPlanTratamientoPSPService CatEdasPlanTratamientoPSPService) {
        this.CatEdasPlanTratamientoPSPService = CatEdasPlanTratamientoPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idEdasPlanTratamiento}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatEdasPlanTratamientoView getDetailsByidEdasPlanTratamiento(@PathVariable() Integer idEdasPlanTratamiento) throws CatEdasPlanTratamientoPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatEdasPlanTratamiento por Id: {}", idEdasPlanTratamiento);
            return CatEdasPlanTratamientoPSPService.getDetailsByidPlanTratamiento(idEdasPlanTratamiento);
        } catch (CatEdasPlanTratamientoPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatEdasPlanTratamientoPSPException cJor = new CatEdasPlanTratamientoPSPException("No fue posible obtener los detalles del CatEdasPlanTratamiento por Id", CatEdasPlanTratamientoPSPException.LAYER_REST, CatEdasPlanTratamientoPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatEdasPlanTratamiento por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatEdasPlanTratamientoView> findAll() throws CatEdasPlanTratamientoPSPException {
        return CatEdasPlanTratamientoPSPService.findAll();
    }




}
