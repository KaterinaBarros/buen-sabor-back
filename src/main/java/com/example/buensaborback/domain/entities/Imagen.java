package com.example.buensaborback.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Imagen extends Base{

    private String url;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
