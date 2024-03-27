package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatTipoInsumoMedException;
import net.amentum.niomedic.catalogos.service.CatTipoInsumoMedService;
import net.amentum.niomedic.catalogos.views.CatTipoInsumoMedView;
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
@RequestMapping("catalogo-tipo-insumo")
public class CatTipoInsumoMedRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatTipoInsumoMedRest.class);

   private CatTipoInsumoMedService catTipoInsumoMedService;

   @Autowired
   public void setCatTipoInsumoMedService(CatTipoInsumoMedService catTipoInsumoMedService) {
      this.catTipoInsumoMedService = catTipoInsumoMedService;
   }

   @RequestMapping(value = "{idCatTipoInsumoMed}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatTipoInsumoMedView getDetailsByIdCatTipoInsumoMed(@PathVariable() Integer idCatTipoInsumoMed) throws CatTipoInsumoMedException {
      try {
         logger.info("===>>>Obtener los detalles del catTipoInsumoMed por Id: {}", idCatTipoInsumoMed);
         return catTipoInsumoMedService.getDetailsByIdCatTipoInsumoMed(idCatTipoInsumoMed);
      } catch (CatTipoInsumoMedException cTpoIE) {
         throw cTpoIE;
      } catch (Exception ex) {
         CatTipoInsumoMedException cTpoIE = new CatTipoInsumoMedException("No fue posible obtener los detalles del catTipoInsumoMed por Id", CatTipoInsumoMedException.LAYER_REST, CatTipoInsumoMedException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catTipoInsumoMed por Id- CODE: {} - ", cTpoIE.getExceptionCode(), ex);
         throw cTpoIE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatTipoInsumoMedView> findAll() throws CatTipoInsumoMedException {
      return catTipoInsumoMedService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatTipoInsumoMedView> getCatTipoInsumoMedSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatTipoInsumoMedException {

      logger.info("===>>>getCatTipoInsumoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionTipoInsumo";

      return catTipoInsumoMedService.getCatTipoInsumoMedSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
