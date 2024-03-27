package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatPesoParaTallaPSPException;
import net.amentum.niomedic.catalogos.service.CatPesoParaTallaPSPService;
import net.amentum.niomedic.catalogos.views.CatPesoParaTallaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("peso-para-talla")
public class CatPesoParaTallaPSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatPesoParaTallaPSPRest.class);

    private CatPesoParaTallaPSPService CatPesoParaTallaPSPService;

    @Autowired
    public void setCatPesoParaTallaService(CatPesoParaTallaPSPService CatPesoParaTallaPSPService) {
        this.CatPesoParaTallaPSPService = CatPesoParaTallaPSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idPesoParaTalla}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatPesoParaTallaView getDetailsByidPesoParaTalla(@PathVariable() Integer idPesoParaTalla) throws CatPesoParaTallaPSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatPesoParaTalla por Id: {}", idPesoParaTalla);
            return CatPesoParaTallaPSPService.getDetailsByidPeso(idPesoParaTalla);
        } catch (CatPesoParaTallaPSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatPesoParaTallaPSPException cJor = new CatPesoParaTallaPSPException("No fue posible obtener los detalles del CatPesoParaTalla por Id", CatPesoParaTallaPSPException.LAYER_REST, CatPesoParaTallaPSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatPesoParaTalla por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatPesoParaTallaView> findAll() throws CatPesoParaTallaPSPException {
        return CatPesoParaTallaPSPService.findAll();
    }




}
