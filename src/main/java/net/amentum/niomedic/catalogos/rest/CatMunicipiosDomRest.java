package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatMunicipiosDomException;
import net.amentum.niomedic.catalogos.service.CatMunicipiosDomService;
import net.amentum.niomedic.catalogos.views.CatMunicipiosDomView;
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
@RequestMapping("catalogo-municipios")
public class CatMunicipiosDomRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatMunicipiosDomRest.class);

   private CatMunicipiosDomService catMunicipiosDomService;

   @Autowired
   public void setCatMunicipiosDomService(CatMunicipiosDomService catMunicipiosDomService) {
      this.catMunicipiosDomService = catMunicipiosDomService;
   }

   @RequestMapping(value = "{idCatMunicipiosDom}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatMunicipiosDomView getDetailsByIdCatMunicipiosDom(@PathVariable() Integer idCatMunicipiosDom) throws CatMunicipiosDomException {
      try {
         logger.info("===>>>Obtener los detalles del catMunicipiosDom por Id: {}", idCatMunicipiosDom);
         return catMunicipiosDomService.getDetailsByIdCatMunicipiosDom(idCatMunicipiosDom);
      } catch (CatMunicipiosDomException cMunE) {
         throw cMunE;
      } catch (Exception ex) {
         CatMunicipiosDomException cMunE = new CatMunicipiosDomException("No fue posible obtener los detalles del catMunicipiosDom por Id", CatMunicipiosDomException.LAYER_REST, CatMunicipiosDomException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catMunicipiosDom por Id- CODE: {} - ", cMunE.getExceptionCode(), ex);
         throw cMunE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatMunicipiosDomView> findAll() throws CatMunicipiosDomException {
      return catMunicipiosDomService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatMunicipiosDomView> getCatMunicipiosDomSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatMunicipiosDomException {

      logger.info("===>>>getCatMunicipiosDomSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionMunicipios";

      return catMunicipiosDomService.getCatMunicipiosDomSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
