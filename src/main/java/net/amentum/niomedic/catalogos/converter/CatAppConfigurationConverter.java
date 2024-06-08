package net.amentum.niomedic.catalogos.converter;

import net.amentum.niomedic.catalogos.views.CatAppConfigurationView;
import net.amentum.niomedic.catalogos.model.CatAppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatAppConfigurationConverter {
    public CatAppConfigurationView toView(CatAppConfiguration entity) {

        CatAppConfigurationView view = new CatAppConfigurationView();
        view.setIdCliente(entity.getIdCliente());
        view.setCliente(entity.getCliente());
        view.setTelefono(entity.getTelefono());
        view.setTelefono_emergencia(entity.getTelefonoEmergencia());
        view.setUrl_agenda(entity.getUrlAgenda());
        view.setUrlvideollamadas(entity.getUrlvideollamadas());
        view.setUrlchat(entity.getUrlchat());
        view.setUrlmail(entity.getUrlmail());
        view.setUrlprivacidad(entity.getUrlprivacidad());
        view.setUrlsms(entity.getUrlsms());
        view.setCampo1(entity.getCampo1());
        view.setCampo2(entity.getCampo2());
        view.setCampo3(entity.getCampo3());
        view.setCampo4(entity.getCampo4());
        return view;
    }
}
