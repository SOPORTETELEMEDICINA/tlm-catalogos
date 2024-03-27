package net.amentum.niomedic.catalogos.rest;

import lombok.extern.slf4j.Slf4j;
import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatCifException;
import net.amentum.niomedic.catalogos.service.CatCifService;
import net.amentum.niomedic.catalogos.views.CatCifView;
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
@RequestMapping("catalogo-discapacidades")
@Slf4j
public class CatCifRest extends RestBaseController {
   private final Logger logger = LoggerFactory.getLogger(CatCifRest.class);
   private CatCifService catCifService;

   @Autowired
   public void setCatCifService(CatCifService catCifService) {
      this.catCifService = catCifService;
   }

   @RequestMapping(value = "{idCatCif}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatCifView getDetailsByIdCatCif(@PathVariable("idCatCif") Integer idCatCif) throws CatCifException {
      log.info("===>>>getDetailsByIdCatCif() - GET - idCatCif: {}", idCatCif);
      return catCifService.getDetailsByIdCatCif(idCatCif);
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatCifView> findAll() throws CatCifException {
      logger.info("===>>>findAll() - GET - obteniendo todo el listado de CIF");
      return catCifService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatCifView> getCatCifSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                           @RequestParam(required = false) Boolean activo,
                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           @RequestParam(required = false, defaultValue = "idCatCif") String orderColumn,
                                           @RequestParam(required = false, defaultValue = "asc") String orderType) throws CatCifException {

      log.info("===>>>getCatCifSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
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

      return catCifService.getCatCifSearch(datosBusqueda, activo, page, size, orderColumn, orderType);
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public CatCifView createCatCif(@RequestBody @Valid CatCifView catCifView) throws CatCifException {
      log.info("===>>>createCatCif() - POST - catCifView: {}", catCifView);
      return catCifService.createCatCif(catCifView);
   }

   @RequestMapping(value = "{idCatCif}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public CatCifView updateCatCif(@PathVariable("idCatCif") Integer idCatCif, @RequestBody @Valid CatCifView catCifView) throws CatCifException {
      log.info("===>>>updateCatCif() - PUT - idCatCif: {} - catCifView: {}", idCatCif, catCifView);
      catCifView.setIdCatCif(idCatCif);
      return catCifService.updateCatCif(catCifView);
   }

}

