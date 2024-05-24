import java.io.*;
public class Calculation {

    private final double[][] chart = new double[151][3];
    public Calculation() {
        readFile();
    }

    public double HoldingCost(double unitCost, double interestRate) {
        return unitCost * interestRate;
    }

    public double AnnualDemand(double leadTimeDemand, double leadTime) {
        return leadTimeDemand / (leadTime / 12);
    }

    public double UnitOrderedPerReplenishment(double k, double annualDemand, double holdingCost) {
        return Math.sqrt((2 * k * annualDemand) / holdingCost);
    }

    public double UnitOrderedPerReplenishmentNew(double k, double annualDemand, double holdingCost,
                                                 double penaltyCost, double nR) {
        return Math.sqrt((2 * annualDemand * (k + (penaltyCost * nR))) / holdingCost);
    }
    public double Zformula(double Q, double holdingCost, double annualDemand, double penaltyCost) {
        double Fr = fR(Q, holdingCost, annualDemand, penaltyCost);
        double index = chart[0][0];
        double min = Math.abs(chart[0][1] - Fr);
        for (int i=0; i< chart.length; i++) {
            if (Math.abs(chart[i][1] - Fr)<= min) {
                index = chart[i][0];
                min = Math.abs(chart[i][1] - Fr);
            }
        }
        return index;
    }

    public double fR(double Q, double holdingCost, double annualDemand, double penaltyCost){
        return 1 - (Q * holdingCost) / (penaltyCost * annualDemand);
    }

    public double Rformula(double leadTimeDemand, double leadTimeStandardDeviation, double Z) {
        return leadTimeDemand + (Z * leadTimeStandardDeviation);
    }

    public double Nformula(double leadTimeStandardDeviation, double Z)// N formula
    {
        double lZ = chart[(int)(75 + 100 * (Z/4))][2];

        return leadTimeStandardDeviation * lZ;
    }

    private void readFile() {
        String fileName = "dataFile.txt";

        // Dosya InputStream olarak açılıyor
        InputStream inputStream = Calculation.class.getResourceAsStream(fileName);

        if (inputStream == null) {
            System.out.println("Dosya bulunamadı: " + fileName);
            return;
        }

        // BufferedReader kullanarak dosyayı okuma
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = -1;
            while ((line = reader.readLine()) != null) {
                if (i == -1){
                    i +=1;
                    continue;
                }
                chart[i][0] = Double.parseDouble(line.split(",")[0]);
                chart[i][1] = Double.parseDouble(line.split(",")[1]);
                chart[i][2] = Double.parseDouble(line.split(",")[2]);
                i +=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}