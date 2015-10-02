package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProductoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProductoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=1499441270562328707L;

  public Integer id;
  public Integer cod_tipo_producto;
  public String nombre;
  public Double peso;

  public ProductoValue()
  {
  }

  public ProductoValue(Integer id, Integer cod_tipo_producto, String nombre, Double peso)
  {
    this.id=id;
    this.cod_tipo_producto=cod_tipo_producto;
    this.nombre=nombre;
    this.peso=peso;
  }

}
