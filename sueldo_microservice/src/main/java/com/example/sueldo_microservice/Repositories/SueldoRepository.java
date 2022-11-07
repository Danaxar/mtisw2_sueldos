package com.example.sueldo_microservice.Repositories;

import com.example.sueldo_microservice.Entities.Sueldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldoRepository extends JpaRepository<Sueldo, Long> {
}
