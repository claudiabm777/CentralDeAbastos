package co.edu.uniandes.cabAndes.client;

import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF6 extends VerticalPanel implements ClickHandler
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public DateBox campoFechaMinimaMovimiento=new DateBox();
  public DateBox campoFechaMaximaMovimiento=new DateBox();
  public Button botonConsultarProductosMayorMovimiento=new Button("Consultar productos con mayor movimiento en el sistema (los más pedidos y los más vendidos)",this);
  public Tabla<ProductoMayorMovimientoValue> tablaProductosMayorMovimiento=new Tabla<ProductoMayorMovimientoValue>("PRODUCTOS CON MAYOR MOVIMIENTO EN EL SISTEMA (LOS MÁS PEDIDOS Y LOS MÁS VENDIDOS)",new ModeloTablaProductosMayorMovimiento(),200);

  public PanelRF6()
  {
    campoFechaMinimaMovimiento.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    campoFechaMaximaMovimiento.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Fecha mínima:"));
    formulario.setWidget(0,1,campoFechaMinimaMovimiento);
    formulario.setWidget(1,0,new Label("Fecha máxima:"));
    formulario.setWidget(1,1,campoFechaMaximaMovimiento);
    super.add(formulario);
    super.add(botonConsultarProductosMayorMovimiento);
    super.add(tablaProductosMayorMovimiento);
  }

  @Override
  public void onClick(ClickEvent event)
  {
    Date fechaMinimaMovimiento=campoFechaMinimaMovimiento.getValue();
    Date fechaMaximaMovimiento=campoFechaMaximaMovimiento.getValue();
    servlet.darProductosMayorMovimiento(fechaMinimaMovimiento,fechaMaximaMovimiento,tablaProductosMayorMovimiento);
  }

}
