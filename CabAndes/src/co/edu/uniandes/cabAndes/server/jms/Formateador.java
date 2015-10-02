package co.edu.uniandes.cabAndes.server.jms;

import java.text.SimpleDateFormat;

public class Formateador {

	  public static SimpleDateFormat FORMATO_FECHA=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	  public static java.util.Date leerFecha(String cadena)
	  {
		  try
		  {
		    return FORMATO_FECHA.parse(cadena);
		  }
		  catch (Throwable th)
		  {
			  return null;
		  }
	  }

	  public static Integer leerEntero(String cadena)
	  {
		  try
		  {
		    return Integer.parseInt(cadena);
		  }
		  catch (Throwable th)
		  {
			  return null;
		  }
	  }

	  public static Double leerFlotante(String cadena)
	  {
		  try
		  {
		    return Double.parseDouble(cadena.replace(',','.'));
		  }
		  catch (Throwable th)
		  {
			  return null;
		  }
	  }
	  
	  public static String formatearFecha(java.util.Date fecha)
	  {
		if (fecha==null)
		{
			return "null";
		}
		else
		{
			return FORMATO_FECHA.format(fecha);
		}
	  }

	  public static String formatearEntero(Integer numero)
	  {
		if (numero==null)
		{
			return "null";
		}
		else
		{
			return ""+numero;
		}
	  }

	  public static String formatearFlotante(Double numero)
	  {
		if (numero==null)
		{
			return "null";
		}
		else
		{
			return ""+numero;
		}
	  }

}
