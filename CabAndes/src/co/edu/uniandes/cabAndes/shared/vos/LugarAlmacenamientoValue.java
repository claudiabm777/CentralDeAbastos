package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase LugarAlmacenamientoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class LugarAlmacenamientoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-2630652398379343802L;

  public Integer id;
  public Integer cod_tipo_producto;
  public String nombre;
  public Double capacidad;

  public LugarAlmacenamientoValue()
  {
  }

  public LugarAlmacenamientoValue(Integer id, Integer cod_tipo_producto, String nombre, Double capacidad)
  {
    this.id=id;
    this.cod_tipo_producto=cod_tipo_producto;
    this.nombre=nombre;
    this.capacidad=capacidad;
  }

}
