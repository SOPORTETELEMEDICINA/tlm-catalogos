package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatSexoException;
import net.amentum.niomedic.catalogos.views.CatSexoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("sexo")
public class CatSexoRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatSexoRest.class);

    private net.amentum.niomedic.catalogos.service.CatSexoService CatSexoService;

    @Autowired
    public void setCatSexoService(net.amentum.niomedic.catalogos.service.CatSexoService CatSexoService) {
        this.CatSexoService = CatSexoService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idSexo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatSexoView getDetailsByidSexo(@PathVariable() Integer idSexo) throws CatSexoException {
        try {
            logger.info("===>>>Obtener los detalles del CatSexo por Id: {}", idSexo);
            return CatSexoService.getDetailsByidSexo(idSexo);
        } catch (CatSexoException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatSexoException cJor = new CatSexoException("No fue posible obtener los detalles del CatSexo por Id", CatSexoException.LAYER_REST, CatSexoException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatSexo por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatSexoView> findAll() throws CatSexoException {
        return CatSexoService.findAll();
    }




}
