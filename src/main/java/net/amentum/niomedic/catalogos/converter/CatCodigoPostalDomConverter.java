package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.model.CatCodigoPostalDom;
import net.amentum.niomedic.catalogos.model.CatEntidadesDom;
import net.amentum.niomedic.catalogos.model.CatMunicipiosDom;
import net.amentum.niomedic.catalogos.model.CatTipoAsentamientoDom;
import net.amentum.niomedic.catalogos.views.CatCodigoPostalDomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatCodigoPostalDomConverter {
   private Logger logger = LoggerFactory.getLogger(CatCodigoPostalDomConverter.class);

   public CatCodigoPostalDomView toView(CatCodigoPostalDom catCodigoPostalDom, Boolean completeConversion) {
      CatCodigoPostalDomView catCodigoPostalDomView = new CatCodigoPostalDomView();
      catCodigoPostalDomView.setIdCatCodigoPostal(catCodigoPostalDom.getIdCatCodigoPostal());
      catCodigoPostalDomView.setCodigoPostal(catCodigoPostalDom.getCodigoPostal());
      catCodigoPostalDomView.setAsentamiento(catCodigoPostalDom.getAsentamiento());
      catCodigoPostalDomView.setCiudad(catCodigoPostalDom.getCiudad());
      catCodigoPostalDomView.setIdAsentaCpcons(catCodigoPostalDom.getIdAsentaCpcons());
      catCodigoPostalDomView.setZona(catCodigoPostalDom.getZona());
      catCodigoPostalDomView.setCveCiudad(catCodigoPostalDom.getCveCiudad());

      if (completeConversion) {
         CatTipoAsentamientoDom catTipoAsentamientoDom = catCodigoPostalDom.getCatTipoAsentamientoDom();
         catCodigoPostalDomView.setDescripcionAsentamiento((catTipoAsentamientoDom == null) ? "No existe" : catTipoAsentamientoDom.getDescripcionAsentamiento());
         catCodigoPostalDomView.setCatTipoAsentamientoId((catTipoAsentamientoDom == null) ? -1 :catTipoAsentamientoDom.getIdCatTipoAsentamiento());

         CatMunicipiosDom catMunicipiosDom = catCodigoPostalDom.getCatMunicipiosDom();
         catCodigoPostalDomView.setDescripcionMunicipios((catMunicipiosDom == null) ? "No existe" : catMunicipiosDom.getDescripcionMunicipios());
         catCodigoPostalDomView.setCatMunicipiosId((catMunicipiosDom == null) ? -1 : catMunicipiosDom.getIdCatMunicipios());

         CatEntidadesDom catEntidadesDom = catCodigoPostalDom.getCatEntidadesDom();
         catCodigoPostalDomView.setDescripcionEntidades((catEntidadesDom == null) ? "No existe" : catEntidadesDom.getDescripcionEntidades());
         catCodigoPostalDomView.setCatEntidadesId((catEntidadesDom == null) ? -1 : catEntidadesDom.getIdCatEntidades());
      }

      logger.debug("convertir catCodigoPostalDom to View: {}", catCodigoPostalDomView);
      return catCodigoPostalDomView;
   }

}
