package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatCapitulosCie10Exception;
import net.amentum.niomedic.catalogos.service.CatCapitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatCapitulosCie10View;
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
@RequestMapping("catalogo-capitulos-cie10")
public class CatCapitulosCie10Rest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatCapitulosCie10Rest.class);

   private CatCapitulosCie10Service catCapitulosCie10Service;

   @Autowired
   public void setCatCapitulosCie10Service(CatCapitulosCie10Service catCapitulosCie10Service) {
      this.catCapitulosCie10Service = catCapitulosCie10Service;
   }

   @RequestMapping(value = "{idCatCapitulosCie10}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatCapitulosCie10View getDetailsByIdCatCapitulosCie10(@PathVariable() Integer idCatCapitulosCie10) throws CatCapitulosCie10Exception {
      try {
         logger.info("===>>>Obtener los detalles del catCapitulosCie10 por Id: {}", idCatCapitulosCie10);
         return catCapitulosCie10Service.getDetailsByIdCatCapitulosCie10(idCatCapitulosCie10);
      } catch (CatCapitulosCie10Exception cCapE) {
         throw cCapE;
      } catch (Exception ex) {
         CatCapitulosCie10Exception cCapE = new CatCapitulosCie10Exception("No fue posible obtener los detalles del catCapitulosCie10 por Id", CatCapitulosCie10Exception.LAYER_REST, CatCapitulosCie10Exception.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catCapitulosCie10 por Id- CODE: {} - ", cCapE.getExceptionCode(), ex);
         throw cCapE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatCapitulosCie10View> findAll() throws CatCapitulosCie10Exception {
      return catCapitulosCie10Service.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatCapitulosCie10View> getCatCapitulosCie10Search(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatCapitulosCie10Exception {

      logger.info("===>>>getCatCapitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "codigosCapitulosCie10";

      return catCapitulosCie10Service.getCatCapitulosCie10Search(datosBusqueda, page, size, orderColumn, orderType);
   }

}
