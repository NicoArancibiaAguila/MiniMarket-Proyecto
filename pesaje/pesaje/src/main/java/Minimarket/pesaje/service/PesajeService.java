package Minimarket.pesaje.service;

import Minimarket.pesaje.model.Pesaje;
import Minimarket.pesaje.repository.PesajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PesajeService {

    @Autowired
    private PesajeRepository pesajeRepository;

    public Pesaje guardarPesaje(Pesaje pesaje) {
        return pesajeRepository.save(pesaje);
    }

    public List<Pesaje> obtenerTodos() {
        return pesajeRepository.findAll();
    }
}