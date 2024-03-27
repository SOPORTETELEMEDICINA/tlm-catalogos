package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatClues;
import net.amentum.niomedic.catalogos.views.CatCluesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatCluesConverter {

    private Logger logger = LoggerFactory.getLogger(CatCluesConverter.class);

    public CatCluesView toView(CatClues CatClues, Boolean completeConversion){
        CatCluesView CatCluesView = new CatCluesView();
        CatCluesView.setIdCatClues(CatClues.getIdCatClues());
        CatCluesView.setClues(CatClues.getClues());
        CatCluesView.setFkEntidad(CatClues.getFkEntidad());
        CatCluesView.setFkCveMunicipio(CatClues.getFkCveMunicipio());
        CatCluesView.setFkCveLocalidad(CatClues.getFkCveLocalidad());
        CatCluesView.setNombreJurisdiccion(CatClues.getNombreJurisdiccion());
        CatCluesView.setClaveJurisdiccion(CatClues.getClaveJurisdiccion());
        CatCluesView.setNombreInstitucion(CatClues.getNombreInstitucion());
        CatCluesView.setClaveInstitucion(CatClues.getClaveInstitucion());
        CatCluesView.setClaveCortaInstitucion(CatClues.getClaveCortaInstitucion());
        CatCluesView.setNombreTipoEstablecimiento(CatClues.getNombreTipoEstablecimiento());
        CatCluesView.setClaveTipoEstablecimiento(CatClues.getClaveTipoEstablecimiento());
        CatCluesView.setNombreTipologia(CatClues.getNombreTipologia());
        CatCluesView.setClaveTipologia(CatClues.getClaveTipologia());
        CatCluesView.setNombreSubtipologia(CatClues.getNombreSubtipologia());
        CatCluesView.setClaveSubtipologia(CatClues.getClaveSubtipologia());
        CatCluesView.setClaveScian(CatClues.getClaveScian());
        CatCluesView.setDescripcionClaveScian(CatClues.getDescripcionClaveScian());
        CatCluesView.setConsultoriosMedGral(CatClues.getConsultoriosMedGral());
        CatCluesView.setConsultoriosOtrasAreas(CatClues.getConsultoriosOtrasAreas());
        CatCluesView.setTotalConsultorios(CatClues.getTotalConsultorios());
        CatCluesView.setCamasAreaHos(CatClues.getCamasAreaHos());
        CatCluesView.setCamasOtrasAreas(CatClues.getCamasOtrasAreas());
        CatCluesView.setTotalCamas(CatClues.getTotalCamas());
        CatCluesView.setNombreUnidad(CatClues.getNombreUnidad());
        CatCluesView.setClaveVialidad(CatClues.getClaveVialidad());
        CatCluesView.setTipoVialidad(CatClues.getTipoVialidad());
        CatCluesView.setVialidad(CatClues.getVialidad());
        CatCluesView.setNumeroExterior(CatClues.getNumeroExterior());
        CatCluesView.setNumeroInterior(CatClues.getNumeroInterior());
        CatCluesView.setClaveTipoAsentamiento(CatClues.getClaveTipoAsentamiento());
        CatCluesView.setTipoAsentamiento(CatClues.getTipoAsentamiento());
        CatCluesView.setAsentamiento(CatClues.getAsentamiento());
        CatCluesView.setEntreTipoVialidad1(CatClues.getEntreTipoVialidad1());
        CatCluesView.setEntreVialidad1(CatClues.getEntreVialidad1());
        CatCluesView.setEntreTipoVialidad2(CatClues.getEntreTipoVialidad2());
        CatCluesView.setEntreVialidad2(CatClues.getEntreVialidad2());
        CatCluesView.setObservacionesDireccion(CatClues.getObservacionesDireccion());
        CatCluesView.setCodigoPostal(CatClues.getCodigoPostal());
        CatCluesView.setEstatusOperacion(CatClues.getEstatusOperacion());
        CatCluesView.setClaveEstatusOperacion(CatClues.getClaveEstatusOperacion());
        CatCluesView.setTieneLicenciaSanitaria(CatClues.getTieneLicenciaSanitaria());
        CatCluesView.setNumeroLicenciaSanitaria(CatClues.getNumeroLicenciaSanitaria());
        CatCluesView.setTieneAvisoFuncionamiento(CatClues.getTieneAvisoFuncionamiento());
        CatCluesView.setFechaEmisionAvFun(CatClues.getFechaEmisionAvFun());
        CatCluesView.setRfcEstablecimiento(CatClues.getRfcEstablecimiento());
        CatCluesView.setFechaConstruccion(CatClues.getFechaConstruccion());
        CatCluesView.setFechaInicioOperacion(CatClues.getFechaInicioOperacion());
        CatCluesView.setUnidadMovilMarca(CatClues.getUnidadMovilMarca());
        CatCluesView.setUnidadMovilModelo(CatClues.getUnidadMovilModelo());
        CatCluesView.setUnidadMovilCapacidad(CatClues.getUnidadMovilCapacidad());
        CatCluesView.setUnidadMovilPrograma(CatClues.getUnidadMovilPrograma());
        CatCluesView.setUnidadMovilClavePrograma(CatClues.getUnidadMovilClavePrograma());
        CatCluesView.setUnidadMovilTipo(CatClues.getUnidadMovilTipo());
        CatCluesView.setUnidadMovilClaveTipo(CatClues.getUnidadMovilClaveTipo());
        CatCluesView.setUnidadMovilTipologia(CatClues.getUnidadMovilTipologia());
        CatCluesView.setUnidadMovilClaveTipologia(CatClues.getUnidadMovilClaveTipologia());
        CatCluesView.setLongitud(CatClues.getLongitud());
        CatCluesView.setLatitud(CatClues.getLatitud());
        CatCluesView.setNombreInsAdm(CatClues.getNombreInsAdm());
        CatCluesView.setClaveInsAdm(CatClues.getClaveInsAdm());
        CatCluesView.setNivelAtencion(CatClues.getNivelAtencion());
        CatCluesView.setClaveNivelAtencion(CatClues.getClaveNivelAtencion());
        CatCluesView.setEstatusAcreditacion(CatClues.getEstatusAcreditacion());
        CatCluesView.setClaveEstatusAcreditacion(CatClues.getClaveEstatusAcreditacion());
        CatCluesView.setAcreditaciones(CatClues.getAcreditaciones());
        CatCluesView.setSubacreditacion(CatClues.getSubacreditacion());
        CatCluesView.setEstratoUnidad(CatClues.getEstratoUnidad());
        CatCluesView.setClaveEstratoUnidad(CatClues.getClaveEstratoUnidad());
        CatCluesView.setTipoObra(CatClues.getTipoObra());
        CatCluesView.setClaveTipoObra(CatClues.getClaveTipoObra());
        CatCluesView.setHorarioAtencion(CatClues.getHorarioAtencion());
        CatCluesView.setAreasServicios(CatClues.getAreasServicios());
        CatCluesView.setUltimoMovimiento(CatClues.getUltimoMovimiento());
        CatCluesView.setFechaUltimoMovimiento(CatClues.getFechaUltimoMovimiento());
        CatCluesView.setCertificacionCsg(CatClues.getCertificacionCsg());
        CatCluesView.setTipoCertificacion(CatClues.getTipoCertificacion());
        CatCluesView.setVigenciaCertificacion(CatClues.getVigenciaCertificacion());

        logger.debug("convertir CatClues to View: {}", CatCluesView);
        return CatCluesView;
    
}
}
