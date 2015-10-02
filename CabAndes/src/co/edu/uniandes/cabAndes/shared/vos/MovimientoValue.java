package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase MovimientoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class MovimientoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=4628029453752606415L;

  public Integer numero;
  public Integer cod_lugar_origen;
  public String nombre_lugar_origen;
  public Integer cod_lugar_destino;
  public String nombre_lugar_destino;
  public java.util.Date fecha_movimiento;
  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer cantidad;

  public MovimientoValue()
  {
  }

  public MovimientoValue(Integer numero, Integer cod_lugar_origen, String nombre_lugar_origen, Integer cod_lugar_destino, String nombre_lugar_destino, java.util.Date fecha_movimiento, Integer cod_producto, Integer cod_presentacion, Integer cantidad)
  {
    this.numero=numero;
    this.cod_lugar_origen=cod_lugar_origen;
    this.nombre_lugar_origen=nombre_lugar_origen;
    this.cod_lugar_destino=cod_lugar_destino;
    this.nombre_lugar_destino=nombre_lugar_destino;
    this.fecha_movimiento=fecha_movimiento;
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.cantidad=cantidad;
  }

}
