package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase NotificacionBodegaPedValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class NotificacionBodegaPedValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=2786037366679441072L;

  public Integer cod_bodega;
  public Integer cod_pedido;
  public String tipo_notificacion;
  public String mensaje_e_mail;

  public NotificacionBodegaPedValue()
  {
  }

  public NotificacionBodegaPedValue(Integer cod_bodega, Integer cod_pedido, String tipo_notificacion, String mensaje_e_mail)
  {
    this.cod_bodega=cod_bodega;
    this.cod_pedido=cod_pedido;
    this.tipo_notificacion=tipo_notificacion;
    this.mensaje_e_mail=mensaje_e_mail;
  }

}
