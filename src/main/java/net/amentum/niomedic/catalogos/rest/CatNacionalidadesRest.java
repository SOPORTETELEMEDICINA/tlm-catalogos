package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatNacionalidadesException;
import net.amentum.niomedic.catalogos.service.CatNacionalidadesService;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;
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

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("catalogo-nacionalidades")
public class CatNacionalidadesRest extends RestBaseController {
	private final Logger logger = LoggerFactory.getLogger(CatNacionalidadesRest.class);

	private CatNacionalidadesService catNacionalidadesService;

	@Autowired
	public void setCatNacionalidadesService(CatNacionalidadesService catNacionalidadesService) {
		this.catNacionalidadesService = catNacionalidadesService;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CatNacionalidadesView createCatNacionalidades(@RequestBody @Valid CatNacionalidadesView catNacionalidadesView) throws CatNacionalidadesException {
		return catNacionalidadesService.createNacionalidades(catNacionalidadesView);
	}
	@RequestMapping(value="{idCatNacionalidades}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public CatNacionalidadesView updateCatNacionalidades(@PathVariable()Integer idCatNacionalidades, @RequestBody @Valid CatNacionalidadesView catNacionalidadesView) throws CatNacionalidadesException {
		logger.info("updateCatNacionalidades() - Actualizando nacionaldiad con el id: {} - {}",idCatNacionalidades, catNacionalidadesView);
		return catNacionalidadesService.updateNacionalidades(idCatNacionalidades, catNacionalidadesView);
	}

	@RequestMapping(value = "{idCatNacionalidades}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public CatNacionalidadesView getDetailsByIdCatNacionalidades(@PathVariable() Integer idCatNacionalidades) throws CatNacionalidadesException {
		logger.info("getDetailsByIdCatNacionalidades() - Obteniendo nacionalidad con el id: {}",idCatNacionalidades);
		return catNacionalidadesService.getDetailsByIdCatNacionalidades(idCatNacionalidades);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<CatNacionalidadesView> findAll() throws CatNacionalidadesException {
		logger.info("findAll() - Obteniendo listado de todas las nacionalidades");
		return catNacionalidadesService.findAll();
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Page<CatNacionalidadesView> getCatNacionalidadesSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
			@RequestParam(required = false) Boolean activo,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size,
			@RequestParam(required = false, defaultValue = "nacPaisDescripcion") String orderColumn,
			@RequestParam(required = false, defaultValue = "asc") String orderType) throws CatNacionalidadesException {

		logger.info("getCatNacionalidadesSearch(): - datosBusqueda: {} - activo:{} - page: {} - size: {} - orderColumn: {} - orderType: {}",
				datosBusqueda,activo, page, size, orderColumn, orderType);
		if(page<0) {page = 0;}
		if(size<0) {size = 10;}
		if(!orderType.toLowerCase().equals("asc") && !orderType.toLowerCase().equals("desc")) {
			orderType = "asc";
		}
		return catNacionalidadesService.getCatNacionalidadesSearch(datosBusqueda, activo, page, size, orderColumn, orderType.toLowerCase());
	}

}
