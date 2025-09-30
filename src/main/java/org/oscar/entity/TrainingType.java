package org.oscar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oscar.enums.TypeTraining;

import java.util.List;

@Entity
@Table(name = "training_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private TypeTraining name;

    @OneToMany(mappedBy = "specialization")
    private List<Trainer> trainers;
}
