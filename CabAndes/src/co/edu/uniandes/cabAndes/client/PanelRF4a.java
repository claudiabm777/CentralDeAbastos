package co.edu.uniandes.cabAndes.client;

import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF4a extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public IntegerBox campoNumeroPedido=new IntegerBox();
  public TextBox campoDireccionElectronicaUsuario=new TextBox();
  public DateBox campoFechaCreacion=new DateBox();
  public ListBox campoProducto[]=new ListBox[10];
  public ListBox campoPresentacion[]=new ListBox[10];
  public IntegerBox campoCantidadSolicitada[]=new IntegerBox[10];
  public Button botonRegistrarPedido=new Button("Registrar el pedido",this);

  public PanelRF4a()
  {
    campoFechaCreacion.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Número de pedido (entre 0 y 9999999 para CabAndes Grupo5):"));
    formulario.setWidget(0,1,campoNumeroPedido);
    formulario.setWidget(1,0,new Label("Dirección electrónica comprador mayorista o administrador de local:"));
    formulario.setWidget(1,1,campoDireccionElectronicaUsuario);
    formulario.setWidget(2,0,new Label("Fecha de pedido:"));
    formulario.setWidget(2,1,campoFechaCreacion);
    for (int i=0; i<10; i++)
    {
      campoProducto[i]=new ListBox(false);
      campoPresentacion[i]=new ListBox(false);
      campoCantidadSolicitada[i]=new IntegerBox();
      formulario.setWidget(i+3,0,new Label("Producto:"));
      formulario.setWidget(i+3,1,campoProducto[i]);
      formulario.setWidget(i+3,2,new Label("Presentación:"));
      formulario.setWidget(i+3,3,campoPresentacion[i]);
      formulario.setWidget(i+3,4,new Label("Cantidad solicitada:"));
      formulario.setWidget(i+3,5,campoCantidadSolicitada[i]);
    }
    super.add(formulario);
    super.add(botonRegistrarPedido);
  }

  @Override
  public void onSuccess(Void result)
  {
    Window.alert("Se registró el pedido con todos los productos dados.");
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

  @Override
  public void onClick(ClickEvent event)
  {
    Integer numeroPedido=campoNumeroPedido.getValue();
    if (numeroPedido==null||numeroPedido<0||numeroPedido>9999999)
    {
    	Window.alert("el número de pedido debe estar entre 0 y 9999999");
    }
    else
    {
        String direccionElectronicaUsuario=campoDireccionElectronicaUsuario.getText();
        Date fechaCreacion=campoFechaCreacion.getValue();
        List<ProductoPedidoValue> productosPedido=new ArrayList<ProductoPedidoValue>();
        for (int i=0; i<10; i++)
        {
          String textoIdProducto=campoProducto[i].getValue(campoProducto[i].getSelectedIndex());
          String textoIdPresentacion=campoPresentacion[i].getValue(campoPresentacion[i].getSelectedIndex());
          Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
          Integer idPresentacion=!textoIdPresentacion.isEmpty()?Integer.parseInt(textoIdPresentacion):null;
          Integer cantidadSolicitada=campoCantidadSolicitada[i].getValue();
          if (idProducto!=null)
          {
            ProductoPedidoValue productoPedido=new ProductoPedidoValue(numeroPedido,idProducto,idPresentacion,cantidadSolicitada,0);
            productosPedido.add(productoPedido);
          }
        }
        PedidoValue pedido=new PedidoValue(numeroPedido,direccionElectronicaUsuario,fechaCreacion,null,null,null,false,false);
        servlet.registrarPedido(pedido,productosPedido,this);
    }    
  }

}
