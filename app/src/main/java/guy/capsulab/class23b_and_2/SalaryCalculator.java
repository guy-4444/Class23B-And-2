package guy.capsulab.class23b_and_2;

import java.text.DecimalFormat;

public class SalaryCalculator {

    private final static double ELIGIBLE_INCOME_CEILING = 8700.0;
    private final static double AVERAGE_SALARY = 10551.0;
    private final static double AVERAGE_SALARY_60 = AVERAGE_SALARY * 0.6;
    private final static double EDUCATION_FUND_LIMIT = 15712.0;
    private final static double REFUND_CREDITS_POINT = 218.0;
    private final static double MAX_INCOME_TO_LIABLE_FOR_INSURANCE_PREMIUMS = 44020.0;


    private static int GROSS_SALARY = 35000;
    private static int BASE_FROM_SALARY = 35000;
    private final static double CREDITS_POINTS = 2.25;
    private static double EDUCATION_FUND_BASE = EDUCATION_FUND_LIMIT;  //  0 - No education fund, 16000 is the max - if you don't know put gross salary.
    private final static double LOSS_OF_ABILITY_TO_WORK_PERCENT = 1.0 / 100.0;  //  0 - No
    private final static double COMPENSATION_PART = 100.0 / 12.0; // 6.0 // 6.0 is the base -> some employers gives 8.333333 (100/12)
    private final static double EXTRA_REDUCES = 0; // e.g. meals


    public static void start(int gross) {
        GROSS_SALARY = gross;
        BASE_FROM_SALARY = GROSS_SALARY;
        EDUCATION_FUND_BASE = Math.min(GROSS_SALARY, EDUCATION_FUND_LIMIT);

        double EducationFundTaxFree = getEducationFund(EDUCATION_FUND_BASE, true);
        double EducationFundAll = getEducationFund(EDUCATION_FUND_BASE, false);
        double EducationFundToTax = (EducationFundAll > EducationFundTaxFree) ? (EducationFundAll - EducationFundTaxFree) * 3 : 0;


        double SocialSecurity = getSocialSecurity(GROSS_SALARY + EducationFundToTax, false);
        double HealthTax = getHealthTax(GROSS_SALARY + EducationFundToTax, false);
        double PensionFund = getPensionFund(BASE_FROM_SALARY);


        double SocialSecretions = EducationFundAll + PensionFund;


        double EmployerSocialSecuritySecretions = getEmployerSocialSecuritySecretions(GROSS_SALARY, false);
        double EmployerEducationFundSecretions = EducationFundAll * 3;
        double EmployerProvidentFundSecretions = PensionFund * (6.5 / 6.0);
        double EmployerCompensationSecretions = PensionFund * (COMPENSATION_PART / 6.0);
        double EmployerLossOfAbilityToWorkSecretions = BASE_FROM_SALARY * LOSS_OF_ABILITY_TO_WORK_PERCENT;


        double TaxWithoutCreditsPointsReduces = calculateIncomeTaxWithCreditsPoints(GROSS_SALARY + EXTRA_REDUCES + EducationFundToTax, 0);
        double CreditsPointsReduces = REFUND_CREDITS_POINT * CREDITS_POINTS;
        double PensionFundTaxReturn = PensionFundTaxReturn(PensionFund);
        double NEW_TAX = Math.max(0, TaxWithoutCreditsPointsReduces - CreditsPointsReduces - PensionFundTaxReturn);


        double TotalMandatoryDeductions = SocialSecurity + HealthTax + NEW_TAX;

        print("GROSS_SALARY", GROSS_SALARY);
        print("BASE_FROM_SALARY", BASE_FROM_SALARY);
        print("CREDITS_POINTS", CREDITS_POINTS);
        print("EDUCATION_FUND_BASE", EDUCATION_FUND_BASE);
        print("COMPENSATION_PART", COMPENSATION_PART);
        print("EXTRA_REDUCES", EXTRA_REDUCES);
        System.out.println("- - - - - - - - - - - -");
        print("TaxWithoutCreditsPointsReduces", TaxWithoutCreditsPointsReduces);
        print("CreditsPointsReduces", CreditsPointsReduces);
        print("PensionFundTaxReturn", PensionFundTaxReturn);
        print("SocialSecurity", SocialSecurity);
        print("HealthTax", HealthTax);
        print("PensionFund", PensionFund);
        print("EducationFundTaxFree", EducationFundTaxFree);
        print("EducationFundAll", EducationFundAll);
        print("EducationFundToTax", EducationFundToTax);
        print("NEW_TAX", NEW_TAX);
        print("YEARLY_TaxWithoutCreditsPointsReduces", TaxWithoutCreditsPointsReduces * 12.0);
        print("YEARLY_NEW_TAX", NEW_TAX * 12.0);
        System.out.println("- - - - - - - - - - - -");
        print("EmployerSocialSecuritySecretions", EmployerSocialSecuritySecretions);
        print("EmployerEducationFundSecretions", EmployerEducationFundSecretions);
        print("EmployerProvidentFundSecretions", EmployerProvidentFundSecretions);
        print("EmployerCompensationSecretions", EmployerCompensationSecretions);
        print("EmployerLossOfAbilityToWorkSecretions", EmployerLossOfAbilityToWorkSecretions);
        double EmployerSecretions = EmployerSocialSecuritySecretions + EmployerEducationFundSecretions + EmployerProvidentFundSecretions + EmployerCompensationSecretions + EmployerLossOfAbilityToWorkSecretions;
        print("EmployerSecretions", EmployerSecretions);
        System.out.println("- - - - - - - - - - - -");

        double NET = GROSS_SALARY - NEW_TAX - SocialSecurity - HealthTax - PensionFund - EducationFundAll;
        System.out.println("GROSS_SALARY (BASE_FROM_SALARY)= " + GROSS_SALARY + " (" + BASE_FROM_SALARY + ")");
        print("SalaryValueForEmployer", GROSS_SALARY + EmployerSecretions);
        print("TotalMandatoryDeductions", TotalMandatoryDeductions);
        print("SocialSecretions", SocialSecretions);
        print("NET", NET);
    }


