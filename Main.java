import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

public static void main(String[] args) throws IOException {

    // Pega os valores da coluna
    List<String> coluna = Files.lines(Paths.get("INFLUD21-01-05-2023.csv"))
    .limit(1)
    .collect(Collectors.toList());
    
    // Pega os valores dos pacientes
    List<String> paciente = Files.lines(Paths.get("INFLUD21-01-05-2023.csv"))
    .filter(n -> n.contains("2021"))
    .limit(5)
    .collect(Collectors.toList());
    
    // Pega o total de pacientes
    long odds = Files.lines(Paths.get("INFLUD21-01-05-2023.csv")).count();
    
    // Organiza as linhas 
    String[] colunas = coluna.get(0).split(";");
    String[] pacientesFiles = paciente.get(0).split(";");
    
    // Transforma os dados em arraysLists 
    ArrayList<String> colunasIndices = new ArrayList<>();
    ArrayList<ArrayList<String>> pacientes = new ArrayList<>();
    ArrayList<String> pacientesIndices = new ArrayList<>();
    
    for (String string : colunas) {
        colunasIndices.add(string);
    }
    
    int indice = 1;
    for (String string : pacientesFiles) {
        
        pacientesIndices.add(string);
        indice++;
        
        if ( indice % 165 == 0) {
            pacientes.add(pacientesIndices);
            pacientesIndices = new ArrayList<>();
        }
        
    }
    
    System.out.println(pacientes.size());
    
    // Printa od dados
    for (int i = 0; i < colunasIndices.size(); i++) {
        if (colunasIndices.get(i).contains("VACINA")) {
            for (int j = 0; j < pacientes.size(); j++) {
                System.out.println(pacientes.get(j).get(i-1));
            }
            i = colunasIndices.size();
        }
    }

}
}