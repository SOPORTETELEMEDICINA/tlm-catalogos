package net.amentum.niomedic.catalogos.service.impl;

import net.amentum.niomedic.catalogos.converter.CatCluesConverter;
import net.amentum.niomedic.catalogos.exception.CatCluesException;
import net.amentum.niomedic.catalogos.exception.ExceptionServiceCode;
import net.amentum.niomedic.catalogos.model.CatClues;
import net.amentum.niomedic.catalogos.persistence.CatCluesRepository;
import net.amentum.niomedic.catalogos.service.CatCluesService;
import net.amentum.niomedic.catalogos.views.CatCluesView;
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
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)

public class CatCluesServiceImpl implements CatCluesService {

    private final Logger logger = LoggerFactory.getLogger(CatCluesServiceImpl.class);
    private final Map<String, Object> colOrderNames = new HashMap<>();

    private CatCluesRepository CatCluesRepository;
    private CatCluesConverter CatCluesConverter;

    {
        colOrderNames.put("clues", "clues");
        colOrderNames.put("fkEntidad", "fkEntidad");
        colOrderNames.put("fkCveMunicipio", "fkCveMunicipio");
        colOrderNames.put("fkCveLocalidad", "fkCveLocalidad");
        colOrderNames.put("nombreJurisdiccion", "nombreJurisdiccion");
        colOrderNames.put("claveJurisdiccion", "claveJurisdiccion");
        colOrderNames.put("nombreInstitucion", "nombreInstitucion");
        colOrderNames.put("claveInstitucion", "claveInstitucion");
        colOrderNames.put("claveCortaInstitucion", "claveCortaInstitucion");
        colOrderNames.put("nombreTipoEstablecimiento", "nombreTipoEstablecimiento");
        colOrderNames.put("claveTipoEstablecimiento", "claveTipoEstablecimiento");
        colOrderNames.put("nombreTipologia", "nombreTipologia");
        colOrderNames.put("claveTipologia", "claveTipologia");
        colOrderNames.put("nombreSubtipologia", "nombreSubtipologia");
        colOrderNames.put("claveSubtipologia", "claveSubtipologia");
        colOrderNames.put("claveScian", "claveScian");
        colOrderNames.put("descripcionClaveScian", "descripcionClaveScian");
        colOrderNames.put("consultoriosMedGral", "consultoriosMedGral");
        colOrderNames.put("consultoriosOtrasAreas", "consultoriosOtrasAreas");
        colOrderNames.put("totalConsultorios", "totalConsultorios");
        colOrderNames.put("camasAreaHos", "camasAreaHos");
        colOrderNames.put("camasOtrasAreas", "camasOtrasAreas");
        colOrderNames.put("totalCamas", "totalCamas");
        colOrderNames.put("nombreUnidad", "nombreUnidad");
        colOrderNames.put("claveVialidad", "claveVialidad");
        colOrderNames.put("tipoVialidad", "tipoVialidad");
        colOrderNames.put("vialidad", "vialidad");
        colOrderNames.put("numeroExterior", "numeroExterior");
        colOrderNames.put("numeroInterior", "numeroInterior");
        colOrderNames.put("claveTipoAsentamiento", "claveTipoAsentamiento");
        colOrderNames.put("tipoAsentamiento", "tipoAsentamiento");
        colOrderNames.put("asentamiento", "asentamiento");
        colOrderNames.put("entreTipoVialidad1", "entreTipoVialidad1");
        colOrderNames.put("entreVialidad1", "entreVialidad1");
        colOrderNames.put("entreTipoVialidad2", "entreTipoVialidad2");
        colOrderNames.put("entreVialidad2", "entreVialidad2");
        colOrderNames.put("observacionesDireccion", "observacionesDireccion");
        colOrderNames.put("codigoPostal", "codigoPostal");
        colOrderNames.put("estatusOperacion", "estatusOperacion");
        colOrderNames.put("claveEstatusOperacion", "claveEstatusOperacion");
        colOrderNames.put("tieneLicenciaSanitaria", "tieneLicenciaSanitaria");
        colOrderNames.put("numeroLicenciaSanitaria", "numeroLicenciaSanitaria");
        colOrderNames.put("tieneAvisoFuncionamiento", "tieneAvisoFuncionamiento");
        colOrderNames.put("fechaEmisionAvFun", "fechaEmisionAvFun");
        colOrderNames.put("rfcEstablecimiento", "rfcEstablecimiento");
        colOrderNames.put("fechaConstruccion", "fechaConstruccion");
        colOrderNames.put("fechaInicioOperacion", "fechaInicioOperacion");
        colOrderNames.put("unidadMovilMarca", "unidadMovilMarca");
        colOrderNames.put("unidadMovilModelo", "unidadMovilModelo");
        colOrderNames.put("unidadMovilCapacidad", "unidadMovilCapacidad");
        colOrderNames.put("unidadMovilPrograma", "unidadMovilPrograma");
        colOrderNames.put("unidadMovilClavePrograma", "unidadMovilClavePrograma");
        colOrderNames.put("unidadMovilTipo", "unidadMovilTipo");
        colOrderNames.put("unidadMovilClaveTipo", "unidadMovilClaveTipo");
        colOrderNames.put("unidadMovilTipologia", "unidadMovilTipologia");
        colOrderNames.put("unidadMovilClaveTipologia", "unidadMovilClaveTipologia");
        colOrderNames.put("longitud", "longitud");
        colOrderNames.put("latitud", "latitud");
        colOrderNames.put("nombreInsAdm", "nombreInsAdm");
        colOrderNames.put("claveInsAdm", "claveInsAdm");
        colOrderNames.put("nivelAtencion", "nivelAtencion");
        colOrderNames.put("claveNivelAtencion", "claveNivelAtencion");
        colOrderNames.put("estatusAcreditacion", "estatusAcreditacion");
        colOrderNames.put("claveEstatusAcreditacion", "claveEstatusAcreditacion");
        colOrderNames.put("acreditaciones", "acreditaciones");
        colOrderNames.put("subacreditacion", "subacreditacion");
        colOrderNames.put("estratoUnidad", "estratoUnidad");
        colOrderNames.put("claveEstratoUnidad", "claveEstratoUnidad");
        colOrderNames.put("tipoObra", "tipoObra");
        colOrderNames.put("claveTipoObra", "claveTipoObra");
        colOrderNames.put("horarioAtencion", "horarioAtencion");
        colOrderNames.put("areasServicios", "areasServicios");
        colOrderNames.put("ultimoMovimiento", "ultimoMovimiento");
        colOrderNames.put("fechaUltimoMovimiento", "fechaUltimoMovimiento");
        colOrderNames.put("certificacionCsg", "certificacionCsg");
        colOrderNames.put("tipoCertificacion", "tipoCertificacion");
        colOrderNames.put("vigenciaCertificacion", "vigenciaCertificacion");


    }

