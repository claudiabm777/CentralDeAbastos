package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase TipoProductoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class TipoProductoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-2271825074961997279L;

  public Integer id;
  public String nombre;
  public String descripcion;

  public TipoProductoValue()
  {
  }

  public TipoProductoValue(Integer id, String nombre, String descripcion)
  {
    this.id=id;
    this.nombre=nombre;
    this.descripcion=descripcion;
  }

}
