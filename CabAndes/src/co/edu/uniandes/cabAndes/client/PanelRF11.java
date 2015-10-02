package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.BodegaValue;
import co.edu.uniandes.cabAndes.shared.vos.LicitacionValue;
import co.edu.uniandes.cabAndes.shared.vos.OfertaProveedorValue;
import co.edu.uniandes.cabAndes.shared.vos.ProductoLugarAlmacValue;
import co.edu.uniandes.cabAndes.shared.vos.ProveedorValue;
import co.edu.uniandes.cabAndes.shared.vos.UsuarioValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelRF11 extends VerticalPanel implements ClickHandler
{
	public CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
	public Tabla<UsuarioValue> tablaProveedores=new Tabla<UsuarioValue>("Proveedores",new ModeloTablaUsuarios(),200);
	public Button botonConsultarLicitacionesProveedores=new Button("Consultar las licitaciones del proveedor seleccionado",this);
	public Tabla<LicitacionValue> tablaLicitaciones=new Tabla<LicitacionValue>("LICITACIONES DEL PROVEEDOR SELECCIONADO",new ModeloTablaLicitaciones(),200);
	public Button botonConsultarOfertasProveedores=new Button("Consultar las ofertas del proveedor seleccionado",this);
	public Tabla<OfertaProveedorValue> tablaOfertas=new Tabla<OfertaProveedorValue>("OFERTAS DEL PROVEEDOR SELECCIONADO",new ModeloTablaOfertasProveedores(),200);
	
	public PanelRF11()
	  {
	    super.add(tablaProveedores);
	    super.add(botonConsultarLicitacionesProveedores);
	    super.add(tablaLicitaciones);
	    super.add(botonConsultarOfertasProveedores);
	    super.add(tablaOfertas);
	  }
	public void onClick(ClickEvent event) 
	{
		if (event.getSource()==botonConsultarLicitacionesProveedores)
		{
			UsuarioValue proveedor=tablaProveedores.darDatoSeleccionado();
			if (proveedor==null)
			{
				Window.alert("Debe seleccionar un proveedor de la tabla");
			}
			else
			{
				servlet.darLicitacionesProveedor(proveedor.direccion_electronica,tablaLicitaciones);
			}
		}
		else
		{
			UsuarioValue proveedor=tablaProveedores.darDatoSeleccionado();
			if (proveedor==null)
			{
				Window.alert("Debe seleccionar una bodega de la tabla");
			}
			else
			{
				servlet.darOfertasProveedor(proveedor.direccion_electronica,tablaOfertas);
			}
		}
		
	}

}
