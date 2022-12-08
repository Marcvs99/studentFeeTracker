package javaFX;

public class Student 
{
	private String name;
	private String address;
	private double amountPaid;
	private double amountDue;
	
	Student(String n, String a, double p, double d)
	{
		name = n;
		address = a;
		amountPaid =p;
		amountDue = d;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
	
}
