package net.amentum.niomedic.catalogos.exception;

import org.springframework.http.HttpStatus;

import net.amentum.common.v2.GenericException;;


public class CatLenguasIndigenasException extends GenericException {
	private static final long serialVersionUID = -4416454698612614991L;
	
	public static final String ITEM_NOT_FOUND = "No se encontró ninguna lengua indígena con el id: %s";
	public static final String SERVER_ERROR = "No fue posible %s lengua indígena";
	public static final String INTEGRITY_ERROR = "Ocurrió un error de integrida en la DB al querer %s legua indígena";

	public CatLenguasIndigenasException(HttpStatus status, String message) {
		super(status, message);
	}

}
