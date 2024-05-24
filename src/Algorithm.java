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
        double nR;

        int iteration = 0;
        do {
            qFirst = qSecond;
            double Z = c.Zformula(qSecond, holdingCost, annualDemand, penaltyCost);
            R = c.Rformula(leadTimeDemand, leadTimeStandardDeviation, Z);
            nR = c.Nformula(leadTimeStandardDeviation, Z);
            qSecond = c.UnitOrderedPerReplenishmentNew(orderingCost, annualDemand, holdingCost, penaltyCost, nR);
            iteration++;

        } while (qSecond != qFirst);

        System.out.println("Q = " + Math.round(qFirst));
        System.out.println("R = " + Math.round(R));
        System.out.println(
                "Number of iterations the software run to obtain the optimal lot size and reorder point: "
                        + iteration);

        double safetyStock = Math.round(Math.round(R) - leadTimeDemand);
        System.out.println("The safety stock: " + safetyStock);

        double averageAnnualHoldingCost = holdingCost
                * (Math.round(qFirst / 2) + Math.round(R) - leadTimeDemand);

        double AverageSetUpCost = (orderingCost * annualDemand) / Math.round(qFirst);

        double AveragePenaltyCost = (unitCost * annualDemand * nR / Math.round(qFirst));

        System.out.println("Average annual holding cost: " + averageAnnualHoldingCost);
        System.out.println("Average setup cost: " + AverageSetUpCost);
        System.out.println("Average penalty costs are: " + AveragePenaltyCost);


        double averageTimeBetweenOrders = qFirst / annualDemand;

        System.out.println(
                "Average time between the placement of orders: " + (averageTimeBetweenOrders)
                        + " months");

        double poc = 1 - c.fR(qFirst, holdingCost, annualDemand, penaltyCost); // The proportion of order cycles in which no stock out occurs

        System.out.println(
                "The proportion of order cycles in which no stock out occurs: " + poc
                        + "%");

        double pod = nR / qFirst; // The proportion of demands that are not met

        System.out.println("The proportion of demands that are not met: " + (pod));
    }
}
