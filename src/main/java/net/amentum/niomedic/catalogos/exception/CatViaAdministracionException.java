package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;

import net.amentum.common.v2.GenericException;


public class CatViaAdministracionException extends GenericException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1001882675814593351L;
	public static final String NOT_FOUND = "No se encontró ningún registro con el id: %s";
	public static final String SERVER_ERROR = "No se pudo efectuar la operación de %s sobre el catálogo vía administración";
	public CatViaAdministracionException(HttpStatus status, String message) {
		super(status, message);
	}
}
