package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatFormacionConverter;
import net.amentum.niomedic.catalogos.exception.CatFormacionException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatFormacion;
import net.amentum.niomedic.catalogos.persistence.CatFormacionRepository;
import net.amentum.niomedic.catalogos.service.CatFormacionService;
import net.amentum.niomedic.catalogos.views.CatFormacionView;
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
public class CatFormacionServiceImpl implements CatFormacionService {
	private final Logger logger = LoggerFactory.getLogger(CatFormacionServiceImpl.class);
	private final Map<String, Object> colOrderNames = new HashMap<>();

	private CatFormacionRepository catFormacionRepository;
	private CatFormacionConverter catFormacionConverter;

	{	
		colOrderNames.put("idCatFormacion", "idCatFormacion");
		colOrderNames.put("formDescripcion", "formDescripcion");
		colOrderNames.put("formAgrupacion", "formAgrupacion");
		colOrderNames.put("formGrado", "formGrado");
		colOrderNames.put("activo", "activo");
	}

	@Autowired
	public void setCatFormacionRepository(CatFormacionRepository catFormacionRepository) {
		this.catFormacionRepository = catFormacionRepository;
	}

	@Autowired
	public void setCatFormacionConverter(CatFormacionConverter catFormacionConverter) {
		this.catFormacionConverter = catFormacionConverter;
	}


	private CatFormacion formacionFindById(Integer idCatFormacion) throws CatFormacionException{
		logger.info("FormacionFindByid() - Buscando formacion por el id: {}",idCatFormacion);
		if (!catFormacionRepository.exists(idCatFormacion)) {
			logger.error("FormacionFindByid() - idCatFormacion no encontrado: {}", idCatFormacion);
			throw  new CatFormacionException(HttpStatus.NOT_FOUND, String.format(CatFormacionException.ITEM_NOT_FOUND, idCatFormacion));

		}
		return catFormacionRepository.findOne(idCatFormacion);
	}

