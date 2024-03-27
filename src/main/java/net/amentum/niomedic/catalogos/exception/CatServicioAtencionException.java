package net.amentum.niomedic.catalogos.exception;

import lombok.Getter;
import lombok.Setter;
import net.amentum.common.GenericException;

public class CatServicioAtencionException extends GenericException{
    private final ExceptionServiceCode MODULE_CODE = ExceptionServiceCode.CATALOGOS;
    @Setter
    @Getter
    private String layer;
    @Getter
    @Setter
    private String action;

    public CatServicioAtencionException(Exception ex, String message, String layer, String action){
        super(ex,message);
        this.layer = layer;
        this.action = action;
    }

    public CatServicioAtencionException(String message, String layer, String action){
        super(message);
        this.layer = layer;
        this.action = action;
    }

    @Override
    public String getExceptionCode() {
        return new StringBuffer(layer).append(MODULE_CODE).append(action).toString();    }
}