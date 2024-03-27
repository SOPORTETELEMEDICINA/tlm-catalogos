package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatNacionalidadesConverter;
import net.amentum.niomedic.catalogos.exception.CatNacionalidadesException;
import net.amentum.niomedic.catalogos.model.CatNacionalidades;
import net.amentum.niomedic.catalogos.persistence.CatNacionalidadesRepository;
import net.amentum.niomedic.catalogos.service.CatNacionalidadesService;
import net.amentum.niomedic.catalogos.views.CatNacionalidadesView;

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
public class CatNacionalidadesServiceImpl implements CatNacionalidadesService {
	private final Logger logger = LoggerFactory.getLogger(CatNacionalidadesServiceImpl.class);
	private final Map<String, Object> colOrderNames = new HashMap<>();

	private CatNacionalidadesRepository catNacionalidadesRepository;
	private CatNacionalidadesConverter catNacionalidadesConverter;

	{	
		colOrderNames.put("activo", "activo");
		colOrderNames.put("idCatNacionalidades", "idCatNacionalidades");
		colOrderNames.put("nacPaisCodigo", "nacPaisCodigo");
		colOrderNames.put("nacPaisDescripcion", "nacPaisDescripcion");
		colOrderNames.put("nacPaisClave", "nacPaisClave");
	}

	@Autowired
	public void setCatNacionalidadesRepository(CatNacionalidadesRepository catNacionalidadesRepository) {
		this.catNacionalidadesRepository = catNacionalidadesRepository;
	}

	@Autowired
	public void setCatNacionalidadesConverter(CatNacionalidadesConverter catNacionalidadesConverter) {
		this.catNacionalidadesConverter = catNacionalidadesConverter;
	}
	private CatNacionalidades findByIdNacionalidades(Integer idCatNacionalidades) throws CatNacionalidadesException {

		logger.info("findByIdNacionalidades() - Buscando nacionalidad con el id: {}",idCatNacionalidades);
		if (!catNacionalidadesRepository.exists(idCatNacionalidades)) {
			logger.error("findByIdNacionalidades() - idCatNacionalidades no encontrado: {}", idCatNacionalidades);
			throw new CatNacionalidadesException(HttpStatus.NOT_FOUND, String.format(CatNacionalidadesException.NOT_FOUND, idCatNacionalidades));
		}
		return catNacionalidadesRepository.findOne(idCatNacionalidades);
	}

