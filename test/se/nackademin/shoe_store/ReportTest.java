package se.nackademin.shoe_store;

import org.junit.jupiter.api.Test;

class ReportTest {

    private Report report = new Report(new Repository());


    @Test
    public void printCustomerByOrderAmount() {
        report.printCustomerByOrderAmount();
    }

    @Test
    public void printCustomerByOrdersTotalSum() {
        report.printCustomerByOrdersTotalSum();
    }

    @Test
    public void printOrdersTotalSumByCity() {
        report.printOrdersTotalSumByCity();
    }

    @Test
    public void printTopFiveMostSoldShoes() {
        report.printTopFiveMostSoldShoes();
    }

}