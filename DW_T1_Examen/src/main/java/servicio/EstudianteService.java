package servicio;

import modelo.Estudiante;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class EstudianteService {

    public List<Estudiante> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Estudiante> estudiantes = null;

        try {
            estudiantes = session.createQuery("FROM Estudiante", Estudiante.class).list();
            System.out.println("Se encontraron " + estudiantes.size() + " estudiantes");
        } catch (Exception e) {
            System.err.println(" Error al listar estudiantes: " + e.getMessage());
        } finally {
            session.close();
        }

        return estudiantes;
    }

    public Estudiante buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Estudiante estudiante = null;

        try {
            estudiante = session.get(Estudiante.class, id);
            if (estudiante != null) {
                System.out.println("Estudiante encontrado: " + estudiante.getNombre());
            } else {
                System.out.println("No se encontró estudiante con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println(" Error al buscar estudiante: " + e.getMessage());
        } finally {
            session.close();
        }

        return estudiante;
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean exito = false;

        try {
            transaction = session.beginTransaction();
            session.save(estudiante);
            transaction.commit();
            exito = true;
            System.out.println(" Estudiante agregado: " + estudiante.getNombre());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        } finally {
            session.close();
        }

        return exito;
    }
}