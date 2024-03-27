package net.amentum.niomedic.catalogos.exception;

import net.amentum.common.v2.GenericException;
import org.springframework.http.HttpStatus;

public class CatMotivosEnvioException extends GenericException{
   public static final String BAD_REQUEST = "Error de validación: \n%s";
   public static final String SERVER_ERROR = "No fue posible %s el CatMotivosEnvio";
   public static final String ITEM_NOT_FOUND = "No se encontró ningún Motivo de Envío con el id: %s";

   public CatMotivosEnvioException(HttpStatus status, String message){
      super(status,message);
   }

}
