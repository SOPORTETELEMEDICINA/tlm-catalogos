package net.amentum.niomedic.catalogos.rest;


import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipocontratoException;
import net.amentum.niomedic.catalogos.service.CatTipocontratoService;
import net.amentum.niomedic.catalogos.views.CatTipocontratoView;
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
@RequestMapping("catalogo-Tipocontrato")

public class CatTipocontratoRest extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(CatTipocontratoRest.class);

    private CatTipocontratoService CatTipocontratoService;

    @Autowired
    public void setCatTipocontratoService(CatTipocontratoService CatTipocontratoService) {
        this.CatTipocontratoService = CatTipocontratoService;
    }

    @RequestMapping(value = "{conid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatTipocontratoView getDetailsByconid(@PathVariable() Integer conid) throws CatTipocontratoException {
        try {
            logger.info("===>>>Obtener los detalles del CatTipocontrato por Id: {}", conid);
            return CatTipocontratoService.getDetailsByconid(conid);
        } catch (CatTipocontratoException cTipC) {
            throw cTipC;
        } catch (Exception ex) {
            CatTipocontratoException cTipC = new CatTipocontratoException("No fue posible obtener los detalles del CatTipocontrato por Id", CatTipocontratoException.LAYER_REST, CatTipocontratoException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatTipocontrato por Id- CODE: {} - ", cTipC.getExceptionCode(), ex);
            throw cTipC;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatTipocontratoView> findAll() throws CatTipocontratoException {
        return CatTipocontratoService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatTipocontratoView> getCatTipocontratoSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatTipocontratoException {

        logger.info("===>>>getCatTipocontratoSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "tipo";

        return CatTipocontratoService.getCatTipocontratoSearch(datosBusqueda, page, size, orderColumn, orderType);
    }


}
