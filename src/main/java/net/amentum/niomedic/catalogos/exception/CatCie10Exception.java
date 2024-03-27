package net.amentum.niomedic.catalogos.exception;

import lombok.Getter;
import lombok.Setter;
import net.amentum.common.GenericException;


public class CatCie10Exception extends GenericException {
   private final ExceptionServiceCode MODULE_CODE = ExceptionServiceCode.CATALOGOS;
   @Getter
   @Setter
   private String layer;
   @Getter
   @Setter
   private String action;

   public CatCie10Exception(Exception ex, String message, String layer, String action){
      super(ex,message);
      this.layer = layer;
      this.action = action;
   }

   public CatCie10Exception(String message, String layer, String action){
      super(message);
      this.layer = layer;
      this.action = action;
   }

   @Override
   public String getExceptionCode() {
      return new StringBuffer(layer).append(MODULE_CODE).append(action).toString();
   }
}
