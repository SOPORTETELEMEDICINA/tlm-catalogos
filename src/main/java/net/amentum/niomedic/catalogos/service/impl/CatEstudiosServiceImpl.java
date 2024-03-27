package net.amentum.niomedic.catalogos.service.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.amentum.niomedic.catalogos.converter.CatEstudiosConverter;
import net.amentum.niomedic.catalogos.exception.CatEstudiosException;
import net.amentum.niomedic.catalogos.model.CatEstudios;
import net.amentum.niomedic.catalogos.persistence.CatEstudiosRepository;
import net.amentum.niomedic.catalogos.service.CatEstudiosService;
import net.amentum.niomedic.catalogos.views.CatEstudiosView;


@Service
@Transactional(readOnly = true)
@Slf4j
public class CatEstudiosServiceImpl implements CatEstudiosService {

	private CatEstudiosConverter catEstudiosConverter;
	private CatEstudiosRepository catEstudiosRepository;
	private Map<String, String> colOrderNames = new HashMap<>();

	{
		colOrderNames.put("idCatEstudio", "idCatEstudio");
		colOrderNames.put("descripcion", "descripcion");
		colOrderNames.put("preparacion", "preparacion");
		colOrderNames.put("estudio", "estudio");
		colOrderNames.put("activo", "activo");
	}

	@Autowired
	public void setCatEstudiosConverter(CatEstudiosConverter catEstudiosConverter) {
		this.catEstudiosConverter = catEstudiosConverter;
	}

	@Autowired
	public void SetCatEstudiosRepository (CatEstudiosRepository catEstudiosRepository) {
		this.catEstudiosRepository = catEstudiosRepository;
	}

