package co.edu.uniandes.cabAndes.client;

import co.edu.uniandes.cabAndes.shared.vos.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class CabAndes implements EntryPoint,AsyncCallback<DatosBasicosValue>
{

  public static DatosBasicosValue datosBasicos=null;

  private CabAndesServletAsync servlet=GWT.create(CabAndesServlet.class);
  private Tabla<UsuarioValue> tablaUsuarios=new Tabla<UsuarioValue>("USUARIOS",new ModeloTablaUsuarios(),400);
  private Tabla<TipoProductoValue> tablaTiposProducto=new Tabla<TipoProductoValue>("TIPOS DE PRODUCTO",new ModeloTablaTiposProducto(),400);
  private Tabla<ProductoValue> tablaProductos=new Tabla<ProductoValue>("PRODUCTOS",new ModeloTablaProductos(),400);
  private Tabla<PresentacionValue> tablaPresentaciones=new Tabla<PresentacionValue>("PRESENTACIONES DE LOS PRODUCTOS",new ModeloTablaPresentaciones(),400);
  private Tabla<LocalValue> tablaLocales=new Tabla<LocalValue>("LOCALES",new ModeloTablaLocales(),400);
  private Tabla<ProveedorValue> tablaProveedores=new Tabla<ProveedorValue>("PROVEEDORES",new ModeloTablaProveedores(),400);
  private PanelRF1 panelRF1=new PanelRF1();
  private PanelRF2 panelRF2=new PanelRF2();
  private PanelRF3a panelRF3a=new PanelRF3a();
  private PanelRF3b panelRF3b=new PanelRF3b();
  private PanelRF3c panelRF3c=new PanelRF3c();
  private PanelRF3d panelRF3d=new PanelRF3d();
  private PanelRF3e panelRF3e=new PanelRF3e();
  private PanelRF4a panelRF4a=new PanelRF4a();
  private PanelRF4b panelRF4b=new PanelRF4b();
  private PanelRF5 panelRF5=new PanelRF5();
  private PanelRF6 panelRF6=new PanelRF6();
  private PanelRF7 panelRF7=new PanelRF7();
  private PanelRF9 panelRF9=new PanelRF9();
  private PanelRF10 panelRF10=new PanelRF10();
  private PanelRF11 panelRF11=new PanelRF11();
  private PanelRF8 panelRF8=new PanelRF8();
  private PanelRF12 panelRF12=new PanelRF12();
  private PanelRF13 panelRF13=new PanelRF13();
  private PanelRF14 panelRF14=new PanelRF14();
  private PanelRF15 panelRF15=new PanelRF15();

  @Override
  public void onModuleLoad()
  {
    TabPanel panelDatosBasicos=new TabPanel();
    panelDatosBasicos.add(tablaUsuarios,"Usuarios");
    panelDatosBasicos.add(tablaTiposProducto,"Tipos de producto");
    panelDatosBasicos.add(tablaProductos,"Productos");
    panelDatosBasicos.add(tablaPresentaciones,"Presentaciones");
    panelDatosBasicos.add(panelRF10,"RF10-Bodegas");
    panelDatosBasicos.add(tablaLocales,"Locales");
    panelDatosBasicos.add(panelRF11,"RF11-Proveedores");
    panelDatosBasicos.setWidth("100%");
    panelDatosBasicos.selectTab(0);
    TabPanel panelPrincipal=new TabPanel();
    panelPrincipal.add(panelDatosBasicos,"Datos básicos");
    panelPrincipal.add(panelRF1,"RF1");
    panelPrincipal.add(panelRF2,"RF2");
    panelPrincipal.add(panelRF3a,"RF3a");
    panelPrincipal.add(panelRF3b,"RF3b");
    panelPrincipal.add(panelRF3c,"RF3c");
    panelPrincipal.add(panelRF3d,"RF3d");
    panelPrincipal.add(panelRF3e,"RF3e");
    panelPrincipal.add(panelRF4a,"RF23a");
    panelPrincipal.add(panelRF4b,"RF23b");
    panelPrincipal.add(panelRF5,"RF5");
    panelPrincipal.add(panelRF6,"RF26");
    panelPrincipal.add(panelRF7,"RF7");
    panelPrincipal.add(panelRF8,"RF24");
    panelPrincipal.add(panelRF9,"RF9");
    panelPrincipal.add(panelRF12,"RF12");
    panelPrincipal.add(panelRF13,"RF13");
    panelPrincipal.add(panelRF14,"RF25");
    panelPrincipal.add(panelRF15,"RF15");
    panelPrincipal.setWidth("900px");
    panelPrincipal.setHeight("500px");
    panelPrincipal.selectTab(0);
    RootPanel.get("contenido").add(panelPrincipal);
    servlet.darDatosBasicos(this);
  }

  @Override
  public void onSuccess(DatosBasicosValue result)
  {
    datosBasicos=result;
    tablaUsuarios.cambiarDatos(datosBasicos.usuarios.values());
    tablaTiposProducto.cambiarDatos(datosBasicos.tiposProducto.values());
    tablaProductos.cambiarDatos(datosBasicos.productos.values());
    tablaPresentaciones.cambiarDatos(datosBasicos.presentaciones.values());
    
    panelRF10.tablaBodegas.cambiarDatos(datosBasicos.bodegas.values());
    panelRF9.tablaBodegas.cambiarDatos(datosBasicos.bodegasMantenimiento.values());
    panelRF8.tablaBodegas.cambiarDatos(datosBasicos.bodegasFuncionamiento.values());
    tablaLocales.cambiarDatos(datosBasicos.locales.values());
    panelRF11.tablaProveedores.cambiarDatos(datosBasicos.provUsuarios.values());
    panelRF8.campoMotivoCierre.addItem("En ampliacion", "1");
    panelRF8.campoMotivoCierre.addItem("En mantenimiento", "2");
    // Llenar campos de selección de tipo de producto
    panelRF1.campoTipoProducto.addItem("Todos los tipos de producto","");
    panelRF7.campoTipoProducto.addItem("Todos los tipos de producto","");
    panelRF9.campoTipoProducto.addItem("Todos los tipos de producto","");
    for (TipoProductoValue tipoProducto:datosBasicos.tiposProducto.values())
    {
      panelRF1.campoTipoProducto.addItem(tipoProducto.nombre,""+tipoProducto.id);
      panelRF7.campoTipoProducto.addItem(tipoProducto.nombre,""+tipoProducto.id);
      panelRF9.campoTipoProducto.addItem(tipoProducto.nombre,""+tipoProducto.id);
      panelRF13.campoTipoProducto.addItem(tipoProducto.nombre,""+tipoProducto.id);
    }
    // Llenar campos de selección de producto
    panelRF1.campoProducto.addItem("Todos los productos","");
    panelRF2.campoProducto.addItem("Todos los productos","");
    panelRF3c.campoProducto.addItem("Seleccione un producto","");
    panelRF7.campoProducto.addItem("Todos los productos","");
    panelRF14.campoProducto.addItem("Todos los productos","");
    panelRF15.campoProducto.addItem("Todos los productos","");
    for (ListBox campoProducto:panelRF4a.campoProducto)
    {
      campoProducto.addItem("Seleccione un producto","");
    }
    for (ProductoValue producto:datosBasicos.productos.values())
    {
      panelRF1.campoProducto.addItem(producto.nombre,""+producto.id);
      panelRF2.campoProducto.addItem(producto.nombre,""+producto.id);
      panelRF3c.campoProducto.addItem(producto.nombre,""+producto.id);
      panelRF7.campoProducto.addItem(producto.nombre,""+producto.id);
      panelRF14.campoProducto.addItem(producto.nombre,""+producto.id);
      panelRF15.campoProducto.addItem(producto.nombre,""+producto.id);
      for (ListBox campoProducto:panelRF4a.campoProducto)
      {
        campoProducto.addItem(producto.nombre,""+producto.id);
      }
    }
    // Llenar campos de selección de presentación
    panelRF1.campoPresentacion.addItem("Todas las presentaciones","");
    panelRF2.campoPresentacion.addItem("Todas las presentaciones","");
    panelRF3c.campoPresentacion.addItem("Seleccione una presentación","");
    panelRF14.campoPresentacion.addItem("Todas las presentaciones","");
    panelRF15.campoPresentacion.addItem("Todas las presentaciones","");
    for (ListBox campoPresentacion:panelRF4a.campoPresentacion)
    {
      campoPresentacion.addItem("Seleccione una presentación","");
    }
    for (PresentacionValue presentacion:datosBasicos.presentaciones.values())
    {
      panelRF1.campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      panelRF2.campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      panelRF3c.campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      panelRF14.campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      panelRF15.campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      for (ListBox campoPresentacion:panelRF4a.campoPresentacion)
      {
        campoPresentacion.addItem(presentacion.nombre,""+presentacion.id);
      }
    }
    // Llenar campos de selección de lugar de almacenamiento
    panelRF1.campoLugarAlmacenamiento.addItem("Todos los lugares de almacenamiento","");
    panelRF14.campoLugarAlmacDestino.addItem("Todos los lugares de almacenamiento","");
    panelRF15.campoLugarAlmacDestino.addItem("Todos los lugares de almacenamiento","");
    for (LugarAlmacenamientoValue lugarAlmacenamiento:datosBasicos.lugaresAlmacenamiento.values())
    {
      panelRF1.campoLugarAlmacenamiento.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
      panelRF14.campoLugarAlmacDestino.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
      panelRF15.campoLugarAlmacDestino.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
    }
    // Llenar campos de selección de local
    panelRF3c.campoLocal.addItem("Seleccione un local","");
    for (LocalValue local:datosBasicos.locales.values())
    {
      LugarAlmacenamientoValue lugarAlmacenamiento=datosBasicos.lugaresAlmacenamiento.get(local.id);
      panelRF3c.campoLocal.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
    }
    
    //Llenar campos de seleccion de bodega
    panelRF14.campoBodegaOrigen.addItem("Seleccione una bodega de origen","");
    panelRF15.campoBodegaOrigen.addItem("Seleccione una bodega de origen","");
    for (BodegaValue bodega:datosBasicos.bodegas.values())
    {
      LugarAlmacenamientoValue lugarAlmacenamiento=datosBasicos.lugaresAlmacenamiento.get(bodega.id);
      panelRF14.campoBodegaOrigen.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
      panelRF15.campoBodegaOrigen.addItem(lugarAlmacenamiento.nombre,""+lugarAlmacenamiento.id);
    }
  }

  @Override
  public void onFailure(Throwable caught)
  {
    Window.alert(caught.getMessage());
  }

}
