package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase LicitacionValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class LicitacionValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-5443547745026199060L;

  public Integer numero;
  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer cantidad_solicitada;
  public Integer cantidad_recibida;
  public String cod_proveedor_elegido;
  public java.util.Date fecha_creacion;
  public java.util.Date fecha_maxima_propuestas;
  public java.util.Date fecha_esperada_entrega;
  public java.util.Date fecha_entrega;
  public Integer calificacion_entrega;
  public Boolean ha_sido_elegida;
  public Boolean ha_sido_satisfecha;

  public LicitacionValue()
  {
  }

  public LicitacionValue(Integer numero, Integer cod_producto, Integer cod_presentacion, Integer cantidad_solicitada, Integer cantidad_recibida, String cod_proveedor_elegido, java.util.Date fecha_creacion, java.util.Date fecha_maxima_propuestas, java.util.Date fecha_esperada_entrega, java.util.Date fecha_entrega, Integer calificacion_entrega, Boolean ha_sido_elegida, Boolean ha_sido_satisfecha)
  {
    this.numero=numero;
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.cantidad_solicitada=cantidad_solicitada;
    this.cantidad_recibida=cantidad_recibida;
    this.cod_proveedor_elegido=cod_proveedor_elegido;
    this.fecha_creacion=fecha_creacion;
    this.fecha_maxima_propuestas=fecha_maxima_propuestas;
    this.fecha_esperada_entrega=fecha_esperada_entrega;
    this.fecha_entrega=fecha_entrega;
    this.calificacion_entrega=calificacion_entrega;
    this.ha_sido_elegida=ha_sido_elegida;
    this.ha_sido_satisfecha=ha_sido_satisfecha;
  }

}
