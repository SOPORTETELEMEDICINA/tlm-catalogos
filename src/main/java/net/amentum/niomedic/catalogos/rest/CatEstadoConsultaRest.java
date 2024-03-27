package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.BaseController;
import net.amentum.niomedic.catalogos.exception.CatEstadoConsultaException;
import net.amentum.niomedic.catalogos.service.CatEstadoConsultaService;
import net.amentum.niomedic.catalogos.views.CatEstadoConsultaView;
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
@RequestMapping("catalogo-estado-consulta")
public class CatEstadoConsultaRest extends BaseController {
   private final Logger logger = LoggerFactory.getLogger(CatEstadoConsultaRest.class);

   private CatEstadoConsultaService catEstadoConsultaService;

   @Autowired
   public void setCatEstadoConsultaService(CatEstadoConsultaService catEstadoConsultaService) {
      this.catEstadoConsultaService = catEstadoConsultaService;
   }

   @RequestMapping(value = "{idEstadoConsulta}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatEstadoConsultaView getDetailsByIdCatEstadoConsulta(@PathVariable() Integer idEstadoConsulta) throws CatEstadoConsultaException {
      try {
         logger.info("===>>>Obtener los detalles del catEstadoConsulta por Id: {}", idEstadoConsulta);
         return catEstadoConsultaService.getDetailsByIdCatEstadoConsulta(idEstadoConsulta);
      } catch (CatEstadoConsultaException cEdoConE) {
         throw cEdoConE;
      } catch (Exception ex) {
         CatEstadoConsultaException cEdoConE = new CatEstadoConsultaException("No fue posible obtener los detalles del catEstadoConsulta por Id", CatEstadoConsultaException.LAYER_REST, CatEstadoConsultaException.ACTION_SELECT);
         logger.error("===>>>Error al obtener los detalles del catEstadoConsulta por Id- CODE: {} - ", cEdoConE.getExceptionCode(), ex);
         throw cEdoConE;
      }
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatEstadoConsultaView> findAll() throws CatEstadoConsultaException {
      return catEstadoConsultaService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatEstadoConsultaView> getCatEstadoConsultaSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer size,
                                           @RequestParam(required = false) String orderColumn,
                                           @RequestParam(required = false) String orderType) throws CatEstadoConsultaException {

      logger.info("===>>>getCatEstadoConsultaSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, page, size, orderColumn, orderType);

      if (page == null)
         page = 0;
      if (size == null)
         size = 10;
      if (orderType == null || orderType.isEmpty())
         orderType = "asc";
      if (orderColumn == null || orderColumn.isEmpty())
         orderColumn = "descripcion";

      return catEstadoConsultaService.getCatEstadoConsultaSearch(datosBusqueda, page, size, orderColumn, orderType);
   }

}
