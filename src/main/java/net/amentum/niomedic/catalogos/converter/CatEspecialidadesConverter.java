package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatEspecialidades;
import net.amentum.niomedic.catalogos.views.CatEspecialidadesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CatEspecialidadesConverter {
   private Logger logger = LoggerFactory.getLogger(CatEspecialidadesConverter.class);

   public CatEspecialidades toEntity(CatEspecialidadesView catEspecialidadesView, CatEspecialidades catEspecialidades, Boolean update) {
      catEspecialidades.setEspecialidadCodigo(catEspecialidadesView.getEspecialidadCodigo().toUpperCase());
      catEspecialidades.setEspecialidadDescripcion(catEspecialidadesView.getEspecialidadDescripcion().toUpperCase());
      catEspecialidades.setActivo(catEspecialidadesView.getActivo());
      catEspecialidades.setDatosBusqueda(catEspecialidadesView.getEspecialidadCodigo().toUpperCase() + " " + catEspecialidadesView.getEspecialidadDescripcion().toUpperCase());
      catEspecialidades.setFechaUltimaModificacion(new Date());

      logger.debug("convertir CatEspecialidadesView to Entity: {}", catEspecialidades);
      return catEspecialidades;
   }

   public CatEspecialidadesView toView(CatEspecialidades catEspecialidades, Boolean completeConversion) {
      CatEspecialidadesView catEspecialidadesView = new CatEspecialidadesView();
      catEspecialidadesView.setIdCatEspecialidades(catEspecialidades.getIdCatEspecialidades());
      catEspecialidadesView.setEspecialidadCodigo(catEspecialidades.getEspecialidadCodigo());
      catEspecialidadesView.setEspecialidadDescripcion(catEspecialidades.getEspecialidadDescripcion());
      catEspecialidadesView.setActivo(catEspecialidades.getActivo());
      catEspecialidadesView.setFechaUltimaModificacion(catEspecialidades.getFechaUltimaModificacion());

      logger.debug("convertir CatEspecialidades to View: {}", catEspecialidadesView);
      return catEspecialidadesView;
   }
}
