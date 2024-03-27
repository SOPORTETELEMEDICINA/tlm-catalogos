package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTitulosCie10Exception;
import net.amentum.niomedic.catalogos.service.CatTitulosCie10Service;
import net.amentum.niomedic.catalogos.views.CatTitulosCie10View;
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
@RequestMapping("catalogo-titulos-cie10")
public class CatTitulosCie10Rest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatTitulosCie10Rest.class);

   private CatTitulosCie10Service catTitulosCie10Service;

   @Autowired
   public void setCatTitulosCie10Service(CatTitulosCie10Service catTitulosCie10Service) {
      this.catTitulosCie10Service = catTitulosCie10Service;
   }

   @RequestMapping(value = "{idCatTitulosCie10}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatTitulosCie10View getDetailsByIdCatTitulosCie10(@PathVariable() Integer idCatTitulosCie10) throws CatTitulosCie10Exception {
      try {
         logger.info("===>>>Obtener los detalles del catTitulosCie10 por Id: {}", idCatTitulosCie10);
         return catTitulosCie10Service.getDetailsByIdCatTitulosCie10(idCatTitulosCie10);
      } catch (CatTitulosCie10Exception cTitE) {
         throw cTitE;
      } catch (Exception ex) {
         CatTitulosCie10Exception cTitE = new CatTitulosCie10Exception("No fue posible obtener los detalles del catTitulosCie10 por Id", CatTitulosCie10Exception.LAYER_REST, CatTitulosCie10Exception.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catTitulosCie10 por Id- CODE: {} - ", cTitE.getExceptionCode(), ex);
         throw cTitE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatTitulosCie10View> findAll() throws CatTitulosCie10Exception {
      return catTitulosCie10Service.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatTitulosCie10View> getCatTitulosCie10Search(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatTitulosCie10Exception {

      logger.info("===>>>getCatTitulosCie10Search(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "codigosTitulosCie10";

      return catTitulosCie10Service.getCatTitulosCie10Search(datosBusqueda, page, size, orderColumn, orderType);
   }

}