    @Autowired
    public void setCatCluesRepository(CatCluesRepository CatCluesRepository) {
        this.CatCluesRepository = CatCluesRepository;
    }

    @Autowired
    public void setCatCluesConverter(CatCluesConverter CatCluesConverter) {
        this.CatCluesConverter = CatCluesConverter;
    }


       @Transactional(readOnly = false, rollbackFor = {CatCluesException.class})
    @Override
    public CatCluesView getDetailsByidCatClues(Integer idCatClues) throws CatCluesException {
        try {
            if (!CatCluesRepository.exists(idCatClues)) {
                logger.error("===>>>idCatClues no encontrado: {}", idCatClues);
                CatCluesException CClu = new CatCluesException("No se encuentra en el sistema CatClues", CatCluesException.LAYER_DAO, CatCluesException.ACTION_VALIDATE);
                CClu.addError("idCatClues no encontrado: " + idCatClues);
                throw CClu;
            }
            CatClues CatClues = CatCluesRepository.findOne(idCatClues);
            return CatCluesConverter.toView(CatClues, Boolean.TRUE);
        } catch (CatCluesException CClu) {
            throw CClu;
        } catch (ConstraintViolationException cve) {
            logger.error("===>>>Error en la validacion");
            CatCluesException CClu = new CatCluesException("Error en la validacion", CatCluesException.LAYER_DAO, CatCluesException.ACTION_VALIDATE);
            final Set<ConstraintViolation<?>> violaciones = cve.getConstraintViolations();
            for (Iterator<ConstraintViolation<?>> iterator = violaciones.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<?> siguiente = iterator.next();
                CClu.addError(siguiente.getPropertyPath() + ": " + siguiente.getMessage());
            }
            throw CClu;
        } catch (DataIntegrityViolationException dive) {
            CatCluesException CClu = new CatCluesException("No fue posible obtener  CatClues", CatCluesException.LAYER_DAO, CatCluesException.ACTION_INSERT);
            CClu.addError("Ocurrio un error al obtener CatClues");
            logger.error("===>>>Error al obtener CatClues - CODE: {} - {}", CClu.getExceptionCode(), idCatClues, dive);
            throw CClu;
        } catch (Exception ex) {
            CatCluesException CClu = new CatCluesException("Error inesperado al obtener  CatClues", CatCluesException.LAYER_DAO, CatCluesException.ACTION_INSERT);
            CClu.addError("Ocurrio un error al obtener CatClues");
            logger.error("===>>>Error al obtener CatClues - CODE: {} - {}", CClu.getExceptionCode(), idCatClues, ex);
            throw CClu;
        }

    }

