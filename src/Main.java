import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JuanBaraja baraja = new JuanBaraja();

        System.out.println("Baraja inicial (ordenada por palos y números 1-13):");
        baraja.imprimirBaraja();

        baraja.menu();
    }
}

class JuanCarta implements Comparable<JuanCarta> {

    private char palo;
    private int valor;

    public JuanCarta(char palo, int valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public char getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

    private String simboloPalo() {
        switch (palo) {
            case 'C':
                return "♥";
            case 'D':
                return "♦";
            case 'P':
                return "♠";
            case 'T':
                return "♣";
            default:
                return "?";
        }
    }

    private String figura() {
        switch (valor) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(valor);
        }
    }

    @Override
    public int compareTo(JuanCarta otra) {
        if (this.palo != otra.palo) {
            return Character.compare(this.palo, otra.palo);
        }
        return Integer.compare(this.valor, otra.valor);
    }

    @Override
    public String toString() {
        return figura() + simboloPalo();
    }
}

class JuanBaraja {

    private static final char[] PALOS = {'C', 'D', 'P', 'T'};

    private MiLista cartas;

    public JuanBaraja() {
        this.cartas = new MiLista();
        inicializar();
    }

    private void inicializar() {
        for (char palo : PALOS) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.insertTail(new JuanCarta(palo, valor));
            }
        }
    }

    public void imprimir(MiLista lista) {
        if (lista.isEmpty()) {
            System.out.println("(lista vacía)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<ListNode> it = lista.iterator();
        while (it.hasNext()) {
            ListNode nodo = it.next();
            sb.append(nodo.dato).append("  ");
        }
        System.out.println(sb.toString().trim());
    }

    public void imprimirBaraja() {
        imprimir(this.cartas);
    }

    public void revolver() {
        this.cartas.shuffle();
    }

    public MiLista ordenarPorPalo(char palo) {
        MiLista extraidas = new MiLista();
        Iterator<ListNode> it = this.cartas.iterator();
        while (it.hasNext()) {
            ListNode nodo = it.next();
            JuanCarta carta = (JuanCarta) nodo.dato;
            if (carta.getPalo() == palo) {
                extraidas.insertTail(carta);
            }
        }
        return extraidas.sortList();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        do {
            System.out.println("\n=== Gestión de Baraja de Póker ===");
            System.out.println("1. Revolver la baraja");
            System.out.println("2. Ordenar por un palo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            if (!sc.hasNextLine()) {
                break;
            }
            String entrada = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    revolver();
                    System.out.println("Baraja revuelta:");
                    imprimirBaraja();
                    break;
                case 2:
                    System.out.print("Seleccione un palo (C, D, P, T): ");
                    if (!sc.hasNextLine()) {
                        break;
                    }
                    String paloTexto = sc.nextLine().trim().toUpperCase();
                    if (paloTexto.isEmpty()) {
                        System.out.println("Palo inválido.");
                        break;
                    }
                    char palo = paloTexto.charAt(0);
                    if (palo != 'C' && palo != 'D' && palo != 'P' && palo != 'T') {
                        System.out.println("Palo inválido. Use C, D, P o T.");
                        break;
                    }
                    MiLista ordenadas = ordenarPorPalo(palo);
                    System.out.println("Cartas del palo '" + palo + "' ordenadas:");
                    imprimir(ordenadas);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
        sc.close();
    }
}
