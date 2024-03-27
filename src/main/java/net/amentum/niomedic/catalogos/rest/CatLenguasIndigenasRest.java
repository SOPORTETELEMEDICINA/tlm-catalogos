package net.amentum.niomedic.catalogos.rest;

import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatLenguasIndigenasException;
import net.amentum.niomedic.catalogos.service.CatLenguasIndigenasService;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;
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
@RequestMapping("catalogo-lenguas-indigenas")
public class CatLenguasIndigenasRest extends RestBaseController {
	private final Logger logger = LoggerFactory.getLogger(CatLenguasIndigenasRest.class);

	private CatLenguasIndigenasService catLenguasIndigenasService;

	@Autowired
	public void setCatLenguasIndigenasService(CatLenguasIndigenasService catLenguasIndigenasService) {
		this.catLenguasIndigenasService = catLenguasIndigenasService;
	}
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CatLenguasIndigenasView createCatLenguasIndigena(@RequestBody() @Valid CatLenguasIndigenasView catLenguasIndigenasView ) throws CatLenguasIndigenasException {
		logger.info("createCatlenguaIndigena() - Agregando una nueva leguna indigena al catalogo {}",catLenguasIndigenasView);
		return catLenguasIndigenasService.createCatLenguasIndigenas(catLenguasIndigenasView);
	}
	
	@RequestMapping(value= "{idCatLenguasIndigenas}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public CatLenguasIndigenasView updateCatLenguasIndigena(@PathVariable()Integer idCatLenguasIndigenas, @RequestBody() @Valid CatLenguasIndigenasView catLenguasIndigenasView ) throws CatLenguasIndigenasException {
		logger.info("createCatLenguasIndigena() - Actualizando una nueva leguna indigena al catalogo {}",catLenguasIndigenasView);
		return catLenguasIndigenasService.updateCatLenguasIndigenas(idCatLenguasIndigenas, catLenguasIndigenasView);
	}
	
	@RequestMapping(value = "{idCatLenguasIndigenas}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public CatLenguasIndigenasView getDetailsByIdCatLenguasIndigenas(@PathVariable() Integer idCatLenguasIndigenas) throws CatLenguasIndigenasException {
			logger.info("===>>>Obtener los detalles del catLenguasIndigenas por Id: {}", idCatLenguasIndigenas);
			return catLenguasIndigenasService.getDetailsByIdCatLenguasIndigenas(idCatLenguasIndigenas);	
	}

	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<CatLenguasIndigenasView> findAll() throws CatLenguasIndigenasException {
		return catLenguasIndigenasService.findAll();
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Page<CatLenguasIndigenasView> getCatLenguasIndigenasSearch(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
			@RequestParam(required = false) Boolean activo,
			@RequestParam(required = false, defaultValue="0") Integer page,
			@RequestParam(required = false, defaultValue="10") Integer size,
			@RequestParam(required = false, defaultValue="lengDescripcion") String orderColumn,
			@RequestParam(required = false, defaultValue="asc") String orderType) throws CatLenguasIndigenasException {

		logger.info("getCatLenguasIndigenasSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
				datosBusqueda, page, size, orderColumn, orderType);
		if(page < 0){page = 0;}
		if(size < 0){size = 10;}
		if(!orderType.toLowerCase().equals("asc") && !orderType.toLowerCase().equals("desc")) {orderType = "asc";}
		return catLenguasIndigenasService.getCatLenguasIndigenasSearch(datosBusqueda,activo, page, size, orderColumn, orderType.toLowerCase());
	}

}
