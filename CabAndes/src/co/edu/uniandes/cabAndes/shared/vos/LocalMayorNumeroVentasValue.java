package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase LocalMayorNumeroVentasValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class LocalMayorNumeroVentasValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-5186955099748758980L;

  public Integer cod_local;
  public Integer numero_ventas;

  public LocalMayorNumeroVentasValue()
  {
  }

  public LocalMayorNumeroVentasValue(Integer cod_local, Integer numero_ventas)
  {
    this.cod_local=cod_local;
    this.numero_ventas=numero_ventas;
  }

}
