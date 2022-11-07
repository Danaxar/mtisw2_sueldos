package com.example.sueldo_microservice.Repositories;

import com.example.sueldo_microservice.Entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {
    @Query("select e from EmpleadoEntity e where e.rut = :rut")
    EmpleadoEntity findByRut(@Param("rut") String rut);

    @Query(value = "select * from empleado", nativeQuery = true)
    List<EmpleadoEntity> findAll();
}
