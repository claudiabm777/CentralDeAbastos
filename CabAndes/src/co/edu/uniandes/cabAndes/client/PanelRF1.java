package co.edu.uniandes.cabAndes.client;

import java.util.*;
import co.edu.uniandes.cabAndes.shared.vos.*;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.*;

public class PanelRF1 extends VerticalPanel implements ClickHandler
{

  public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  public ListBox campoProducto=new ListBox(false);
  public ListBox campoTipoProducto=new ListBox(false);
  public ListBox campoPresentacion=new ListBox(false);
  public DateBox campoFechaMinimaExpiracion=new DateBox();
  public DateBox campoFechaMaximaExpiracion=new DateBox();
  public ListBox campoLugarAlmacenamiento=new ListBox(false);
  public Button botonConsultar=new Button("Consultar las existencias de productos en CabAndes",this);
  public Tabla<ProductoLugarAlmacValue> tablaProductosLugarAlmacenamiento=new Tabla<ProductoLugarAlmacValue>("EXISTENCIAS DE LOS PRODUCTOS CON LOS CRITERIOS",new ModeloTablaProductosLugarAlmacenamiento(),400);

  public PanelRF1()
  {
    campoFechaMinimaExpiracion.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    campoFechaMaximaExpiracion.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
    FlexTable formulario=new FlexTable();
    formulario.setWidget(0,0,new Label("Producto:"));
    formulario.setWidget(0,1,campoProducto);
    formulario.setWidget(1,0,new Label("Tipo de producto:"));
    formulario.setWidget(1,1,campoTipoProducto);
    formulario.setWidget(2,0,new Label("Presentación:"));
    formulario.setWidget(2,1,campoPresentacion);
    formulario.setWidget(0,2,new Label("Lugar de almacenamiento:"));
    formulario.setWidget(0,3,campoLugarAlmacenamiento);
    formulario.setWidget(1,2,new Label("Fecha mínima de expiración:"));
    formulario.setWidget(1,3,campoFechaMinimaExpiracion);
    formulario.setWidget(2,2,new Label("Fecha máxima de expiración:"));
    formulario.setWidget(2,3,campoFechaMaximaExpiracion);
    super.add(formulario);
    super.add(botonConsultar);
    super.add(tablaProductosLugarAlmacenamiento);
  }

  @Override
  public void onClick(ClickEvent event)
  {
    String textoIdProducto=campoProducto.getValue(campoProducto.getSelectedIndex());
    String textoIdTipoProducto=campoTipoProducto.getValue(campoTipoProducto.getSelectedIndex());
    String textoIdPresentacion=campoPresentacion.getValue(campoPresentacion.getSelectedIndex());
    String textoIdLugarAlmacenamiento=campoLugarAlmacenamiento.getValue(campoLugarAlmacenamiento.getSelectedIndex());
    Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
    Integer idTipoProducto=!textoIdTipoProducto.isEmpty()?Integer.parseInt(textoIdTipoProducto):null;
    Integer idPresentacion=!textoIdPresentacion.isEmpty()?Integer.parseInt(textoIdPresentacion):null;
    Date fechaMinimaExpiracion=campoFechaMinimaExpiracion.getValue();
    Date fechaMaximaExpiracion=campoFechaMaximaExpiracion.getValue();
    Integer idLugarAlmacenamiento=!textoIdLugarAlmacenamiento.isEmpty()?Integer.parseInt(textoIdLugarAlmacenamiento):null;
    servlet.darExistenciasProductos(idProducto,idTipoProducto,idPresentacion,fechaMinimaExpiracion,fechaMaximaExpiracion,idLugarAlmacenamiento,tablaProductosLugarAlmacenamiento);
  }

}
