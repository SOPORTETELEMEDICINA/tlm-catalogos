package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatServicioAtencionException;
import net.amentum.niomedic.catalogos.views.CatServicioAtencionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("servicio-atencion")
public class CatServicioAtencionRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatServicioAtencionRest.class);

    private net.amentum.niomedic.catalogos.service.CatServicioAtencionService CatServicioAtencionService;

    @Autowired
    public void setCatServicioAtencionService(net.amentum.niomedic.catalogos.service.CatServicioAtencionService CatServicioAtencionService) {
        this.CatServicioAtencionService = CatServicioAtencionService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idServicioAtencion}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatServicioAtencionView getDetailsByidServicioAtencion(@PathVariable() Integer idServicioAtencion) throws CatServicioAtencionException {
        try {
            logger.info("===>>>Obtener los detalles del CatServicioAtencion por Id: {}", idServicioAtencion);
            return CatServicioAtencionService.getDetailsByidServicioAtencion(idServicioAtencion);
        } catch (CatServicioAtencionException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatServicioAtencionException cJor = new CatServicioAtencionException("No fue posible obtener los detalles del CatServicioAtencion por Id", CatServicioAtencionException.LAYER_REST, CatServicioAtencionException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatServicioAtencion por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatServicioAtencionView> findAll() throws CatServicioAtencionException {
        return CatServicioAtencionService.findAll();
    }




}
