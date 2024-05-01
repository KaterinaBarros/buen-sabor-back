package com.example.buensaborback;

import com.example.buensaborback.domain.entities.*;
import com.example.buensaborback.domain.entities.enums.Estado;
import com.example.buensaborback.domain.entities.enums.FormaPago;
import com.example.buensaborback.domain.entities.enums.TipoEnvio;
import com.example.buensaborback.domain.entities.enums.TipoPromocion;
import com.example.buensaborback.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class BuenSaborBackApplication {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(BuenSaborBackApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {

            // PAIS
            Pais pais = Pais.builder()
                    .nombre("Argentina")
                    .build();

            // PROVINCIA
            Provincia provincia = Provincia.builder()
                    .nombre("Mendoza")
                    .pais(pais) // Establece la relación con el Pais
                    .build();

            // LOCALIDAD
            Localidad localidad = Localidad.builder()
                    .nombre("Capital")
                    .provincia(provincia) // Establece la relación con la Provincia
                    .build();


            localidadRepository.save(localidad); //Guarda Repositorio y como cascada Provincia y Pais


            //----------------------------------------------------------------------------------------

            // DOMICILIO
            Domicilio domicilioViamonte = Domicilio.builder()
                    .cp(5509).calle("Viamonte")
                    .numero(500).localidad(localidad)
                    .build();

            // SUCURSAL
            Sucursal sucursalChacras = Sucursal.builder()
                    .nombre("Pepe´s food - chacras")
                    .horarioApertura(LocalTime.of(17, 0))
                    .horarioCierre(LocalTime.of(23, 0))
                    .domicilio(domicilioViamonte)
                    .build();
            // EMPRESA
            Empresa empresaBrown = Empresa.builder()
                    .nombre("Pepe´s food")
                    .cuil(30503167)
                    .razonSocial("Venta de Alimentos")
                    .build();
            empresaBrown.getSucursales().add(sucursalChacras);
            domicilioViamonte.setSucursal(sucursalChacras);

            empresaRepository.save(empresaBrown); //Guarda empresa y como cascada Sucursal y Domicilio

            //----------------------------------------------------------------------------------------

            // UNIDAD DE MEDIDA
            UnidadMedida unidadMedidaLitros = UnidadMedida.builder()
                    .denominacion("Litros")
                    .build();
            unidadMedidaRepository.save(unidadMedidaLitros);

            // INSUMO
            ArticuloInsumo cocaCola = ArticuloInsumo.builder()
                    .denominacion("Coca cola")
                    .unidadMedida(unidadMedidaLitros)
                    .esParaElaborar(false)
                    .stockActual(5)
                    .stockMaximo(50)
                    .precioCompra(50.0)
                    .precioVenta(70.0)
                    .build();

            // IMAGEN
            Imagen imagenCoca = Imagen.builder().url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg").build();
            cocaCola.getImagenes().add(imagenCoca);

            // CATEGORIA
            Categoria categoriaBebidas = Categoria.builder()
                    .denominacion("Bebidas")
                    .build();

            categoriaBebidas.getArticulos().add(cocaCola);
            categoriaBebidas.getSucursales().add(sucursalChacras);
            sucursalChacras.getCategorias().add(categoriaBebidas);

            //SUBCATEGORIA
            Categoria categoriaGaseosas = Categoria.builder()
                    .denominacion("Gaseosas")
                    .build();

            categoriaBebidas.getSubCategorias().add(categoriaGaseosas);
            categoriaGaseosas.getSucursales().add(sucursalChacras);
            sucursalChacras.getCategorias().add(categoriaGaseosas);


            categoriaRepository.save(categoriaBebidas); //Guarda Categoria y como cascada guarda SubCategoria, Articulo, UnidadMedida, Imagen


            // --------------------------------------------------------------


            // MANUFACTURADO
            ArticuloManufacturadoDetalle detalleVasoGaseosa = ArticuloManufacturadoDetalle.builder()
                    .cantidad(1d)
                    .build();

            ArticuloManufacturado vasoGaseosa = ArticuloManufacturado.builder()
                    .denominacion("Vaso de Gaseosa")
                    .descripcion("Un vaso plastico chico de gaseosa")
                    .unidadMedida(unidadMedidaLitros)
                    .precioVenta(100.0)
                    .tiempoEstimadoMinutos(1)
                    .preparacion("Verter gaseosa en vaso")
                    .build();

            vasoGaseosa.getArticuloManufacturadoDetalles().add(detalleVasoGaseosa);
            vasoGaseosa.setCategoria(categoriaGaseosas);

            categoriaBebidas.getArticulos().add(cocaCola);

            detalleVasoGaseosa.setArticuloInsumo(cocaCola);

            // IMAGEN
            Imagen imagenVasoGaseosa = Imagen.builder().url("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg").build();
            vasoGaseosa.getImagenes().add(imagenVasoGaseosa);

            // PROMOCION
            Promocion promocionDeGaseosa = Promocion.builder().denominacion("Dia de las Gaseosas")
                    .fechaDesde(LocalDate.of(2024, 3, 15))
                    .fechaHasta(LocalDate.of(2024, 3, 17))
                    .horaDesde(LocalTime.of(0, 0))
                    .horaHasta(LocalTime.of(23, 59))
                    .descripcionDescuento("15 de marzo es el día de la gaseosa")
                    .precioPromocional(180d)
                    .tipoPromocion(TipoPromocion.Promocion)
                    .build();
            promocionDeGaseosa.getArticulos().add(vasoGaseosa);

            articuloManufacturadoRepository.save(vasoGaseosa); //Guarda ArticuloManufacturado y como cascada guarda ArticuloManufacturadoDetalle, Imagen, Promoción


            sucursalChacras.getCategorias().add(categoriaBebidas);
            sucursalChacras.getPromociones().add(promocionDeGaseosa);

            sucursalRepository.save(sucursalChacras); //Update de sucursal para agregar promoción y categoría bebida



            //----------------------------------------------------------------------------------------

            // USUARIO
            Usuario usuario1 = Usuario.builder()
                    .username("pepe-honguito75")
                    .auth0Id("iVBORw0KGgoAAAANSUhEUgAAAK0AAACUCAMAAADWBFkUAAABEVBMVEX")
                    .build();

            //----------------------------------------------------------------------------------------

            // IMAGEN CLIENTE
            Imagen imagenCliente = Imagen.builder()
                    .url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsa2xSPPay4GD7E3cthBMCcvPMADEjFufUWQ&s")
                    .build();


            // DOMICILIO CLIENTE
            Domicilio domicilioCliente1 = Domicilio.builder()
                    .calle("Sarmiento")
                    .numero(123)
                    .cp(5507)
                    .localidad(localidad)
                    .build();

            //----------------------------------------------------------------------------------------

            // CLIENTE
            Cliente cliente1 = Cliente.builder()
                    .nombre("Vanina")
                    .email("vani3@gmail.com")
                    .apellido("Luna")
                    .imagen(imagenCliente)
                    .telefono("2614523698")
                    .usuario(usuario1)
                    .fechaNacimiento(LocalDate.of(1991, 8, 15))
                    .build();

            cliente1.getDomicilios().add(domicilioCliente1);

            imagenCliente.setCliente(cliente1);

            clienteRepository.save(cliente1); //Guardar cliente y como cascada Guarda Imagen y Usuario

            //----------------------------------------------------------------------------------------

            // FACTURA
            // agrega en cascada
            Factura factura = Factura.builder()
                    .fechaFacturacion(LocalDate.of(2024, 2, 13))
                    .formaPago(FormaPago.MercadoPago)
                    .mpMerchantOrderId(1)
                    .mpPaymentId(1)
                    .mpPaymentType("mercado pago")
                    .mpPreferenceId("0001")
                    .totalVenta(2500d)
                    .build();

            // DETALLE PEDIDO
            // agrega en cascada
            DetallePedido detallePedido1 = DetallePedido.builder()
                    .articulo(vasoGaseosa)
                    .cantidad(1)
                    .subTotal(130d)
                    .build();
            DetallePedido detallePedido2 = DetallePedido.builder()
                    .articulo(cocaCola)
                    .cantidad(1)
                    .subTotal(70d)
                    .build();

            // PEIDIDO
            Pedido pedido = Pedido.builder()
                    .domicilio(domicilioCliente1)
                    .estado(Estado.Entregado)
                    .factura(factura)
                    .formaPago(FormaPago.MercadoPago)
                    .fechaPedido(LocalDate.of(2024, 4, 18))
                    .horaEstimadaFinalizacion(LocalTime.of(12, 30))
                    .sucursal(sucursalChacras)
                    .tipoEnvio(TipoEnvio.Delivery)
                    .total(200d)
                    .totalCosto(180d)
                    .build();

            pedido.getDetallePedidos().add(detallePedido1);
            pedido.getDetallePedidos().add(detallePedido2);
            factura.setPedido(pedido);

            pedidoRepository.save(pedido); //Guardar pedido

            cliente1.getPedidos().add(pedido);

            clienteRepository.save(cliente1); //Update para agregar el pedido


        };
    }
}







