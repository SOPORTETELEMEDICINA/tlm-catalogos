package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatCodigoPostalDomException;
import net.amentum.niomedic.catalogos.service.CatCodigoPostalDomService;
import net.amentum.niomedic.catalogos.views.CatCodigoPostalDomView;
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
@RequestMapping("catalogo-codigo-postal")
public class CatCodigoPostalDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatCodigoPostalDomRest.class);

   private CatCodigoPostalDomService catCodigoPostalDomService;

   @Autowired
   public void setCatCodigoPostalDomService(CatCodigoPostalDomService catCodigoPostalDomService) {
      this.catCodigoPostalDomService = catCodigoPostalDomService;
   }

   @RequestMapping(value = "{idCatCodigoPostalDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatCodigoPostalDomView getDetailsByIdCatCodigoPostalDom(@PathVariable() Long idCatCodigoPostalDom) throws CatCodigoPostalDomException {
      try {
         logger.info("===>>>Obtener los detalles del catCodigoPostalDom por Id: {}", idCatCodigoPostalDom);
         return catCodigoPostalDomService.getDetailsByIdCatCodigoPostalDom(idCatCodigoPostalDom);
      } catch (CatCodigoPostalDomException cCPE) {
         throw cCPE;
      } catch (Exception ex) {
         CatCodigoPostalDomException cCPE = new CatCodigoPostalDomException("No fue posible obtener los detalles del catCodigoPostalDom por Id", CatCodigoPostalDomException.LAYER_REST, CatCodigoPostalDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catCodigoPostalDom por Id- CODE: {} - ", cCPE.getExceptionCode(), ex);
         throw cCPE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatCodigoPostalDomView> findAll() throws CatCodigoPostalDomException {
      return catCodigoPostalDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatCodigoPostalDomView> getCatCodigoPostalDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatCodigoPostalDomException {

      logger.info("===>>>getCatCodigoPostalDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "codigoPostal";

      return catCodigoPostalDomService.getCatCodigoPostalDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