	@Override
	public CatFormacionView getDetailsByIdCatFormacion(Integer idCatFormacion) throws CatFormacionException {
		try {
			CatFormacion catFormacion = formacionFindById(idCatFormacion);
			logger.info("getDetailsByIdCatFormacion()  - {}",catFormacion);
			return catFormacionConverter.toView(catFormacion, Boolean.TRUE);
		} catch (CatFormacionException cFormE) {
			throw cFormE;
		} catch (ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores = "";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores +""+siguiente.getPropertyPath() +":"+siguiente.getMessage()+"\n";
			}
			logger.error("Error en la validacion: {}",errores);
			throw new CatFormacionException(HttpStatus.BAD_REQUEST, String.format(CatFormacionException.BAD_REQUEST, errores));
		} catch (DataIntegrityViolationException dive) {
			logger.error("Error al obtener CatFormacion - Error: {}",dive.getCause().getMessage());
			throw new CatFormacionException(HttpStatus.CONFLICT, dive.getCause().getMessage());
		} catch (Exception ex) {
			logger.error("Error al obtener CatFormacion - error: {}", ex);
			throw  new CatFormacionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatFormacionException.SERVER_ERROR, "obtner por ID"));
		}

	}


	@Override
	public List<CatFormacionView> findAll() throws CatFormacionException {
		try {
			List<CatFormacion> catFormacionList = catFormacionRepository.findAll();
			List<CatFormacionView> catFormacionViewList = new ArrayList<>();
			for (CatFormacion cn : catFormacionList) {
				catFormacionViewList.add(catFormacionConverter.toView(cn, Boolean.TRUE));
			}
			return catFormacionViewList;
		} catch (Exception ex) {
			logger.error("Error al obtener todos los registros de CatFormacion - error: {}", ex);
			throw new CatFormacionException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener la lista de formacion");
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Page<CatFormacionView> getCatFormacionSearch(String datosBusqueda, Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatFormacionException {
		try {
			logger.info("getCatFormacionSearch(): - datosBusqueda: {} - activo: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
					datosBusqueda,activo, page, size, orderColumn, orderType);

			if(!colOrderNames.containsKey(orderColumn)){orderColumn= "idCatFormacion";}

			List<CatFormacionView> catFormacionViewList = new ArrayList<>();
			Page<CatFormacion> catFormacionPage = null;
			logger.info("getCatFormacionSearch() - Creando ordenamiento SORT");
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("formDescripcion"));

			if (orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
			}

			PageRequest request = new PageRequest(page, size, sort);
			final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatFormacion> spec = Specifications.where(
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
			logger.info("getCatFormacionSearch() - Realizando búsqueda");
			catFormacionPage = catFormacionRepository.findAll(spec, request);

			catFormacionPage.getContent().forEach(catFormacion -> {
				catFormacionViewList.add(catFormacionConverter.toView(catFormacion, Boolean.TRUE));
			});
			PageImpl<CatFormacionView> catFormacionViewPage = new PageImpl<CatFormacionView>(catFormacionViewList, request, catFormacionPage.getTotalElements());
			return catFormacionViewPage;
		} catch (Exception ex) {
			logger.error(ExceptionServiceCode.GROUP + "Error al tratar de seleccionar lista CatFormacion paginable - eror: {}", ex);
			throw  new CatFormacionException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible filtrar el catalogo de formacion");

		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	@Transactional(readOnly=false, rollbackFor=CatFormacionException.class)
	@Override
	public CatFormacionView creteCatFormacion(CatFormacionView catFormacionView) throws CatFormacionException {
		try {
			catFormacionView.setIdCatFormacion(null);
			catFormacionView.setActivo(true);
			CatFormacion entity = catFormacionConverter.toEntity(new CatFormacion(), catFormacionView, Boolean.FALSE);
			logger.info("creteCatFormacion() - Guardando {}",entity);
			catFormacionRepository.save(entity);
			logger.info("creteCatFormacion() - Se Guardo exitosamente {}",entity);
			return catFormacionConverter.toView(entity, Boolean.TRUE);
		}catch(ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores = "";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores +""+siguiente.getPropertyPath() +":"+siguiente.getMessage()+"\n";
			}
			logger.error("creteCatFormacion() - Errores de validacion en la tabla: {}",errores);
			throw new CatFormacionException(HttpStatus.BAD_REQUEST, String.format(CatFormacionException.BAD_REQUEST, errores));
		}catch(DataIntegrityViolationException dive){
			logger.error("creteCatFormacion() - Ocurrio un error de integridad - error: {}",dive);
			throw new CatFormacionException(HttpStatus.CONFLICT, dive.getCause().getMessage());
		}catch(Exception e) {
			logger.error("creteCatFormacion() - Ocurrio un error inesperado - error: {}",e);
			throw  new CatFormacionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatFormacionException.SERVER_ERROR, "crear"));
		}	
	}

	@Transactional(readOnly=false, rollbackFor=CatFormacionException.class)
	@Override
	public CatFormacionView updateCatFormacion(Integer idCatFormacion, CatFormacionView catFormacionView)
			throws CatFormacionException {
		try {
			CatFormacion entity = formacionFindById(idCatFormacion);
			if(catFormacionView.getActivo()==null) {
				catFormacionView.setActivo(true);
			}
			catFormacionView.setIdCatFormacion(idCatFormacion);
			catFormacionConverter.toEntity(entity, catFormacionView, Boolean.TRUE);
			logger.info("updateCatFormacion() - Actualizando catFormacion con el id:{} - {}", idCatFormacion,catFormacionView);
			logger.debug("updateCatFormacion() - Actualizando catFormacion con el id:{} - {}", idCatFormacion,catFormacionView);
			catFormacionRepository.save(entity);
			logger.info("updateCatFormacion() - Se actualizo catFormacion con el id:{} - {}", idCatFormacion,catFormacionView);
			logger.debug("updateCatFormacion() -Se Actualizo catFormacion con el id:{} - {}", idCatFormacion,catFormacionView);
			return catFormacionConverter.toView(entity, Boolean.TRUE);
		}catch(CatFormacionException cfe) {
			throw cfe;
		}catch(ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores = "";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores +""+siguiente.getPropertyPath() +":"+siguiente.getMessage()+"\n";
			}
			logger.error("updateCatFormacion() - Errores de validacion en la tabla: {}",errores);
			throw new CatFormacionException(HttpStatus.BAD_REQUEST, String.format(CatFormacionException.BAD_REQUEST, errores));
		}catch(DataIntegrityViolationException dive){
			logger.error("updateCatFormacion() - Ocurrio un error de integridad - error: {}",dive);
			throw new CatFormacionException(HttpStatus.CONFLICT, dive.getCause().getMessage());
		}catch(Exception e) {
			logger.error("updateCatFormacion() - Ocurrio un error inesperado - error: {}",e);
			throw  new CatFormacionException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatFormacionException.SERVER_ERROR, "actualizar"));
		}
	}


}
