package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.OfertaProveedorValue;
import co.edu.uniandes.cabAndes.shared.vos.UsuarioValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelRF9 extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{
	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public IntegerBox campoID = new IntegerBox();
	public ListBox campoTipoProducto=new ListBox(false);
	public TextBox campoNombre = new TextBox();
	public DoubleBox campoCapacidad = new DoubleBox();
	public Button botonRegistrar=new Button("Registrar nueva bodega",this);
	public Tabla<BodegaValue> tablaBodegas=new Tabla<BodegaValue>("BODEGAS",new ModeloTablaBodegas(),200);
	public Button botonHabilitarBodega=new Button("Poner en funcionamiento bodega seleccionada",this);
	
	
	public PanelRF9 ()
	{
		FlexTable formulario=new FlexTable();
		formulario.setWidget(0,0,new Label("Ingrese ID de la Bodega:"));
		formulario.setWidget(0,1,campoID);
		formulario.setWidget(1,0,new Label("Tipo de producto de la bodega:"));
		formulario.setWidget(1,1,campoTipoProducto);
		formulario.setWidget(2,0,new Label("Nombre de la bodega:"));
		formulario.setWidget(2,1,campoNombre);
		formulario.setWidget(3,0,new Label("Capacidad de la Bodega:"));
		formulario.setWidget(3,1,campoCapacidad);
	    super.add(new HTML("<b>REGISTRAR UNA BODEGA NUEVA (NO EXISTENTE)</b>"));
		super.add(formulario);
		super.add(botonRegistrar);
		super.add(new HTML("  "));
		super.add(new HTML("  "));
		super.add(new HTML("<b>PONER EN FUNCIONAMIENTO UNA BODEGA QUE SE ENCONTRABA EN MANTENIMIENTO</b>"));
		super.add(new HTML("Seleccione la bodega que desea habilitar"));
		super.add(tablaBodegas);
		super.add(botonHabilitarBodega);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getSource()==botonHabilitarBodega)
		{
			BodegaValue bodega=tablaBodegas.darDatoSeleccionado();
			if (bodega==null)
			{
				Window.alert("Debe seleccionar una bodega de la tabla");
			}
			else
			{
				servlet.ponerBodegaEnFuncionamiento(bodega.id,this);
			}
		}
		else
		{
			Integer textoIdPBodega=campoID.getValue();
			String stextoTipoProducto=campoTipoProducto.getValue(campoTipoProducto.getSelectedIndex());
			String stextoNombre=campoNombre.getValue();
			Double textoCapacidad=campoCapacidad.getValue();

			Integer textoTipoProducto=!stextoTipoProducto.isEmpty()? Integer.parseInt(campoTipoProducto.getValue(campoTipoProducto.getSelectedIndex())):null;
			String textoNombre=!stextoNombre.isEmpty()?campoNombre.getValue():null;
			servlet.registrarBodega(textoIdPBodega,textoTipoProducto,textoNombre,textoCapacidad,this);
		}
	}

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		 Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {
		// TODO Auto-generated method stub
		
		 Window.alert("Se registro la nueva Bodega exitosamente");
		
	}

}
