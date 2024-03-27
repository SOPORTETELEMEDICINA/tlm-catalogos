package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;

import net.amentum.common.v2.GenericException;

public class CatEstudiosException extends GenericException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4332281146605366075L;
	public static final String SERVER_ERROR = "No se pudo efectuar la operación %s sobre el catálogo de estudios";
	public static final String NOT_FOUND = "No se encontró ningún estudio con el ID: %s";
	public CatEstudiosException(HttpStatus status, String message) {
		super(status, message);
	}

}
