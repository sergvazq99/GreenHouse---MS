package Presentacion.Controller.Command;

import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.AltaEmpleadoDeCajaCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.BajaEmpleadoDeCajaCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.ListarEmpleadoDeCajaCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.ListarEmpleadoDeCajaPorNombreCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.ListarEmpleadoDeCajaPorTurnoCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.ModificarEmpleadoDeCajaCommand;
import Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA.MostrarEmpleadoDeCajaPorIdCommand;
import Presentacion.Controller.Command.CommandEntrada.CommandAltaEntrada;
import Presentacion.Controller.Command.CommandEntrada.CommandBajaEntrada;
import Presentacion.Controller.Command.CommandEntrada.CommandListarEntrada;
import Presentacion.Controller.Command.CommandEntrada.CommandListarEntradasPorInvernadero;
import Presentacion.Controller.Command.CommandEntrada.CommandModificarEntrada;
import Presentacion.Controller.Command.CommandEntrada.CommandMostrarEntrada;
import Presentacion.Controller.Command.CommandFabricante.CommandAltaFabricante;
import Presentacion.Controller.Command.CommandFabricante.CommandGUIBajaFabricante;
import Presentacion.Controller.Command.CommandFabricante.CommandListarFabricantes;
import Presentacion.Controller.Command.CommandFabricante.CommandListarFabricantesExtranjeros;
import Presentacion.Controller.Command.CommandFabricante.CommandListarFabricantesLocales;
import Presentacion.Controller.Command.CommandFabricante.CommandListarInformacionFabricanteDeSistemaDeRiegoDeUnInvernadero;
import Presentacion.Controller.Command.CommandFabricante.CommandModificarFabricante;
import Presentacion.Controller.Command.CommandFabricante.CommandMostrarFabricantePorId;
import Presentacion.Controller.Command.CommandFactura.AbrirFacturaCommand;
import Presentacion.Controller.Command.CommandFactura.CerrarFacturaCommand;
import Presentacion.Controller.Command.CommandFactura.DevolverFacturaCommand;
import Presentacion.Controller.Command.CommandFactura.ListarFacturasCommand;
import Presentacion.Controller.Command.CommandFactura.ModificarFacturaCommand;
import Presentacion.Controller.Command.CommandFactura.MostrarFacturaPorIDCommand;
import Presentacion.Controller.Command.CommandInvernadero.CommandAltaInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandBajaInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandCalcularLas3FechasMasVendidasDeUnInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandDesvincularSRInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandListarInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandListarInvernaderoPorSR;
import Presentacion.Controller.Command.CommandInvernadero.CommandModificarInvernadero;
import Presentacion.Controller.Command.CommandInvernadero.CommandMostrarInvernaderoPorID;
import Presentacion.Controller.Command.CommandInvernadero.CommandVincularSRInvernadero;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandAltaMarca;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandBajaMarca;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandListarMarcas;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandListarMarcasPorProveedor;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandModificarMarca;
import Presentacion.Controller.Command.CommandMarcaJPA.CommandMostrarMarcaPorId;
import Presentacion.Controller.Command.CommandPlanta.CommandAltaPlanta;
import Presentacion.Controller.Command.CommandPlanta.CommandBajaPlanta;
import Presentacion.Controller.Command.CommandPlanta.CommandListarPlanta;
import Presentacion.Controller.Command.CommandPlanta.CommandListarPlantasPorInvernadero;
import Presentacion.Controller.Command.CommandPlanta.CommandListarPlantasPorTipo;
import Presentacion.Controller.Command.CommandPlanta.CommandModificarPlanta;
import Presentacion.Controller.Command.CommandPlanta.CommandMostarPlantaPorId;
import Presentacion.Controller.Command.CommandProductoJPA.CommandAltaProducto;
import Presentacion.Controller.Command.CommandProductoJPA.CommandBajaProducto;
import Presentacion.Controller.Command.CommandProductoJPA.CommandListarProducto;
import Presentacion.Controller.Command.CommandProductoJPA.CommandListarProductoPorMarca;
import Presentacion.Controller.Command.CommandProductoJPA.CommandListarProductoPorTipo;
import Presentacion.Controller.Command.CommandProductoJPA.CommandListarProductoPorVenta;
import Presentacion.Controller.Command.CommandProductoJPA.CommandModificarProducto;
import Presentacion.Controller.Command.CommandProductoJPA.CommandMostrarProductoPorId;
import Presentacion.Controller.Command.CommandProveedorJPA.altaProveedorCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.bajaProveedorCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.desvincularMarcaCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.listarProveedoresCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.listarProveedoresDeMarcaCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.modificarProveedorCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.mostarProveedorPorIdCommand;
import Presentacion.Controller.Command.CommandProveedorJPA.vincularMarcaCommand;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandAltaSistemaDeRiego;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandBajaSistemaDeRiego;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandModificarSistemaDeRiego;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandMostrarSistemaDeRiegoDelInvernadero;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandMostrarSistemaDeRiegoPorFabricante;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandMostrarSistemasDeRiego;
import Presentacion.Controller.Command.CommandSistemaDeRiego.CommandMostrarSistemasDeRiegoPorId;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandAltaTurno;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandBajaTurno;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandListarTurnos;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandModificarTurno;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandMostrarTurno;
import Presentacion.Controller.Command.CommandTurnoJPA.CommandObtenerNominaDelTurno;
import Presentacion.Controller.Command.CommandVentasJPA.AbrirVentaCommand;
import Presentacion.Controller.Command.CommandVentasJPA.CerrarVentaCommand;
import Presentacion.Controller.Command.CommandVentasJPA.DevolverVentaCommand;
import Presentacion.Controller.Command.CommandVentasJPA.ListarVentaCommand;
import Presentacion.Controller.Command.CommandVentasJPA.ModificarVentaCommand;
import Presentacion.Controller.Command.CommandVentasJPA.MostrarVentaPorIdCommand;
import Presentacion.Controller.Command.CommandVentasJPA.VentasPorEmpleadoDeCajaCommand;
import Presentacion.FactoriaVistas.Evento;

