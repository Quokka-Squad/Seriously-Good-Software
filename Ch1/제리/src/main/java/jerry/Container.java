package jerry;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Container {

	private double amount;
	private final Set<Container> containerSet;

	public Container() {
		this.amount = 0;
		containerSet = new HashSet<>();
		containerSet.add(this);
	}

	public void connectTo(Container other) {
		this.containerSet.addAll(other.containerSet);

		Iterator<Container> iterator = containerSet.iterator();
		while (iterator.hasNext()) {
			iterator.next().containerSet.addAll(this.containerSet);
		}

		double totalAmount = containerSet.stream().mapToDouble(container -> container.amount).sum();

		double average = totalAmount / containerSet.size();
		iterator = containerSet.iterator();
		while (iterator.hasNext()) {
			iterator.next().amount = average;
		}

	}


	public void addWater(double amount) {
		Iterator<Container> iterator = containerSet.iterator();

		double divideAmount = amount / containerSet.size();

		while (iterator.hasNext()) {
			iterator.next().amount += divideAmount;
		}
	}

	public double getAmount() {
		return amount;
	}

}
