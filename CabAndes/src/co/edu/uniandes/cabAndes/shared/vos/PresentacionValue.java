package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase PresentacionValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class PresentacionValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-7215545896783033403L;

  public Integer id;
  public String nombre;
  public String descripcion;
  public String tipo_empaque;
  public Double capacidad;

  public PresentacionValue()
  {
  }

  public PresentacionValue(Integer id, String nombre, String descripcion, String tipo_empaque, Double capacidad)
  {
    this.id=id;
    this.nombre=nombre;
    this.descripcion=descripcion;
    this.tipo_empaque=tipo_empaque;
    this.capacidad=capacidad;
  }

}
