package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatGpoTerapeuticoMedException;
import net.amentum.niomedic.catalogos.service.CatGpoTerapeuticoMedService;
import net.amentum.niomedic.catalogos.views.CatGpoTerapeuticoMedView;
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
@RequestMapping("catalogo-grupo-terapeutico")
public class CatGpoTerapeuticoMedRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatGpoTerapeuticoMedRest.class);

   private CatGpoTerapeuticoMedService catGpoTerapeuticoMedService;

   @Autowired
   public void setCatGpoTerapeuticoMedService(CatGpoTerapeuticoMedService catGpoTerapeuticoMedService) {
      this.catGpoTerapeuticoMedService = catGpoTerapeuticoMedService;
   }

   @RequestMapping(value = "{idCatGpoTerapeuticoMed}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatGpoTerapeuticoMedView getDetailsByIdCatGpoTerapeuticoMed(@PathVariable() Integer idCatGpoTerapeuticoMed) throws CatGpoTerapeuticoMedException {
      try {
         logger.info("===>>>Obtener los detalles del catGpoTerapeuticoMed por Id: {}", idCatGpoTerapeuticoMed);
         return catGpoTerapeuticoMedService.getDetailsByIdCatGpoTerapeuticoMed(idCatGpoTerapeuticoMed);
      } catch (CatGpoTerapeuticoMedException cGTeE) {
         throw cGTeE;
      } catch (Exception ex) {
         CatGpoTerapeuticoMedException cGTeE = new CatGpoTerapeuticoMedException("No fue posible obtener los detalles del catGpoTerapeuticoMed por Id", CatGpoTerapeuticoMedException.LAYER_REST, CatGpoTerapeuticoMedException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catGpoTerapeuticoMed por Id- CODE: {} - ", cGTeE.getExceptionCode(), ex);
         throw cGTeE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatGpoTerapeuticoMedView> findAll() throws CatGpoTerapeuticoMedException {
      return catGpoTerapeuticoMedService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatGpoTerapeuticoMedView> getCatGpoTerapeuticoMedSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatGpoTerapeuticoMedException {

      logger.info("===>>>getCatGpoTerapeuticoMedSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcionGpoTerapeutico";

      return catGpoTerapeuticoMedService.getCatGpoTerapeuticoMedSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
