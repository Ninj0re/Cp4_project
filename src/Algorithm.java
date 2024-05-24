import java.util.Scanner;

public class Algorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculation c = new Calculation();

        System.out.println("If decimal values throws an error try to write it with comma (,)");
        System.out.println("Please enter the values");
        System.out.print("Unit Cost: ");
        double unitCost = sc.nextDouble();
        System.out.print("Ordering Cost: ");
        double orderingCost = sc.nextDouble();
        System.out.print("Penalty Cost: ");
        double penaltyCost = sc.nextDouble();
        System.out.print("Interest Rate: ");
        double interestRate = sc.nextDouble();

        System.out.print("Lead Time (month): ");
        double leadTime = sc.nextDouble();
        System.out.print("Lead Time Demand: ");
        double leadTimeDemand = sc.nextDouble();
        System.out.print("Lead Time Standard Deviation: ");
        double leadTimeStandardDeviation = sc.nextDouble();

        double holdingCost = c.HoldingCost(unitCost, interestRate);
        double annualDemand = c.AnnualDemand(leadTimeDemand, leadTime);

        double qFirst = c.UnitOrderedPerReplenishment(orderingCost, annualDemand, holdingCost);
        double qSecond = qFirst;

        double R;

        int iteration = 0;
        do {
            qFirst = qSecond;
            double Z = c.Zformula(qSecond, holdingCost, annualDemand, penaltyCost);
            R = c.Rformula(leadTimeDemand, leadTimeStandardDeviation, Z);
            double nR = c.Nformula(leadTimeStandardDeviation, Z);
            qSecond = c.UnitOrderedPerReplenishmentNew(orderingCost, annualDemand, holdingCost, penaltyCost, nR);
            iteration++;

        } while (qSecond != qFirst);

        System.out.println("Q = " + qFirst);
        System.out.println("R = " + R);
        System.out.println(
                "Number of iterations the software run to obtain the optimal lot size and reorder point: "
                        + iteration);
    }
}
