package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatSubtitulosCie10Exception;
import net.amentum.niomedic.catalogos.service.CatSubtitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatSubtitulosCie10View;
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
@RequestMapping("catalogo-subtitulos-cie10")
public class CatSubtitulosCie10Rest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatSubtitulosCie10Rest.class);

   private CatSubtitulosCie10Service catSubtitulosCie10Service;

   @Autowired
   public void setCatSubtitulosCie10Service(CatSubtitulosCie10Service catSubtitulosCie10Service) {
      this.catSubtitulosCie10Service = catSubtitulosCie10Service;
   }

   @RequestMapping(value = "{idCatSubtitulosCie10}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatSubtitulosCie10View getDetailsByIdCatSubtitulosCie10(@PathVariable() Integer idCatSubtitulosCie10) throws CatSubtitulosCie10Exception {
      try {
         logger.info("===>>>Obtener los detalles del catSubtitulosCie10 por Id: {}", idCatSubtitulosCie10);
         return catSubtitulosCie10Service.getDetailsByIdCatSubtitulosCie10(idCatSubtitulosCie10);
      } catch (CatSubtitulosCie10Exception cSubE) {
         throw cSubE;
      } catch (Exception ex) {
         CatSubtitulosCie10Exception cSubE = new CatSubtitulosCie10Exception("No fue posible obtener los detalles del catSubtitulosCie10 por Id", CatSubtitulosCie10Exception.LAYER_REST, CatSubtitulosCie10Exception.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catSubtitulosCie10 por Id- CODE: {} - ", cSubE.getExceptionCode(), ex);
         throw cSubE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatSubtitulosCie10View> findAll() throws CatSubtitulosCie10Exception {
      return catSubtitulosCie10Service.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatSubtitulosCie10View> getCatSubtitulosCie10Search(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatSubtitulosCie10Exception {

      logger.info("===>>>getCatSubtitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "codigosSubtitulosCie10";

      return catSubtitulosCie10Service.getCatSubtitulosCie10Search(datosBusqueda, page, size, orderColumn, orderType);
   }

}
