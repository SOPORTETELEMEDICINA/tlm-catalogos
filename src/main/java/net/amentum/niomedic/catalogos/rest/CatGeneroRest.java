package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatGeneroException;
import net.amentum.niomedic.catalogos.views.CatGeneroView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("genero")
public class CatGeneroRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatGeneroRest.class);

    private net.amentum.niomedic.catalogos.service.CatGeneroService CatGeneroService;

    @Autowired
    public void setCatGeneroService(net.amentum.niomedic.catalogos.service.CatGeneroService CatGeneroService) {
        this.CatGeneroService = CatGeneroService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idGenero}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatGeneroView getDetailsByidGenero(@PathVariable() Integer idGenero) throws CatGeneroException {
        try {
            logger.info("===>>>Obtener los detalles del CatGenero por Id: {}", idGenero);
            return CatGeneroService.getDetailsByidGenero(idGenero);
        } catch (CatGeneroException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatGeneroException cJor = new CatGeneroException("No fue posible obtener los detalles del CatGenero por Id", CatGeneroException.LAYER_REST, CatGeneroException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatGenero por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatGeneroView> findAll() throws CatGeneroException {
        return CatGeneroService.findAll();
    }




}
