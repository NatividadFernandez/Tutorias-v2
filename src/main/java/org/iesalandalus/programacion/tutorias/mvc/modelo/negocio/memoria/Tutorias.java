package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;

public class Tutorias implements ITutorias {

	private List<Tutoria> coleccionTutorias;

	// Constructor
	public Tutorias() {
		coleccionTutorias = new ArrayList<Tutoria>();
	}

	// Getters
	@Override
	public List<Tutoria> get() {
		List<Tutoria> tutoriasOrdenadas = copiaProfundaTutorias();
		Comparator<Profesor> comprobadorProfesores = Comparator.comparing(Profesor::getDni);
		tutoriasOrdenadas.sort(
				Comparator.comparing(Tutoria::getProfesor, comprobadorProfesores).thenComparing(Tutoria::getNombre));
		return tutoriasOrdenadas;
	}

	// Copia profunda tutorias
	private List<Tutoria> copiaProfundaTutorias() {
		List<Tutoria> copiaTutoria = new ArrayList<>();
		for (Tutoria tutoria : coleccionTutorias) {
			copiaTutoria.add(new Tutoria(tutoria));
		}
		return copiaTutoria;
	}

	// Tutoria profesor
	@Override
	public List<Tutoria> get(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Tutoria> tutoriasProfesor = new ArrayList<>();
		for (Tutoria tutoria : coleccionTutorias) {
			if (tutoria.getProfesor().equals(profesor)) {
				tutoriasProfesor.add(new Tutoria(tutoria));
			}
		}
		tutoriasProfesor.sort(Comparator.comparing(Tutoria::getNombre));
		return tutoriasProfesor;
	}

	@Override
	public int getTamano() {
		return coleccionTutorias.size();
	}

	// Insertar tutoria
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {

		if (tutoria == null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}

		int indice = coleccionTutorias.indexOf(tutoria);

		if (indice == -1) {
			coleccionTutorias.add(new Tutoria(tutoria));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}

	}

	// Buscar tutorias
	@Override
	public Tutoria buscar(Tutoria tutoria) {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}

		int indice = coleccionTutorias.indexOf(tutoria);
		if (indice == -1) {
			return null;
		} else {
			return new Tutoria(coleccionTutorias.get(indice));
		}

	}

	// Borrar tutorias
	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}

		int indice = coleccionTutorias.indexOf(tutoria);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		} else {
			coleccionTutorias.remove(indice);
		}
	}

}
