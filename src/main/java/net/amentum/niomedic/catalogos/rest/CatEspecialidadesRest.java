package net.amentum.niomedic.catalogos.rest;

import lombok.extern.slf4j.Slf4j;
import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatEspecialidadesException;
import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import net.amentum.niomedic.catalogos.service.CatEspecialidadesService;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("catalogo-especialidades")
@Slf4j
public class CatEspecialidadesRest extends RestBaseController {
   private final Logger logger = LoggerFactory.getLogger(CatEspecialidadesRest.class);
   private CatEspecialidadesService catEspecialidadesService;

   @Autowired
   public void setCatEspecialidadesService(CatEspecialidadesService catEspecialidadesService) {
      this.catEspecialidadesService = catEspecialidadesService;
   }

   @GetMapping
   public List<CatEspecialidades> getEspecialidadesActivas() {
      // Llama al metodo en el servicio que devuelve sólo las especialidades activas
      return catEspecialidadesService.getAllActiveEspecialidades();
   }

   @RequestMapping(value = "{idCatEspecialidades}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatEspecialidadesView getDetailsByIdCatEspecialidades(@PathVariable("idCatEspecialidades") Integer idCatEspecialidades) throws CatEspecialidadesException {
      log.info("===>>>getDetailsByIdCatEspecialidades() - GET - idCatEspecialidades: {}", idCatEspecialidades);
      return catEspecialidadesService.getDetailsByIdCatEspecialidades(idCatEspecialidades);
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatEspecialidadesView> findAll() throws CatEspecialidadesException {
      logger.info("===>>>findAll() - GET - obteniendo todo el listado de CatEspecialidades");
      return catEspecialidadesService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatEspecialidadesView> getCatEspecialidadesSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
                                                                 @RequestParam(required = false) Boolean activo,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "idCatEspecialidades") String orderColumn,
                                                                 @RequestParam(required = false, defaultValue = "asc") String orderType) throws CatEspecialidadesException {

      log.info("===>>>getCatEspecialidadesSearch(): datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
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

      return catEspecialidadesService.getCatEspecialidadesSearch(datosBusqueda, activo, page, size, orderColumn, orderType);
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public CatEspecialidadesView createCatEspecialidades(@RequestBody @Valid CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException {
      log.info("===>>>createCatEspecialidades() - POST - catEspecialidadesView: {}", catEspecialidadesView);
      return catEspecialidadesService.createCatEspecialidades(catEspecialidadesView);
   }

   @RequestMapping(value = "{idCatEspecialidades}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public CatEspecialidadesView updateCatEspecialidades(@PathVariable("idCatEspecialidades") Integer idCatEspecialidades, @RequestBody @Valid CatEspecialidadesView catEspecialidadesView) throws CatEspecialidadesException {
      log.info("===>>>updateCatEspecialidades() - PUT - idCatEspecialidades: {} - catEspecialidadesView: {}", idCatEspecialidades, catEspecialidadesView);
      catEspecialidadesView.setIdCatEspecialidades(idCatEspecialidades);
      return catEspecialidadesService.updateCatEspecialidades(catEspecialidadesView);
   }

}

