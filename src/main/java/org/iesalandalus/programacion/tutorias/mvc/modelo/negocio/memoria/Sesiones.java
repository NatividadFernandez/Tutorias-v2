package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ISesiones;

public class Sesiones implements ISesiones {

	private List<Sesion> coleccionSesiones;

	// Constructor
	public Sesiones() {
		coleccionSesiones = new ArrayList<Sesion>();
	}

	// Getters
	@Override
	public List<Sesion> get() {
		List<Sesion> sesionesOrdenadas = copiaProfundaSesiones();
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getDni);
		Comparator<Tutoria> comparadorTutoria = Comparator.comparing(Tutoria::getProfesor, comparadorProfesor)
				.thenComparing(Tutoria::getNombre);
		sesionesOrdenadas
				.sort(Comparator.comparing(Sesion::getTutoria, comparadorTutoria).thenComparing(Sesion::getFecha));
		return sesionesOrdenadas;
	}

	// Copia profunda sesiones
	private List<Sesion> copiaProfundaSesiones() {
		List<Sesion> copiaSesion = new ArrayList<>();
		for (Sesion sesion : coleccionSesiones) {
			copiaSesion.add(new Sesion(sesion));
		}
		return copiaSesion;
	}

	// Sesion tutoria
	@Override
	public List<Sesion> get(Tutoria tutoria) {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}

		List<Sesion> sesionesTutoria = new ArrayList<>();
		for (Sesion sesion : coleccionSesiones) {
			if (sesion.getTutoria().equals(tutoria)) {
				sesionesTutoria.add(new Sesion(sesion));
			}
		}
		sesionesTutoria.sort(Comparator.comparing(Sesion::getFecha));
		return sesionesTutoria;
	}

	@Override
	public int getTamano() {
		return coleccionSesiones.size();
	}

	// Insertar sesion
	@Override
	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}

		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			coleccionSesiones.add(new Sesion(sesion));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}

	}

	// Buscar sesiones
	@Override
	public Sesion buscar(Sesion sesion) {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}

		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			return null;
		} else {
			return new Sesion(coleccionSesiones.get(indice));
		}

	}

	// Borrar sesiones
	@Override
	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}

		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		} else {
			coleccionSesiones.remove(indice);
		}
	}

}
