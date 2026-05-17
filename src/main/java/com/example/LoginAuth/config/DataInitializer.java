package com.example.loginauth.config;

import com.example.loginauth.model.Rol;
import com.example.loginauth.model.Usuario;
import com.example.loginauth.repository.RolRepository;
import com.example.loginauth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            // crear Roles si no existen en la base de datos
            Rol rolAdmin = crearRolSiNoExiste(rolRepository, "ADMIN");
            Rol rolSupervisor = crearRolSiNoExiste(rolRepository, "SUPERVISOR");
            Rol rolCajero = crearRolSiNoExiste(rolRepository, "CAJERO");
            Rol rolPanadero = crearRolSiNoExiste(rolRepository, "PANADERO");

            // si la tabla de usuarios esta vacia se crea a todo el equipo
            if (usuarioRepository.count() == 0) {
                System.out.println("Ingresando a la base de datos con usuarios iniciales...");

                // Admin, dios mismo
                crearUsuario(usuarioRepository, passwordEncoder, "admin", "admin123", rolAdmin, "Hernan Saavedra", "15234678-5");

                // Supervisores, angeles
                crearUsuario(usuarioRepository, passwordEncoder, "super1", "super123", rolSupervisor, "Romina Sanchez", "17345567-2");
                crearUsuario(usuarioRepository, passwordEncoder, "super2", "super123", rolSupervisor, "Dennis Fraser", "18236567-4");

                // Cajeros, simples mortales
                crearUsuario(usuarioRepository, passwordEncoder, "cajero1", "cajero123", rolCajero, "Benjamin Gonzalez", "19567789-3");
                crearUsuario(usuarioRepository, passwordEncoder, "cajero2", "cajero123", rolCajero, "Nicolas Arancibia", "19234456-2");

                // Panaderos, mortales de elite, indispensables
                crearUsuario(usuarioRepository, passwordEncoder, "panadero1", "panadero123", rolPanadero, "Juan Perez", "12323434-5");
                crearUsuario(usuarioRepository, passwordEncoder, "panadero2", "panadero123", rolPanadero, "Cristiano Ronaldo", "14234566-3");
                crearUsuario(usuarioRepository, passwordEncoder, "panadero3", "panadero123", rolPanadero, "Leo Messi", "16237485-2");
                crearUsuario(usuarioRepository, passwordEncoder, "panadero4", "panadero123", rolPanadero, "Alexis Sanchez", "17234567-4");

                System.out.println("Los 9 usuarios iniciales fueron creados con exito");
            }
        };
    }

    // metodos aux para no repetir código 

    private Rol crearRolSiNoExiste(RolRepository repository, String nombreRol) {
        return repository.findByNombreRol(nombreRol).orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol(nombreRol);
            return repository.save(nuevoRol);
        });
    }

    private void crearUsuario(UsuarioRepository repo, PasswordEncoder encoder, String username, String password, Rol rol, String nombre, String rut) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password)); // ¡Aquí se encripta!
        usuario.setRol(rol);
        usuario.setNombre(nombre); 
        usuario.setRut(rut);
        repo.save(usuario);
    }
}
