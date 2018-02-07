package com.reyhalconmotors;

/**
 * Created by Amit Yadav on 2/1/2018.
 */

public class AddEmployeeDetailsHelper {
    public String nameOfEmployee, contactNumberOfEmployee, salaryOfEmployee, designationOfEmployee, inTimeOfEmployee,accountNumberOfEmployee,tokenOfEmployee;

    public AddEmployeeDetailsHelper() {
    }

    public String getTokenOfEmployee() {
        return tokenOfEmployee;
    }

    public void setTokenOfEmployee(String tokenOfEmployee) {
        this.tokenOfEmployee = tokenOfEmployee;
    }

    public AddEmployeeDetailsHelper(String nameOfEmployee, String contactNumberOfEmployee, String salaryOfEmployee, String designationOfEmployee, String inTimeOfEmployee, String accountNumberOfEmployee, String tokenOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
        this.contactNumberOfEmployee = contactNumberOfEmployee;
        this.salaryOfEmployee = salaryOfEmployee;
        this.designationOfEmployee = designationOfEmployee;
        this.inTimeOfEmployee = inTimeOfEmployee;

        this.accountNumberOfEmployee = accountNumberOfEmployee;

        this.tokenOfEmployee = tokenOfEmployee;
    }

    public AddEmployeeDetailsHelper(String nameOfEmployee, String contactNumberOfEmployee, String salaryOfEmployee, String designationOfEmployee, String inTimeOfEmployee, String accountNumberOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
        this.contactNumberOfEmployee = contactNumberOfEmployee;
        this.salaryOfEmployee = salaryOfEmployee;
        this.designationOfEmployee = designationOfEmployee;
        this.inTimeOfEmployee = inTimeOfEmployee;
        this.accountNumberOfEmployee = accountNumberOfEmployee;
    }

    public void setNameOfEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public void setContactNumberOfEmployee(String contactNumberOfEmployee) {
        this.contactNumberOfEmployee = contactNumberOfEmployee;
    }

    public void setSalaryOfEmployee(String salaryOfEmployee) {
        this.salaryOfEmployee = salaryOfEmployee;
    }

    public void setDesignationOfEmployee(String designationOfEmployee) {
        this.designationOfEmployee = designationOfEmployee;
    }

    public void setInTimeOfEmployee(String inTimeOfEmployee) {
        this.inTimeOfEmployee = inTimeOfEmployee;
    }

    public void setAccountNumberOfEmployee(String accountNumberOfEmployee) {
        this.accountNumberOfEmployee = accountNumberOfEmployee;
    }


    public String getNameOfEmployee() {
        return nameOfEmployee;
    }

    public String getContactNumberOfEmployee() {
        return contactNumberOfEmployee;
    }

    public String getSalaryOfEmployee() {
        return salaryOfEmployee;
    }

    public String getDesignationOfEmployee() {
        return designationOfEmployee;
    }

    public String getInTimeOfEmployee() {
        return inTimeOfEmployee;
    }

    public String getAccountNumberOfEmployee() {
        return accountNumberOfEmployee;
    }

}
