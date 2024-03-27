package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatLenguasIndigenasConverter;
import net.amentum.niomedic.catalogos.exception.CatLenguasIndigenasException;
import net.amentum.niomedic.catalogos.model.CatLenguasIndigenas;
import net.amentum.niomedic.catalogos.persistence.CatLenguasIndigenasRepository;
import net.amentum.niomedic.catalogos.service.CatLenguasIndigenasService;
import net.amentum.niomedic.catalogos.views.CatLenguasIndigenasView;
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
public class CatLenguasIndigenasServiceImpl implements CatLenguasIndigenasService {
	private final Logger logger = LoggerFactory.getLogger(CatLenguasIndigenasServiceImpl.class);
	private final Map<String, Object> colOrderNames = new HashMap<>();

	private CatLenguasIndigenasRepository catLenguasIndigenasRepository;
	private CatLenguasIndigenasConverter catLenguasIndigenasConverter;

	{
		colOrderNames.put("lengDescripcion", "lengDescripcion");
		colOrderNames.put("lengCatalogKey", "lengCatalogKey");
		colOrderNames.put("idCatLenguasIndigenas", "idCatLenguasIndigenas");
		colOrderNames.put("activo", "activo");
	}

	@Autowired
	public void setCatLenguasIndigenasRepository(CatLenguasIndigenasRepository catLenguasIndigenasRepository) {
		this.catLenguasIndigenasRepository = catLenguasIndigenasRepository;
	}

	@Autowired
	public void setCatLenguasIndigenasConverter(CatLenguasIndigenasConverter catLenguasIndigenasConverter) {
		this.catLenguasIndigenasConverter = catLenguasIndigenasConverter;
	}

