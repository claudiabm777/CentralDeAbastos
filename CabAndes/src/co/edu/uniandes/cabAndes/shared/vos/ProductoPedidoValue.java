package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase ProductoPedidoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class ProductoPedidoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=5524253180816940894L;

  public Integer cod_pedido;
  public Integer cod_producto;
  public Integer cod_presentacion;
  public Integer cantidad_solicitada;
  public Integer cantidad_entregada;

  public ProductoPedidoValue()
  {
  }

  public ProductoPedidoValue(Integer cod_pedido, Integer cod_producto, Integer cod_presentacion, Integer cantidad_solicitada, Integer cantidad_entregada)
  {
    this.cod_pedido=cod_pedido;
    this.cod_producto=cod_producto;
    this.cod_presentacion=cod_presentacion;
    this.cantidad_solicitada=cantidad_solicitada;
    this.cantidad_entregada=cantidad_entregada;
  }

}
