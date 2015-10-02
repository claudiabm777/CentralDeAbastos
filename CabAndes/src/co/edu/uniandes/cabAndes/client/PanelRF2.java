package co.edu.uniandes.cabAndes.client;

import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF2 extends VerticalPanel implements ClickHandler
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public TextBox campoDireccionElectronicaUsuario=new TextBox();
  public ListBox campoHaSidoEntregado=new ListBox(false);
  public DateBox campoFechaMinimaCreacion=new DateBox();
  public DateBox campoFechaMaximaCreacion=new DateBox();
  public IntegerBox campoCostoMinimo=new IntegerBox();
  public IntegerBox campoCostoMaximo=new IntegerBox();
  public ListBox campoProducto=new ListBox(false);
  public ListBox campoPresentacion=new ListBox(false);
  public Button botonConsultarPedidos=new Button("Consultar los pedidos del usuario",this);
  public Tabla<PedidoValue> tablaPedidos=new Tabla<PedidoValue>("PEDIDOS CON LOS CRITERIOS",new ModeloTablaPedidos(),200);
  public Button botonConsultarProductosPedido=new Button("Consultar los productos del pedido seleccionado",this);
  public Tabla<ProductoPedidoValue> tablaProductosPedido=new Tabla<ProductoPedidoValue>("PRODUCTOS DEL PEDIDO SELECCIONADO",new ModeloTablaProductosPedido(),200);

  public PanelRF2()
  {
    campoHaSidoEntregado.addItem("Cualquier estado de pedido","");
    campoHaSidoEntregado.addItem("Pedido satisfecho","true");
    campoHaSidoEntregado.addItem("Pedido pendiente de entrega","false");
    campoFechaMinimaCreacion.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    campoFechaMaximaCreacion.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Dirección electrónica comprador mayorista o administrador de local:"));
    formulario.setWidget(0,1,campoDireccionElectronicaUsuario);
    formulario.setWidget(0,2,new Label("Estado pedido:"));
    formulario.setWidget(0,3,campoHaSidoEntregado);
    formulario.setWidget(1,0,new Label("Fecha mínima de creación:"));
    formulario.setWidget(1,1,campoFechaMinimaCreacion);
    formulario.setWidget(1,2,new Label("Fecha máxima de creación:"));
    formulario.setWidget(1,3,campoFechaMaximaCreacion);
    formulario.setWidget(2,0,new Label("Costo mínimo:"));
    formulario.setWidget(2,1,campoCostoMinimo);
    formulario.setWidget(2,2,new Label("Costo máximo:"));
    formulario.setWidget(2,3,campoCostoMaximo);
    formulario.setWidget(3,0,new Label("Producto:"));
    formulario.setWidget(3,1,campoProducto);
    formulario.setWidget(3,2,new Label("Presentación:"));
    formulario.setWidget(3,3,campoPresentacion);
    super.add(formulario);
    super.add(botonConsultarPedidos);
    super.add(tablaPedidos);
    super.add(botonConsultarProductosPedido);
    super.add(tablaProductosPedido);
  }

  @Override
  public void onClick(ClickEvent event)
  {
    if (event.getSource()==botonConsultarPedidos)
    {
      String textoHaSidoEntregado=campoHaSidoEntregado.getValue(campoHaSidoEntregado.getSelectedIndex());
      String textoIdProducto=campoProducto.getValue(campoProducto.getSelectedIndex());
      String textoIdPresentacion=campoPresentacion.getValue(campoPresentacion.getSelectedIndex());
      String direccionElectronicaUsuario=campoDireccionElectronicaUsuario.getText();
      Boolean haSidoEntregado=!textoHaSidoEntregado.isEmpty()?textoHaSidoEntregado.equals("true"):null;
      Date fechaMinimaCreacion=campoFechaMinimaCreacion.getValue();
      Date fechaMaximaCreacion=campoFechaMaximaCreacion.getValue();
      Integer costoMinimo=campoCostoMinimo.getValue();
      Integer costoMaximo=campoCostoMaximo.getValue();
      Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
      Integer idPresentacion=!textoIdPresentacion.isEmpty()?Integer.parseInt(textoIdPresentacion):null;
      servlet.darPedidosUsuario(direccionElectronicaUsuario,haSidoEntregado,fechaMinimaCreacion,fechaMaximaCreacion,costoMinimo,costoMaximo,idProducto,idPresentacion,tablaPedidos);
    }
    else if (event.getSource()==botonConsultarProductosPedido)
    {
      PedidoValue pedido=tablaPedidos.darDatoSeleccionado();
      if (pedido==null)
      {
        Window.alert("Debe seleccionar un pedido de la tabla");
      }
      else
      {
        servlet.darProductosPedido(pedido.numero,tablaProductosPedido);
      }
    }
  }

}
