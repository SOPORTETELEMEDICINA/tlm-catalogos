package net.amentum.niomedic.catalogos.rest;

import lombok.extern.slf4j.Slf4j;
import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatMedicamentosException;
import net.amentum.niomedic.catalogos.service.CatMedicamentosService;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
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
@RequestMapping("catalogo-medicamentos")
@Slf4j
public class CatMedicamentosRest extends RestBaseController {
   private final Logger logger = LoggerFactory.getLogger(CatMedicamentosRest.class);
   private CatMedicamentosService catMedicamentosService;

   @Autowired
   public void setCatMedicamentosService(CatMedicamentosService catMedicamentosService) {
      this.catMedicamentosService = catMedicamentosService;
   }

   @RequestMapping(value = "{idCatMedicamentos}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatMedicamentosView getDetailsByIdCatMedicamentos(@PathVariable("idCatMedicamentos") Integer idCatMedicamentos) throws CatMedicamentosException {
      log.info("===>>>getDetailsByIdCatMedicamentos() - GET - idCatMedicamentos: {}", idCatMedicamentos);
      return catMedicamentosService.getDetailsByIdCatMedicamentos(idCatMedicamentos);
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatMedicamentosView> findAll() throws CatMedicamentosException {
      logger.info("===>>>findAll() - GET - obteniendo todo el listado de CatMedicamentos");
      return catMedicamentosService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatMedicamentosView> getCatMedicamentosSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                                 @RequestParam(required = false) Boolean activo,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "idCatMedicamentos") String orderColumn,
                                                                 @RequestParam(required = false, defaultValue = "asc") String orderType) throws CatMedicamentosException {

      log.info("===>>>getCatMedicamentosSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
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

      return catMedicamentosService.getCatMedicamentosSearch(datosBusqueda, activo, page, size, orderColumn, orderType);
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public CatMedicamentosView createCatMedicamentos(@RequestBody @Valid CatMedicamentosView catMedicamentosView) throws CatMedicamentosException {
      log.info("===>>>createCatMedicamentos() - POST - catMedicamentosView: {}", catMedicamentosView);
      return catMedicamentosService.createCatMedicamentos(catMedicamentosView);
   }

   @RequestMapping(value = "{idCatMedicamentos}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public CatMedicamentosView updateCatMedicamentos(@PathVariable("idCatMedicamentos") Integer idCatMedicamentos, @RequestBody @Valid CatMedicamentosView catMedicamentosView) throws CatMedicamentosException {
      log.info("===>>>updateCatMedicamentos() - PUT - idCatMedicamentos: {} - catMedicamentosView: {}", idCatMedicamentos, catMedicamentosView);
      catMedicamentosView.setIdCatMedicamentos(idCatMedicamentos);
      return catMedicamentosService.updateCatMedicamentos(catMedicamentosView);
   }

}

