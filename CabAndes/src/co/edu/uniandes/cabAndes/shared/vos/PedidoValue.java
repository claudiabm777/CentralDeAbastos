package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase PedidoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class PedidoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-8330050726315832900L;

  public Integer numero;
  public String cod_usuario;
  public java.util.Date fecha_creacion;
  public java.util.Date fecha_esperada_entrega;
  public java.util.Date fecha_entrega;
  public Integer precio_cabandes;
  public Boolean ha_sido_cancelado;
  public Boolean ha_sido_entregado;

  public PedidoValue()
  {
  }

  public PedidoValue(Integer numero, String cod_usuario, java.util.Date fecha_creacion, java.util.Date fecha_esperada_entrega, java.util.Date fecha_entrega, Integer precio_cabandes, Boolean ha_sido_cancelado, Boolean ha_sido_entregado)
  {
    this.numero=numero;
    this.cod_usuario=cod_usuario;
    this.fecha_creacion=fecha_creacion;
    this.fecha_esperada_entrega=fecha_esperada_entrega;
    this.fecha_entrega=fecha_entrega;
    this.precio_cabandes=precio_cabandes;
    this.ha_sido_cancelado=ha_sido_cancelado;
    this.ha_sido_entregado=ha_sido_entregado;
  }

}
