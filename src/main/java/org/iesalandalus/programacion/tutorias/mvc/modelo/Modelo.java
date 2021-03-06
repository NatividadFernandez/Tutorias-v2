package org.iesalandalus.programacion.tutorias.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.*;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria.Alumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria.Citas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria.Profesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria.Sesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria.Tutorias;

public class Modelo implements IModelo {

	private IAlumnos alumnos;
	private ICitas citas;
	private IProfesores profesores;
	private ISesiones sesiones;
	private ITutorias tutorias;

	public Modelo(IFuenteDatos fuenteDatos) {
		alumnos = fuenteDatos.crearAlumnos();
		profesores = fuenteDatos.crearProfesores();
		tutorias = fuenteDatos.crearTutorias();
		sesiones = fuenteDatos.crearSesiones();
		citas = fuenteDatos.crearCitas();
	}

	// Alumno
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.insertar(alumno);
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		return alumnos.buscar(alumno);
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		List<Cita> citasAlumno = citas.get(alumno);
		for (Cita cita : citasAlumno) {
			citas.borrar(cita);
		}
		alumnos.borrar(alumno);
	}

	@Override
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}

	// Profesor
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}

	@Override
	public Profesor buscar(Profesor profesor) {
		return profesores.buscar(profesor);
	}

	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		List<Tutoria> citasProfesor = tutorias.get(profesor);
		for (Tutoria tutoria : citasProfesor) {
			borrar(tutoria);
		}
		profesores.borrar(profesor);
	}

	@Override
	public List<Profesor> getProfesores() {
		return profesores.get();
	}

	// Tutoría
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}

		Profesor profesor = profesores.buscar(tutoria.getProfesor());
		if (profesor == null) {
			throw new OperationNotSupportedException("ERROR: No existe el profesor de esta tutoría.");
		}
		tutorias.insertar(new Tutoria(profesor, tutoria.getNombre()));
	}

	@Override
	public Tutoria buscar(Tutoria tutoria) {
		return tutorias.buscar(tutoria);
	}

	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		List<Sesion> sesionesTutoria = sesiones.get(tutoria);
		for (Sesion sesion : sesionesTutoria) {
			borrar(sesion);
		}
		tutorias.borrar(tutoria);
	}

	@Override
	public List<Tutoria> getTutorias() {
		return tutorias.get();
	}

	@Override
	public List<Tutoria> getTutorias(Profesor profesor) {
		return tutorias.get(profesor);
	}

	// Sesion
	@Override
	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}

		Tutoria tutoria = tutorias.buscar(sesion.getTutoria());
		if (tutoria == null) {
			throw new OperationNotSupportedException("ERROR: No existe la tutoría de esta sesión.");
		}

		sesiones.insertar(new Sesion(tutoria, sesion.getFecha(), sesion.getHoraInicio(), sesion.getHoraFin(),
				sesion.getMinutosDuracion()));
	}

	@Override
	public Sesion buscar(Sesion sesion) {
		return sesiones.buscar(sesion);
	}

	@Override
	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		List<Cita> citasSesion = citas.get(sesion);
		for (Cita cita : citasSesion) {
			citas.borrar(cita);
		}
		sesiones.borrar(sesion);
	}

	@Override
	public List<Sesion> getSesiones() {
		return sesiones.get();
	}

	@Override
	public List<Sesion> getSesiones(Tutoria tutoria) {
		return sesiones.get(tutoria);
	}

	// Cita
	@Override
	public void insertar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		Alumno alumno = alumnos.buscar(cita.getAlumno());
		if (alumno == null) {
			throw new OperationNotSupportedException("ERROR: No existe el alumno de esta cita.");
		}

		Sesion sesion = sesiones.buscar(cita.getSesion());
		if (sesion == null) {
			throw new OperationNotSupportedException("ERROR: No existe la sesión de esta cita.");
		}

		citas.insertar(new Cita(alumno, sesion, cita.getHora()));
	}

	@Override
	public Cita buscar(Cita cita) {
		return citas.buscar(cita);
	}

	@Override
	public void borrar(Cita cita) throws OperationNotSupportedException {
		citas.borrar(cita);
	}

	@Override
	public List<Cita> getCitas() {
		return citas.get();
	}

	@Override
	public List<Cita> getCitas(Sesion sesion) {
		return citas.get(sesion);
	}

	@Override
	public List<Cita> getCitas(Alumno alumno) {
		return citas.get(alumno);
	}

}
