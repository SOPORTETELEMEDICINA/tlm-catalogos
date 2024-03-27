package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatInstitucionesException;
import net.amentum.niomedic.catalogos.service.CatInstitucionesService;
import net.amentum.niomedic.catalogos.views.CatInstitucionesView;
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
@RequestMapping("catalogo-Instituciones")


public class CatInstitucionesRest extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(CatInstitucionesRest.class);

    private CatInstitucionesService CatInstitucionesService;

    @Autowired
    public void setCatInstitucionesService(CatInstitucionesService CatInstitucionesService) {
        this.CatInstitucionesService = CatInstitucionesService;
    }

    @RequestMapping(value = "{idinstitucion}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatInstitucionesView getDetailsByidinstitucion(@PathVariable() Integer idinstitucion) throws CatInstitucionesException {
        try {
            logger.info("===>>>Obtener los detalles del CatInstituciones por Id: {}", idinstitucion);
            return CatInstitucionesService.getDetailsByidinstitucion(idinstitucion);
        } catch (CatInstitucionesException cInt) {
            throw cInt;
        } catch (Exception ex) {
            CatInstitucionesException cInt = new CatInstitucionesException("No fue posible obtener los detalles del CatInstituciones por Id", CatInstitucionesException.LAYER_REST, CatInstitucionesException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatInstituciones por Id- CODE: {} - ", cInt.getExceptionCode(), ex);
            throw cInt;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatInstitucionesView> findAll() throws CatInstitucionesException {
        return CatInstitucionesService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatInstitucionesView> getCatInstitucionesSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatInstitucionesException {

        logger.info("===>>>getCatInstitucionesSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 150;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "instnombre";

        return CatInstitucionesService.getCatInstitucionesSearch(datosBusqueda, page, size, orderColumn, orderType);
    }


}
