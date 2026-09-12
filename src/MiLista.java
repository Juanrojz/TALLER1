import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class MiLista implements ListInterface {
    ListNode cabeza;

    @Override
    public boolean isEmpty() {
        return this.cabeza == null;
    }

    @Override
    public int getSize() {
        int contador = 0;
        ListNode iterador = this.cabeza;
        while (iterador != null) {
            contador = contador + 1;
            iterador = iterador.siguiente;
        }
        return contador;
    }

    @Override
    public void clear() {
        this.cabeza = null;
    }

    @Override
    public Object getHead() {
        if (this.cabeza == null) {
            return null;
        }
        return this.cabeza.dato;
    }

    @Override
    public Object getTail() {
        if (this.cabeza == null) {
            return null;
        }
        ListNode iterador = this.cabeza;
        while (iterador.siguiente != null) {
            iterador = iterador.siguiente;
        }
        return iterador.dato;
    }

    @Override
    public Object get(ListNode node) {
        if (node == null) {
            return null;
        }
        return node.dato;
    }

    @Override
    public Object search(Object object) {
        ListNode iterador = this.cabeza;
        while (iterador != null) {
            if (iterador.dato != null && iterador.dato.equals(object)) {
                return iterador;
            }
            iterador = iterador.siguiente;
        }
        return null;
    }

    @Override
    public boolean add(Object object) {
        return insertTail(object);
    }

    @Override
    public boolean insert(ListNode node, Object object) {
        if (node == null) {
            return false;
        }
        try {
            ListNode nuevo = new ListNode(object);
            nuevo.siguiente = node.siguiente;
            node.siguiente = nuevo;
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrió un error");
            return false;
        }
    }

    @Override
    public boolean insert(Object ob, Object object) {
        ListNode encontrado = (ListNode) search(ob);
        if (encontrado == null) {
            return false;
        }
        return insert(encontrado, object);
    }

    @Override
    public boolean insertHead(Object object) {
        try {
            ListNode nuevaCabeza = new ListNode(object);
            nuevaCabeza.siguiente = this.cabeza;
            this.cabeza = nuevaCabeza;
            return true;
        } catch (Exception e) {
            System.out.println("Ocurrió un error");
            return false;
        }
    }

    @Override
    public boolean insertTail(Object object) {
        if (this.cabeza == null) {
            ListNode nuevaCabeza = new ListNode(object);
            this.cabeza = nuevaCabeza;
        } else {
            ListNode nuevaCola = new ListNode(object);
            ListNode iterador = this.cabeza;
            while (iterador.siguiente != null) {
                iterador = iterador.siguiente;
            }
            iterador.siguiente = nuevaCola;
        }
        return true;
    }

    @Override
    public boolean set(ListNode node, Object object) {
        if (node == null) {
            return false;
        }
        node.dato = object;
        return true;
    }

    @Override
    public boolean remove(ListNode node) {
        if (node == null || this.cabeza == null) {
            return false;
        }
        if (this.cabeza == node) {
            this.cabeza = this.cabeza.siguiente;
            return true;
        }
        ListNode iterador = this.cabeza;
        while (iterador.siguiente != null) {
            if (iterador.siguiente == node) {
                iterador.siguiente = node.siguiente;
                return true;
            }
            iterador = iterador.siguiente;
        }
        return false;
    }

    @Override
    public boolean contains(Object object) {
        return search(object) != null;
    }

    @Override
    public Iterator<ListNode> iterator() {
        return new Iterator<ListNode>() {
            private ListNode actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public ListNode next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ListNode nodoActual = actual;
                actual = actual.siguiente;
                return nodoActual;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arreglo = new Object[getSize()];
        ListNode iterador = this.cabeza;
        int i = 0;
        while (iterador != null) {
            arreglo[i] = iterador.dato;
            i++;
            iterador = iterador.siguiente;
        }
        return arreglo;
    }

    @Override
    public Object[] toArray(Object[] object) {
        int tamano = getSize();
        if (object.length < tamano) {
            object = new Object[tamano];
        }
        ListNode iterador = this.cabeza;
        int i = 0;
        while (iterador != null) {
            object[i] = iterador.dato;
            i++;
            iterador = iterador.siguiente;
        }
        if (object.length > tamano) {
            object[tamano] = null;
        }
        return object;
    }

    @Override
    public Object getBeforeTo() {
        if (this.cabeza == null || this.cabeza.siguiente == null) {
            return null;
        }
        ListNode iterador = this.cabeza;
        while (iterador.siguiente.siguiente != null) {
            iterador = iterador.siguiente;
        }
        return iterador.dato;
    }

    @Override
    public Object getBeforeTo(ListNode node) {
        if (node == null || this.cabeza == null || this.cabeza == node) {
            return null;
        }
        ListNode iterador = this.cabeza;
        while (iterador.siguiente != null) {
            if (iterador.siguiente == node) {
                return iterador.dato;
            }
            iterador = iterador.siguiente;
        }
        return null;
    }

    @Override
    public Object getNextTo() {
        if (this.cabeza == null || this.cabeza.siguiente == null) {
            return null;
        }
        return this.cabeza.siguiente.dato;
    }

    @Override
    public Object getNextTo(ListNode node) {
        if (node == null || node.siguiente == null) {
            return null;
        }
        return node.siguiente.dato;
    }

    @Override
    public MiLista subList(ListNode from, ListNode to) {
        MiLista sub = new MiLista();
        if (from == null) {
            return sub;
        }
        ListNode iterador = from;
        while (iterador != null) {
            sub.insertTail(iterador.dato);
            if (iterador == to) {
                break;
            }
            iterador = iterador.siguiente;
        }
        return sub;
    }

    @Override
    public MiLista sortList() {
        MiLista ordenada = new MiLista();
        ListNode iterador = this.cabeza;
        while (iterador != null) {
            insertarOrdenado(ordenada, iterador.dato);
            iterador = iterador.siguiente;
        }
        return ordenada;
    }

    private void insertarOrdenado(MiLista lista, Object dato) {
        if (lista.cabeza == null || compara(dato, lista.cabeza.dato) < 0) {
            ListNode nuevo = new ListNode(dato);
            nuevo.siguiente = lista.cabeza;
            lista.cabeza = nuevo;
            return;
        }
        ListNode iterador = lista.cabeza;
        while (iterador.siguiente != null && compara(dato, iterador.siguiente.dato) >= 0) {
            iterador = iterador.siguiente;
        }
        ListNode nuevo = new ListNode(dato);
        nuevo.siguiente = iterador.siguiente;
        iterador.siguiente = nuevo;
    }

    @SuppressWarnings("unchecked")
    private int compara(Object a, Object b) {
        if (a instanceof Comparable) {
            return ((Comparable<Object>) a).compareTo(b);
        }
        return 0;
    }

    public void shuffle() {
        int tamano = getSize();
        if (tamano < 2) {
            return;
        }
        Object[] datos = toArray();
        Random random = new Random();
        for (int i = datos.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Object temp = datos[i];
            datos[i] = datos[j];
            datos[j] = temp;
        }
        ListNode iterador = this.cabeza;
        int i = 0;
        while (iterador != null) {
            iterador.dato = datos[i];
            i++;
            iterador = iterador.siguiente;
        }
    }

    @Override
    public String toString() {
        return "MiLista{" +
                "cabeza=" + cabeza +
                '}';
    }
}
