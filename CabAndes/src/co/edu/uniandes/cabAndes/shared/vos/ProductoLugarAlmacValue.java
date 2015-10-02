package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProductoLugarAlmacValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProductoLugarAlmacValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=4327110152885517391L;

  public Integer cod_lugar_almac;
  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer cantidad;
  public Integer numero_lote;
  public java.util.Date fecha_expiracion_lote;
  public Double promocion_lote;

  public ProductoLugarAlmacValue()
  {
  }

  public ProductoLugarAlmacValue(Integer cod_lugar_almac, Integer cod_producto, Integer cod_presentacion, Integer cantidad, Integer numero_lote, java.util.Date fecha_expiracion_lote, Double promocion_lote)
  {
    this.cod_lugar_almac=cod_lugar_almac;
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.cantidad=cantidad;
    this.numero_lote=numero_lote;
    this.fecha_expiracion_lote=fecha_expiracion_lote;
    this.promocion_lote=promocion_lote;
  }

}
