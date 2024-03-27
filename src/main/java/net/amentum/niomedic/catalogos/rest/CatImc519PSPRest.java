package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatImc519PSPException;
import net.amentum.niomedic.catalogos.service.CatImc519PSPService;
import net.amentum.niomedic.catalogos.views.CatImc519View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("imc5-19")
public class CatImc519PSPRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatImc519PSPRest.class);

    private CatImc519PSPService CatImc519PSPService;

    @Autowired
    public void setCatImc519Service(CatImc519PSPService CatImc519PSPService) {
        this.CatImc519PSPService = CatImc519PSPService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idImc519}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatImc519View getDetailsByidImc519(@PathVariable() Integer idImc519) throws CatImc519PSPException {
        try {
            logger.info("===>>>Obtener los detalles del CatImc519 por Id: {}", idImc519);
            return CatImc519PSPService.getDetailsByidImc(idImc519);
        } catch (CatImc519PSPException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatImc519PSPException cJor = new CatImc519PSPException("No fue posible obtener los detalles del CatImc519 por Id", CatImc519PSPException.LAYER_REST, CatImc519PSPException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatImc519 por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatImc519View> findAll() throws CatImc519PSPException {
        return CatImc519PSPService.findAll();
    }




}
