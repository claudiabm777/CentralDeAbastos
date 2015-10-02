package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase BodegaValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class BodegaValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-5708708501409416742L;

  public Integer id;
  public String estado;

  public BodegaValue()
  {
  }

  public BodegaValue(Integer id, String estado)
  {
    this.id=id;
    this.estado=estado;
  }

}
