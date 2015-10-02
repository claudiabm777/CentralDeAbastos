package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase VentaValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class VentaValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=4354564891353432432L;

  public Integer numero;
  public Integer cod_local;
  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer cantidad;
  public java.util.Date fecha;
  public Integer precio;

  public VentaValue()
  {
  }

  public VentaValue(Integer numero, Integer cod_local, Integer cod_producto, Integer cod_presentacion, Integer cantidad, java.util.Date fecha, Integer precio)
  {
    this.numero=numero;
    this.cod_local=cod_local;
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.cantidad=cantidad;
    this.fecha=fecha;
    this.precio=precio;
  }

}
