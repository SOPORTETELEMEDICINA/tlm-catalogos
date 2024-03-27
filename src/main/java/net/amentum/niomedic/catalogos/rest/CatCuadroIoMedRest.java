package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatCuadroIoMedException;
import net.amentum.niomedic.catalogos.service.CatCuadroIoMedService;
import net.amentum.niomedic.catalogos.views.CatCuadroIoMedView;
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
@RequestMapping("catalogo-cuadroIO")
public class CatCuadroIoMedRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatCuadroIoMedRest.class);

   private CatCuadroIoMedService catCuadroIoMedService;

   @Autowired
   public void setCatCuadroIoMedService(CatCuadroIoMedService catCuadroIoMedService) {
      this.catCuadroIoMedService = catCuadroIoMedService;
   }

   @RequestMapping(value = "{idCatCuadroIoMed}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatCuadroIoMedView getDetailsByIdCatCuadroIoMed(@PathVariable() Integer idCatCuadroIoMed) throws CatCuadroIoMedException {
      try {
         logger.info("===>>>Obtener los detalles del catCuadroIoMed por Id: {}", idCatCuadroIoMed);
         return catCuadroIoMedService.getDetailsByIdCatCuadroIoMed(idCatCuadroIoMed);
      } catch (CatCuadroIoMedException cCioE) {
         throw cCioE;
      } catch (Exception ex) {
         CatCuadroIoMedException cCioE = new CatCuadroIoMedException("No fue posible obtener los detalles del catCuadroIoMed por Id", CatCuadroIoMedException.LAYER_REST, CatCuadroIoMedException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catCuadroIoMed por Id- CODE: {} - ", cCioE.getExceptionCode(), ex);
         throw cCioE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatCuadroIoMedView> findAll() throws CatCuadroIoMedException {
      return catCuadroIoMedService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatCuadroIoMedView> getCatCuadroIoMedSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatCuadroIoMedException {

      logger.info("===>>>getCatCuadroIoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionCuadroIo";

      return catCuadroIoMedService.getCatCuadroIoMedSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
