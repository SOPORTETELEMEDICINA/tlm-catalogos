package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatOrigenDificultadException;
import net.amentum.niomedic.catalogos.views.CatOrigenDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("origen-dificultad")
public class CatOrigenDificultadRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatOrigenDificultadRest.class);

    private net.amentum.niomedic.catalogos.service.CatOrigenDificultadService CatOrigenDificultadService;

    @Autowired
    public void setCatOrigenDificultadService(net.amentum.niomedic.catalogos.service.CatOrigenDificultadService CatOrigenDificultadService) {
        this.CatOrigenDificultadService = CatOrigenDificultadService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idOrigenDificultad}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatOrigenDificultadView getDetailsByidOrigenDificultad(@PathVariable() Integer idOrigenDificultad) throws CatOrigenDificultadException {
        try {
            logger.info("===>>>Obtener los detalles del CatOrigenDificultad por Id: {}", idOrigenDificultad);
            return CatOrigenDificultadService.getDetailsByidOrigenDificultad(idOrigenDificultad);
        } catch (CatOrigenDificultadException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatOrigenDificultadException cJor = new CatOrigenDificultadException("No fue posible obtener los detalles del CatOrigenDificultad por Id", CatOrigenDificultadException.LAYER_REST, CatOrigenDificultadException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatOrigenDificultad por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatOrigenDificultadView> findAll() throws CatOrigenDificultadException {
        return CatOrigenDificultadService.findAll();
    }




}
