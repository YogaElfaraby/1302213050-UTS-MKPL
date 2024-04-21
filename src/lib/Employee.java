package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private boolean isMale; // true = Laki-laki, false = Perempuan

	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private spouse spouse;
    private List<Child> children;

	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean isMale) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.isMale = isMale;
		
		children = new LinkedList<>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	// Set gaji bulanan berdasarkan grade
    public void setMonthlySalary(int grade) {
        calculateBaseMonthlySalary(grade);
        applyForeignerBonus();
    }

    // Hitung gaji bulanan dasar
    private void calculateBaseMonthlySalary(int grade) {
        switch (grade) {
            case 1:
                monthlySalary = 3000000;
                break;
            case 2:
                monthlySalary = 5000000;
                break;
            case 3:
                monthlySalary = 7000000;
                break;
            default:
                throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }

    // Terapkan bonus untuk warga negara asing
    private void applyForeignerBonus() {
        if (isForeigner) {
            monthlySalary *= 1.5;
        }
    }
	
	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouse = new spouse(spouseName, spouseIdNumber);
    }
	
	public void addChild(String childName, String childIdNumber) {
		children.add(new Child(childName, childIdNumber));
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		}else {
			monthWorkingInYear = 12;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouse.hasNoSpouse(), children.size());
    }
}
