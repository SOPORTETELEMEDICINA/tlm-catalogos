package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipoDificultadException;
import net.amentum.niomedic.catalogos.views.CatTipoDificultadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("tipo-dificultad")
public class CatTipoDificultadRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatTipoDificultadRest.class);

    private net.amentum.niomedic.catalogos.service.CatTipoDificultadService CatTipoDificultadService;

    @Autowired
    public void setCatTipoDificultadService(net.amentum.niomedic.catalogos.service.CatTipoDificultadService CatTipoDificultadService) {
        this.CatTipoDificultadService = CatTipoDificultadService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idTipoDificultad}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatTipoDificultadView getDetailsByidTipoDificultad(@PathVariable() Integer idTipoDificultad) throws CatTipoDificultadException {
        try {
            logger.info("===>>>Obtener los detalles del CatTipoDificultad por Id: {}", idTipoDificultad);
            return CatTipoDificultadService.getDetailsByidTipoDificultad(idTipoDificultad);
        } catch (CatTipoDificultadException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatTipoDificultadException cJor = new CatTipoDificultadException("No fue posible obtener los detalles del CatTipoDificultad por Id", CatTipoDificultadException.LAYER_REST, CatTipoDificultadException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatTipoDificultad por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatTipoDificultadView> findAll() throws CatTipoDificultadException {
        return CatTipoDificultadService.findAll();
    }




}