	@Transactional(readOnly = false, rollbackFor = {CatLenguasIndigenasException.class})
	@Override
	public CatLenguasIndigenasView createCatLenguasIndigenas(CatLenguasIndigenasView catLenguasIndigenasView)
			throws CatLenguasIndigenasException {
		try {
			logger.info("creatCatLenguasIndigenas()- Guardando nueva lengua indígena: {}", catLenguasIndigenasView);
			logger.debug("creatCatLenguasIndigenas()- Guardando nueva lengua indígena: {}", catLenguasIndigenasView);
			catLenguasIndigenasView.setIdCatLenguasIndigenas(null);
			catLenguasIndigenasView.setActivo(Boolean.TRUE);
			CatLenguasIndigenas entity = catLenguasIndigenasConverter.toEntity(catLenguasIndigenasView, new CatLenguasIndigenas(), Boolean.TRUE);
			catLenguasIndigenasRepository.save(entity);
			logger.info("creatCatLenguasIndigenas() -La nueva lengua indígena se gurado exitosamente");
			logger.debug("creatCatLenguasIndigenas() -La nueva lengua indígena se gurado exitosamente: {}",entity);
			CatLenguasIndigenasView view = catLenguasIndigenasConverter.toView(entity, Boolean.TRUE);
			return view;
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("creatCatLenguasIndigenas() - Error de validacion al  crear un nuevo registros en cat_lenguas_indigenas - error: {}", errores);
			throw new CatLenguasIndigenasException(HttpStatus.BAD_REQUEST, errores);
		}catch(DataIntegrityViolationException de) {
			logger.error("creatCatLenguasIndigenas() - Ocurrio un erro de Integridad en la DB al querer insertar una nueva lengua indigena - error{}", de.getCause().getMessage());
			throw new CatLenguasIndigenasException(HttpStatus.CONFLICT, de.getCause().getMessage());
		}catch(Exception e) {
			logger.error("creatCatLenguasIndigenas() - Ocurrio un error inesperado al crear una nueva lengua indigena - error: {}",e);
			throw new CatLenguasIndigenasException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CatLenguasIndigenasException.SERVER_ERROR, "crear una nueva") );
		}
	}

	@Transactional(readOnly = false, rollbackFor = {CatLenguasIndigenasException.class})
	@Override
	public CatLenguasIndigenasView updateCatLenguasIndigenas(Integer idCatLenguasIndigenas,CatLenguasIndigenasView view)
			throws CatLenguasIndigenasException {
		try {
			if(view.getActivo()==null) {
				view.setActivo(true);
			}
			view.setIdCatLenguasIndigenas(idCatLenguasIndigenas);
			logger.info("updateCatLenguasIndigenas() - Actualizando lengua indígena: {}", view);
			logger.debug("updateCatLenguasIndigenas() - Actualizando lengua inígena: {}", view);
			CatLenguasIndigenas entity= getCatLenguasIndigenasById(idCatLenguasIndigenas);
			view.setIdCatLenguasIndigenas(idCatLenguasIndigenas);
			entity = catLenguasIndigenasConverter.toEntity(view, entity, Boolean.TRUE);
			catLenguasIndigenasRepository.save(entity);
			logger.info("updateCatLenguasIndigenas() - Se actualizo exitosamente");
			logger.debug("updateCatLenguasIndigenas() - Se actualizo exitosamente: {}",entity);
			return catLenguasIndigenasConverter.toView(entity, Boolean.TRUE);
		}catch(CatLenguasIndigenasException cile){
			throw cile;
		}catch(ConstraintViolationException ce) {
			final Set<ConstraintViolation<?>> violaciones = ce.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("creatCatLenguasIndigenas() - Error de validacion al actualizar un registros en cat_lenguas_indigenas - error: {}", errores);
			throw new CatLenguasIndigenasException(HttpStatus.BAD_REQUEST, errores);
		}catch(DataIntegrityViolationException de) {
			logger.error("updateCatLenguasIndigenas() - Ocurrio un erro de Integridad en la DB al querer actualizar una nueva lengua indigena - error{}", de.getCause().getMessage());
			throw new CatLenguasIndigenasException(HttpStatus.CONFLICT, String.format(CatLenguasIndigenasException.INTEGRITY_ERROR, "crear una nueva"));
		}catch(Exception e) {
			logger.error("updateCatLenguasIndigenas() - Ocurrio un error inesperado al actualizar una nueva lengua indigena - error: {}",e);
			throw new CatLenguasIndigenasException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CatLenguasIndigenasException.SERVER_ERROR, "crear una nueva") );
		}
	}

	private CatLenguasIndigenas getCatLenguasIndigenasById(Integer idCatLenguasIndigenas)throws CatLenguasIndigenasException {
		logger.info("getCatLenguasIndigenasById - Buscando lengua indígena con el id: {}",idCatLenguasIndigenas);
		logger.debug("getCatLenguasIndigenasById - Buscando lengua indígena con el id: {}",idCatLenguasIndigenas);
		if (!catLenguasIndigenasRepository.exists(idCatLenguasIndigenas)) {
			logger.error("getCatLenguasIndigenasById() - idCatLenguasIndigenas no encontrado: {}", idCatLenguasIndigenas);
			throw new CatLenguasIndigenasException(HttpStatus.NOT_FOUND,String.format(CatLenguasIndigenasException.ITEM_NOT_FOUND, idCatLenguasIndigenas));
		}
		CatLenguasIndigenas entity = catLenguasIndigenasRepository.findOne(idCatLenguasIndigenas);
		logger.info("getCatLenguasIndigenasById - Se ecnotro una lengua indígena con el id: {}",idCatLenguasIndigenas);
		logger.debug("getCatLenguasIndigenasById - Se ecnotro una lengua indígena {} - ",entity);
		return entity;
	}

	@Override
	public CatLenguasIndigenasView getDetailsByIdCatLenguasIndigenas(Integer idCatLenguasIndigenas) throws CatLenguasIndigenasException {
		try {
			CatLenguasIndigenas catLenguasIndigenas = getCatLenguasIndigenasById(idCatLenguasIndigenas);
			return catLenguasIndigenasConverter.toView(catLenguasIndigenas, Boolean.TRUE);
		} catch (CatLenguasIndigenasException cLiE) {
			throw cLiE;
		} catch (ConstraintViolationException cve) {
			final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
			String errores="";
			for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
				ConstraintViolation<?> siguiente = iterator.next();
				errores = errores + siguiente.getPropertyPath()+": "+siguiente.getMessage()+"\n";
			}
			logger.error("getDetailsByIdCatLenguasIndigenas() - Error en la validacion");
			throw new CatLenguasIndigenasException(HttpStatus.BAD_REQUEST, errores);
		} catch (DataIntegrityViolationException dive) {

			logger.error("getDetailsByIdCatLenguasIndigenas() - Error  de integridad al obtener CatLenguasIndigenas por id: {} - error: {}", idCatLenguasIndigenas, dive);
			throw  new CatLenguasIndigenasException(HttpStatus.CONFLICT, String.format(CatLenguasIndigenasException.INTEGRITY_ERROR, "obtener una"));
		} catch (Exception ex) {

			logger.error("getDetailsByIdCatLenguasIndigenas() - Error inesperado al obtener CatLenguasIndigenas por id: {} -error: {}", idCatLenguasIndigenas, ex);
			throw new CatLenguasIndigenasException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(CatLenguasIndigenasException.SERVER_ERROR, "obtener la"));
		}

	}


	@Override
	public List<CatLenguasIndigenasView> findAll() throws CatLenguasIndigenasException {
		try {
			List<CatLenguasIndigenas> catLenguasIndigenasList = catLenguasIndigenasRepository.findAll();
			List<CatLenguasIndigenasView> catLenguasIndigenasViewList = new ArrayList<>();
			for (CatLenguasIndigenas cn : catLenguasIndigenasList) {
				catLenguasIndigenasViewList.add(catLenguasIndigenasConverter.toView(cn, Boolean.TRUE));
			}
			return catLenguasIndigenasViewList;
		} catch (Exception ex) {
			logger.error("findAll() - Error inesperado al obtener todas las lenguas indígenas - error: {}", ex);
			throw new CatLenguasIndigenasException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible obtener todas las lengua indígena del catalogo");
		}
	}


	@Override
	public Page<CatLenguasIndigenasView> getCatLenguasIndigenasSearch(String datosBusqueda,Boolean activo, Integer page, Integer size, String orderColumn, String orderType) throws CatLenguasIndigenasException {
		try {
			logger.info("getCatLenguasIndigenasSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
					datosBusqueda, page, size, orderColumn, orderType);
			if(!colOrderNames.containsKey(orderColumn)) {
				orderColumn = "lengDescripcion";
			}

			List<CatLenguasIndigenasView> catLenguasIndigenasViewList = new ArrayList<>();
			Page<CatLenguasIndigenas> catLenguasIndigenasPage = null;
			Sort sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get("lengDescripcion"));

			if (orderType.equalsIgnoreCase("asc")) {
				sort = new Sort(Sort.Direction.ASC, (String) colOrderNames.get(orderColumn));
			} else {
				sort = new Sort(Sort.Direction.DESC, (String) colOrderNames.get(orderColumn));
			}

			PageRequest request = new PageRequest(page, size, sort);
			final String patternSearch = "%" + datosBusqueda.toLowerCase() + "%";
			Specifications<CatLenguasIndigenas> spec = Specifications.where(
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

			catLenguasIndigenasPage = catLenguasIndigenasRepository.findAll(spec, request);


			catLenguasIndigenasPage.getContent().forEach(catLenguasIndigenas -> {
				catLenguasIndigenasViewList.add(catLenguasIndigenasConverter.toView(catLenguasIndigenas, Boolean.TRUE));
			});
			PageImpl<CatLenguasIndigenasView> catLenguasIndigenasViewPage = new PageImpl<CatLenguasIndigenasView>(catLenguasIndigenasViewList, request, catLenguasIndigenasPage.getTotalElements());
			return catLenguasIndigenasViewPage;
		} catch (Exception ex) {
			logger.error("findAll() - Error inesperado al filtrar las lenguas indígenas - error: {}", ex);
			throw new CatLenguasIndigenasException(HttpStatus.INTERNAL_SERVER_ERROR, "No fue posible filtar las lenguas indígenas");

		}
	}

	private String sinAcentos(String cadena) {
		return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
}
