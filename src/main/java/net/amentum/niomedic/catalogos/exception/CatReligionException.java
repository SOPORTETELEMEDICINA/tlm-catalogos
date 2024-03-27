package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;

import net.amentum.common.v2.GenericException;;


public class CatReligionException extends GenericException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9019719282710353783L;

	public static final String ITEM_NOT_FOUND = "No se encontró ninguna religión con el id: %s";
	public static final String SERVER_ERROR = "No fue posible %s las religiones";
	public static final String INTEGRITY_ERROR = "Ocurrió un error de integrida en la DB al efectuar la operación %s";

	public CatReligionException(HttpStatus status, String message) {
		super(status, message);
	}

}
