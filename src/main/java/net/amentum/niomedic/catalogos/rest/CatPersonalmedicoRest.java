package net.amentum.niomedic.catalogos.rest;


import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatPersonalmedicoException;
import net.amentum.niomedic.catalogos.service.CatPersonalmedicoService;
import net.amentum.niomedic.catalogos.views.CatPersonalmedicoView;
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
@RequestMapping("catalogo-Personalmedico")

public class CatPersonalmedicoRest extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CatPersonalmedicoRest.class);

    private CatPersonalmedicoService CatPersonalmedicoService;

    @Autowired
    public void setCatPersonalmedicoService(CatPersonalmedicoService CatPersonalmedicoService) {
        this.CatPersonalmedicoService = CatPersonalmedicoService;
    }

    @RequestMapping(value = "{perid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatPersonalmedicoView getDetailsByperid(@PathVariable() Integer perid) throws CatPersonalmedicoException {
        try {
            logger.info("===>>>Obtener los detalles del CatPersonalmedico por Id: {}", perid);
            return CatPersonalmedicoService.getDetailsByperid(perid);
        } catch (CatPersonalmedicoException cPerM) {
            throw cPerM;
        } catch (Exception ex) {
            CatPersonalmedicoException cPerM = new CatPersonalmedicoException("No fue posible obtener los detalles del CatPersonalmedico por Id", CatPersonalmedicoException.LAYER_REST, CatPersonalmedicoException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatPersonalmedico por Id- CODE: {} - ", cPerM.getExceptionCode(), ex);
            throw cPerM;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatPersonalmedicoView> findAll() throws CatPersonalmedicoException {
        return CatPersonalmedicoService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatPersonalmedicoView> getCatPersonalmedicoSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatPersonalmedicoException {

        logger.info("===>>>getCatPersonalmedicoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "descripcion";

        return CatPersonalmedicoService.getCatPersonalmedicoSearch(datosBusqueda, page, size, orderColumn, orderType);
    }

}
