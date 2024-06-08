package net.amentum.niomedic.catalogos.service;

import net.amentum.niomedic.catalogos.views.CatAppConfigurationView;
import net.amentum.niomedic.catalogos.exception.CatAppConfigurationException;

public interface CatAppConfigurationService {
    CatAppConfigurationView getUserByidCliente(Integer idCliente) throws CatAppConfigurationException;

}
