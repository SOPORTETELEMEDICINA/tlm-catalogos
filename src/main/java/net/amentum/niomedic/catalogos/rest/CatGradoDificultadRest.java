package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatGradoDificultadException;
import net.amentum.niomedic.catalogos.views.CatGradoDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("grado-dificultad")
public class CatGradoDificultadRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatGradoDificultadRest.class);

    private net.amentum.niomedic.catalogos.service.CatGradoDificultadService CatGradoDificultadService;

    @Autowired
    public void setCatGradoDificultadService(net.amentum.niomedic.catalogos.service.CatGradoDificultadService CatGradoDificultadService) {
        this.CatGradoDificultadService = CatGradoDificultadService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idGradoDificultad}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatGradoDificultadView getDetailsByidGradoDificultad(@PathVariable() Integer idGradoDificultad) throws CatGradoDificultadException {
        try {
            logger.info("===>>>Obtener los detalles del CatGradoDificultad por Id: {}", idGradoDificultad);
            return CatGradoDificultadService.getDetailsByidGradoDificultad(idGradoDificultad);
        } catch (CatGradoDificultadException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatGradoDificultadException cJor = new CatGradoDificultadException("No fue posible obtener los detalles del CatGradoDificultad por Id", CatGradoDificultadException.LAYER_REST, CatGradoDificultadException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatGradoDificultad por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatGradoDificultadView> findAll() throws CatGradoDificultadException {
        return CatGradoDificultadService.findAll();
    }




}
