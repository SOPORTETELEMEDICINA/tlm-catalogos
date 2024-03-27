package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;

import net.amentum.common.v2.GenericException;


public class CatFormacionException extends GenericException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1945469824082815670L;
	public static final String BAD_REQUEST = "Error de validacion: \n%s";
	public static final String SERVER_ERROR = "No fue posible %s la Formacion";
	public static final String ITEM_NOT_FOUND = "No se encontró ninguna formación con el id: %s";
	
	public CatFormacionException(HttpStatus status, String message){
		super(status,message);
	}
  
}