      @Transactional(readOnly = false, rollbackFor = {CatCluesException.class})
    @Override
    public List<CatCluesView> findAll() throws CatCluesException {
        try {
            List<CatClues> CatCluesList = CatCluesRepository.findAll();
            List<CatCluesView> CatCluesViewList = new ArrayList<>();
            for (CatClues cn : CatCluesList) {
                CatCluesViewList.add(CatCluesConverter.toView(cn, Boolean.TRUE));
            }
            return CatCluesViewList;
        } catch (Exception ex) {
            CatCluesException CClu = new CatCluesException("Error inesperado al obtener todos los registros de  CatClues", CatCluesException.LAYER_DAO, CatCluesException.ACTION_INSERT);
            CClu.addError("Ocurrio un error al obtener CatClues");
            logger.error("===>>>Error al obtener todos los registros de CatClues - CODE: {} - {}", CClu.getExceptionCode(), ex);
            throw CClu;
        }
    }


@Transactional(readOnly = false, rollbackFor = {CatCluesException.class})
    @Override
    public Page<CatCluesView> getCatCluesSearch(String fkEntidad,String claveInstitucion) throws CatCluesException {
        try {
            logger.info("===>>>getCatCluesSearch(): - datosBusqueda: {} - page: {} - size: {} - orderColumn: {} - orderType: {}",
                   fkEntidad,claveInstitucion);

//         if (datosBusqueda.trim().isEmpty() || datosBusqueda == null) {
//            logger.error("===>>>datosBusqueda viene NULO/VACIO: {}", datosBusqueda);
//            CatCluesException CClu = new CatCluesException("No se encuentra en el sistema CatClues", CatCluesException.LAYER_DAO, CatCluesException.ACTION_VALIDATE);
//            CClu.addError("datosBusqueda viene NULO/VACIO: " + datosBusqueda);
//            throw CClu;
//         }

            List<CatCluesView> CatCluesViewList = new ArrayList<>();
            List<CatClues> CatCluesPage = null;


            final String patternSearch = "%" + fkEntidad.toLowerCase() + "%";
            final String patternSearch2 = "%" + claveInstitucion.toLowerCase() + "%";

            Specifications<CatClues> spec = Specifications.where(
                    (root, query, cb) -> {
                        Predicate tc = null;
                        if (fkEntidad != null && !fkEntidad.isEmpty() && claveInstitucion != null && !claveInstitucion.isEmpty()) {
                            Predicate fk_entidad=cb.like(cb.function("unaccent",String.class, cb.lower(root.get("fkEntidad"))),sinAcentos(patternSearch));
                            Predicate clave_inst=cb.like(cb.function("unaccent", String.class, cb.lower(root.get("claveInstitucion"))), sinAcentos(patternSearch2));

                            tc = cb.and(fk_entidad,clave_inst);
                        }
//               if (active != null) {
//                  tc = (tc != null ? cb.and(tc, cb.equal(root.get("activo"), active)) : cb.equal(root.get("activo"), active));
//               }
                        return tc;
                    }
            );

            if (spec == null) {
                CatCluesPage = CatCluesRepository.findAll();
            } else {
                CatCluesPage = CatCluesRepository.findAll(spec);
            }

            CatCluesPage.forEach(CatClues -> {
                CatCluesViewList.add(CatCluesConverter.toView(CatClues, Boolean.TRUE));
            });
            PageImpl<CatCluesView> CatCluesViewPage= new PageImpl<CatCluesView>(CatCluesViewList);
            return CatCluesViewPage;
//      } catch (CatCluesException CClu) {
//         throw CClu;
        } catch (IllegalArgumentException iae) {
            logger.error("===>>>Algun parametro no es correcto");
            CatCluesException pe = new CatCluesException("Algun parametro no es correcto:", CatCluesException.LAYER_SERVICE, CatCluesException.ACTION_VALIDATE);
            pe.addError("Puede que sea null, vacio o valor incorrecto");
            throw pe;
        } catch (Exception ex) {
            CatCluesException CClu = new CatCluesException("Ocurrio un error al seleccionar lista CatClues paginable", CatCluesException.LAYER_SERVICE, CatCluesException.ACTION_SELECT);
            logger.error(ExceptionServiceCode.GROUP + "===>>>Error al tratar de seleccionar lista CatClues paginable - CODE: {}", CClu.getExceptionCode(), ex);
            throw CClu;
        }
    }



    private String sinAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }




}
