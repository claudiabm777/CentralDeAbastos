package co.edu.uniandes.cabAndes.shared.vos;

import java.io.*;
import com.google.gwt.user.client.rpc.*;

/**
 * Clase BodegaPedidoValue, que representa un Value Object.
 * 
 * Los atributos son públicos para que GWT los vea.
 */
public class BodegaPedidoValue implements Serializable,IsSerializable
{

  private static final long serialVersionUID=-3473638967265320908L;

  public Integer cod_bodega;
  public Integer cod_pedido;

  public BodegaPedidoValue()
  {
  }

  public BodegaPedidoValue(Integer cod_bodega, Integer cod_pedido)
  {
    this.cod_bodega=cod_bodega;
    this.cod_pedido=cod_pedido;
  }

}
