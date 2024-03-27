package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatViaAdministracionConverter;
import net.amentum.niomedic.catalogos.exception.CatViaAdministracionException;
import net.amentum.niomedic.catalogos.model.CatViaAdministracion;
import net.amentum.niomedic.catalogos.persistence.CatViaAdministracionRepository;
import net.amentum.niomedic.catalogos.service.CatViaAdministracionService;
import net.amentum.niomedic.catalogos.views.CatViaAdministracionView;
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
public class CatViaAdministracionServiceImpl implements CatViaAdministracionService {
	private final Logger logger = LoggerFactory.getLogger(CatViaAdministracionServiceImpl.class);
	private final Map<String, Object> colOrderNames = new HashMap<>();

	private CatViaAdministracionRepository catViaAdministracionRepository;
	private CatViaAdministracionConverter catViaAdministracionConverter;

	{
		colOrderNames.put("activo", "activo");
		colOrderNames.put("idCatViaAdministracion", "idCatViaAdministracion");
		colOrderNames.put("vaDescripcion", "vaDescripcion");
	}

	@Autowired
	public void setCatViaAdministracionRepository(CatViaAdministracionRepository catViaAdministracionRepository) {
		this.catViaAdministracionRepository = catViaAdministracionRepository;
	}

	@Autowired
	public void setCatViaAdministracionConverter(CatViaAdministracionConverter catViaAdministracionConverter) {
		this.catViaAdministracionConverter = catViaAdministracionConverter;
	}

	private CatViaAdministracion finByIdCatViaAdministracion(Integer idCatViaAdministracion)throws CatViaAdministracionException {
		logger.info("finByIdCatViaAdministracion() - Buscando registro con el idCatViaAdministracion: {}", idCatViaAdministracion);
		if (!catViaAdministracionRepository.exists(idCatViaAdministracion)) {
			logger.error("finByIdCatViaAdministracion() - idCatViaAdministracion no encontrado: {}", idCatViaAdministracion);
			throw new CatViaAdministracionException(HttpStatus.NOT_FOUND,String.format(CatViaAdministracionException.NOT_FOUND, idCatViaAdministracion));
		}
		CatViaAdministracion entity = catViaAdministracionRepository.findOne(idCatViaAdministracion);
		logger.info("finByIdCatViaAdministracion() - Registro encontrado: {}", entity);
		return entity;
	}

