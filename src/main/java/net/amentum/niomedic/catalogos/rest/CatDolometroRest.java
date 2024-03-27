package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatDolometroException;
import net.amentum.niomedic.catalogos.service.CatDolometroService;
import net.amentum.niomedic.catalogos.views.CatDolometroView;
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
@RequestMapping("catalogo-dolometro")
public class CatDolometroRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatDolometroRest.class);

   private CatDolometroService catDolometroService;

   @Autowired
   public void setCatDolometroService(CatDolometroService catDolometroService) {
      this.catDolometroService = catDolometroService;
   }

   @RequestMapping(value = "{idCatDolometro}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatDolometroView getDetailsByIdCatDolometro(@PathVariable() Integer idCatDolometro) throws CatDolometroException {
      try {
         logger.info("===>>>Obtener los detalles del catDolometro por Id: {}", idCatDolometro);
         return catDolometroService.getDetailsByIdCatDolometro(idCatDolometro);
      } catch (CatDolometroException cDoloE) {
         throw cDoloE;
      } catch (Exception ex) {
         CatDolometroException cDoloE = new CatDolometroException("No fue posible obtener los detalles del catDolometro por Id", CatDolometroException.LAYER_REST, CatDolometroException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catDolometro por Id- CODE: {} - ", cDoloE.getExceptionCode(), ex);
         throw cDoloE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatDolometroView> findAll() throws CatDolometroException {
      return catDolometroService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatDolometroView> getCatDolometroSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatDolometroException {

      logger.info("===>>>getCatDolometroSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "doloDescripcion";

      return catDolometroService.getCatDolometroSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
