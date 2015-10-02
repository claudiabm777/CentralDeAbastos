package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoLugarAlmacValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class PanelRF13 extends VerticalPanel implements ClickHandler
{
	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public DoubleBox campoPorcentajeCapacidad=new DoubleBox();
	public ListBox campoTipoProducto=new ListBox(false);
	public Tabla<BodegaValue> tablaBodegas=new Tabla<BodegaValue>("BODEGAS CON LOS CRITERIOS",new ModeloTablaBodegas(),400);
	public Button botonConsultar=new Button("Consultar las bodegas de acuerdo a las criterios",this);
	  

	 public PanelRF13()
	  {
	    FlexTable formulario=new FlexTable();
	    formulario.setWidget(0,0,new Label("Porcentaje de la capacidad deseado:"));
	    formulario.setWidget(0,1,campoPorcentajeCapacidad);
	    formulario.setWidget(0,2, new Label("%"));
	    formulario.setWidget(1,0,new Label("Tipo de producto:"));
	    formulario.setWidget(1,1,campoTipoProducto);
	    
	    super.add(formulario);
	    super.add(botonConsultar);
	    super.add(tablaBodegas);
	  }


	@Override
	public void onClick(ClickEvent event) 
	{
		Double porcentaje=campoPorcentajeCapacidad.getValue();
		String textoIdTipoProducto=campoTipoProducto.getValue(campoTipoProducto.getSelectedIndex());
		Integer idTipoProducto=!textoIdTipoProducto.isEmpty()?Integer.parseInt(textoIdTipoProducto):null;
		if(porcentaje==null)
		{
			Window.alert("Debe ingresar un porcentaje de capacidad");
		}
		
		else
		{
			servlet.darBodegasCapacidad(porcentaje,idTipoProducto,tablaBodegas);
		}
		  
	}

}
