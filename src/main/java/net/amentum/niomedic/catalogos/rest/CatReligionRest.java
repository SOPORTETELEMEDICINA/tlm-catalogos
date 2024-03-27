package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatReligionException;
import net.amentum.niomedic.catalogos.service.CatReligionService;
import net.amentum.niomedic.catalogos.views.CatReligionView;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("catalogo-religion")
public class CatReligionRest extends RestBaseController {
   private final Logger logger = LoggerFactory.getLogger(CatReligionRest.class);

   private CatReligionService catReligionService;

   @Autowired
   public void setCatReligionService(CatReligionService catReligionService) {
      this.catReligionService = catReligionService;
   }
   @RequestMapping(method= RequestMethod.POST)
   @ResponseStatus(HttpStatus.CREATED)
   public CatReligionView createCatReligion(@RequestBody @Valid CatReligionView catReligionView) throws CatReligionException {
	   logger.info("createCatReligion() - Creando una religión {}",catReligionView);
	   return catReligionService.createCatReligion(catReligionView);
   }
   
   @RequestMapping(value="{idCatReligion}", method= RequestMethod.PUT)
   @ResponseStatus(HttpStatus.OK)
   public CatReligionView updateCatReligion(@PathVariable()Integer idCatReligion, @RequestBody @Valid CatReligionView catReligionView) throws CatReligionException {
	   logger.info("updateCatReligion() - Actualizanod catReligion con el idCatReligion: {}",idCatReligion);
	   return catReligionService.updateCatReligion(catReligionView,idCatReligion);
   }
   
   @RequestMapping(value = "{idCatReligion}", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public CatReligionView getDetailsByIdCatReligion(@PathVariable() Integer idCatReligion) throws CatReligionException {
         logger.info("===>>>Obtener los detalles del catReligion por Id: {}", idCatReligion);
         return catReligionService.getDetailsByIdCatReligion(idCatReligion);
   }

   @RequestMapping(value = "findAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public List<CatReligionView> findAll() throws CatReligionException {
      return catReligionService.findAll();
   }

   @RequestMapping(value = "search", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   public Page<CatReligionView> getCatReligionSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
		   								   @RequestParam(required = false) Boolean activo,
                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                           @RequestParam(required = false, defaultValue = "asc") String orderColumn,
                                           @RequestParam(required = false, defaultValue = "relDescripcion") String orderType) throws CatReligionException {

      logger.info("getCatReligionSearch(): - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
         datosBusqueda, activo, page, size, orderColumn, orderType);

      if(!orderType.toLowerCase().equals("asc") && !orderType.toLowerCase().equals("desc")) {
			orderType = "asc";
		}
		
		if(page<0) {
			page=0;
		}
		if(size<0) {
			size=10;
		}
      
      return catReligionService.getCatReligionSearch(datosBusqueda, activo, page, size, orderColumn, orderType.toLowerCase());
   }

}
