package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatJornadaException;
import net.amentum.niomedic.catalogos.service.CatJornadaService;
import net.amentum.niomedic.catalogos.views.CatJornadaView;
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
@RequestMapping("catalogo-Jornada")

public class CatJornadaRest extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CatJornadaRest.class);

    private CatJornadaService CatJornadaService;

    @Autowired
    public void setCatJornadaService(CatJornadaService CatJornadaService) {
        this.CatJornadaService = CatJornadaService;
    }

    @RequestMapping(value = "{jorid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatJornadaView getDetailsByjorid(@PathVariable() Integer jorid) throws CatJornadaException {
        try {
            logger.info("===>>>Obtener los detalles del CatJornada por Id: {}", jorid);
            return CatJornadaService.getDetailsByjorid(jorid);
        } catch (CatJornadaException cJor) {
            throw cJor;
        } catch (Exception ex) {
            CatJornadaException cJor = new CatJornadaException("No fue posible obtener los detalles del CatJornada por Id", CatJornadaException.LAYER_REST, CatJornadaException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatJornada por Id- CODE: {} - ", cJor.getExceptionCode(), ex);
            throw cJor;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatJornadaView> findAll() throws CatJornadaException {
        return CatJornadaService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatJornadaView> getCatJornadaSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatJornadaException {

        logger.info("===>>>getCatJornadaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "descripcion";

        return CatJornadaService.getCatJornadaSearch(datosBusqueda, page, size, orderColumn, orderType);
    }

}
