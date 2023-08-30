package com.hemodialBackend.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "code")
        })

public class Caisse extends AbstractEntity{
	
	@Column
	private String code;
	
	@Column
	private String label;

	@OneToMany(mappedBy = "caisse")
	@JsonIgnore
	private List<Patient> patient;

}

