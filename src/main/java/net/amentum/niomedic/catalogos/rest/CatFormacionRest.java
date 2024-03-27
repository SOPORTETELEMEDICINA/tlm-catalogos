package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatFormacionException;
import net.amentum.niomedic.catalogos.service.CatFormacionService;
import net.amentum.niomedic.catalogos.views.CatFormacionView;
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
@RequestMapping("catalogo-formacion")
public class CatFormacionRest extends RestBaseController {
	private final Logger logger = LoggerFactory.getLogger(CatFormacionRest.class);

	private CatFormacionService catFormacionService;

	@Autowired
	public void setCatFormacionService(CatFormacionService catFormacionService) {
		this.catFormacionService = catFormacionService;
	}
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CatFormacionView createCatFormacion(@RequestBody @Valid CatFormacionView catFormacionView) throws CatFormacionException {
		logger.info("createCatFormacion() - Creandon un catFormacion: {}",catFormacionView);
		return catFormacionService.creteCatFormacion(catFormacionView);
	}
	@RequestMapping(value="{idCatFormacion}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public CatFormacionView updateCatFormacion(@PathVariable()Integer idCatFormacion, @RequestBody @Valid CatFormacionView catFormacionView) throws CatFormacionException {
		logger.info("updateCatFormacion() - Actualizando un catFormacion con el id:{} - {}",idCatFormacion,catFormacionView);
		return catFormacionService.updateCatFormacion(idCatFormacion, catFormacionView);
	}

	@RequestMapping(value = "{idCatFormacion}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public CatFormacionView getDetailsByIdCatFormacion(@PathVariable() Integer idCatFormacion) throws CatFormacionException {
		logger.info("getDetailsByIdCatFormacion() - Obtener los detalles del catFormacion por Id: {}", idCatFormacion);
		return catFormacionService.getDetailsByIdCatFormacion(idCatFormacion);
	}

	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<CatFormacionView> findAll() throws CatFormacionException {
		logger.info("findAll() - Obteniendo una lista de todo lo almacenado en catFormacion");
		return catFormacionService.findAll();
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Page<CatFormacionView> getCatFormacionSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
														@RequestParam(required = false) Boolean activo,
														@RequestParam(required = false, defaultValue= "0") Integer page,
														@RequestParam(required = false, defaultValue = "10") Integer size,
														@RequestParam(required = false, defaultValue = "formDescripcion") String orderColumn,
														@RequestParam(required = false, defaultValue = "asc") String orderType) throws CatFormacionException {

		logger.info("getCatFormacionSearch(): - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
				datosBusqueda,activo, page, size, orderColumn, orderType);

		if(!orderType.toLowerCase().equals("asc") && !orderType.toLowerCase().equals("desc")) {orderType = "asc";}
		if(page<0) {page=0;}
		if(size<0) {size=10;}

		return catFormacionService.getCatFormacionSearch(datosBusqueda,activo, page, size, orderColumn, orderType.toLowerCase());
	}

}
