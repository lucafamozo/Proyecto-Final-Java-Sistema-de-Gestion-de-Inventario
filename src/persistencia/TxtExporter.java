/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.Producto;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Luca
 */
public class TxtExporter {

    public void exportar(List<? extends Producto> lista, String encabezado, String ruta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            // Encabezado
            bw.write("================================================");
            bw.newLine();
            bw.write("  " + encabezado);
            bw.newLine();
            bw.write("  Fecha de exportación: " + LocalDate.now());
            bw.newLine();
            bw.write("  Total de productos: " + lista.size());
            bw.newLine();
            bw.write("================================================");
            bw.newLine();
            bw.newLine();

            for (Producto p : lista) {
                bw.write(p.toString());
                bw.newLine();
                bw.write("  Precio final: $" + p.calcularPrecioFinal());
                bw.newLine();
                bw.write("------------------------------------------------");
                bw.newLine();
            }
            System.out.println("TXT exportado en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al exportar TXT: " + e.getMessage());
        }
    }
}