package net.amentum.niomedic.catalogos.rest;


import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipoplazaException;
import net.amentum.niomedic.catalogos.service.CatTipoplazaService;
import net.amentum.niomedic.catalogos.views.CatTipoplazaView;
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
@RequestMapping("catalogo-Tipoplaza")


public class CatTipoplazaRest extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CatTipoplazaRest.class);

    private CatTipoplazaService CatTipoplazaService;

    @Autowired
    public void setCatTipoplazaService(CatTipoplazaService CatTipoplazaService) {
        this.CatTipoplazaService = CatTipoplazaService;
    }

    @RequestMapping(value = "{plaid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CatTipoplazaView getDetailsByplaid(@PathVariable() Integer plaid) throws CatTipoplazaException {
        try {
            logger.info("===>>>Obtener los detalles del CatTipoplaza por Id: {}", plaid);
            return CatTipoplazaService.getDetailsByplaid(plaid);
        } catch (CatTipoplazaException cTipP) {
            throw cTipP;
        } catch (Exception ex) {
            CatTipoplazaException cTipP = new CatTipoplazaException("No fue posible obtener los detalles del CatTipoplaza por Id", CatTipoplazaException.LAYER_REST, CatTipoplazaException.ACTION_SELECT);
            logger.error("===>>>Error al obtener los detalles del CatTipoplaza por Id- CODE: {} - ", cTipP.getExceptionCode(), ex);
            throw cTipP;
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CatTipoplazaView> findAll() throws CatTipoplazaException {
        return CatTipoplazaService.findAll();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<CatTipoplazaView> getCatTipoplazaSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String orderColumn,
                                                        @RequestParam(required = false) String orderType) throws CatTipoplazaException {

        logger.info("===>>>getCatTipoplazaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                datosBusqueda, page, size, orderColumn, orderType);

        if (page == null)
            page = 0;
        if (size == null)
            size = 30;
        if (orderType == null || orderType.isEmpty())
            orderType = "asc";
        if (orderColumn == null || orderColumn.isEmpty())
            orderColumn = "tipo";

        return CatTipoplazaService.getCatTipoplazaSearch(datosBusqueda, page, size, orderColumn, orderType);
    }


}
