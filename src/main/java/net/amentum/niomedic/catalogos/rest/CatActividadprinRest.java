package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatActividadprinException;
import net.amentum.niomedic.catalogos.service.CatActividadprinService;
import net.amentum.niomedic.catalogos.views.CatActividadprinView;
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
@RequestMapping("catalogo-Actividadprincipal")

public class CatActividadprinRest extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CatActividadprinRest.class);

    private CatActividadprinService CatActividadprinService;

    @Autowired
    public void setCatActividadprinService(CatActividadprinService CatActividadprinService) {
        this.CatActividadprinService = CatActividadprinService;
    }

    @RequestMapping(value = "{actid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatActividadprinView getDetailsByactid(@PathVariable() Integer actid) throws CatActividadprinException {
        try {
            logger.info("===>>>Obtener los detalles del CatActividadprin por Id: {}", actid);
            return CatActividadprinService.getDetailsByactid(actid);
        } catch (CatActividadprinException CActp) {
            throw CActp;
        } catch (Exception ex) {
            CatActividadprinException CActp = new CatActividadprinException("No fue posible obtener los detalles del CatActividadprin por Id", CatActividadprinException.LAYER_REST, CatActividadprinException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatActividadprin por Id- CODE: {} - ", CActp.getExceptionCode(), ex);
            throw CActp;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatActividadprinView> findAll() throws CatActividadprinException {
        return CatActividadprinService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatActividadprinView> getCatActividadprinSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatActividadprinException {

        logger.info("===>>>getCatActividadprinSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "descripcion";

        return CatActividadprinService.getCatActividadprinSearch(datosBusqueda, page, size, orderColumn, orderType);
    }


}
