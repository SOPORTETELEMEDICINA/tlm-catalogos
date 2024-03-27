package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatEntidadesDomException;
import net.amentum.niomedic.catalogos.service.CatEntidadesDomService;
import net.amentum.niomedic.catalogos.views.CatEntidadesDomView;
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
@RequestMapping("catalogo-entidades")
public class CatEntidadesDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatEntidadesDomRest.class);

   private CatEntidadesDomService catEntidadesDomService;

   @Autowired
   public void setCatEntidadesDomService(CatEntidadesDomService catEntidadesDomService) {
      this.catEntidadesDomService = catEntidadesDomService;
   }

   @RequestMapping(value = "{idCatEntidadesDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatEntidadesDomView getDetailsByIdCatEntidadesDom(@PathVariable() Integer idCatEntidadesDom) throws CatEntidadesDomException {
      try {
         logger.info("===>>>Obtener los detalles del catEntidadesDom por Id: {}", idCatEntidadesDom);
         return catEntidadesDomService.getDetailsByIdCatEntidadesDom(idCatEntidadesDom);
      } catch (CatEntidadesDomException cEntiE) {
         throw cEntiE;
      } catch (Exception ex) {
         CatEntidadesDomException cEntiE = new CatEntidadesDomException("No fue posible obtener los detalles del catEntidadesDom por Id", CatEntidadesDomException.LAYER_REST, CatEntidadesDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catEntidadesDom por Id- CODE: {} - ", cEntiE.getExceptionCode(), ex);
         throw cEntiE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatEntidadesDomView> findAll() throws CatEntidadesDomException {
      return catEntidadesDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatEntidadesDomView> getCatEntidadesDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatEntidadesDomException {

      logger.info("===>>>getCatEntidadesDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionEntidades";

      return catEntidadesDomService.getCatEntidadesDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
