import modelo.Estudiante;
import servicio.EstudianteService;
import util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static EstudianteService estudianteService = new EstudianteService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("SISTEMA");
        System.out.println("Iniciando aplicación...\n");

        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    listarTodosLosEstudiantes();
                    break;
                case 2:
                    buscarEstudiantePorId();
                    break;
                case 3:
                    agregarNuevoEstudiante();
                    break;
                case 4:
                    salir = true;
                    System.out.println("👋 ¡Hasta luego! Cerrando aplicación...");
                    break;
                default:
                    System.out.println(" Opción inválida. Intenta de nuevo.");
            }

            if (!salir) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }

        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MENÚ PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1. Ver todos los estudiantes");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Agregar nuevo estudiante");
        System.out.println("4. Salir");
        System.out.println("=".repeat(40));
        System.out.print("Selecciona una opción (1-4): ");
    }

    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listarTodosLosEstudiantes() {
        System.out.println("\n=== LISTA DE TODOS LOS ESTUDIANTES ===");

        List<Estudiante> estudiantes = estudianteService.listarTodos();

        if (estudiantes != null && !estudiantes.isEmpty()) {
            System.out.printf("%-5s %-20s %-25s %-5s%n", "ID", "NOMBRE", "EMAIL", "EDAD");
            System.out.println("-".repeat(60));

            for (Estudiante e : estudiantes) {
                System.out.printf("%-5d %-20s %-25s %-5d%n",
                        e.getId(), e.getNombre(), e.getEmail(), e.getEdad());
            }
        } else {
            System.out.println("No hay estudiantes registrados.");
        }
    }

    private static void buscarEstudiantePorId() {
        System.out.println("\n🔍 === BUSCAR ESTUDIANTE POR ID ===");
        System.out.print("Ingresa el ID del estudiante: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Estudiante estudiante = estudianteService.buscarPorId(id);

            if (estudiante != null) {
                System.out.println("\nEstudiante encontrado:");
                System.out.println("-".repeat(40));
                System.out.println("ID:     " + estudiante.getId());
                System.out.println("Nombre: " + estudiante.getNombre());
                System.out.println("Email:  " + estudiante.getEmail());
                System.out.println("Edad:   " + estudiante.getEdad());
            } else {
                System.out.println("No se encontró ningún estudiante con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Debe ser un número.");
        }
    }

    private static void agregarNuevoEstudiante() {
        System.out.println("\n➕ === AGREGAR NUEVO ESTUDIANTE ===");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Edad: ");

        try {
            int edad = Integer.parseInt(scanner.nextLine());

            if (nombre.isEmpty() || email.isEmpty()) {
                System.out.println("El nombre y email no pueden estar vacíos.");
                return;
            }

            Estudiante nuevoEstudiante = new Estudiante(nombre, email, edad);
            boolean exito = estudianteService.agregarEstudiante(nuevoEstudiante);

            if (exito) {
                System.out.println("¡Estudiante agregado exitosamente!");
            } else {
                System.out.println(" Error al agregar el estudiante.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Edad inválida. Debe ser un número.");
        }
    }
}