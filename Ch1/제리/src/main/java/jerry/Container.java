package jerry;

import java.util.Iterator;
import java.util.Set;

public class Container {

	private double amount;
	ContainerBox containerBox = new ContainerBox();

	public Container() {
		this.amount = 0;
		containerBox.addContainer(this);
	}

	public void connectTo(Container other) {

		other.getContainerBox().addContainerBox(this.containerBox);
		containerBox.addContainer(other);
		containerBox.setContainerList(other.getContainerBox().getContainerSet());
		other.getContainerBox().setContainerList(this.containerBox.getContainerSet());

		double sharedAmount = getSharedAmount(containerBox);

		setSameWaterLevel(sharedAmount);

	}

	private void setSameWaterLevel(double sharedAmount) {
		for (Container tmp : containerBox.getContainerSet()) {
			tmp.addWater(sharedAmount - tmp.getAmount());
		}
	}

	private double getSharedAmount(ContainerBox containerBox) {
		Set<Container> containerSet = containerBox.getContainerSet();
		Iterator<Container> containerIterator = containerSet.iterator();
		int total = 0;
		double size = containerSet.size();
		while (containerIterator.hasNext()) {
			total += containerIterator.next().getAmount();
		}
		return total / size;
	}

	public ContainerBox getContainerBox() {
		return containerBox;
	}

	public void addWater(double amount) {
		this.amount += amount;
	}

	public double getAmount() {
		return amount;
	}

}
