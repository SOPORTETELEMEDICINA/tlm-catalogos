package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipoVialidadDomException;
import net.amentum.niomedic.catalogos.service.CatTipoVialidadDomService;
import net.amentum.niomedic.catalogos.views.CatTipoVialidadDomView;
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
@RequestMapping("catalogo-tipo-vialidad")
public class CatTipoVialidadDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatTipoVialidadDomRest.class);

   private CatTipoVialidadDomService catTipoVialidadDomService;

   @Autowired
   public void setCatTipoVialidadDomService(CatTipoVialidadDomService catTipoVialidadDomService) {
      this.catTipoVialidadDomService = catTipoVialidadDomService;
   }

   @RequestMapping(value = "{idCatTipoVialidadDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatTipoVialidadDomView getDetailsByIdCatTipoVialidadDom(@PathVariable() Integer idCatTipoVialidadDom) throws CatTipoVialidadDomException {
      try {
         logger.info("===>>>Obtener los detalles del catTipoVialidadDom por Id: {}", idCatTipoVialidadDom);
         return catTipoVialidadDomService.getDetailsByIdCatTipoVialidadDom(idCatTipoVialidadDom);
      } catch (CatTipoVialidadDomException cVialE) {
         throw cVialE;
      } catch (Exception ex) {
         CatTipoVialidadDomException cVialE = new CatTipoVialidadDomException("No fue posible obtener los detalles del catTipoVialidadDom por Id", CatTipoVialidadDomException.LAYER_REST, CatTipoVialidadDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catTipoVialidadDom por Id- CODE: {} - ", cVialE.getExceptionCode(), ex);
         throw cVialE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatTipoVialidadDomView> findAll() throws CatTipoVialidadDomException {
      return catTipoVialidadDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatTipoVialidadDomView> getCatTipoVialidadDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatTipoVialidadDomException {

      logger.info("===>>>getCatTipoVialidadDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionVialidad";

      return catTipoVialidadDomService.getCatTipoVialidadDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
