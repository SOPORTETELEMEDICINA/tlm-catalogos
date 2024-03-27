package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatCie10Exception;
import net.amentum.niomedic.catalogos.service.CatCie10Service;
import net.amentum.niomedic.catalogos.views.CatCie10View;
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
@RequestMapping("catalogo-diagnosticos")
public class CatCie10Rest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatCie10Rest.class);

   private CatCie10Service catCie10Service;

   @Autowired
   public void setCatCie10Service(CatCie10Service catCie10Service) {
      this.catCie10Service = catCie10Service;
   }

   @RequestMapping(value = "{idCatCie10}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatCie10View getDetailsByIdCatCie10(@PathVariable() Integer idCatCie10) throws CatCie10Exception {
      try {
         logger.info("===>>>Obtener los detalles del catCie10 por Id: {}", idCatCie10);
         return catCie10Service.getDetailsByIdCatCie10(idCatCie10);
      } catch (CatCie10Exception cCie10E) {
         throw cCie10E;
      } catch (Exception ex) {
         CatCie10Exception cCie10E = new CatCie10Exception("No fue posible obtener los detalles del catCie10 por Id", CatCie10Exception.LAYER_REST, CatCie10Exception.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catCie10 por Id- CODE: {} - ", cCie10E.getExceptionCode(), ex);
         throw cCie10E;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatCie10View> findAll() throws CatCie10Exception {
      return catCie10Service.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatCie10View> getCatCie10Search(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatCie10Exception {

      logger.info("===>>>getCatCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "catalogKey";

      return catCie10Service.getCatCie10Search(datosBusqueda, page, size, orderColumn, orderType);
   }

}
