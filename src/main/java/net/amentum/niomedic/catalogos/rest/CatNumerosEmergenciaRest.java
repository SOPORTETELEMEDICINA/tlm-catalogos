package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatNumerosEmergenciaException;
import net.amentum.niomedic.catalogos.service.CatNumerosEmergenciaService;
import net.amentum.niomedic.catalogos.views.CatNumerosEmergenciaView;
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
@RequestMapping("catalogo-numeros-emergencia")
public class CatNumerosEmergenciaRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatNumerosEmergenciaRest.class);

   private CatNumerosEmergenciaService catNumerosEmergenciaService;

   @Autowired
   public void setCatNumerosEmergenciaService(CatNumerosEmergenciaService catNumerosEmergenciaService) {
      this.catNumerosEmergenciaService = catNumerosEmergenciaService;
   }

   @RequestMapping(value = "{idCatNumerosEmergencia}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatNumerosEmergenciaView getDetailsByIdCatNumerosEmergencia(@PathVariable() Integer idCatNumerosEmergencia) throws CatNumerosEmergenciaException {
      try {
         logger.info("===>>>Obtener los detalles del catNumerosEmergencia por Id: {}", idCatNumerosEmergencia);
         return catNumerosEmergenciaService.getDetailsByIdCatNumerosEmergencia(idCatNumerosEmergencia);
      } catch (CatNumerosEmergenciaException numEmerE) {
         throw numEmerE;
      } catch (Exception ex) {
         CatNumerosEmergenciaException numEmerE = new CatNumerosEmergenciaException("No fue posible obtener los detalles del catNumerosEmergencia por Id", CatNumerosEmergenciaException.LAYER_REST, CatNumerosEmergenciaException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catNumerosEmergencia por Id- CODE: {} - ", numEmerE.getExceptionCode(), ex);
         throw numEmerE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatNumerosEmergenciaView> findAll() throws CatNumerosEmergenciaException {
      return catNumerosEmergenciaService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatNumerosEmergenciaView> getCatNumerosEmergenciaSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatNumerosEmergenciaException {

      logger.info("===>>>getCatNumerosEmergenciaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "numeroEmergenciaDescripcion";

      return catNumerosEmergenciaService.getCatNumerosEmergenciaSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
