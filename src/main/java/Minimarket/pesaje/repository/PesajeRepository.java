package Minimarket.pesaje.repository;

import Minimarket.pesaje.model.Pesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesajeRepository extends JpaRepository<Pesaje, Long> {
}