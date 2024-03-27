package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipoAsentamientoDomException;
import net.amentum.niomedic.catalogos.service.CatTipoAsentamientoDomService;
import net.amentum.niomedic.catalogos.views.CatTipoAsentamientoDomView;
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
@RequestMapping("catalogo-tipo-asentamiento")
public class CatTipoAsentamientoDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatTipoAsentamientoDomRest.class);

   private CatTipoAsentamientoDomService catTipoAsentamientoDomService;

   @Autowired
   public void setCatTipoAsentamientoDomService(CatTipoAsentamientoDomService catTipoAsentamientoDomService) {
      this.catTipoAsentamientoDomService = catTipoAsentamientoDomService;
   }

   @RequestMapping(value = "{idCatTipoAsentamientoDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatTipoAsentamientoDomView getDetailsByIdCatTipoAsentamientoDom(@PathVariable() Integer idCatTipoAsentamientoDom) throws CatTipoAsentamientoDomException {
      try {
         logger.info("===>>>Obtener los detalles del catTipoAsentamientoDom por Id: {}", idCatTipoAsentamientoDom);
         return catTipoAsentamientoDomService.getDetailsByIdCatTipoAsentamientoDom(idCatTipoAsentamientoDom);
      } catch (CatTipoAsentamientoDomException cTAseE) {
         throw cTAseE;
      } catch (Exception ex) {
         CatTipoAsentamientoDomException cTAseE = new CatTipoAsentamientoDomException("No fue posible obtener los detalles del catTipoAsentamientoDom por Id", CatTipoAsentamientoDomException.LAYER_REST, CatTipoAsentamientoDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catTipoAsentamientoDom por Id- CODE: {} - ", cTAseE.getExceptionCode(), ex);
         throw cTAseE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatTipoAsentamientoDomView> findAll() throws CatTipoAsentamientoDomException {
      return catTipoAsentamientoDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatTipoAsentamientoDomView> getCatTipoAsentamientoDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatTipoAsentamientoDomException {

      logger.info("===>>>getCatTipoAsentamientoDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionAsentamiento";

      return catTipoAsentamientoDomService.getCatTipoAsentamientoDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
