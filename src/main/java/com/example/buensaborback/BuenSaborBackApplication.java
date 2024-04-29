package com.example.buensaborback;

import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.entities.enums.Estado;
import com.example.buensaborback.domain.entities.enums.FormaPago;
import com.example.buensaborback.domain.entities.enums.TipoEnvio;
import com.example.buensaborback.domain.entities.enums.TipoPromocion;
import com.example.buensaborback.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class BuenSaborBackApplication {
// Aca tiene que inyectar todos los repositorios
// Es por ello que deben crear el paquete reositorio

// Ejemplo  @Autowired
//	private ClienteRepository clienteRepository;

	private static final Logger logger = LoggerFactory.getLogger(BuenSaborBackApplication.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private SucursalRepository	sucursalRepository;

	@Autowired
	private DomicilioRepository domicilioRepository;

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ArticuloInsumoRepository articuloInsumoRepository;

	@Autowired
	private ArticuloManufacturadoRepository articuloManufacturadoRepository;

	@Autowired
	private ImagenRepository imagenRepository;

	@Autowired
	private PromocionRepository promocionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private  FacturaRepository facturaRepository;

	@Autowired
	private  PedidoRepository pedidoRepository;

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(BuenSaborBackApplication.class, args);
	}
//j

	@Bean
	CommandLineRunner init() {
		return args -> {
			Pais pais1 = Pais.builder().nombre("Argentina").build();

			//PROVINCIAS
			Provincia provincia1 = Provincia.builder().nombre("Mendoza").pais(pais1).build();
			Provincia provincia2 = Provincia.builder().nombre("Santa Fe").pais(pais1).build();

			//LOCALIDADES
			Localidad localidad1 = Localidad.builder().nombre("Las Heras").provincia(provincia1).build();
			Localidad localidad2 = Localidad.builder().nombre("Ciudad").provincia(provincia2).build();
//			Localidad localidad3 = Localidad.builder().nombre("Achiras").provincia(provincia2).build();
//			Localidad localidad4 = Localidad.builder().nombre("Agua de Oro").provincia(provincia2).build();
			localidadRepository.save(localidad1);
			localidadRepository.save(localidad2);
//			provincia1.getLocalidades().add(localidad1);
//			provincia1.getLocalidades().add(localidad2);
//
//			pais1.getProvincias().add(provincia1);
//			pais1.getProvincias().add(provincia2);

			// Crear 1 empresa
			// Crear 2 sucursales para esa empresa
			// crear los Domicilios para esas sucursales
			Empresa empresaGreen = Empresa.builder().nombre("Green").cuil(30503167).razonSocial("Venta de Alimentos").build();

			Sucursal sucursalHeras = Sucursal.builder().nombre("En las heras").horarioApertura(LocalTime.of(17,0)).horarioCierre(LocalTime.of(23,0)).build();
//			sucursalChacras.setEmpresa(empresaGreen);
			Sucursal sucursalCiudad = Sucursal.builder().nombre("En ciudad").horarioApertura(LocalTime.of(16,0)).horarioCierre(LocalTime.of(23,30)).build();
//			sucursalGodoyCruz.setEmpresa(empresaGreen);

			Domicilio domicilioHuarpes = Domicilio.builder().cp(5509).calle("Huaroes").numero(222).localidad(localidad1).build();

			Domicilio domicilioGaribaldi = Domicilio.builder().cp(5511).calle("Garibaldi").numero(281).localidad(localidad2).build();

			domicilioRepository.save(domicilioGaribaldi);
			domicilioRepository.save(domicilioHuarpes);
//			domicilioRepository.save(domicilioHuarpes);
//			domicilioRepository.save(domicilioGaribaldi);

//			var localidadGet1 = localidadRepository.findAll().get(0);
//			localidadGet1.getDomicilios().add(domicilioViamonte);

//			localidadRepository.save(localidadGet1);

//			domicilioRepository.save(domicilioGaribaldi);
//			domicilioRepository.save(domicilioHuarpes);
//			var localidadGet2 = localidadRepository.findAll().get(0).getDomicilios().add(domicilioGaribaldi);

			sucursalHeras.setDomicilio(domicilioHuarpes);
			sucursalCiudad.setDomicilio(domicilioGaribaldi);

			empresaGreen.getSucursales().add(sucursalHeras);
			empresaGreen.getSucursales().add(sucursalCiudad);
//			domicilioRepository.save(domicilioViamonte);
//			domicilioRepository.save(domicilioSanMartin);
//			sucursalRepository.save(sucursalChacras);
//			sucursalRepository.save(sucursalGodoyCruz);
			empresaRepository.save(empresaGreen);


			// Crear Unidades de medida
			UnidadMedida unidadMedidaLitros = UnidadMedida.builder().denominacion("Litros").build();
			UnidadMedida unidadMedidaGramos = UnidadMedida.builder().denominacion("Gramos").build();
			UnidadMedida unidadMedidaCantidad = UnidadMedida.builder().denominacion("Cantidad").build();
			UnidadMedida unidadMedidaPorciones = UnidadMedida.builder().denominacion("Porciones").build();
			unidadMedidaRepository.save(unidadMedidaLitros);
			unidadMedidaRepository.save(unidadMedidaGramos);
			unidadMedidaRepository.save(unidadMedidaCantidad);
			unidadMedidaRepository.save(unidadMedidaPorciones);

			// Crear Categorías de productos y subCategorías de los mismos
			Categoria categoriaBebidas = Categoria.builder().denominacion("Bebidas").build();
			categoriaRepository.save(categoriaBebidas);
			Categoria categoriaGaseosas = Categoria.builder().denominacion("Gaseosas").build();
			categoriaRepository.save(categoriaGaseosas);
			Categoria categoriaTragos = Categoria.builder().denominacion("Tragos").build();
			categoriaRepository.save(categoriaTragos);
			Categoria categoriaPizzas = Categoria.builder().denominacion("Pizzas").build();
			categoriaRepository.save(categoriaPizzas);
			Categoria categoriaInsumos = Categoria.builder().denominacion("Insumos").build();
			categoriaRepository.save(categoriaInsumos);
			categoriaBebidas.getSubCategorias().add(categoriaGaseosas);
			categoriaBebidas.getSubCategorias().add(categoriaTragos);
			categoriaRepository.save(categoriaBebidas);

			// Crear Insumos , coca cola , harina , etc
			ArticuloInsumo cocaCola = ArticuloInsumo.builder().denominacion("Coca cola").unidadMedida(unidadMedidaLitros).esParaElaborar(false).stockActual(5).stockMaximo(50).precioCompra(50.0).precioVenta(70.0).build();
			ArticuloInsumo harina = ArticuloInsumo.builder().denominacion("Harina").unidadMedida(unidadMedidaGramos).esParaElaborar(true).stockActual(4).stockMaximo(40).precioCompra(40.0).precioVenta(60.5).build();
			ArticuloInsumo queso = ArticuloInsumo.builder().denominacion("Queso").unidadMedida(unidadMedidaGramos).esParaElaborar(true).stockActual(20).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();
			ArticuloInsumo tomate = ArticuloInsumo.builder().denominacion("Tomate").unidadMedida(unidadMedidaCantidad).esParaElaborar(true).stockActual(20).stockMaximo(50).precioCompra(23.6).precioVenta(66.6).build();

			// crear fotos para cada insumo
			Imagen imagenCoca = Imagen.builder().url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg").build();
			Imagen imagenHarina = Imagen.builder().url("https://mandolina.co/wp-content/uploads/2023/03/648366622-1024x683.jpg").build();
			Imagen imagenQueso = Imagen.builder().url("https://superdepaso.com.ar/wp-content/uploads/2021/06/SANTAROSA-PATEGRAS-04.jpg").build();
			Imagen imagenTomate = Imagen.builder().url("https://thefoodtech.com/wp-content/uploads/2020/06/Componentes-de-calidad-en-el-tomate-828x548.jpg").build();
			imagenRepository.save(imagenCoca);
			imagenRepository.save(imagenHarina);
			imagenRepository.save(imagenQueso);
			imagenRepository.save(imagenTomate);

			cocaCola.getImagenes().add(imagenCoca);
			harina.getImagenes().add(imagenHarina);
			queso.getImagenes().add(imagenQueso);
			tomate.getImagenes().add(imagenTomate);
			articuloInsumoRepository.save(cocaCola);
			articuloInsumoRepository.save(harina);
			articuloInsumoRepository.save(queso);
			articuloInsumoRepository.save(tomate);

			// Crear Articulos Manufacturados
			ArticuloManufacturado pizzaMuzarella = ArticuloManufacturado.builder().denominacion("Pizza Muzarella").descripcion("Una pizza clasica").unidadMedida(unidadMedidaPorciones).precioVenta(130.0).tiempoEstimadoMinutos(15).preparacion("Esto se prepara asi").build();
			ArticuloManufacturado pizzaNapolitana = ArticuloManufacturado.builder().denominacion("Pizza Napolitana").descripcion("Una pizza clasica con tomate").unidadMedida(unidadMedidaPorciones).precioVenta(150.0).tiempoEstimadoMinutos(15).preparacion("Esto se prepara asi").build();

			// Crear fotos para los artículos manufacturados
			Imagen imagenPizzaMuzarella = Imagen.builder().url("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg").build();
			Imagen imagenPizzaNapolitana = Imagen.builder().url("https://assets.elgourmet.com/wp-content/uploads/2023/03/8metlvp345_portada-pizza-1024x686.jpg.webp").build();
			imagenRepository.save(imagenPizzaMuzarella);
			imagenRepository.save(imagenPizzaNapolitana);

			pizzaMuzarella.getImagenes().add(imagenPizzaMuzarella);
			pizzaNapolitana.getImagenes().add(imagenPizzaNapolitana);

			// Establcer las relaciones entre estos objetos.
			ArticuloManufacturadoDetalle detalleHarinaPizzaMuzarella = ArticuloManufacturadoDetalle.builder().cantidad(10d).articuloInsumo(harina).build();
			ArticuloManufacturadoDetalle detalleQuesoPizzaMuzarella = ArticuloManufacturadoDetalle.builder().cantidad(20d).articuloInsumo(queso).build();
			ArticuloManufacturadoDetalle detalleHarinaPizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(30d).articuloInsumo(harina).build();
			ArticuloManufacturadoDetalle detalleQuesoPizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(10d).articuloInsumo(queso).build();
			ArticuloManufacturadoDetalle detalleTomatePizzaNapolatina = ArticuloManufacturadoDetalle.builder().cantidad(20d).articuloInsumo(tomate).build();
			articuloManufacturadoDetalleRepository.save(detalleHarinaPizzaMuzarella);
			articuloManufacturadoDetalleRepository.save(detalleQuesoPizzaMuzarella);
			articuloManufacturadoDetalleRepository.save(detalleHarinaPizzaNapolatina);
			articuloManufacturadoDetalleRepository.save(detalleQuesoPizzaNapolatina);
			articuloManufacturadoDetalleRepository.save(detalleTomatePizzaNapolatina);
			pizzaMuzarella.getArticuloManufacturadoDetalles().add(detalleHarinaPizzaMuzarella);
			pizzaMuzarella.getArticuloManufacturadoDetalles().add(detalleQuesoPizzaMuzarella);
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleHarinaPizzaNapolatina);
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleQuesoPizzaNapolatina);
			pizzaNapolitana.getArticuloManufacturadoDetalles().add(detalleTomatePizzaNapolatina);
			articuloManufacturadoRepository.save(pizzaMuzarella);
			articuloManufacturadoRepository.save(pizzaNapolitana);

			// Establecer relaciones de las categorias
			categoriaInsumos.getArticulos().add(harina);
			categoriaInsumos.getArticulos().add(tomate);
			categoriaInsumos.getArticulos().add(queso);
			categoriaGaseosas.getArticulos().add(cocaCola);
			categoriaPizzas.getArticulos().add(pizzaMuzarella);
			categoriaPizzas.getArticulos().add(pizzaNapolitana);
			categoriaRepository.save(categoriaInsumos);
			categoriaRepository.save(categoriaGaseosas);
			categoriaRepository.save(categoriaPizzas);

			// Crear promocion para sucursal - Dia de los enamorados
			// Tener en cuenta que esa promocion es exclusivamente para una sucursal determinada d euna empresa determinada
			Promocion promocionDiaEnamorados = Promocion.builder().denominacion("Dia de los Enamorados")
					.fechaDesde(LocalDate.of(2024,2,13))
					.fechaHasta(LocalDate.of(2024,2,15))
					.horaDesde(LocalTime.of(0,0))
					.horaHasta(LocalTime.of(23,59))
					.descripcionDescuento("14 de febrero es el día de los enamorados")
					.precioPromocional(180d)
					.tipoPromocion(TipoPromocion.Promocion)
					.build();
			promocionDiaEnamorados.getArticulos().add(cocaCola);
			promocionDiaEnamorados.getArticulos().add(pizzaNapolitana);
			promocionRepository.save(promocionDiaEnamorados);

			Imagen imagenPromocionEnamorados = Imagen.builder().url("https://www.bbva.com/wp-content/uploads/2021/02/san-valentin-14-febrero-corazon-amor-bbva-recurso-1920x1280-min.jpg").build();
			imagenRepository.save(imagenPromocionEnamorados);

			promocionDiaEnamorados.getImagenes().add(imagenPromocionEnamorados);

			promocionRepository.save(promocionDiaEnamorados);

			//Agregar categorias y promociones a sucursales
			sucursalHeras.getCategorias().add(categoriaBebidas);
			sucursalHeras.getCategorias().add(categoriaPizzas);
			sucursalHeras.getPromociones().add(promocionDiaEnamorados);

			sucursalCiudad.getCategorias().add(categoriaBebidas);
			sucursalCiudad.getCategorias().add(categoriaPizzas);

			sucursalRepository.save(sucursalHeras);
			sucursalRepository.save(sucursalCiudad);

			logger.info("Sucursal Las Heras {}",sucursalHeras);
			logger.info("Sucursal Ciudad {}",sucursalCiudad);


			// agregar usuario
			Usuario usuario1 = Usuario.builder().username("pepe09").auth0Id("iVBORw0KGgoAAAANSUhEUgAAAK0AAACUCAMAAADWBFkUAAABEVBMVEX").build();
			usuarioRepository.save(usuario1);

			//Agregar imágen de cliente
			Imagen imagenUsuario = Imagen.builder().url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsa2xSPPay4GD7E3cthBMCcvPMADEjFufUWQ&s").build();
			imagenRepository.save(imagenUsuario);

			//agregar domicilios de cliente
			Domicilio domicilioCliente1 = Domicilio.builder().calle("Huarpes").numero(123).cp(5507).localidad(localidad1).build();
			Domicilio domicilioCliente2 = Domicilio.builder().calle("Garibaldi").numero(412).cp(5501).localidad(localidad2).build();
			domicilioRepository.save(domicilioCliente1);
			domicilioRepository.save(domicilioCliente2);

			// agregar factura
			Factura factura = Factura.builder().fechaFacturacion(LocalDate.of(2024, 2, 13)).formaPago(FormaPago.MercadoPago).mpMerchantOrderId(1).mpPaymentId(1).mpPaymentType("mercado pago").mpPreferenceId("0001").totalVenta(2500d).build();
			facturaRepository.save(factura);

			// agregar detalle pedido
			DetallePedido detallePedido1 = DetallePedido.builder().articulo(pizzaMuzarella).cantidad(1).subTotal(130d).build();
			DetallePedido detallePedido2 = DetallePedido.builder().articulo(cocaCola).cantidad(1).subTotal(70d).build();

			// agregar pedido
			Pedido pedido = Pedido.builder()
					.domicilio(domicilioCliente1)
					.estado(Estado.Entregado)
					.factura(factura)
					.formaPago(FormaPago.MercadoPago)
					.fechaPedido(LocalDate.of(2024, 4, 18))
					.horaEstimadaFinalizacion(LocalTime.of(12, 30))
					.sucursal(sucursalHeras)
					.tipoEnvio(TipoEnvio.Delivery)
					.total(200d)
					.totalCosto(180d)
					.build();

			pedido.getDetallePedidos().add(detallePedido1);
			pedido.getDetallePedidos().add(detallePedido2);
			pedidoRepository.save(pedido);

			// agregar cliente
			Cliente cliente1 = Cliente.builder().nombre("Pepe").email("alex@gmail.com").apellido("Gonzales").imagen(imagenUsuario).telefono("2634666666").usuario(usuario1).fechaNacimiento(LocalDate.of(1990, 12, 15)).build();
			cliente1.getDomicilios().add(domicilioCliente1);
			cliente1.getDomicilios().add(domicilioCliente2);
			cliente1.getPedidos().add(pedido);
			clienteRepository.save(cliente1);

		};
	}
}