	@Transactional(readOnly = false, rollbackFor = CatEstudiosException.class)
	@Override
	public CatEstudiosView createCatEstudio(CatEstudiosView catEstudiosView) throws CatEstudiosException {
		try {
			catEstudiosView.setActivo(true);
			catEstudiosView.setIdCatEstudio(null);
			CatEstudios entity = catEstudiosConverter.toEntity(catEstudiosView, new CatEstudios(), Boolean.TRUE);
			log.info("createCatEstudio() - Guardando nuevo estudio {}",entity);
			catEstudiosRepository.save(entity);
			log.info("createCatEstudio() - Se guardó exitosamente");
			return catEstudiosConverter.toView(entity, Boolean.TRUE);
		}catch(ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores = "";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores +""+siguiente.getPropertyPath() +":"+siguiente.getMessage()+", ";
			}
			log.info("createCatEstudioView() - Error de validacion al crear un nuevo estudio: {}", errores);
			throw new CatEstudiosException(HttpStatus.BAD_REQUEST,errores);
		}catch(DataIntegrityViolationException dive) {
			log.info("createCatEstudioView() - Error de integridad al crear un nuevo estudio: {}", dive);
			throw new CatEstudiosException(HttpStatus.CONFLICT,dive.getCause().getCause().getMessage());
		}catch(Exception e) {
			log.info("createCatEstudioView() - Error de inesperado al crear un nuevo estudio: {}", e);
			throw new CatEstudiosException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CatEstudiosException.SERVER_ERROR, "crear"));
		}
	}

	@Transactional(readOnly = false, rollbackFor = CatEstudiosException.class)
	@Override
	public CatEstudiosView updateCatEstudio(Integer idCatEstudio, CatEstudiosView catEstudiosView) throws CatEstudiosException {
		try {
			CatEstudios entity = finById(idCatEstudio);
			if(catEstudiosView.getActivo() == null) {
				catEstudiosView.setActivo(true);
			}
			catEstudiosView.setIdCatEstudio(idCatEstudio);
			entity = catEstudiosConverter.toEntity(catEstudiosView, entity, Boolean.TRUE);
			log.info("updateCatEstudio() - Actualizando estudio con el id: {} - {}",idCatEstudio,entity );
			catEstudiosRepository.save(entity);
			log.info("updateCatEstudio() - Se actualizó exitosamente");
			return catEstudiosConverter.toView(entity, Boolean.TRUE);
		}catch(CatEstudiosException cee) {
			throw cee;
		}catch(ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores = "";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores +""+siguiente.getPropertyPath() +":"+siguiente.getMessage()+", ";
			}
			log.info("updateCatEstudio() - Error de validacion al actualizar el estudio con el id: {} - error:{}",idCatEstudio, errores);
			throw new CatEstudiosException(HttpStatus.BAD_REQUEST,errores);
		}catch(DataIntegrityViolationException dive) {
			log.info("updateCatEstudio() - Error de integridad al actualizar el estudio con el id: {} - error:{}",idCatEstudio, dive);
			throw new CatEstudiosException(HttpStatus.CONFLICT,dive.getCause().getCause().getMessage());
		}catch(Exception e) {
			log.info("updateCatEstudio() - Error de inesperado al actualizar el nuevo estudio con el id: {} - error:{}",idCatEstudio, e);
			throw new CatEstudiosException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CatEstudiosException.SERVER_ERROR, "crear"));
		}
	}

	private CatEstudios finById(Integer idCatEstudios)throws CatEstudiosException {
		log.info("finById() - Buscando estudio con el id: {}",idCatEstudios);
		if(!catEstudiosRepository.exists(idCatEstudios)) {
			log.info("finById() - No se encontró estudio con el id: {}",idCatEstudios);
			throw new CatEstudiosException(HttpStatus.NOT_FOUND, String.format(CatEstudiosException.NOT_FOUND,idCatEstudios));
		}
		return catEstudiosRepository.findOne(idCatEstudios);
	}

	@Override
	public CatEstudiosView getCatEstudios(Integer idCatEstudios) throws CatEstudiosException {
		try {
			CatEstudios entity = finById(idCatEstudios);
			log.info("getCatEstudios() - Estudio encontrado {}",entity);
			return catEstudiosConverter.toView(entity, Boolean.TRUE);
		}catch(CatEstudiosException cee) {
			throw cee;
		}catch(Exception e) {
			throw new CatEstudiosException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatEstudiosException.SERVER_ERROR,"obtener por ID"));
		}
	}

	@Override
	public Page<CatEstudiosView> searchCatEstudios(String datosBusqueda, Boolean activo, Integer page, Integer size,
			String orderColumn, String orderType) throws CatEstudiosException {
		try {
			if(!colOrderNames.containsKey(orderColumn)) {
				orderColumn = "descripcion";
			}
			log.info("searchCatEstudios() - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn:{} - orderType: {}");
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			if(orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn) );
			}
			final String pattern = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatEstudios> spec = Specifications.where(
					(root, query, cb) -> {
						Predicate tc = null;
						if(!datosBusqueda.isEmpty()) {
							tc = cb.like(cb.function("unaccent", String.class, cb.lower(root.get("descripcion"))), sinAcentos(pattern));
						}
						if(activo != null) {
							tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), activo)) : cb.equal(root.get("activo"), activo));
						}
						return tc;
					});
			PageRequest request = new PageRequest(page,size, sort);
			Page<CatEstudios> paginated = catEstudiosRepository.findAll(spec, request);
			List<CatEstudiosView> estudiosList = new ArrayList<>();
			paginated.getContent().forEach(catEstudios -> {
				estudiosList.add(catEstudiosConverter.toView(catEstudios, true));
			});
			return new PageImpl<CatEstudiosView>(estudiosList, request, paginated.getTotalElements()); 
		}catch(Exception e) {
			log.error("searchCatEstudios() - Ocurrio un error inesperado al hacer un filtado en el catalogo de estudios - error: {}",e);
			throw new CatEstudiosException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatEstudiosException.SERVER_ERROR,"de filtrado"));
		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
