package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelRF8 extends VerticalPanel implements ClickHandler,AsyncCallback<Void>
{
	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public Tabla<BodegaValue> tablaBodegas=new Tabla<BodegaValue>("BODEGAS",new ModeloTablaBodegas(),200);
	public ListBox campoMotivoCierre=new ListBox(false);
	public Button botonCerrar=new Button("Cerrar bodega seleccionada",this);
	public PanelRF8 ()
	{
		
		FlexTable formulario=new FlexTable();
		formulario.setWidget(0,0,new HTML("<b>Seleccione la bodega que desea cerrar de la siguiente tabla</b>"));
		formulario.setWidget(1,0, tablaBodegas);
		formulario.setWidget(2,0,new Label("   "));
		formulario.setWidget(3,0,new Label("Seleccione el motivo de cierre: "));
		formulario.setWidget(3,1,campoMotivoCierre);
		formulario.setWidget(4,0,new Label("   "));
		super.add(formulario);
		super.add(botonCerrar);
	}
	public void onClick(ClickEvent event) {
		
			BodegaValue bodega=tablaBodegas.darDatoSeleccionado();
			String scampoMotivoCierre=campoMotivoCierre.getValue(campoMotivoCierre.getSelectedIndex());
			Integer textoMotivoCierre=!scampoMotivoCierre.isEmpty()? Integer.parseInt(campoMotivoCierre.getValue(campoMotivoCierre.getSelectedIndex())):null;

			if (bodega==null)
			{
				Window.alert("Debe seleccionar una bodega de la tabla");
			}
			
			else if(textoMotivoCierre==null)
			{
				Window.alert("Debe seleccionar el motivo de cierre");
			}
			else
			{
				servlet.cerrarBodega(bodega.id,textoMotivoCierre, this);
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
		
		 Window.alert("Se cerro la nueva Bodega exitosamente");
		
	}
	
}
