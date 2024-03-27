package net.amentum.niomedic.catalogos.rest;


import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatAreatrabajoException;
import net.amentum.niomedic.catalogos.service.CatAreatrabajoService;
import net.amentum.niomedic.catalogos.views.CatAreatrabajoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("catalogo-Areatrabajo")

public class CatAreatrabajoRest extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CatAreatrabajoRest.class);

    private CatAreatrabajoService CatAreatrabajoService;

    @Autowired
    public void setCatAreatrabajoService(CatAreatrabajoService CatAreatrabajoService) {
        this.CatAreatrabajoService = CatAreatrabajoService;
    }

    @RequestMapping(value = "{atrid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatAreatrabajoView getDetailsByatrid(@PathVariable() Integer atrid) throws CatAreatrabajoException {
        try {
            logger.info("===>>>Obtener los detalles del CatAreatrabajo por Id: {}", atrid);
            return CatAreatrabajoService.getDetailsByatrid(atrid);
        } catch (CatAreatrabajoException cAreaT) {
            throw cAreaT;
        } catch (Exception ex) {
            CatAreatrabajoException cAreaT = new CatAreatrabajoException("No fue posible obtener los detalles del CatAreatrabajo por Id", CatAreatrabajoException.LAYER_REST, CatAreatrabajoException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatAreatrabajo por Id- CODE: {} - ", cAreaT.getExceptionCode(), ex);
            throw cAreaT;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatAreatrabajoView> findAll() throws CatAreatrabajoException {
        return CatAreatrabajoService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatAreatrabajoView> getCatAreatrabajoSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatAreatrabajoException {

        logger.info("===>>>getCatAreatrabajoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "nombre";

        return CatAreatrabajoService.getCatAreatrabajoSearch(datosBusqueda, page, size, orderColumn, orderType);
    }

}
