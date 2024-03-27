package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatProgramaException;
import net.amentum.niomedic.catalogos.views.CatProgramaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("programa")
public class CatProgramaRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatProgramaRest.class);

    private net.amentum.niomedic.catalogos.service.CatProgramaService CatProgramaService;

    @Autowired
    public void setCatProgramaService(net.amentum.niomedic.catalogos.service.CatProgramaService CatProgramaService) {
        this.CatProgramaService = CatProgramaService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idPrograma}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatProgramaView getDetailsByidPrograma(@PathVariable() Integer idPrograma) throws CatProgramaException {
        try {
            logger.info("===>>>Obtener los detalles del CatPrograma por Id: {}", idPrograma);
            return CatProgramaService.getDetailsByidPrograma(idPrograma);
        } catch (CatProgramaException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatProgramaException cJor = new CatProgramaException("No fue posible obtener los detalles del CatPrograma por Id", CatProgramaException.LAYER_REST, CatProgramaException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatPrograma por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatProgramaView> findAll() throws CatProgramaException {
        return CatProgramaService.findAll();
    }




}