	@Override
	public CatNacionalidadesView getDetailsByIdCatNacionalidades(Integer idCatNacionalidades) throws CatNacionalidadesException {
		try {
			CatNacionalidades catNacionalidades = findByIdNacionalidades(idCatNacionalidades);
			return catNacionalidadesConverter.toView(catNacionalidades, Boolean.TRUE);
		} catch (CatNacionalidadesException cNacE) {
			throw cNacE;
		} catch (ConstraintViolationException cve) {
			String error = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				error = error + "" + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("Error en la validacion: {}",error);
			throw new CatNacionalidadesException(HttpStatus.BAD_REQUEST, error);
		} catch (DataIntegrityViolationException dive) {
			logger.error("Error al obtener CatNacionalidades - id: {} - error: {}",idCatNacionalidades, dive.getCause().getCause().getMessage());
			throw new CatNacionalidadesException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("Error al obtener CatNacionalidades - id: {} - error: {}", idCatNacionalidades, ex);
			throw new CatNacionalidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatNacionalidadesException.SEERVER_ERROR, "obtener por id"));
		}

	}

	@Override
	public List<CatNacionalidadesView> findAll() throws CatNacionalidadesException {
		try {
			List<CatNacionalidades> catNacionalidadesList = catNacionalidadesRepository.findAll();
			List<CatNacionalidadesView> catNacionalidadesViewList = new ArrayList<>();
			for (CatNacionalidades cn : catNacionalidadesList) {
				catNacionalidadesViewList.add(catNacionalidadesConverter.toView(cn, Boolean.TRUE));
			}
			return catNacionalidadesViewList;
		} catch (Exception ex) {
			logger.error("Error al obtener todos los registros de CatNacionalidades - error: {}",ex);
			throw new CatNacionalidadesException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CatNacionalidadesException.SERVER_ERROR, "obtener todos los registros"));
		}
	}


	@Override
	public Page<CatNacionalidadesView> getCatNacionalidadesSearch(String datosBusqueda, Boolean activo,Integer page, Integer size, String orderColumn, String orderType) throws CatNacionalidadesException {
		try {
			logger.info("getCatNacionalidadesSearch(): - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
					datosBusqueda, activo, page, size, orderColumn, orderType);
			List<CatNacionalidadesView> catNacionalidadesViewList = new ArrayList<>();
			Page<CatNacionalidades> catNacionalidadesPage = null;
			if(!colOrderNames.containsKey(orderColumn)) {
				orderColumn ="nacPaisDescripcion";
			}
			
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("nacPaisDescripcion"));
			
			if (orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
			}

			PageRequest request = new PageRequest(page, size, sort);
			final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatNacionalidades> spec = Specifications.where(
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

			catNacionalidadesPage = catNacionalidadesRepository.findAll(spec, request);


			catNacionalidadesPage.getContent().forEach(catNacionalidades -> {
				catNacionalidadesViewList.add(catNacionalidadesConverter.toView(catNacionalidades, Boolean.TRUE));
			});
			PageImpl<CatNacionalidadesView> catNacionalidadesViewPage = new PageImpl<CatNacionalidadesView>(catNacionalidadesViewList, request, catNacionalidadesPage.getTotalElements());
			return catNacionalidadesViewPage;
		} catch (Exception ex) {
			logger.error("Error al tratar de seleccionar lista CatNacionalidades paginable - error: {}",ex);
			throw new CatNacionalidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatNacionalidadesException.SEERVER_ERROR, "filtrado"));
		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	@Transactional(readOnly = false, rollbackFor = CatNacionalidadesException.class)
	@Override
	public CatNacionalidadesView createNacionalidades(CatNacionalidadesView catNacionalidadesView)
			throws CatNacionalidadesException {
		try {
			catNacionalidadesView.setIdCatNacionalidades(null);
			catNacionalidadesView.setActivo(true);
			CatNacionalidades entity = catNacionalidadesConverter.toEntity(catNacionalidadesView, new CatNacionalidades(), Boolean.FALSE);
			logger.info("createNacionalidades() - Guardando - {}",catNacionalidadesView);
			catNacionalidadesRepository.save(entity);
			logger.info("createNacionalidades() - se guardo existosamente - {}",catNacionalidadesView);
			return catNacionalidadesConverter.toView(entity, Boolean.TRUE);
		} catch (ConstraintViolationException cve) {
			String error = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				error = error + "" + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("createNacionalidades() - Error en la validacion: {}",error);
			throw new CatNacionalidadesException(HttpStatus.BAD_REQUEST, error);
		} catch (DataIntegrityViolationException dive) {
			logger.error("createNacionalidades() - Error de integridad al crear una nacionalidad - error: {}", dive.getCause().getCause().getMessage());
			throw new CatNacionalidadesException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("createNacionalidades() - Error inesperado al crear una nacionalidad - error: {}",  ex);
			throw new CatNacionalidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatNacionalidadesException.SEERVER_ERROR, "obtener por id"));
		}
	}

	@Transactional(readOnly = false, rollbackFor = CatNacionalidadesException.class)
	@Override
	public CatNacionalidadesView updateNacionalidades(Integer idCatNacionalidades,
			CatNacionalidadesView catNacionalidadesView) throws CatNacionalidadesException {
		try {
			CatNacionalidades entity = findByIdNacionalidades(idCatNacionalidades);
			catNacionalidadesView.setIdCatNacionalidades(idCatNacionalidades);
			entity = catNacionalidadesConverter.toEntity(catNacionalidadesView, entity, Boolean.TRUE);
			logger.info("updateNacionalidades() - Actualizando - {}",catNacionalidadesView);
			catNacionalidadesRepository.save(entity);
			logger.info("updateNacionalidades() - Se actualizó exitosamente - {}",catNacionalidadesView);
			catNacionalidadesRepository.flush();
			return catNacionalidadesConverter.toView(entity, Boolean.TRUE);
		}catch(CatNacionalidadesException cne) {
			throw cne;
		} catch (ConstraintViolationException cve) {
			String error = "";
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				error = error + "" + siguiente.getPropertyPath() + ": " + siguiente.getMessage() + "\n";
			}
			logger.error("updateNacionalidades() - Error en la validacion: {}",error);
			throw new CatNacionalidadesException(HttpStatus.BAD_REQUEST, error);
		} catch (DataIntegrityViolationException dive) {
			logger.error("updateNacionalidades() - Error de integridad al actualizar la nacionalidad con el id: {} - error: {}", idCatNacionalidades, dive.getCause().getCause().getMessage());
			throw new CatNacionalidadesException(HttpStatus.CONFLICT, dive.getCause().getCause().getMessage());
		} catch (Exception ex) {
			logger.error("updateNacionalidades() - Error inesperado al actualizar la nacionalidad con el id: {} - error: {}",idCatNacionalidades,  ex);
			throw new CatNacionalidadesException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatNacionalidadesException.SEERVER_ERROR, "obtener por id"));
		}
	}


}
