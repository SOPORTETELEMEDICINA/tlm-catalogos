package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatEstadoConsulta;
import net.amentum.niomedic.catalogos.views.CatEstadoConsultaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatEstadoConsultaConverter {
   private Logger logger = LoggerFactory.getLogger(CatEstadoConsultaConverter.class);

   public CatEstadoConsultaView toView(CatEstadoConsulta catEstadoConsulta, Boolean completeConversion){
      CatEstadoConsultaView catEstadoConsultaView = new CatEstadoConsultaView();
      catEstadoConsultaView.setIdEstadoConsulta(catEstadoConsulta.getIdEstadoConsulta());
      catEstadoConsultaView.setDescripcion(catEstadoConsulta.getDescripcion());
      catEstadoConsultaView.setColor(catEstadoConsulta.getColor());
      catEstadoConsultaView.setActivo(catEstadoConsulta.getActivo());

      logger.debug("convertir catEstadoConsulta to View: {}", catEstadoConsultaView);
      return catEstadoConsultaView;
   }

}
