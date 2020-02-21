package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

	private List<Profesor> coleccionProfesores;

	// Constructor
	public Profesores() {
		coleccionProfesores = new ArrayList<Profesor>();
	}

	// Getters
	@Override
	public List<Profesor> get() {
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores();
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getDni));
		return profesoresOrdenados;
	}

	// Copia profunda de Profesores
	private List<Profesor> copiaProfundaProfesores() {
		List<Profesor> copiaProfesor = new ArrayList<>();
		for (Profesor profesor : coleccionProfesores) {
			copiaProfesor.add(new Profesor(profesor));
		}
		return copiaProfesor;
	}

	@Override
	public int getTamano() {
		return coleccionProfesores.size();
	}

	// Insertar profesor
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}

		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}

	}

	// Buscar profesores
	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}

		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			return null;
		} else {
			return new Profesor(coleccionProfesores.get(indice));
		}

	}

	// Borrar profesore
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}

		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		} else {
			coleccionProfesores.remove(indice);
		}
	}

}
