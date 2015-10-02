package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase LocalValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class LocalValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=8935543265942362016L;

  public Integer id;
  public String cod_administrador;

  public LocalValue()
  {
  }

  public LocalValue(Integer id, String cod_administrador)
  {
    this.id=id;
    this.cod_administrador=cod_administrador;
  }

}