    private static void print(String title, double amount) {
        DecimalFormat df = new DecimalFormat("##.##");
        System.out.println(title + "= " + df.format(amount));
    }

    private static double getEmployerSocialSecuritySecretions(double salary, boolean under18) {
        if (under18)
            return 0;

        double rest = salary;
        double tax = Math.min(rest, AVERAGE_SALARY_60) * 0.0355;
        rest -= AVERAGE_SALARY_60;
        if (rest > 0) {
            tax += Math.min(rest, MAX_INCOME_TO_LIABLE_FOR_INSURANCE_PREMIUMS) * 0.076;
        }
        return tax;
    }

    private static double getSocialSecurity(double salary, boolean under18) {
        if (under18)
            return 0;


        double rest = salary;
        double tax = Math.min(rest, AVERAGE_SALARY_60) * 0.004;
        rest -= AVERAGE_SALARY_60;
        if (rest > 0) {
            tax += Math.min(rest, MAX_INCOME_TO_LIABLE_FOR_INSURANCE_PREMIUMS) * 0.07;
        }
        return tax;
    }

    private static double getHealthTax(double salary, boolean under18) {
        if (under18)
            return 0;

        double rest = salary;
        double tax = Math.min(rest, AVERAGE_SALARY_60) * 0.031;
        rest -= AVERAGE_SALARY_60;
        if (rest > 0) {
            tax += Math.min(rest, MAX_INCOME_TO_LIABLE_FOR_INSURANCE_PREMIUMS) * 0.05;
        }
        return tax;
    }

    private static double getPensionFund(double salary) {
        return salary * 0.06;
    }

    private static double getEducationFund(double amount, boolean limitToMax) {
        double salaryToCalculate = limitToMax ? Math.min(amount, EDUCATION_FUND_LIMIT) : amount;
        return salaryToCalculate * 0.025;
    }

    private static double PensionFundTaxReturn(double PensionFund) {
        return Math.min(ELIGIBLE_INCOME_CEILING * 0.07, PensionFund) * 0.35;
    }

    private static double calculateIncomeTaxWithCreditsPoints(double grossSalary, double creditsPoints) {
        double[] steps = {0.0, 6290.0, 9030.0, 14490.0, 20140.0, 41910.0, 53970.0};
        double[] prc = {10.0, 14.0, 20.0, 31.0, 35.0, 47.0, 50.0d};

        double rest = grossSalary;
        double tax = 0.0d;

        for (int i = steps.length - 1; i >= 0; i--) {
            double step = steps[i];
            if (rest > step) {
                double t = (rest - step) * (prc[i] / 100.0d);
                tax += t;
                rest = step;
            }
        }

        return tax - Math.min(tax, REFUND_CREDITS_POINT * creditsPoints);
    }
}