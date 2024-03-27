package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatSeAutodenominaAfromexicanoException;
import net.amentum.niomedic.catalogos.views.CatSeAutodenominaAfromexicanoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("autodenomina-afromexicano")
public class CatSeAutodenominaAfromexicanoRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatSeAutodenominaAfromexicanoRest.class);

    private net.amentum.niomedic.catalogos.service.CatSeAutodenominaAfromexicanoService CatSeAutodenominaAfromexicanoService;

    @Autowired
    public void setCatSeAutodenominaAfromexicanoService(net.amentum.niomedic.catalogos.service.CatSeAutodenominaAfromexicanoService CatSeAutodenominaAfromexicanoService) {
        this.CatSeAutodenominaAfromexicanoService = CatSeAutodenominaAfromexicanoService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idSeAutodenominaAfromexicano}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatSeAutodenominaAfromexicanoView getDetailsByidSeAutodenominaAfromexicano(@PathVariable() Integer idSeAutodenominaAfromexicano) throws CatSeAutodenominaAfromexicanoException {
        try {
            logger.info("===>>>Obtener los detalles del CatSeAutodenominaAfromexicano por Id: {}", idSeAutodenominaAfromexicano);
            return CatSeAutodenominaAfromexicanoService.getDetailsByidSeAutodenominaAfromexicano(idSeAutodenominaAfromexicano);
        } catch (CatSeAutodenominaAfromexicanoException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatSeAutodenominaAfromexicanoException cJor = new CatSeAutodenominaAfromexicanoException("No fue posible obtener los detalles del CatSeAutodenominaAfromexicano por Id", CatSeAutodenominaAfromexicanoException.LAYER_REST, CatSeAutodenominaAfromexicanoException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatSeAutodenominaAfromexicano por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatSeAutodenominaAfromexicanoView> findAll() throws CatSeAutodenominaAfromexicanoException {
        return CatSeAutodenominaAfromexicanoService.findAll();
    }

   


}
