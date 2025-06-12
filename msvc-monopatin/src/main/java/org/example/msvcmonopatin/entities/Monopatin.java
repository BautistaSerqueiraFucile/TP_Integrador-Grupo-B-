package org.example.msvcmonopatin.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "monopatines")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Monopatin {
    @Id
    private String id; // Mongo usa String en lugar de Long para IDs
    private double kilometrosActuales;//desde el ultimo service

    private String estado;//en uso, service, libre, etc...

    @Field("ubicacion") // Mapea el campo en MongoDB
    private Ubicacion ubicacion; // Clase interna para la ubicación

    private String paradaActual;//id de la parada actual - es un string porque mongo



}
