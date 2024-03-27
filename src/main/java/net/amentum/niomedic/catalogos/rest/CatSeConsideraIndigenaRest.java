package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatSeConsideraIndigenaException;
import net.amentum.niomedic.catalogos.views.CatSeConsideraIndigenaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("considera-indigena")
public class CatSeConsideraIndigenaRest extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(CatSeConsideraIndigenaRest.class);

    private net.amentum.niomedic.catalogos.service.CatSeConsideraIndigenaService CatSeConsideraIndigenaService;

    @Autowired
    public void setCatSeConsideraIndigenaService(net.amentum.niomedic.catalogos.service.CatSeConsideraIndigenaService CatSeConsideraIndigenaService) {
        this.CatSeConsideraIndigenaService = CatSeConsideraIndigenaService;
    }
    @Value("${application.controller.direccion}")
    private String direccion;



    @RequestMapping(value = "{idSeConsideraIndigena}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatSeConsideraIndigenaView getDetailsByidSeConsideraIndigena(@PathVariable() Integer idSeConsideraIndigena) throws CatSeConsideraIndigenaException {
        try {
            logger.info("===>>>Obtener los detalles del CatSeConsideraIndigena por Id: {}", idSeConsideraIndigena);
            return CatSeConsideraIndigenaService.getDetailsByidSeConsideraIndigena(idSeConsideraIndigena);
        } catch (CatSeConsideraIndigenaException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatSeConsideraIndigenaException cJor = new CatSeConsideraIndigenaException("No fue posible obtener los detalles del CatSeConsideraIndigena por Id", CatSeConsideraIndigenaException.LAYER_REST, CatSeConsideraIndigenaException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatSeConsideraIndigena por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatSeConsideraIndigenaView> findAll() throws CatSeConsideraIndigenaException {
        return CatSeConsideraIndigenaService.findAll();
    }




}
