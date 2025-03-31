import java.io.*;
import java.util.*;

// Clase Paciente que implementa Comparable
class Paciente implements Comparable<Paciente> {
    String nombre;
    String sintoma;
    char prioridad;

    public Paciente(String nombre, String sintoma, char prioridad) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        return Character.compare(this.prioridad, otro.prioridad);
    }

    @Override
    public String toString() {
        return nombre + ", " + sintoma + ", " + prioridad;
    }
}

// Implementación de VectorHeap (MinHeap)
class VectorHeap<E extends Comparable<E>> {
    private List<E> heap;

    public VectorHeap() {
        heap = new ArrayList<>();
    }

    public void add(E item) {
        heap.add(item);
        int index = heap.size() - 1;
        while (index > 0 && heap.get(index).compareTo(heap.get((index - 1) / 2)) < 0) {
            Collections.swap(heap, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public E remove() {
        if (heap.isEmpty()) return null;
        E min = heap.get(0);
        heap.set(0, heap.remove(heap.size() - 1));
        heapify(0);
        return min;
    }

    private void heapify(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0) {
            smallest = left;
        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            Collections.swap(heap, index, smallest);
            heapify(smallest);
        }
    }
}

// Clase de pruebas unitarias para VectorHeap
class TestVectorHeap {
    public static void main(String[] args) {
        VectorHeap<Paciente> heap = new VectorHeap<>();
        heap.add(new Paciente("Maria Ramirez", "apendicitis", 'A'));
        heap.add(new Paciente("Juan Perez", "fractura de pierna", 'C'));
        heap.add(new Paciente("Carmen Sarmientos", "dolores de parto", 'B'));
        heap.add(new Paciente("Lorenzo Toledo", "chikunguya", 'E'));

        System.out.println("Orden esperado: A, B, C, E");
        while (true) {
            Paciente p = heap.remove();
            if (p == null) break;
            System.out.println("Atendiendo a: " + p);
        }
    }
}

// Clase principal con PriorityQueue de Java Collection Framework
public class Hospital
 {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        PriorityQueue<Paciente> colaPacientes = new PriorityQueue<>();

        // Leer el archivo de pacientes
        try (BufferedReader br = new BufferedReader(new FileReader("pacientes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(", ");
                colaPacientes.add(new Paciente(datos[0], datos[1], datos[2].charAt(0)));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        // Atender pacientes en orden de prioridad
        while (true) {
            System.out.println("Presione Enter para atender al siguiente paciente...");
            new Scanner(System.in).nextLine();
            Paciente atendido = colaPacientes.poll();
            if (atendido == null) break;
            System.out.println("Atendiendo a: " + atendido);
        }
    }
}
