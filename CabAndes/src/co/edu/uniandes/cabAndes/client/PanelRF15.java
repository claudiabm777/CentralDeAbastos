package co.edu.uniandes.cabAndes.client;

import java.util.Date;

import co.edu.uniandes.cabAndes.shared.vos.MovimientoValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class PanelRF15 extends VerticalPanel implements ClickHandler
{
	

	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public DateBox campoFechaMinima=new DateBox();
	public DateBox campoFechaMaxima=new DateBox();
	public ListBox campoProducto=new ListBox(false);
	public ListBox campoPresentacion=new ListBox(false);
	public ListBox campoBodegaOrigen=new ListBox(false);
	public ListBox campoLugarAlmacDestino=new ListBox(false);
	public IntegerBox campoCantidad=new IntegerBox();
	public Button botonConsultar=new Button("Consultar movimientos",this);
	public Tabla<MovimientoValue> tablaMovimiento=new Tabla<MovimientoValue>("MOVIMIENTOS SEGUN LOS CRITERIOS",new ModeloTablaMovimiento(),400);
	
	public PanelRF15()
	  {
	    campoFechaMinima.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
	    campoFechaMaxima.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
	    FlexTable formulario=new FlexTable();
	    formulario.setWidget(0,0,new Label("Producto:"));
	    formulario.setWidget(0,1,campoProducto);
	    formulario.setWidget(1,0,new Label("Presentación:"));
	    formulario.setWidget(1,1,campoPresentacion);
	    formulario.setWidget(2,0,new Label("Bodega de origen:"));
	    formulario.setWidget(2,1,campoBodegaOrigen);
	    formulario.setWidget(0,2,new Label("Lugar de almacenamiento destino:"));
	    formulario.setWidget(0,3,campoLugarAlmacDestino);
	    formulario.setWidget(1,2,new Label("Cantidad:"));
	    formulario.setWidget(1,3,campoCantidad);
	    formulario.setWidget(3,0,new Label("Fecha minima:"));
	    formulario.setWidget(3,1,campoFechaMinima);
	    formulario.setWidget(3,2,new Label("Fecha máxima:"));
	    formulario.setWidget(3,3,campoFechaMaxima);
	    super.add(formulario);
	    super.add(botonConsultar);
	    super.add(tablaMovimiento);
	  }

	  @Override
	  public void onClick(ClickEvent event)
	  {System.out.println(" 1111 req15 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		  
	    String textoIdProducto=campoProducto.getValue(campoProducto.getSelectedIndex());
	    String textoIdPresentacion=campoPresentacion.getValue(campoPresentacion.getSelectedIndex());
	    String textoIdBodegaOrigen=campoBodegaOrigen.getValue(campoBodegaOrigen.getSelectedIndex());
	    String textoIdLugarAlmDest=campoLugarAlmacDestino.getValue(campoLugarAlmacDestino.getSelectedIndex());
	    Integer idProducto=!textoIdProducto.isEmpty()?Integer.parseInt(textoIdProducto):null;
	    Integer idBodegaOrigen=!textoIdBodegaOrigen.isEmpty()?Integer.parseInt(textoIdBodegaOrigen):null;
	    Integer idPresentacion=!textoIdPresentacion.isEmpty()?Integer.parseInt(textoIdPresentacion):null;
	    Integer idLugarDestino=!textoIdLugarAlmDest.isEmpty()?Integer.parseInt(textoIdLugarAlmDest):null;
	    Integer cantidad=campoCantidad.getValue();
	    Date fechaMinima=campoFechaMinima.getValue();
	    Date fechaMaxima=campoFechaMaxima.getValue();
	    System.out.println(" 2222 req15 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    servlet.darMovmientosNo(cantidad,idProducto,idPresentacion,idBodegaOrigen,idLugarDestino,fechaMinima,fechaMaxima,tablaMovimiento);
	  }

}