public class CommandFactoryImp extends CommandFactory {

	public Command getCommand(Integer event) {
		switch (event) {
		// Eventos de Factura
		case Evento.ABRIR_FACTURA:
			return new AbrirFacturaCommand();
		case Evento.CERRAR_FACTURA:
			return new CerrarFacturaCommand();
		case Evento.MODIFICAR_FACTURA:
			return new ModificarFacturaCommand();
		case Evento.MOSTRAR_FACTURA_POR_ID:
			return new MostrarFacturaPorIDCommand();
		case Evento.LISTAR_FACTURAS:
			return new ListarFacturasCommand();
		case Evento.DEVOLVER_FACTURA:
			return new DevolverFacturaCommand();

		// Eventos de Entrada
		case Evento.ALTA_ENTRADA:
			return new CommandAltaEntrada();
		case Evento.BAJA_ENTRADA:
			return new CommandBajaEntrada();
		case Evento.MODIFICAR_ENTRADA:
			return new CommandModificarEntrada();
		case Evento.MOSTRAR_ENTRADA_POR_ID:
			return new CommandMostrarEntrada();
		case Evento.LISTAR_ENTRADAS:
			return new CommandListarEntrada();
		case Evento.LISTAR_ENTRADAS_POR_INVERNADERO:
			return new CommandListarEntradasPorInvernadero();

		// Eventos de Fabricante
		case Evento.ALTA_FABRICANTE:
			return new CommandAltaFabricante();
		case Evento.BAJA_FABRICANTE:
			return new CommandGUIBajaFabricante();
		case Evento.MODIFICAR_FABRICANTE:
			return new CommandModificarFabricante();
		case Evento.LISTAR_FABRICANTES:
			return new CommandListarFabricantes();
		case Evento.MOSTRAR_FABRICANTE_POR_ID:
			return new CommandMostrarFabricantePorId();
		//		case Evento.LISTAR_FABRICANTES_POR_NOMBRE:
		//		    return new CommandListarFabricantePorNombre();
		case Evento.LISTAR_FABRICANTES_LOCALES:
			return new CommandListarFabricantesLocales();
		case Evento.LISTAR_FABRICANTES_EXTRANJEROS:
			return new CommandListarFabricantesExtranjeros();
		case Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO:
			return new CommandListarInformacionFabricanteDeSistemaDeRiegoDeUnInvernadero();

		// Eventos de Invernadero
		case Evento.ALTA_INVERNADERO:
			return new CommandAltaInvernadero();
		case Evento.BAJA_INVERNADERO:
			return new CommandBajaInvernadero();
		case Evento.MODIFICAR_INVERNADERO:
			return new CommandModificarInvernadero();
		case Evento.LISTAR_INVERNADEROS:
			return new CommandListarInvernadero();
		case Evento.MOSTRAR_INVERNADERO_POR_ID:
			return new CommandMostrarInvernaderoPorID();
		case Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO:
			return new CommandListarInvernaderoPorSR();
		case Evento.VINCULAR_SISTEMA_RIEGO_A_INVERNADERO:
			return new CommandVincularSRInvernadero();
		case Evento.DESVINCULAR_SISTEMA_RIEGO_DE_INVERNADERO:
			return new CommandDesvincularSRInvernadero();
		case Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO:
			return new CommandCalcularLas3FechasMasVendidasDeUnInvernadero();

		// Eventos de Planta
		case Evento.ALTA_PLANTA:
			return new CommandAltaPlanta();
		case Evento.BAJA_PLANTA:
			return new CommandBajaPlanta();
		case Evento.MODIFICAR_PLANTA:
			return new CommandModificarPlanta();
		case Evento.LISTAR_PLANTAS:
			return new CommandListarPlanta();
		case Evento.MOSTRAR_PLANTA_POR_ID:
			return new CommandMostarPlantaPorId();
		case Evento.LISTAR_PLANTAS_POR_TIPO:
			return new CommandListarPlantasPorTipo();
		case Evento.LISTAR_PLANTAS_DE_INVERNADERO:
			return new CommandListarPlantasPorInvernadero();

		// Eventos de Sistema de Riego
		case Evento.ALTA_SISTEMA_RIEGO:
			return new CommandAltaSistemaDeRiego();
		case Evento.BAJA_SISTEMA_RIEGO:
			return new CommandBajaSistemaDeRiego();
		case Evento.MODIFICAR_SISTEMA_RIEGO:
			return new CommandModificarSistemaDeRiego();
		case Evento.MOSTRAR_SISTEMA_RIEGO_POR_ID:
			return new CommandMostrarSistemasDeRiegoPorId();
		case Evento.LISTAR_SISTEMAS_RIEGO_DE_INVERNADERO:
			return new CommandMostrarSistemaDeRiegoDelInvernadero();
		case Evento.LISTAR_SISTEMAS_RIEGO_POR_FABRICANTE:
			return new CommandMostrarSistemaDeRiegoPorFabricante();
		case Evento.LISTAR_SISTEMAS_RIEGO:
			return new CommandMostrarSistemasDeRiego();
		
		// Eventos de Turno
		case Evento.ALTA_TURNO:
			return new CommandAltaTurno();
		case Evento.BAJA_TURNO:
			return new CommandBajaTurno();
		case Evento.MODIFICAR_TURNO:
			return new CommandModificarTurno();
		case Evento.MOSTRAR_TURNO:
			return new CommandMostrarTurno();
		case Evento.LISTAR_TURNO:
			return new CommandListarTurnos();
		case Evento.OBTENER_NOMINA_DE_TURNO:
			return new CommandObtenerNominaDelTurno();
			
		// Eventos Venta
		case Evento.ABRIR_VENTA:
			return new AbrirVentaCommand();
		case Evento.CERRAR_VENTA:
			return new CerrarVentaCommand();
		case Evento.LISTAR_VENTAS:
			return new ListarVentaCommand();
		case Evento.VENTAS_POR_EMPLEADO_DE_CAJA:
			return new VentasPorEmpleadoDeCajaCommand();
		case Evento.MOSTRAR_VENTA_POR_ID:
			return new MostrarVentaPorIdCommand();
		case Evento.MODIFICAR_VENTAS:
			return new ModificarVentaCommand();
		case Evento.DEVOLVER_VENTA:
			return new DevolverVentaCommand();
			
		// Eventos de Marca JPA
		case Evento.ALTA_MARCA:
			return new CommandAltaMarca();
		case Evento.BAJA_MARCA:
			return new CommandBajaMarca();
		case Evento.MODIFICAR_MARCA:
			return new CommandModificarMarca();
		case Evento.MOSTRAR_MARCA:
			return new CommandMostrarMarcaPorId();
		case Evento.LISTAR_MARCAS:
			return new CommandListarMarcas();
		case Evento.LISTAR_MARCAS_POR_PROVEEDOR:
			return new CommandListarMarcasPorProveedor();	
			
			
			// Eventos de Proveedor JPA
		case Evento.ALTA_PROVEEDOR:
			return new altaProveedorCommand();
		case Evento.BAJA_PROVEEDOR:
			return new bajaProveedorCommand();
		case Evento.MODIFICAR_PROVEEDORES:
			return new modificarProveedorCommand();
		case Evento.LISTAR_PROVEEDORES:
			return new listarProveedoresCommand();
		case Evento.MOSTRAR_PROVEEDORES_POR_ID:
			return new mostarProveedorPorIdCommand();
		case Evento.LISTAR_PROVEEDORES_DE_MARCA:
			return new listarProveedoresDeMarcaCommand();
		case Evento.VINCULAR_MARCA_PROVEEDOR:
			return new vincularMarcaCommand();
		case Evento.DESVINCULAR_MARCA_PROVEEDOR:
			return new desvincularMarcaCommand();
			
			//PRODUCTO
			
		case Evento.ALTA_PRODUCTO:
			return new CommandAltaProducto();
		case Evento.BAJA_PRODUCTO:
			return new CommandBajaProducto();
		case Evento.LISTAR_PRODUCTOS:
			return new CommandListarProducto();
		case Evento.MOSTRAR_PRODUCTO_POR_ID:
			return new CommandMostrarProductoPorId();
		case Evento.LISTAR_PRODUCTOS_POR_MARCA:
			return new CommandListarProductoPorMarca();
		case Evento.LISTAR_PRODUCTOS_POR_TIPO:
			return new CommandListarProductoPorTipo();
		case Evento.LISTAR_PRODUCTOS_POR_VENTA:
			return new CommandListarProductoPorVenta();
		case Evento.MODIFICAR_PRODUCTO:
			return new CommandModificarProducto();

			
			
			// Eventos de Empleado De Caja JPA
		case Evento.ALTA_EMPLEADO_DE_CAJA:
			return new AltaEmpleadoDeCajaCommand();
		case Evento.BAJA_EMPLEADO_DE_CAJA:
			return new BajaEmpleadoDeCajaCommand();
		case Evento.MODIFICAR_EMPLEADO_DE_CAJA:
			return new ModificarEmpleadoDeCajaCommand();
		case Evento.LISTAR_EMPLEADOS_DE_CAJA:
			return new ListarEmpleadoDeCajaCommand();
		case Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID:
			return new MostrarEmpleadoDeCajaPorIdCommand();
		case Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_TURNO:
			return new ListarEmpleadoDeCajaPorTurnoCommand();
		case Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE:
			return new ListarEmpleadoDeCajaPorNombreCommand();
		

		}
		return null;
	}
}