package com.example.buensaborback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    protected String denominacion;
    protected Double precioVenta;

    @OneToMany
    @JoinColumn(name= "Articulo_ID")
    @Builder.Default
    protected Set<Imagen> imagenes = new HashSet<Imagen>();

    @ManyToOne
    protected UnidadMedida unidadMedida;

    @ManyToMany(mappedBy = "articulos")
    @Builder.Default
    protected Set<Promocion> estaEnPromociones = new HashSet<>();

}
