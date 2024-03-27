package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatLocalidadesDomException;
import net.amentum.niomedic.catalogos.service.CatLocalidadesDomService;
import net.amentum.niomedic.catalogos.views.CatLocalidadesDomView;
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
@RequestMapping("catalogo-localidades")
public class CatLocalidadesDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatLocalidadesDomRest.class);

   private CatLocalidadesDomService catLocalidadesDomService;

   @Autowired
   public void setCatLocalidadesDomService(CatLocalidadesDomService catLocalidadesDomService) {
      this.catLocalidadesDomService = catLocalidadesDomService;
   }

   @RequestMapping(value = "{idCatLocalidadesDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatLocalidadesDomView getDetailsByIdCatLocalidadesDom(@PathVariable() Integer idCatLocalidadesDom) throws CatLocalidadesDomException {
      try {
         logger.info("===>>>Obtener los detalles del catLocalidadesDom por Id: {}", idCatLocalidadesDom);
         return catLocalidadesDomService.getDetailsByIdCatLocalidadesDom(idCatLocalidadesDom);
      } catch (CatLocalidadesDomException cLocalE) {
         throw cLocalE;
      } catch (Exception ex) {
         CatLocalidadesDomException cLocalE = new CatLocalidadesDomException("No fue posible obtener los detalles del catLocalidadesDom por Id", CatLocalidadesDomException.LAYER_REST, CatLocalidadesDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catLocalidadesDom por Id- CODE: {} - ", cLocalE.getExceptionCode(), ex);
         throw cLocalE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatLocalidadesDomView> findAll() throws CatLocalidadesDomException {
      return catLocalidadesDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatLocalidadesDomView> getCatLocalidadesDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatLocalidadesDomException {

      logger.info("===>>>getCatLocalidadesDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionLocalidades";

      return catLocalidadesDomService.getCatLocalidadesDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