	@Override
	public CatViaAdministracionView getDetailsByIdCatViaAdministracion(Integer idCatViaAdministracion) throws CatViaAdministracionException {
		try {
			CatViaAdministracion catViaAdministracion = finByIdCatViaAdministracion(idCatViaAdministracion);
			return catViaAdministracionConverter.toView(catViaAdministracion, Boolean.TRUE);
		} catch (CatViaAdministracionException cVaE) {
			throw cVaE;
		} catch (ConstraintViolationException cve) {
			String errores = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("getDetailsByIdCatViaAdministracion() - Error en la validacion - error: {}", errores);
			throw new CatViaAdministracionException(HttpStatus.BAD_REQUEST, errores);
		} catch (DataIntegrityViolationException dive) {
			logger.error("getDetailsByIdCatViaAdministracion() - Error de integridad en la tabla CatViaAdministracion - error: {}",  dive);
			throw new CatViaAdministracionException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("getDetailsByIdCatViaAdministracion() - Error al obtener CatViaAdministracion - id: {} - error: {}", idCatViaAdministracion, ex);
			throw new CatViaAdministracionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatViaAdministracionException.SERVER_ERROR, "obtener por ID"));
		}
	}

	@Override
	public List<CatViaAdministracionView> findAll() throws CatViaAdministracionException {
		try {
			List<CatViaAdministracion> catViaAdministracionList = catViaAdministracionRepository.findAll();
			List<CatViaAdministracionView> catViaAdministracionViewList = new ArrayList<>();
			for (CatViaAdministracion cn : catViaAdministracionList) {
				catViaAdministracionViewList.add(catViaAdministracionConverter.toView(cn, Boolean.TRUE));
			}
			return catViaAdministracionViewList;
		} catch (Exception ex) {
			logger.error("findAll() - Error al obtener todos los registros de CatViaAdministracion - error: {}",  ex);
			throw new CatViaAdministracionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatViaAdministracionException.SERVER_ERROR, "obtener todo los registros"));
		}
	}


	@Override
	public Page<CatViaAdministracionView> getCatViaAdministracionSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatViaAdministracionException {
		try {
			if(!colOrderNames.containsKey(orderColumn)) {
				logger.info("getCatViaAdministracionSearch() - Columna de ordenamiento errone {}, asignandon uno por defecto",orderColumn);
				orderColumn = "vaDescripcion";
			}
			logger.info("getCatViaAdministracionSearch(): - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
					datosBusqueda, activo, page, size, orderColumn, orderType);

			List<CatViaAdministracionView> catViaAdministracionViewList = new ArrayList<>();
			Page<CatViaAdministracion> catViaAdministracionPage = null;
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("vaDescripcion"));

			if (orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
			}

			PageRequest request = new PageRequest(page, size, sort);
			final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatViaAdministracion> spec = Specifications.where(
					(root, query, cb) -> {
						Predicate tc = null;
						if (!datosBusqueda.isEmpty()) {
							tc = cb.like(cb.function("unaccent", String.class, cb.lower(root.get("datosBusqueda"))), sinAcentos(patternSearch));
						}
						if (activo != null) {
							tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), activo)) : cb.equal(root.get("activo"), activo));
						}
						return tc;
					}
					);

			catViaAdministracionPage = catViaAdministracionRepository.findAll(spec, request);

			catViaAdministracionPage.getContent().forEach(catViaAdministracion -> {
				catViaAdministracionViewList.add(catViaAdministracionConverter.toView(catViaAdministracion, Boolean.TRUE));
			});
			PageImpl<CatViaAdministracionView> catViaAdministracionViewPage = new PageImpl<CatViaAdministracionView>(catViaAdministracionViewList, request, catViaAdministracionPage.getTotalElements());
			return catViaAdministracionViewPage;
		} catch (Exception ex) {
			logger.error("getCatViaAdministracionSearch() -  Error al tratar de seleccionar lista CatViaAdministracion paginable - Error: {}",ex);
			throw new CatViaAdministracionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatViaAdministracionException.SERVER_ERROR, "filtrado"));
		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	@Transactional(readOnly=false, rollbackFor=CatViaAdministracionException.class)
	@Override
	public CatViaAdministracionView createViaAdministracion(CatViaAdministracionView catViaAdministracionView)
			throws CatViaAdministracionException {
		try {
			catViaAdministracionView.setIdCatViaAdministracion(null);
			catViaAdministracionView.setActivo(true);
			CatViaAdministracion entity = catViaAdministracionConverter.toEntity(catViaAdministracionView, new CatViaAdministracion(), Boolean.FALSE);
			logger.info("createViaAdministracion() - Guardando nueva via administracion: {}", entity);
			catViaAdministracionRepository.save(entity);
			logger.info("createViaAdministracion() - Se guardo exitosamente");
			return catViaAdministracionConverter.toView(entity, Boolean.TRUE);
		} catch (ConstraintViolationException cve) {
			String errores = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("getDetailsByIdCatViaAdministracion() - Error en la validacion - error: {}", errores.replace("\n", ","));
			throw new CatViaAdministracionException(HttpStatus.BAD_REQUEST, errores);
		} catch (DataIntegrityViolationException dive) {
			logger.error("getDetailsByIdCatViaAdministracion() - Error de integridad en la tabla CatViaAdministracion - error: {}",  dive);
			throw new CatViaAdministracionException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("getDetailsByIdCatViaAdministracion() - Error al obtener CatViaAdministracion - error: {}",ex);
			throw new CatViaAdministracionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatViaAdministracionException.SERVER_ERROR, "crear nuevo registro"));
		}
	}

	@Transactional(readOnly=false, rollbackFor=CatViaAdministracionException.class)
	@Override
	public CatViaAdministracionView updateViaAdministracion(Integer idCatViaAdministracion,
			CatViaAdministracionView catViaAdministracionView) throws CatViaAdministracionException {
		try {
			CatViaAdministracion entity = finByIdCatViaAdministracion(idCatViaAdministracion);
			catViaAdministracionView.setIdCatViaAdministracion(idCatViaAdministracion);
			if(catViaAdministracionView.getActivo() == null) {catViaAdministracionView.setActivo(true);}
			entity = catViaAdministracionConverter.toEntity(catViaAdministracionView, entity, Boolean.TRUE);
			catViaAdministracionRepository.save(entity);
			return catViaAdministracionConverter.toView(entity, Boolean.TRUE);
		} catch(CatViaAdministracionException cae) {
			throw cae;
		} catch (ConstraintViolationException cve) {
			String errores = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("updateViaAdministracion() - Error en la validacion - error: {}", errores.replace("\n", ","));
			throw new CatViaAdministracionException(HttpStatus.BAD_REQUEST, errores);
		} catch (DataIntegrityViolationException dive) {
			logger.error("updateViaAdministracion() - Error de integridad en la tabla CatViaAdministracion - error: {}",  dive);
			throw new CatViaAdministracionException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("updateViaAdministracion() - Error al obtener CatViaAdministracion - error: {}",ex);
			throw new CatViaAdministracionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatViaAdministracionException.SERVER_ERROR, "crear nuevo registro"));
		}

	}


}
