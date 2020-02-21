package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;

public class Alumnos implements IAlumnos {

	private List<Alumno> coleccionAlumnos;

	// Constructor
	public Alumnos() {
		coleccionAlumnos = new ArrayList<Alumno>();
	}

	// Getters
	@Override
	public List<Alumno> get() {
		List<Alumno> alumnosOrdenados = copiaProfundaAlumnos();
		alumnosOrdenados.sort(Comparator.comparing(Alumno::getCorreo));
		return alumnosOrdenados;
	}

	// Copia profunda alumnos
	private List<Alumno> copiaProfundaAlumnos() {
		List<Alumno> copiaAlumno = new ArrayList<>();
		for (Alumno alumno : coleccionAlumnos) {
			copiaAlumno.add(new Alumno(alumno));
		}
		return copiaAlumno;
	}

	@Override
	public int getTamano() {
		return coleccionAlumnos.size();
	}

	// Insertar alumno
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}

		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			coleccionAlumnos.add(new Alumno(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}

	}

	// Buscar alumnos
	@Override
	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}

		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			return null;
		} else {
			return new Alumno(coleccionAlumnos.get(indice));
		}
	}

	// Borrar alumnos
	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}

		int indice = coleccionAlumnos.indexOf(alumno);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		} else {
			coleccionAlumnos.remove(indice);
		}
	}

}
