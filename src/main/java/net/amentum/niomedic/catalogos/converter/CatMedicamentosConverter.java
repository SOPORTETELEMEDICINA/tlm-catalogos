package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCuadroIoMed;
import net.amentum.niomedic.catalogos.model.CatGpoTerapeuticoMed;
import net.amentum.niomedic.catalogos.model.CatMedicamentos;
import net.amentum.niomedic.catalogos.model.CatTipoInsumoMed;
import net.amentum.niomedic.catalogos.persistence.CatCuadroIoMedRepository;
import net.amentum.niomedic.catalogos.persistence.CatGpoTerapeuticoMedRepository;
import net.amentum.niomedic.catalogos.persistence.CatTipoInsumoMedRepository;
import net.amentum.niomedic.catalogos.views.CatMedicamentosView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CatMedicamentosConverter {
   private Logger logger = LoggerFactory.getLogger(CatMedicamentosConverter.class);

   @Autowired
   private CatTipoInsumoMedRepository catTipoInsumoMedRepository;

   @Autowired
   private CatCuadroIoMedRepository catCuadroIoMedRepository;

   @Autowired
   private CatGpoTerapeuticoMedRepository catGpoTerapeuticoMedRepository;

   public CatMedicamentos toEntity(CatMedicamentosView catMedicamentosView, CatMedicamentos catMedicamentos, Boolean update) {
      catMedicamentos.setCveCodigo(catMedicamentosView.getCveCodigo().toUpperCase());
      catMedicamentos.setSubCveCodigo(catMedicamentosView.getSubCveCodigo().toUpperCase());
      catMedicamentos.setNombreGenerico(catMedicamentosView.getNombreGenerico().toUpperCase());
      catMedicamentos.setFormaFarmaceutica(catMedicamentosView.getFormaFarmaceutica().toUpperCase());
      catMedicamentos.setConcentracion(catMedicamentosView.getConcentracion().toUpperCase());
      catMedicamentos.setPresentacion(catMedicamentosView.getPresentacion().toUpperCase());
      catMedicamentos.setPrincipalIndicaciones(catMedicamentosView.getPrincipalIndicaciones().toUpperCase());
      catMedicamentos.setDemasIndicaciones(catMedicamentosView.getDemasIndicaciones().toUpperCase());
      catMedicamentos.setTipoActualizacion(catMedicamentosView.getTipoActualizacion().toUpperCase());
      catMedicamentos.setNumActualizacion(catMedicamentosView.getNumActualizacion().toUpperCase());
      catMedicamentos.setDescripcionCompleta(catMedicamentosView.getDescripcionCompleta().toUpperCase());

      CatTipoInsumoMed catTipoInsumoMed = catTipoInsumoMedRepository.findOne(catMedicamentosView.getCatTipoInsumoId());
      if(catTipoInsumoMed != null){
         catMedicamentos.setCatTipoInsumoMed(catTipoInsumoMed);
      }else {
         catMedicamentos.setCatTipoInsumoMed(null);
      }

      CatCuadroIoMed catCuadroIoMed = catCuadroIoMedRepository.findOne(catMedicamentosView.getCatCuadroIoId());
      if(catCuadroIoMed != null){
         catMedicamentos.setCatCuadroIoMed(catCuadroIoMed);
      }else {
         catMedicamentos.setCatCuadroIoMed(null);
      }

      CatGpoTerapeuticoMed catGpoTerapeuticoMed = catGpoTerapeuticoMedRepository.findOne(catMedicamentosView.getCatGpoTerapeuticoId());
      if(catGpoTerapeuticoMed != null){
         catMedicamentos.setCatGpoTerapeuticoMed(catGpoTerapeuticoMed);
      }else {
         catMedicamentos.setCatGpoTerapeuticoMed(null);
      }

      catMedicamentos.setActivo(catMedicamentosView.getActivo());
      catMedicamentos.setDatosBusqueda(catMedicamentosView.getNombreGenerico().toUpperCase() + " " + catMedicamentosView.getPrincipalIndicaciones().toUpperCase());
      catMedicamentos.setFechaUltimaModificacion(new Date());

      logger.debug("convertir CatMedicamentosView to Entity: {}", catMedicamentos);
      return catMedicamentos;
   }


   public CatMedicamentosView toView(CatMedicamentos catMedicamentos, Boolean completeConversion) {
      CatMedicamentosView catMedicamentosView = new CatMedicamentosView();
      catMedicamentosView.setIdCatMedicamentos(catMedicamentos.getIdCatMedicamentos());
      catMedicamentosView.setCveCodigo(catMedicamentos.getCveCodigo());
      catMedicamentosView.setSubCveCodigo(catMedicamentos.getSubCveCodigo());
      catMedicamentosView.setNombreGenerico(catMedicamentos.getNombreGenerico());
      catMedicamentosView.setFormaFarmaceutica(catMedicamentos.getFormaFarmaceutica());
      catMedicamentosView.setConcentracion(catMedicamentos.getConcentracion());
      catMedicamentosView.setPresentacion(catMedicamentos.getPresentacion());
      catMedicamentosView.setPrincipalIndicaciones(catMedicamentos.getPrincipalIndicaciones());
      catMedicamentosView.setDemasIndicaciones(catMedicamentos.getDemasIndicaciones());
      catMedicamentosView.setTipoActualizacion(catMedicamentos.getTipoActualizacion());
      catMedicamentosView.setNumActualizacion(catMedicamentos.getNumActualizacion());
      catMedicamentosView.setDescripcionCompleta(catMedicamentos.getDescripcionCompleta());

      //      if (completeConversion) {
         CatTipoInsumoMed catTipoInsumoMed = catMedicamentos.getCatTipoInsumoMed();
//         catMedicamentosView.set_descripcionTipoInsumo((catTipoInsumoMed == null) ? "No existe" : catTipoInsumoMed.getDescripcionTipoInsumo());
         catMedicamentosView.setCatTipoInsumoId((catTipoInsumoMed == null) ? -1 : catTipoInsumoMed.getIdCatTipoInsumo());

         CatCuadroIoMed catCuadroIoMed = catMedicamentos.getCatCuadroIoMed();
//         catMedicamentosView.set_descripcionCuadroIo((catCuadroIoMed == null) ? "No existe" : catCuadroIoMed.getDescripcionCuadroIo());
         catMedicamentosView.setCatCuadroIoId((catCuadroIoMed == null) ? -1 : catCuadroIoMed.getIdCatCuadroIo());

         CatGpoTerapeuticoMed catGpoTerapeuticoMed = catMedicamentos.getCatGpoTerapeuticoMed();
//         catMedicamentosView.set_descripcionGpoTerapeutico((catGpoTerapeuticoMed == null) ? "No existe" : catGpoTerapeuticoMed.getDescripcionGpoTerapeutico());
         catMedicamentosView.setCatGpoTerapeuticoId((catGpoTerapeuticoMed == null) ? -1 : catGpoTerapeuticoMed.getIdCatGpoTerapeutico());
//      }

      catMedicamentosView.setActivo(catMedicamentos.getActivo());
      catMedicamentosView.setFechaUltimaModificacion(catMedicamentos.getFechaUltimaModificacion());

      logger.debug("convertir catMedicamentos to View: {}", catMedicamentosView);
      return catMedicamentosView;
   }

}
