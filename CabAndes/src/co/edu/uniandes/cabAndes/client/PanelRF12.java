package co.edu.uniandes.cabAndes.client;

import java.util.Date;

import co.edu.uniandes.cabAndes.shared.vos.PedidoValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class PanelRF12 extends VerticalPanel implements ClickHandler
{
	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public IntegerBox campoIdentificadorLocal=new IntegerBox();
	public Button botonConsultarPedidosVigentes=new Button("Consultar los pedidos vigentes del local",this);
	public DateBox campoFechaMinima=new DateBox();
	public DateBox campoFechaMaxima=new DateBox();
	public Tabla<PedidoValue> tablaPedidos=new Tabla<PedidoValue>("PEDIDOS DEL LOCAL EN EL RANGO DE FECHAS SELECCIONADO",new ModeloTablaPedidos(),200);
	
	 public PanelRF12()
	  {
	  
	    campoFechaMinima.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
	    campoFechaMaxima.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
	    FlexTable formulario=new FlexTable();
	    formulario.setWidget(0,0,new Label("ID del local del que requiere consultar los pedidos:"));
	    formulario.setWidget(0,1,campoIdentificadorLocal);
	    formulario.setWidget(0,2,new Label("Fecha mínima del rango:"));
	    formulario.setWidget(0,3,campoFechaMinima);
	    formulario.setWidget(1,0,new Label("Fecha máxima del rango:"));
	    formulario.setWidget(1,1,campoFechaMaxima);
	    
	    super.add(formulario);
	    super.add(botonConsultarPedidosVigentes);
	    super.add(tablaPedidos);
	  }
	 
	 public void onClick(ClickEvent event)
	  {
	    
	      Date fechaMinima=campoFechaMinima.getValue();
	      Date fechaMaxima=campoFechaMaxima.getValue();
	      Integer id=campoIdentificadorLocal.getValue();
	      
	      if (id==null)
	      {
	        Window.alert("Debe ingresar el ID de un local");
	      }
	      else
	      {
	        servlet.darPedidosVigentesLocales(id,fechaMinima,fechaMaxima,tablaPedidos);
	      }
	    
	  }

}
