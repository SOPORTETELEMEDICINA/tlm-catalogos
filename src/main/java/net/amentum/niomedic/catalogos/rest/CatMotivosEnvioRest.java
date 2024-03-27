package net.amentum.niomedic.catalogos.rest;

import lombok.extern.slf4j.Slf4j;
import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatMotivosEnvioException;
import net.amentum.niomedic.catalogos.service.CatMotivosEnvioService;
import net.amentum.niomedic.catalogos.views.CatMotivosEnvioView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("catalogo-motivos-envio")
@Slf4j
public class CatMotivosEnvioRest extends RestBaseController {
   private final Logger logger = LoggerFactory.getLogger(CatMotivosEnvioRest.class);
   private CatMotivosEnvioService catMotivosEnvioService;

   @Autowired
   public void setCatMotivosEnvioService(CatMotivosEnvioService catMotivosEnvioService) {
      this.catMotivosEnvioService = catMotivosEnvioService;
   }

   @RequestMapping(value = "{idCatMotivosEnvio}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatMotivosEnvioView getDetailsByIdCatMotivosEnvio(@PathVariable("idCatMotivosEnvio") Integer idCatMotivosEnvio) throws CatMotivosEnvioException {
      log.info("===>>>getDetailsByIdCatMotivosEnvio() - GET - idCatMotivosEnvio: {}", idCatMotivosEnvio);
      return catMotivosEnvioService.getDetailsByIdCatMotivosEnvio(idCatMotivosEnvio);
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatMotivosEnvioView> findAll() throws CatMotivosEnvioException {
      logger.info("===>>>findAll() - GET - obteniendo todo el listado de CatMotivosEnvio");
      return catMotivosEnvioService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatMotivosEnvioView> getCatMotivosEnvioSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Boolean activo,
                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           @RequestParam(required = false, defaultValue = "idCatMotivosEnvio") String orderColumn,
                                           @RequestParam(required = false, defaultValue = "asc") String orderType) throws CatMotivosEnvioException {

      log.info("===>>>getCatMotivosEnvioSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, activo, page, size, orderColumn, orderType);

      if (!orderType.equalsIgnoreCase("asc") && !orderType.equalsIgnoreCase("desc")) {
         orderType = "asc";
      }
      if (page < 0) {
         page = 0;
      }
      if (size < 0) {
         size = 10;
      }

      return catMotivosEnvioService.getCatMotivosEnvioSearch(datosBusqueda, activo, page, size, orderColumn, orderType);
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public CatMotivosEnvioView createCatMotivosEnvio(@RequestBody @Valid CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException {
      log.info("===>>>createCatMotivosEnvio() - POST - catMotivosEnvioView: {}", catMotivosEnvioView);
      return catMotivosEnvioService.createCatMotivosEnvio(catMotivosEnvioView);
   }

   @RequestMapping(value = "{idCatMotivosEnvio}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public CatMotivosEnvioView updateCatMotivosEnvio(@PathVariable("idCatMotivosEnvio") Integer idCatMotivosEnvio, @RequestBody @Valid CatMotivosEnvioView catMotivosEnvioView) throws CatMotivosEnvioException {
      log.info("===>>>updateCatMotivosEnvio() - PUT - idCatMotivosEnvio: {} - catMotivosEnvioView: {}", idCatMotivosEnvio, catMotivosEnvioView);
      catMotivosEnvioView.setIdCatMotivosEnvio(idCatMotivosEnvio);
      return catMotivosEnvioService.updateCatMotivosEnvio(catMotivosEnvioView);
   }

}

