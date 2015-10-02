package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProveedorValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProveedorValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=1714505079936070546L;

  public String cod_usuario;
  public Integer cod_tipo_producto;
  public Double calificacion;

  public ProveedorValue()
  {
  }

  public ProveedorValue(String cod_usuario, Integer cod_tipo_producto, Double calificacion)
  {
    this.cod_usuario=cod_usuario;
    this.cod_tipo_producto=cod_tipo_producto;
    this.calificacion=calificacion;
  }

}
