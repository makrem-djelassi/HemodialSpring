package com.hemodialBackend.models;

import java.util.List;

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

	@OneToOne
	private Patient patient;
}

