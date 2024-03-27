package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatReligionConverter;
import net.amentum.niomedic.catalogos.exception.CatReligionException;
import net.amentum.niomedic.catalogos.model.CatReligion;
import net.amentum.niomedic.catalogos.persistence.CatReligionRepository;
import net.amentum.niomedic.catalogos.service.CatReligionService;
import net.amentum.niomedic.catalogos.views.CatReligionView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class CatReligionServiceImpl implements CatReligionService {
	private final Logger logger = LoggerFactory.getLogger(CatReligionServiceImpl.class);
	private final Map<String, Object> colOrderNames = new HashMap<>();

	private CatReligionRepository catReligionRepository;
	private CatReligionConverter catReligionConverter;

	{
		colOrderNames.put("relDescripcion", "relDescripcion");
		colOrderNames.put("idCatReligion", "idCatReligion");
		colOrderNames.put("activo", "activo");
	}

	@Autowired
	public void setCatReligionRepository(CatReligionRepository catReligionRepository) {
		this.catReligionRepository = catReligionRepository;
	}

	@Autowired
	public void setCatReligionConverter(CatReligionConverter catReligionConverter) {
		this.catReligionConverter = catReligionConverter;
	}


	@Transactional(readOnly = false, rollbackFor = {CatReligionException.class})
	@Override
	public CatReligionView createCatReligion(CatReligionView catReligionView) throws CatReligionException {
		try {
			catReligionView.setIdCatReligion(null);
			catReligionView.setActivo(true);
			CatReligion entity= catReligionConverter.toEntity(catReligionView, new CatReligion(), Boolean.FALSE);
			catReligionRepository.save(entity);
			logger.info("{} Se guardo exitosamente",entity);
			return catReligionConverter.toView(entity, Boolean.TRUE);
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("IMPL - createCatReligion() -  Ocurrió un error de validación en la tabla, error: {}", errores);
			throw new CatReligionException(HttpStatus.BAD_REQUEST, errores);   
		}catch(DataIntegrityViolationException de) {
			logger.error("IMPL - createCatReligion() -  Ocurrió un error de integridad en la tabla, error: {}",de.getRootCause().getMessage());
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.INTEGRITY_ERROR, "crear"));   
		}catch(Exception e) {
			logger.error("IMPL - createCatReligion() -  Ocurrió un error inesperado al crear la religión error:{}",e);
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.SERVER_ERROR, "crear"));   
		}
	}

	@Transactional(readOnly=false, rollbackFor = {CatReligionException.class})
	@Override
	public CatReligionView updateCatReligion(CatReligionView catReligionView, Integer idCatReligion)
			throws CatReligionException {
		try {
			if(catReligionView.getActivo()==null) {
				catReligionView.setActivo(true);
			}
			CatReligion entity = getCatReligionById(idCatReligion);
			entity = catReligionConverter.toEntity(catReligionView, new CatReligion(), Boolean.TRUE);
			catReligionRepository.save(entity);
			logger.info("{} Se guardo exitosamente",entity);
			return catReligionConverter.toView(entity, Boolean.TRUE);
		}catch(CatReligionException cre){
			throw cre;
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("updateCatReligion() -  Ocurrió un error de validación en la tabla, error: {}", errores);
			throw new CatReligionException(HttpStatus.BAD_REQUEST, errores);
		}catch(DataIntegrityViolationException de) {
			logger.error("updateCatReligion() -  Ocurrió un error de integridad en la tabla al actualizar un registro, error: {}",de.getRootCause().getMessage());
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.INTEGRITY_ERROR, "actualizar"));
		}catch(Exception e) {
			logger.error("updateCatReligion() -  Ocurrió un error inesperado al actualizar la religión error:{}",e);
			throw new CatReligionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatReligionException.SERVER_ERROR, "actualizar"));
		}
	}

	private CatReligion getCatReligionById(Integer idCatReligion) throws CatReligionException {
		if (!catReligionRepository.exists(idCatReligion)) {
			logger.error("getCatReligionById() - No se encontro registro con el idCatReligiones: {}", idCatReligion);
			throw new CatReligionException(HttpStatus.NOT_FOUND, String.format(CatReligionException.ITEM_NOT_FOUND, idCatReligion) );  
		}
		return catReligionRepository.findOne(idCatReligion);
	}

	@Override
	public CatReligionView getDetailsByIdCatReligion(Integer idCatReligion) throws CatReligionException {
		try {
			CatReligion catReligion = getCatReligionById(idCatReligion);
			return catReligionConverter.toView(catReligion, Boolean.TRUE);
		} catch (CatReligionException cRelE) {
			throw cRelE;
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("getDetailsByIdCatReligion() -  Ocurrió un error de validación en la tabla, error: {}", errores);
			throw new CatReligionException(HttpStatus.BAD_REQUEST, errores);   
		}catch(DataIntegrityViolationException de) {
			logger.error("getDetailsByIdCatReligion() -  Ocurrió un error de integridad en la tabla al actualizar un registro, error: {}",de.getRootCause().getMessage());
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.INTEGRITY_ERROR, "obtener registros por ID"));   
		}catch(Exception e) {
			logger.error("getDetailsByIdCatReligion() -  Ocurrió un error inesperado al actualizar la religión error:{}",e);
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.SERVER_ERROR, "obtener registros por ID"));   
		}

	}

	@Override
	public List<CatReligionView> findAll() throws CatReligionException {
		try {
			List<CatReligion> catReligionList = catReligionRepository.findAll();
			List<CatReligionView> catReligionViewList = new ArrayList<>();
			for (CatReligion cn : catReligionList) {
				catReligionViewList.add(catReligionConverter.toView(cn, Boolean.TRUE));
			}
			return catReligionViewList;
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("findAll() -  Ocurrió un error de validación en la tabla, error: {}", errores);
			throw new CatReligionException(HttpStatus.BAD_REQUEST, errores);   
		}catch(DataIntegrityViolationException de) {
			logger.error("findAll() -  Ocurrió un error de integridad en la tabla al obtener todos los registros, error: {}",de.getRootCause().getMessage());
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.INTEGRITY_ERROR, "obtener todos lo registros"));   
		}catch(Exception e) {
			logger.error("findAll() -  Ocurrió un error inesperado al obtener todos los registros de cat_religión, error:{}",e);
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.SERVER_ERROR, "obtener todos lo registros"));   
		}
	}


	@Override
	public Page<CatReligionView> getCatReligionSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatReligionException {
		try {
			logger.info("getCatReligionSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
					datosBusqueda, page, size, orderColumn, orderType);

			if(!colOrderNames.containsValue(orderColumn)){
				orderColumn= "relDescripcion";
			}

			List<CatReligionView> catReligionViewList = new ArrayList<>();
			Page<CatReligion> catReligionPage = null;
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("relDescripcion"));

			if (orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
			}

			PageRequest request = new PageRequest(page, size, sort);
			final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatReligion> spec = Specifications.where(
					(root, query, cb) -> {
						Predicate tc = null;
						if (!datosBusqueda.isEmpty()) {
							tc = (tc != null ? cb.and(tc, cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch))) : cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch)));
						}
						if (activo != null) {
							tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), activo)) : cb.equal(root.get("activo"), activo));
						}
						return tc;
					}
					);

			catReligionPage = catReligionRepository.findAll(spec, request);

			catReligionPage.getContent().forEach(catReligion -> {
				catReligionViewList.add(catReligionConverter.toView(catReligion, Boolean.TRUE));
			});
			PageImpl<CatReligionView> catReligionViewPage = new PageImpl<CatReligionView>(catReligionViewList, request, catReligionPage.getTotalElements());
			return catReligionViewPage;

		}catch(Exception e) {
			logger.error("getCatReligionSearch() -  Ocurrió un error inesperado al filtrar los registros en la tabla cat_religion, error:{}",e);
			throw new CatReligionException(HttpStatus.CONFLICT, String.format(CatReligionException.SERVER_ERROR, "filtrar"));   
		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}


}
