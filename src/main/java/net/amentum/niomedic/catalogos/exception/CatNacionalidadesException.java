package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;
import net.amentum.common.v2.GenericException;


public class CatNacionalidadesException extends GenericException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3007196962107953348L;
	public static final String NOT_FOUND = "No se encontró nacionalidad con el id: %s";
	public static final String SEERVER_ERROR = "No se pudo efectuar la operación de %s en el catálogo de nacionalidades";
	
	public CatNacionalidadesException(HttpStatus status, String text){
		super(status,text);
	}
}
