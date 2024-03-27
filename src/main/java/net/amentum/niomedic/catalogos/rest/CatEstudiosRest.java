package net.amentum.niomedic.catalogos.rest;

import javax.validation.Valid;

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

import lombok.extern.slf4j.Slf4j;
import net.amentum.common.v2.RestBaseController;
import net.amentum.niomedic.catalogos.exception.CatEstudiosException;
import net.amentum.niomedic.catalogos.service.CatEstudiosService;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;

@RestController
@RequestMapping("catalogo-estudios")
@Slf4j
public class CatEstudiosRest extends RestBaseController {
	
	@Autowired
	private CatEstudiosService catEstudiosServie;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public CatEstudiosView createCatEstudio(@RequestBody @Valid CatEstudiosView catEstudiosView) throws CatEstudiosException {
		log.info("POST - createCatEstudio() - Creando un nuevo estudio :{}",catEstudiosView);
		return catEstudiosServie.createCatEstudio(catEstudiosView);
	}
	
	@RequestMapping(value="{idCatEstudio}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public CatEstudiosView updateCatEstudio(@PathVariable()Integer idCatEstudio, @RequestBody @Valid CatEstudiosView catEstudiosView) throws CatEstudiosException {
		log.info("PUT - updateCatEstudio() - Actualizando el estudio con el id:{} - {}",catEstudiosView);
		return catEstudiosServie.updateCatEstudio(idCatEstudio, catEstudiosView);
	}
	
	@RequestMapping(value="{idCatEstudio}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public CatEstudiosView getCatEstudio(@PathVariable()Integer idCatEstudio) throws CatEstudiosException {
		log.info("GET - getCatEstudio() - Obteniendo estudio con el id:{}",idCatEstudio);
		return catEstudiosServie.getCatEstudios(idCatEstudio);
	}
	
	@RequestMapping(value = "search")
	@ResponseStatus(HttpStatus.OK)
	public Page<CatEstudiosView> searchCatEstudios(@RequestParam(required = false, defaultValue = "") String datosBusqueda,
								  @RequestParam(required = false) Boolean activo,
								  @RequestParam(required = false, defaultValue = "0") Integer page,
								  @RequestParam(required = false, defaultValue = "10") Integer size,
								  @RequestParam(required = false, defaultValue = "descripcion") String orderColumn,
								  @RequestParam(required = false, defaultValue = "asc") String orderType) throws CatEstudiosException {
		log.info("GET - searchCatEstudios() - Búsqueda sobre el catálogo de estudios - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn:{} - orderType: {}");
		if(page<0) {page=0;}
		if(size<0) {size=10;}
		if(orderColumn.toLowerCase().equals("asc") && orderColumn.toLowerCase().equals("desc")) {
			orderColumn = "asc";
		}
		return catEstudiosServie.searchCatEstudios(datosBusqueda, activo, page, size, orderColumn, orderType);
	}
	
	
}
