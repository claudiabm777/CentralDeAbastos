package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class PanelRF7 extends VerticalPanel implements ClickHandler
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public ListBox campoTipoProducto=new ListBox(false);
  public ListBox campoProducto=new ListBox(false);
  public Button botonConsultarLocalesMayorNumeroVentas=new Button("Consultar datos del local que ha realizado el mayor número de ventas",this);
  public Tabla<LocalMayorNumeroVentasValue> tablaLocalesMayorNumeroVentas=new Tabla<LocalMayorNumeroVentasValue>("DATOS DEL LOCAL QUE HA REALIZADO EL MAYOR NÚMERO DE VENTAS",new ModeloTablaLocalesMayorNumeroVentas(),200);

  public PanelRF7()
  {
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Tipo de producto:"));
    formulario.setWidget(0,1,campoTipoProducto);
    formulario.setWidget(1,0,new Label("Producto:"));
    formulario.setWidget(1,1,campoProducto);
    super.add(formulario);
    super.add(botonConsultarLocalesMayorNumeroVentas);
    super.add(tablaLocalesMayorNumeroVentas);
  }

  @Override
  public void onClick(ClickEvent event)
  {
    String textoIdTipoProducto=campoTipoProducto.getValue(campoTipoProducto.getSelectedIndex());
    String textoIdProducto=campoProducto.getValue(campoProducto.getSelectedIndex());
    Integer idTipoProducto=!textoIdTipoProducto.isEmpty()?Integer.parseInt(textoIdTipoProducto):null;
    Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
    servlet.darLocalesMayorNumeroVentas(idTipoProducto,idProducto,tablaLocalesMayorNumeroVentas);
  }

}
