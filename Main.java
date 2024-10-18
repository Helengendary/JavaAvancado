import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

public static void main(String[] args) throws IOException {
    
    // Pega o total de pacientes
    long odds = Files.lines(Paths.get("INFLUD21-01-05-2023.csv")).count();

    // Pega os valores da coluna
    List<String> coluna = Files.lines(Paths.get("INFLUD21-01-05-2023.csv"))
    .limit(1)
    .collect(Collectors.toList());
    
    // Pega os valores dos pacientes
    List<String> paciente = Files.lines(Paths.get("INFLUD21-01-05-2023.csv"))
    .filter(n -> n.contains("2021"))
    .limit(200000)
    .collect(Collectors.toList());
    
    // Organiza as linhas 
    String[] colunas = coluna.get(0).split(";");

    // Transforma os dados em arraysLists 
    ArrayList<String> colunasIndices = new ArrayList<>();
    ArrayList<String[]> pacientes = new ArrayList<>();
    
    for (String string : colunas) {
        colunasIndices.add(string);
    }

    for (int i = 0; i < paciente.size(); i++) {
        pacientes.add(paciente.get(i).split(";"));
    }
    
    int morreuETomou = 0;
    int soMorreu = 0;
    int classificacaoFinal = 0;
    int vacinaCovid = 0;
    int obito = 0;

    // Printa od dados
    for (int i = 0; i < colunasIndices.size(); i++) {
        if (colunasIndices.get(i).contains("CLASSI_FIN")) {
            classificacaoFinal = i;
        } else if (colunasIndices.get(i).contains("VACINA_COV")) {
            vacinaCovid = i;
        } else if (colunasIndices.get(i).contains("EVOLUCAO")) {
            obito = i;
        }
    }

    for (String[] string : pacientes) {
        int t = 0;
        boolean eraCovid = false;
        boolean morreu = false;
        boolean tomou = false;

        for (int i = 0; i < 3; i++) {
            for (String strings : string) {
                if (t == classificacaoFinal && strings.equals("5") ) {
                    eraCovid = true;
                }
                
                if (t == obito && strings.equals("2")) {
                    morreu = true;
                }
                
                if (t == vacinaCovid && !strings.equals("")) {
                    tomou = true;
                } 
                            
                t++;
            }
        }

        if (eraCovid && morreu && tomou) {
            morreuETomou++;
        } else if (eraCovid && morreu && !tomou) {
            soMorreu++;
        }
        
    }

    System.out.println("Não tem vacina: " + soMorreu);
    System.out.println("Tem vacina: " + morreuETomou);
}

    


}
