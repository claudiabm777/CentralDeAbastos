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

public class PanelRF3c extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public IntegerBox campoNumero=new IntegerBox();
  public ListBox campoProducto=new ListBox(false);
  public ListBox campoPresentacion=new ListBox(false);
  public ListBox campoLocal=new ListBox(false);
  public IntegerBox campoCantidad=new IntegerBox();
  public DateBox campoFecha=new DateBox();
  public IntegerBox campoPrecio=new IntegerBox();
  public Button botonVenderProductoEnLocal=new Button("Vender el producto en el local de ventas",this);

  public PanelRF3c()
  {
    campoFecha.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Número de venta:"));
    formulario.setWidget(0,1,campoNumero);
    formulario.setWidget(1,0,new Label("Producto:"));
    formulario.setWidget(1,1,campoProducto);
    formulario.setWidget(2,0,new Label("Presentación:"));
    formulario.setWidget(2,1,campoPresentacion);
    formulario.setWidget(3,0,new Label("Local de venta:"));
    formulario.setWidget(3,1,campoLocal);
    formulario.setWidget(4,0,new Label("Cantidad vendida:"));
    formulario.setWidget(4,1,campoCantidad);
    formulario.setWidget(5,0,new Label("Fecha de venta:"));
    formulario.setWidget(5,1,campoFecha);
    formulario.setWidget(6,0,new Label("Precio de venta:"));
    formulario.setWidget(6,1,campoPrecio);
    super.add(formulario);
    super.add(botonVenderProductoEnLocal);
  }

  @Override
  public void onSuccess(Void result)
  {
    Window.alert("Se vendió el producto en el local de ventas, disminuyendo sus existencias.");
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

  @Override
  public void onClick(ClickEvent event)
  {
    String textoIdLocal=campoLocal.getValue(campoLocal.getSelectedIndex());
    String textoIdProducto=campoProducto.getValue(campoProducto.getSelectedIndex());
    String textoIdPresentacion=campoPresentacion.getValue(campoPresentacion.getSelectedIndex());
    Integer numero=campoNumero.getValue();
    Integer idLocal=!textoIdLocal.isEmpty()?Integer.parseInt(textoIdLocal):null;
    Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
    Integer idPresentacion=!textoIdPresentacion.isEmpty()?Integer.parseInt(textoIdPresentacion):null;
    Integer cantidad=campoCantidad.getValue();
    Date fecha=campoFecha.getValue();
    Integer precio=campoPrecio.getValue();
    VentaValue venta=new VentaValue(numero,idLocal,idProducto,idPresentacion,cantidad,fecha,precio);
    servlet.venderProductoEnLocal(venta,this);
  }

}
